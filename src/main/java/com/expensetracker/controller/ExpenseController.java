package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // CREATE - Add a new expense
    @PostMapping("/expenses")
    public ResponseEntity<Map<String, Object>> addExpense(@RequestBody Map<String, Object> request) {
        try {
            Expense expense = new Expense();
            expense.setCategory((String) request.get("category"));
            expense.setAmount(Double.parseDouble(request.get("amount").toString()));
            
            String dateStr = (String) request.get("date");
            if (dateStr != null && !dateStr.isEmpty()) {
                expense.setDate(LocalDate.parse(dateStr));
            } else {
                expense.setDate(LocalDate.now());
            }
            
            String description = (String) request.get("description");
            expense.setDescription(description != null ? description : "");
            
            Expense saved = expenseService.addExpense(expense);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Expense added successfully");
            response.put("expense", saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error adding expense: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // READ - Get all expenses (sorted by date, most recent first)
    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    // READ - Get expense by ID
    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        if (expense != null) {
            return ResponseEntity.ok(expense);
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE - Update an expense
    @PutMapping("/expenses/{id}")
    public ResponseEntity<Map<String, Object>> updateExpense(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            Expense expense = new Expense();
            expense.setCategory((String) request.get("category"));
            expense.setAmount(Double.parseDouble(request.get("amount").toString()));
            
            String dateStr = (String) request.get("date");
            if (dateStr != null && !dateStr.isEmpty()) {
                expense.setDate(LocalDate.parse(dateStr));
            } else {
                expense.setDate(LocalDate.now());
            }
            
            String description = (String) request.get("description");
            expense.setDescription(description != null ? description : "");
            
            Expense updated = expenseService.updateExpense(id, expense);
            if (updated != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Expense updated successfully");
                response.put("expense", updated);
                return ResponseEntity.ok(response);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Expense not found");
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error updating expense: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // DELETE - Delete an expense
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Map<String, Object>> deleteExpense(@PathVariable Long id) {
        boolean deleted = expenseService.deleteExpense(id);
        Map<String, Object> response = new HashMap<>();
        if (deleted) {
            response.put("success", true);
            response.put("message", "Expense deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Expense not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    // Get statistics
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", expenseService.getTotalExpense());
        stats.put("byCategory", expenseService.getTotalByCategory());
        stats.put("highest", expenseService.getHighestCategory());
        stats.put("lowest", expenseService.getLowestCategory());
        return ResponseEntity.ok(stats);
    }

    // Get categories
    @GetMapping("/categories")
    public ResponseEntity<Set<String>> getCategories() {
        return ResponseEntity.ok(expenseService.getCategories());
    }
}

