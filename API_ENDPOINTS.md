# API Endpoints Reference

## Base URL
```
http://localhost:8080/api
```

## Expense Management Endpoints

### 1. Add Expense (CREATE)
**Endpoint:** `POST /api/expenses`

**Request:**
```json
{
  "category": "Food",
  "amount": 45.50,
  "date": "2024-01-15"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Expense added successfully",
  "expense": {
    "id": 1,
    "category": "Food",
    "amount": 45.50,
    "date": "2024-01-15"
  }
}
```

---

### 2. Get All Expenses (READ)
**Endpoint:** `GET /api/expenses`

**Response:** Returns array of expenses sorted by date (most recent first)
```json
[
  {
    "id": 1,
    "category": "Food",
    "amount": 45.50,
    "date": "2024-01-15"
  },
  {
    "id": 2,
    "category": "Transport",
    "amount": 25.00,
    "date": "2024-01-14"
  }
]
```

---

### 3. Get Expense by ID (READ)
**Endpoint:** `GET /api/expenses/{id}`

**Example:** `GET /api/expenses/1`

**Response:**
```json
{
  "id": 1,
  "category": "Food",
  "amount": 45.50,
  "date": "2024-01-15"
}
```

**Error Response (404):**
```
Not Found
```

---

### 4. Update Expense (UPDATE)
**Endpoint:** `PUT /api/expenses/{id}`

**Example:** `PUT /api/expenses/1`

**Request:**
```json
{
  "category": "Transport",
  "amount": 30.00,
  "date": "2024-01-16"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Expense updated successfully",
  "expense": {
    "id": 1,
    "category": "Transport",
    "amount": 30.00,
    "date": "2024-01-16"
  }
}
```

**Error Response (404):**
```json
{
  "success": false,
  "message": "Expense not found"
}
```

---

### 5. Delete Expense (DELETE)
**Endpoint:** `DELETE /api/expenses/{id}`

**Example:** `DELETE /api/expenses/1`

**Response:**
```json
{
  "success": true,
  "message": "Expense deleted successfully"
}
```

**Error Response (404):**
```json
{
  "success": false,
  "message": "Expense not found"
}
```

---

## Statistics Endpoints

### 6. Get Statistics
**Endpoint:** `GET /api/stats`

**Response:**
```json
{
  "total": 1234.56,
  "byCategory": {
    "Food": 450.50,
    "Transport": 200.00,
    "Entertainment": 300.00,
    "Utilities": 150.00,
    "Rent": 100.00,
    "Other": 34.06
  },
  "highest": {
    "category": "Food",
    "amount": 450.50
  },
  "lowest": {
    "category": "Other",
    "amount": 34.06
  },
  "trend": [
    {
      "month": "2024-01",
      "total": 600.00
    },
    {
      "month": "2024-02",
      "total": 634.56
    }
  ]
}
```

---

### 7. Get Categories
**Endpoint:** `GET /api/categories`

**Response:**
```json
["Food", "Transport", "Entertainment", "Utilities", "Rent", "Other"]
```

---

## Supported Categories

- **Food**
- **Transport**
- **Entertainment**
- **Utilities**
- **Rent**
- **Other**

---

## Example cURL Commands

### Add Expense
```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{"category":"Food","amount":45.50,"date":"2024-01-15"}'
```

### Get All Expenses
```bash
curl http://localhost:8080/api/expenses
```

### Get Expense by ID
```bash
curl http://localhost:8080/api/expenses/1
```

### Update Expense
```bash
curl -X PUT http://localhost:8080/api/expenses/1 \
  -H "Content-Type: application/json" \
  -d '{"category":"Transport","amount":30.00,"date":"2024-01-16"}'
```

### Delete Expense
```bash
curl -X DELETE http://localhost:8080/api/expenses/1
```

### Get Statistics
```bash
curl http://localhost:8080/api/stats
```

### Get Categories
```bash
curl http://localhost:8080/api/categories
```

