import java.io.*;
import java.util.*;

public class DataHelper {
    
    public static void loadSeedData(List<Expense> expenses, Set<String> categories) {
        File seedFile = new File("data/expenses.csv");
        if (seedFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(seedFile))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String category = parts[0].trim();
                        double amount = Double.parseDouble(parts[1].trim());
                        String date = parts[2].trim();
                        expenses.add(new Expense(category, amount, date));
                        categories.add(category);
                    }
                }
                System.out.println("Loaded " + expenses.size() + " expenses from seed data");
            } catch (IOException e) {
                System.out.println("Could not load seed data: " + e.getMessage());
            }
        }
    }
    
    public static void extractCategories(List<Expense> expenses, Set<String> categories) {
        for (Expense expense : expenses) {
            categories.add(expense.getCategory());
        }
    }
    
    public static Map<String, String> parseBody(String body) {
        Map<String, String> map = new HashMap<>();
        if (body == null || body.isEmpty()) return map;
        
        try {
            String[] pairs = body.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    String key = java.net.URLDecoder.decode(kv[0], java.nio.charset.StandardCharsets.UTF_8);
                    String value = java.net.URLDecoder.decode(kv[1], java.nio.charset.StandardCharsets.UTF_8);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            // Simple fallback
        }
        return map;
    }
}

