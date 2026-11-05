# CloudCart - Cloud-Native E-Commerce Platform

CloudCart is a modern, production-ready e-commerce backend built with Java 21 and Spring Boot 4.x. Designed with a microservices-ready architecture, it utilizes an event-driven asynchronous checkout flow, distributed caching, and cloud object storage to deliver high performance and scalability. 

## 🏗️ Architecture & Tech Stack

| Domain | Technology / Tool | Version / Details |
| :--- | :--- | :--- |
| **Core Framework** | Java, Spring Boot | Java 21, Spring Boot 4.0.0 |
| **Security** | Spring Security, JWT | Spring Security 7.0, JJWT 0.12.5 (Stateless) |
| **Database & ORM** | PostgreSQL, Spring Data JPA | PostgreSQL 18.6, Hibernate 7.1.8 |
| **Message Broker** | Apache Kafka | KRaft Mode (Docker Compose) |
| **Caching** | Redis | Spring Data Redis |
| **Cloud Storage** | Amazon Web Services (AWS) | AWS Java SDK v2 (S3 `ap-south-1`) |
| **Configuration** | Dotenv, Maven | `dotenv-java` for environment secrets |

---

## 🚀 Development Roadmap & Progress

### Phase 1 & 2: Project Initialization & Data Modeling (Completed)
- [x] Bootstrapped Spring Boot 4 application with Java 21.
- [x] Configured PostgreSQL connection pooling (HikariCP).
- [x] Designed normalized relational schemas for `Users`, `Products`, `Categories`, `Carts`, `CartItems`, `Orders`, and `OrderItems`.
- [x] Environment variable management setup via `.env`.

### Phase 3: Core Monolithic Foundation (Completed)
- [x] **User Management & Security:** Secure user registration, BCrypt password hashing, and stateless API security using a custom JWT authentication filter.
- [x] **Product Catalog:** Product entities with lazy-loaded relationships, search endpoints, and pagination/sorting using Spring Data `Pageable`.
- [x] **Shopping Cart (Bounded Context):** Cart domain designed to store relational IDs instead of direct entity mappings for cleaner microservices extraction. Dynamic price calculation at the DTO layer.
- [x] **Order Management:** Synchronous order lifecycle tracking (Pending, Confirmed) and strict inventory validation prior to checkout.

### Phase 4: Advanced Backend Architecture (Completed)
- [x] **Distributed Caching (Redis):** High-performance caching for product catalog read queries (`@Cacheable`) with automated invalidation upon new product creation (`@CacheEvict`).
- [x] **Concurrency Control:** Optimistic locking utilizing JPA `@Version` to prevent inventory race conditions, overselling, and concurrent modification collisions.
- [x] **Cloud Object Storage (AWS S3):** Secure product media uploads utilizing the official AWS Java SDK v2 with configured IAM policies and public URL generation.

### Phase 5: Event-Driven Architecture (Completed)
- [x] **Message Broker Setup:** Containerized Apache Kafka (KRaft mode) via Docker Compose (`cloudcart-kafka`).
- [x] **Asynchronous Checkout Flow:** Transitioned order placement into an event-driven model publishing `OrderCreatedEvent` payloads using type-safe `KafkaTemplate` configurations.
- [x] **Transactional Integrity:** Aligned JPA transaction boundaries and explicit cart item deletion (`cartItemRepository.deleteAll()`) to prevent delayed-flush relational conflicts and ensure database-to-broker reliability.

### Phase 6: External Integrations (Upcoming)
- [ ] **Notification Service:** Independent Kafka consumer module to listen for `OrderCreatedEvent` and trigger asynchronous email notifications.
- [ ] **Payment Gateway Integration:** Secure payment intent generation and webhook listening via Stripe or Razorpay.
- [ ] **Webhook Validation:** Cryptographic verification of payment success before advancing the `OrderStatus` to `PAID`.

### Phase 7: Microservices Extraction (Future Scope)
- [ ] **API Gateway:** Implement Spring Cloud Gateway for centralized routing and rate limiting.
- [ ] **Service Discovery:** Integrate Netflix Eureka for dynamic service registry.
- [ ] **Domain Segregation:** Physically split the monolith into independent microservices (`user-service`, `catalog-service`, `order-service`, `notification-service`).

---

## 🛠️ Key Engineering Decisions & Challenges Overcome

*   **Spring Boot 4.0 / Spring Security 7 Migration:** Adapted security configurations to handle the removal of `setUserDetailsService` by implementing the new immutable `DaoAuthenticationProvider` constructor pattern.
*   **JPA & Lombok Proxy Conflicts:** Resolved critical transactional failures caused by Lombok's `@Data` annotation on entities with bidirectional relationships. Refactored entities to explicit `@Getter`/`@Setter` to preserve Hibernate proxy integrity.
*   **Hibernate Collection Management (Orphan Removal):** Bypassed delayed-flush PostgreSQL constraint violations during cart clearing by explicitly invoking `cartItemRepository.deleteAll()` alongside orphan removal.
*   **Concurrency Resiliency:** Prevented `NullPointerException` crashes during optimistic lock version increments by initializing default values (`bigint default 0`) for the JPA `@Version` lifecycle.

---

## ⚙️ Local Development Setup

**1. Prerequisites**
*   Java 21 JDK
*   Maven 3.8+
*   Docker & Docker Compose
*   PostgreSQL 18

**2. Environment Variables**
Create a `.env` file in the root directory:
```env
DB_URL=jdbc:postgresql://localhost:5432/cloudcart_db
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password
JWT_SECRET=your_base64_encoded_secure_secret_key
AWS_ACCESS_KEY=your_aws_access_key
AWS_SECRET_KEY=your_aws_secret_key