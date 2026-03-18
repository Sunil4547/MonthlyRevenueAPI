# 🧾 Order Management API

A Spring Boot application for managing orders and calculating monthly revenue with support for customer-based discounts.

---

## 🚀 Features

* Create and fetch orders via REST APIs
* Filter orders by month (`YYYY-MM`)
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
com.example.orders
 ├── controller       # REST Controllers
 ├── service          # Business logic
 ├── repository       # In-memory storage
 ├── model            # DTOs & Enums
 │    ├── OrderDTO.java
 │    └── CustomerType.java
 ├── exception        # Global exception handling
 └── OrderApplication.java
```

---

## ▶️ Getting Started

### 1. Clone the repository

```
git clone <your-repo-url>
cd order-api
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

* `200 OK`

---

## ⚠️ Validation Rules

* `amount` must be ≥ 0
* `customerType` must not be null
* `orderDate` must not be null

---

## ❗ Error Handling

Centralized error handling using `@RestControllerAdvice`.

Example error
