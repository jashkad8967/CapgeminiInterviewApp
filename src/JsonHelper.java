import java.util.*;

public class JsonHelper {
    
    public static String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    public static String buildStatsJson(List<Expense> expenses) {
        double total = ExpenseCalculator.getTotalExpense(expenses);
        Map<String, Double> byCategory = ExpenseCalculator.getTotalByCategory(expenses);
        Map<String, Object> highest = ExpenseCalculator.getHighestCategory(expenses);
        Map<String, Object> lowest = ExpenseCalculator.getLowestCategory(expenses);
        List<Map<String, Object>> trend = ExpenseCalculator.getExpenseTrend(expenses);
        
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"total\": ").append(total).append(",\n");
        json.append("  \"byCategory\": {\n");
        boolean first = true;
        for (Map.Entry<String, Double> entry : byCategory.entrySet()) {
            if (!first) json.append(",\n");
            json.append("    \"").append(escapeJson(entry.getKey())).append("\": ").append(entry.getValue());
            first = false;
        }
        json.append("\n  },\n");
        json.append("  \"highest\": {\"category\": \"").append(escapeJson((String)highest.get("category")))
             .append("\", \"amount\": ").append(highest.get("amount")).append("},\n");
        json.append("  \"lowest\": {\"category\": \"").append(escapeJson((String)lowest.get("category")))
             .append("\", \"amount\": ").append(lowest.get("amount")).append("},\n");
        json.append("  \"trend\": [\n");
        first = true;
        for (Map<String, Object> item : trend) {
            if (!first) json.append(",\n");
            json.append("    {\"month\": \"").append(escapeJson((String)item.get("month")))
                 .append("\", \"total\": ").append(item.get("total")).append("}");
            first = false;
        }
        json.append("\n  ]\n");
        json.append("}");
        return json.toString();
    }
    
    public static String buildExpensesJson(List<Expense> expenses) {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        boolean first = true;
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            if (!first) json.append(",\n");
            json.append("  {\n");
            json.append("    \"id\": ").append(i).append(",\n");
            json.append("    \"category\": \"").append(escapeJson(expense.getCategory())).append("\",\n");
            json.append("    \"amount\": ").append(expense.getAmount()).append(",\n");
            json.append("    \"date\": \"").append(escapeJson(expense.getDate())).append("\"\n");
            json.append("    \"description\": \"").append(escapeJson(expense.getDescription())).append("\"\n");
            json.append("  }");
            first = false;
        }
        json.append("\n]");
        return json.toString();
    }
    
    public static String buildCategoriesJson(java.util.Set<String> categories) {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        boolean first = true;
        for (String category : categories) {
            if (!first) json.append(",\n");
            json.append("  \"").append(escapeJson(category)).append("\"");
            first = false;
        }
        json.append("\n]");
        return json.toString();
    }
}

