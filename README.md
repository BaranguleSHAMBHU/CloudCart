# CloudCart - Cloud-Native E-Commerce Platform

CloudCart is a modern, microservices-ready e-commerce backend built with Java 21 and Spring Boot.

## Development Roadmap & Progress

### Phase 3: Monolithic Foundation
- [x] **Module 1: User Management & Security**
  - PostgreSQL database integration using Spring Data JPA.
  - Secure user registration and authentication.
  - Password hashing using BCrypt.
  - Stateless API security using JSON Web Tokens (JWT).
  - Custom JWT authentication filter.
- [x] **Module 2: Product Management**
  - Category and Product entities with lazy-loaded relationships.
  - Pagination and sorting using Spring Data `Pageable`.
- [x] **Module 3: Shopping Cart**
  - Bounded context design (storing IDs instead of direct entity mappings).
  - Dynamic price calculation during DTO mapping.
- [x] **Module 4: Order Management**
  - Order lifecycle tracking (Pending, Confirmed, Cancelled).
  - Synchronous inventory deduction during checkout.
  - Cart clearing upon successful order placement.

### Phase 4: Advanced Backend Architecture
- [x] **Redis Caching**
  - High-performance caching for product catalog read queries (`@Cacheable`).
  - Cache invalidation on new product creation (`@CacheEvict`).
- [x] **Concurrency Control**
  - Optimistic locking using JPA `@Version` to prevent inventory race conditions and overselling.
  - Global exception handling for concurrent updates (`ObjectOptimisticLockingFailureException`).
- [x] **Cloud Object Storage**
  - Product media uploads using official AWS Java SDK v2 (`software.amazon.awssdk:s3`).
  - Storage and public URL generation via Amazon S3 (`ap-south-1`).

## Tech Stack
* **Java 21** & **Spring Boot 3.x / 4.x**
* **PostgreSQL** & **Spring Data JPA**
* **Redis** (Docker containerized)
* **AWS S3** (Amazon Web Services SDK v2)
* **Spring Security** & **JWT (jjwt 0.12.5)**
* **Maven**