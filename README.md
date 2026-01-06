# Expense Tracker - Spring Boot Application

Simple expense tracking application built with Spring Boot.

## Features

- Add expenses with category, amount, date, and description
- View all expenses in a table
- Edit existing expenses
- Delete expenses
- View statistics: total expense, expenses by category, highest/lowest category

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## How to Run

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

### Access
Open browser: `http://localhost:8080`

## API Endpoints

- `POST /api/expenses` - Add expense
- `GET /api/expenses` - Get all expenses
- `GET /api/expenses/{id}` - Get expense by ID
- `PUT /api/expenses/{id}` - Update expense
- `DELETE /api/expenses/{id}` - Delete expense
- `GET /api/stats` - Get statistics

## Categories

- Food
- Transport
- Entertainment
- Utilities
- Rent
- Other

## Data Storage

- In-memory storage (no database)
- Seed data loaded from `data/expenses.csv` on startup
- Data is lost when server stops

## Project Structure

```
src/main/java/com/expensetracker/
├── ExpenseTrackerApplication.java
├── controller/ExpenseController.java
├── service/ExpenseService.java
└── model/Expense.java

src/main/resources/
└── static/index.html

data/
└── expenses.csv
```

## Testing

1. Start the application
2. Open `http://localhost:8080`
3. Add an expense using the form
4. View expenses in the table
5. Click "Refresh" in Statistics section
6. Test edit and delete functionality

See `PRESENTATION.md` for detailed testing instructions.
