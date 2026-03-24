# 🧾 Order Management API

A Spring Boot application for managing orders and calculating monthly revenue with support for customer-based discounts.

---

## 🚀 Features

* Create and fetch orders via REST APIs
* Filter orders by month (`YYYY-MM`)
* Calculate monthly revenue
* Apply **10% discount for PREMIUM customers**
* Ignore invalid orders (null/negative amounts)
* In-memory storage (no database required)
* Bean Validation for request validation
* Centralized exception handling
* Java 17 + Spring Boot 3.x

---

## 🛠️ Tech Stack

* Java 17+
* Spring Boot 3.x
* Maven
* Lombok
* Jakarta Validation

---

## 📁 Project Structure

```
org.example
 ├── controller       # REST Controllers
 ├── service          # Business logic (OrderService, RevenueService, DiscountService)
 ├── repository       # In-memory storage
 ├── model            # DTOs & Enums
 │    ├── OrderDTO.java
 │    └── CustomerType.java
 ├── exception        # Global exception handling
 └── MonthlyRevenueApp.java
```

---

## ▶️ Getting Started

### 1. Clone the repository

```
git clone <your-repo-url>
cd MonthlyRevenue
```

### 2. Build the project

```
mvn clean install
```

### 3. Run the application

```
mvn spring-boot:run
```

Application will start at:

```
http://localhost:8080
```

---

## 📌 API Endpoints

### 🔹 Create Order

**POST /orders**

Request:

```json
{
  "amount": 1500,
  "customerType": "PREMIUM",
  "orderDate": "2026-03-10"
}
```

Response:

* `201 Created`

---

### 🔹 Get Order by ID

**GET /orders/{id}**

Response:

* `200 OK`
* `404 Not Found`

---

### 🔹 Get Orders by Month

**GET /orders?month=YYYY-MM**

Example:

```
/orders?month=2026-03
```

Response:

```json
[
  {
    "id": 1,
    "amount": 1500,
    "customerType": "PREMIUM",
    "orderDate": "2026-03-10"
  }
]
```

---

### 🔹 Get Monthly Revenue

**GET /orders/revenue**

#### Sample Response

```json
{
  "2026-03": 1400
}
```

#### Business Logic

* PREMIUM customers → 10% discount applied
* REGULAR customers → no discount
* Ignore null or negative amounts
* Revenue grouped by `YearMonth`

---

## ⚠️ Validation Rules

* `amount` must be ≥ 0
* `customerType` must not be null
* `orderDate` must not be null

---

## ❗ Error Handling

Centralized error handling using `@RestControllerAdvice`.

### 🔹 Validation Error Example

```json
{
  "timestamp": "2026-03-24T20:30:00",
  "status": 400,
  "message": "amount: must be greater than 0"
}
```

### 🔹 Not Found Error Example

```json
{
  "timestamp": "2026-03-24T20:30:00",
  "status": 404,
  "message": "Order not found"
}
```

---

## 📝 Notes

* Uses in-memory storage (no database)
* Thread-safe repository implementation
* Logging added for key operations
* Clean layered architecture (Controller → Service → Repository)

---

## 🚀 Future Enhancements

* Add database support (JPA/Hibernate)
* Add Swagger/OpenAPI documentation
* Add authentication & authorization
