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

## Current Tech Stack
* Java 21
* Spring Boot 3.x
* PostgreSQL
* Spring Security + JWT (jjwt 0.12.5)
* Maven