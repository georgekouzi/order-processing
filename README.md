# Order Processing

##  Overview
This project implements a **distributed microservice system** simulating an order processing workflow.  
It is built with **Java 17, Spring Boot, Kafka, and Redis**, and demonstrates asynchronous communication between services, temporary data storage, and category-specific inventory rules.

### Microservices
1. **order-service**
    - Exposes REST API `POST /orders`
    - Receives order requests from clients
    - Saves full order to **Redis** with status `PENDING`
    - Publishes order event to Kafka topic `orders`

2. **inventory-service**
    - Consumes order events from Kafka topic `orders`
    - Validates items based on **catalog rules**
    - Publishes result to Kafka topic `inventory-results`

3. **notification-service**
    - Consumes inventory results from Kafka topic `inventory-results`
    - Retrieves original order from Redis
    - Logs a confirmation message:
        - `APPROVED` → success log
        - `REJECTED` → logs missing items

---

##  Tech Stack
- **Java 17**
- **Spring Boot 3.2**
- **Spring Data Redis**
- **Spring Kafka**
- **Docker** (for running Kafka + Redis)
- **Maven**

---

##  How to Run

### 1. Clone repo
```bash
git clone https://github.com/georgekouzi/order-processing.git
cd order-processing
```

### 2. Start dependencies (Kafka + Redis)
Using Docker Compose:



Run:
```bash
docker-compose up -d
```

### 3. Build all services
```bash
./mvnw clean install
```

### 4. Run services
Each service can be run separately:
```bash
./mvnw -pl order-service spring-boot:run
./mvnw -pl inventory-service spring-boot:run
./mvnw -pl notification-service spring-boot:run
```

---

##  API Usage

### Order Request Example
```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "id": "O1001",
    "customerName": "Alice",
    "items": [
      { "productId": "P1001", "quantity": 2, "category": "standard" },
      { "productId": "P1002", "quantity": 1, "category": "perishable" }
    ],
    "requestedAt": "2025-09-04T10:00:00Z"
  }'
```

---

##  Inventory Rules

- **standard** → order can be fulfilled if `availableQuantity >= requestedQuantity`
- **perishable** → must not be expired + must have enough quantity
- **digital** → always available
- **unknown product/category** → rejected

---

##  Test Scenarios

### Approved Order
```json
{
  "id": "O2001",
  "customerName": "Alice",
  "items": [ { "productId": "P1001", "quantity": 5, "category": "standard" } ]
}
```
**Expected:** APPROVED

---

### Rejected – Out of Stock
```json
{
  "id": "O2002",
  "customerName": "Bob",
  "items": [ { "productId": "P1004", "quantity": 1, "category": "standard" } ]
}
```
**Expected:** REJECTED, `missingItems=["P1004 insufficient stock"]`

---



