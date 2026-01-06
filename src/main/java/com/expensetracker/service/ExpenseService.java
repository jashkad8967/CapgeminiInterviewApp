package com.expensetracker.service;

import com.expensetracker.model.Expense;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    private final List<Expense> expenses = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final Set<String> categories = new LinkedHashSet<>(Arrays.asList(
        "Food", "Transport", "Entertainment", "Utilities", "Rent", "Other"
    ));

    public ExpenseService() {
        loadSeedData();
    }

    // CRUD Operations
    public Expense addExpense(Expense expense) {
        expense.setId(idGenerator.getAndIncrement());
        expenses.add(expense);
        categories.add(expense.getCategory());
        return expense;
    }

    public List<Expense> getAllExpenses() {
        return expenses.stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .collect(Collectors.toList());
    }

    public Expense getExpenseById(Long id) {
        return expenses.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        Expense expense = getExpenseById(id);
        if (expense != null) {
            expense.setCategory(updatedExpense.getCategory());
            expense.setAmount(updatedExpense.getAmount());
            expense.setDate(updatedExpense.getDate());
            expense.setDescription(updatedExpense.getDescription());
            categories.add(updatedExpense.getCategory());
            return expense;
        }
        return null;
    }

    public boolean deleteExpense(Long id) {
        return expenses.removeIf(e -> e.getId().equals(id));
    }

    // Statistics
    public Double getTotalExpense() {
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Map<String, Double> getTotalByCategory() {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }


    public Map<String, Object> getHighestCategory() {
        Map<String, Double> byCategory = getTotalByCategory();
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

    public Map<String, Object> getLowestCategory() {
        Map<String, Double> byCategory = getTotalByCategory();
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

    public Set<String> getCategories() {
        return new LinkedHashSet<>(categories);
    }

    private void loadSeedData() {
        try {
            java.io.File seedFile = new java.io.File("data/expenses.csv");
            if (seedFile.exists()) {
                try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(seedFile))) {
                    String line;
                    boolean firstLine = true;
                    while ((line = br.readLine()) != null) {
                        if (firstLine) {
                            firstLine = false;
                            continue;
                        }
                        String[] parts = line.split(",");
                        if (parts.length >= 3) {
                            try {
                                String category = parts[0].trim();
                                Double amount = Double.parseDouble(parts[1].trim());
                                LocalDate date = LocalDate.parse(parts[2].trim());
                                String description = parts.length >= 4 ? parts[3].trim() : "";
                                Expense expense = new Expense(category, amount, date, description);
                                expense.setId(idGenerator.getAndIncrement());
                                expenses.add(expense);
                                categories.add(category);
                            } catch (Exception e) {
                                // Skip invalid lines
                            }
                        }
                    }
                    System.out.println("Loaded " + expenses.size() + " expenses from seed data");
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load seed data: " + e.getMessage());
        }
    }
}

