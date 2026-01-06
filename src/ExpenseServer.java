import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ExpenseServer {
    static List<Expense> expenses = new ArrayList<>();
    static Set<String> categories = new LinkedHashSet<>();

    public static void main(String[] args) throws Exception {
        // Preset categories
        categories.add("Food");
        categories.add("Transport");
        categories.add("Entertainment");
        categories.add("Shopping");
        categories.add("Bills");
        categories.add("Healthcare");
        categories.add("Education");
        categories.add("Travel");
        categories.add("Other");

        // Load seed data
        DataHelper.loadSeedData(expenses, categories);
        DataHelper.extractCategories(expenses, categories);

        // Start server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", ExpenseServer::handleUI);
        server.createContext("/add", ExpenseServer::handleAdd);
        server.createContext("/delete", ExpenseServer::handleDelete);
        server.createContext("/stats", ExpenseServer::handleStats);
        server.createContext("/expenses", ExpenseServer::handleExpenses);
        server.createContext("/categories", ExpenseServer::handleCategories);
        server.setExecutor(null);
        server.start();
        
        System.out.println("Server started at http://localhost:8080");
    }

    static void handleUI(HttpExchange exchange) throws IOException {
        File file = new File("public/index.html");
        if (file.exists()) {
            byte[] response = new FileInputStream(file).readAllBytes();
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
        } else {
            String error = "404 - Not Found";
            exchange.sendResponseHeaders(404, error.length());
            exchange.getResponseBody().write(error.getBytes());
        }
        exchange.close();
    }

    static void handleAdd(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, 0);
            exchange.close();
            return;
        }

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String body = br.readLine();
        Map<String, String> params = DataHelper.parseBody(body);
        
        String category = params.getOrDefault("category", "").trim();
        String amountStr = params.getOrDefault("amount", "").trim();
        String date = params.getOrDefault("date", "").trim();

        String response;
        int statusCode;

        if (category.isEmpty() || amountStr.isEmpty() || date.isEmpty()) {
            response = "{\"success\": false, \"message\": \"All fields are required\"}";
            statusCode = 400;
        } else {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount < 0) {
                    response = "{\"success\": false, \"message\": \"Amount must be positive\"}";
                    statusCode = 400;
                } else {
                    expenses.add(new Expense(category, amount, date));
                    categories.add(category);
                    response = "{\"success\": true, \"message\": \"Expense added successfully\"}";
                    statusCode = 200;
                }
            } catch (NumberFormatException e) {
                response = "{\"success\": false, \"message\": \"Invalid amount format\"}";
                statusCode = 400;
            }
        }

        sendJsonResponse(exchange, response, statusCode);
    }

    static void handleDelete(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, 0);
            exchange.close();
            return;
        }

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String body = br.readLine();
        Map<String, String> params = DataHelper.parseBody(body);
        String idStr = params.getOrDefault("id", "").trim();

        String response;
        int statusCode;

        if (idStr.isEmpty()) {
            response = "{\"success\": false, \"message\": \"Expense ID is required\"}";
            statusCode = 400;
        } else {
            try {
                int id = Integer.parseInt(idStr);
                if (id >= 0 && id < expenses.size()) {
                    expenses.remove(id);
                    DataHelper.extractCategories(expenses, categories);
                    response = "{\"success\": true, \"message\": \"Expense deleted successfully\"}";
                    statusCode = 200;
                } else {
                    response = "{\"success\": false, \"message\": \"Invalid expense ID\"}";
                    statusCode = 400;
                }
            } catch (NumberFormatException e) {
                response = "{\"success\": false, \"message\": \"Invalid ID format\"}";
                statusCode = 400;
            }
        }

        sendJsonResponse(exchange, response, statusCode);
    }

    static void handleStats(HttpExchange exchange) throws IOException {
        String response = JsonHelper.buildStatsJson(expenses);
        sendJsonResponse(exchange, response, 200);
    }

    static void handleExpenses(HttpExchange exchange) throws IOException {
        String response = JsonHelper.buildExpensesJson(expenses);
        sendJsonResponse(exchange, response, 200);
    }

    static void handleCategories(HttpExchange exchange) throws IOException {
        String response = JsonHelper.buildCategoriesJson(categories);
        sendJsonResponse(exchange, response, 200);
    }

    static void sendJsonResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(statusCode, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}
