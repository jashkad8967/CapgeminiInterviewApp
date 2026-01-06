import java.util.*;
import java.util.stream.Collectors;

public class ExpenseCalculator {
    
    public static double getTotalExpense(List<Expense> expenses) {
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
    
    public static Map<String, Double> getTotalByCategory(List<Expense> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }
    
    public static List<Map<String, Object>> getExpenseTrend(List<Expense> expenses) {
        Map<String, Double> monthlyTrend = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getDate().substring(0, 7), // YYYY-MM
                        Collectors.summingDouble(Expense::getAmount)
                ));
        
        return monthlyTrend.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("month", entry.getKey());
                    item.put("total", entry.getValue());
                    return item;
                })
                .collect(Collectors.toList());
    }
    
    public static Map<String, Object> getHighestCategory(List<Expense> expenses) {
        Map<String, Double> byCategory = getTotalByCategory(expenses);
        
        if (byCategory.isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("category", "N/A");
            result.put("amount", 0.0);
            return result;
        }
        
        Map.Entry<String, Double> highest = byCategory.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        
        Map<String, Object> result = new HashMap<>();
        result.put("category", highest.getKey());
        result.put("amount", highest.getValue());
        return result;
    }
    
    public static Map<String, Object> getLowestCategory(List<Expense> expenses) {
        Map<String, Double> byCategory = getTotalByCategory(expenses);
        
        if (byCategory.isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("category", "N/A");
            result.put("amount", 0.0);
            return result;
        }
        
        Map.Entry<String, Double> lowest = byCategory.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);
        
        Map<String, Object> result = new HashMap<>();
        result.put("category", lowest.getKey());
        result.put("amount", lowest.getValue());
        return result;
    }
}

