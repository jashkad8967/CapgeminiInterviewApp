# Expense Tracker Application - Presentation

## Approach and Design

### Overview
Simple expense tracking application built with Spring Boot. The application allows users to track expenses by category, view statistics, and manage expense records.

### Design Philosophy
- **Simplicity**: Minimal code, straightforward implementation
- **In-Memory Storage**: No database required - data stored in ArrayList
- **REST API**: Clean API endpoints for CRUD operations
- **Basic UI**: Simple HTML interface without complex styling

### Architecture
```
Client (Browser)
    ↓
Spring Boot Application
    ↓
Controller Layer (REST API)
    ↓
Service Layer (Business Logic)
    ↓
In-Memory Data Storage (ArrayList)
```

### Key Components
1. **Expense Model**: Simple POJO with id, category, amount, date, description
2. **ExpenseService**: Handles all business logic and data operations
3. **ExpenseController**: REST API endpoints for client communication
4. **HTML UI**: Basic form-based interface

## Key Files and Folders

### Project Structure
```
expense-tracker/
├── src/main/java/com/expensetracker/
│   ├── ExpenseTrackerApplication.java    # Main Spring Boot app
│   ├── controller/
│   │   └── ExpenseController.java       # REST API endpoints
│   ├── service/
│   │   └── ExpenseService.java          # Business logic
│   └── model/
│       └── Expense.java                 # Expense entity
├── src/main/resources/
│   ├── static/
│   │   └── index.html                   # Web UI
│   └── application.properties           # Configuration
├── data/
│   └── expenses.csv                     # Seed data
├── pom.xml                              # Maven dependencies
└── README.md                             # Documentation
```

### Key Files Description

**ExpenseTrackerApplication.java**
- Main Spring Boot application class
- Entry point that starts the server

**ExpenseController.java**
- REST API endpoints:
  - `POST /api/expenses` - Add expense
  - `GET /api/expenses` - Get all expenses
  - `GET /api/expenses/{id}` - Get expense by ID
  - `PUT /api/expenses/{id}` - Update expense
  - `DELETE /api/expenses/{id}` - Delete expense
  - `GET /api/stats` - Get statistics

**ExpenseService.java**
- Core business logic
- CRUD operations for expenses
- Statistics calculations (total, by category, highest/lowest)
- Seed data loading

**Expense.java**
- Data model with fields: id, category, amount, date, description

**index.html**
- Simple HTML form for adding expenses
- Table displaying all expenses
- Statistics display
- Edit and delete functionality

**expenses.csv**
- Seed data file with sample expenses
- Format: Category,Amount,Date,Description

## Process to Run, Test, and Verify

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Step 1: Build the Application
```bash
mvn clean install
```

### Step 2: Run the Application
```bash
mvn spring-boot:run
```

Or run the JAR file:
```bash
java -jar target/expense-tracker-1.0.0.jar
```

### Step 3: Access the Application
Open browser and navigate to:
```
http://localhost:8080
```

### Step 4: Test the Application

#### Test Adding an Expense
1. Fill in the form:
   - Select a category (e.g., Food)
   - Enter amount (e.g., 45.50)
   - Select a date
   - Optionally add description
2. Click "Add Expense"
3. Verify the expense appears in the table

#### Test Viewing Statistics
1. Click "Refresh" button in Statistics section
2. Verify:
   - Total expense is displayed
   - Highest and lowest categories are shown
   - Breakdown by category is listed

#### Test Editing an Expense
1. Click "Edit" button next to any expense
2. Modify the fields
3. Click "Save"
4. Verify changes are reflected

#### Test Deleting an Expense
1. Click "Delete" button next to any expense
2. Confirm deletion
3. Verify expense is removed from list
4. Verify statistics update

#### Test API Endpoints (Optional)
Using curl or Postman:

**Add Expense:**
```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{"category":"Food","amount":45.50,"date":"2024-01-15","description":"Lunch"}'
```

**Get All Expenses:**
```bash
curl http://localhost:8080/api/expenses
```

**Get Statistics:**
```bash
curl http://localhost:8080/api/stats
```

**Update Expense:**
```bash
curl -X PUT http://localhost:8080/api/expenses/1 \
  -H "Content-Type: application/json" \
  -d '{"category":"Transport","amount":30.00,"date":"2024-01-16","description":"Bus fare"}'
```

**Delete Expense:**
```bash
curl -X DELETE http://localhost:8080/api/expenses/1
```

### Verification Checklist
- [ ] Application starts without errors
- [ ] Homepage loads correctly
- [ ] Can add new expenses
- [ ] Expenses appear in the table
- [ ] Statistics display correctly
- [ ] Can edit expenses
- [ ] Can delete expenses
- [ ] Statistics update after add/edit/delete
- [ ] Seed data loads on startup

## Test Data and Seed Data

### Seed Data File
Location: `data/expenses.csv`

Format:
```
Category,Amount,Date,Description
Food,45.50,2024-01-15,Lunch at restaurant
Transport,25.00,2024-01-15,Bus ticket
Entertainment,60.00,2024-01-16,Movie tickets
```

### Sample Test Data
The seed data file contains sample expenses across different categories:
- Food expenses
- Transport expenses
- Entertainment expenses
- Utilities expenses
- Rent expenses
- Other expenses

### Loading Seed Data
- Seed data is automatically loaded when the application starts
- Data is read from `data/expenses.csv` file
- If file doesn't exist, application starts with empty expense list
- Seed data is parsed and added to in-memory storage

### Testing with Custom Data
1. Edit `data/expenses.csv` file
2. Add your test data following the format
3. Restart the application
4. Verify your test data appears in the expense list

### Test Scenarios

**Scenario 1: Empty State**
- Delete or rename `data/expenses.csv`
- Start application
- Verify empty expense list
- Add a new expense
- Verify it appears

**Scenario 2: Multiple Categories**
- Add expenses in different categories
- Verify statistics show all categories
- Verify highest/lowest category calculation

**Scenario 3: CRUD Operations**
- Create: Add new expense
- Read: View expense in table
- Update: Edit expense details
- Delete: Remove expense

**Scenario 4: Statistics Accuracy**
- Add multiple expenses
- Manually calculate total
- Verify displayed total matches
- Verify category breakdown is correct

## Summary

This is a simple, functional expense tracker built in one day. It demonstrates:
- Basic Spring Boot REST API
- Simple CRUD operations
- In-memory data storage
- Basic HTML/JavaScript frontend
- Statistics calculations

The application is easy to understand, modify, and extend.

