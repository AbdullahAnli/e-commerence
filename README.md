# E-Commerce Modular Backend

A modern, scalable e-commerce backend built with **Spring Boot 3.2.1** using **Modular Monolith Architecture**. This project demonstrates clean architecture principles, domain-driven design, and containerization with Docker.

## 🏗️ Architecture

This project follows a **Modular Monolith** approach, where the application is organized into distinct modules:

\`\`\`
ecommerce-parent/
├── ecommerce-shared/          # Common entities, exceptions, utilities
├── ecommerce-user/            # User management module
├── ecommerce-product/         # Product & category management
├── ecommerce-order/           # Order processing module
├── ecommerce-auth/            # Authentication & security
└── ecommerce-api/             # REST API controllers & main app
\`\`\`

## 🚀 Features

- **Modular Architecture**: Clean separation of concerns with Maven modules
- **JWT Authentication**: Secure token-based authentication
- **Role-based Authorization**: Admin and Customer roles
- **RESTful APIs**: Well-designed REST endpoints
- **Database Integration**: MySQL with JPA/Hibernate
- **Docker Support**: Containerized application with Docker Compose
- **Input Validation**: Comprehensive request validation
- **Exception Handling**: Global exception handling
- **Pagination**: Built-in pagination for product listings
- **Search Functionality**: Product search capabilities

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Security 6**
- **Spring Data JPA**
- **MySQL 8.0**
- **JWT (JSON Web Tokens)**
- **Maven 3.9+**
- **Docker & Docker Compose**
- **MapStruct** (for DTO mapping)

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.9+
- Docker & Docker Compose
- MySQL 8.0 (if running without Docker)

## 🚀 Quick Start

### Using Docker (Recommended)

1. **Clone the repository**
   \`\`\`bash
   git clone <repository-url>
   cd ecommerce-backend
   \`\`\`

2. **Start the application**
   \`\`\`bash
   docker-compose up -d
   \`\`\`

3. **Access the API**
   - API Base URL: `http://localhost:8080/api`
   - MySQL: `localhost:3306`

### Manual Setup

1. **Clone and build**
   \`\`\`bash
   git clone <repository-url>
   cd ecommerce-backend
   mvn clean install
   \`\`\`

2. **Setup MySQL Database**
   \`\`\`sql
   CREATE DATABASE ecommerce_db;
   \`\`\`

3. **Update application.properties**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   \`\`\`

4. **Run the application**
   \`\`\`bash
   cd ecommerce-api
   mvn spring-boot:run
   \`\`\`

## 📚 API Documentation

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | User login |

### Product Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/products` | Get all products (paginated) | No |
| GET | `/api/products/{id}` | Get product by ID | No |
| GET | `/api/products/search?keyword=` | Search products | No |
| POST | `/api/products` | Create product | Admin |
| PUT | `/api/products/{id}` | Update product | Admin |
| DELETE | `/api/products/{id}` | Delete product | Admin |

### Category Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/products/categories` | Get all categories | No |
| GET | `/api/products/categories/{id}` | Get category by ID | No |
| POST | `/api/products/categories` | Create category | Admin |

## 🔐 Authentication

The API uses JWT (JSON Web Tokens) for authentication. Include the token in the Authorization header:

\`\`\`
Authorization: Bearer <your-jwt-token>
\`\`\`

### Sample Requests

**Register User:**
\`\`\`bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "password": "password123",
    "phone": "+1234567890"
  }'
\`\`\`

**Login:**
\`\`\`bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
\`\`\`

**Create Product (Admin only):**
\`\`\`bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "name": "iPhone 15",
    "description": "Latest iPhone model",
    "price": 999.99,
    "stockQuantity": 50,
    "categoryId": 1,
    
  }'
\`\`\`

## 🗄️ Database Schema

### Users Table
- id, first_name, last_name, email, password, phone, role, is_active, created_at, updated_at

### Categories Table
- id, name, description, is_active, created_at, updated_at

### Products Table
- id, name, description, price, stock_quantity, image_url, is_active, category_id, created_at, updated_at

### Orders Table
- id, user_id, total_amount, status, shipping_address, notes, created_at, updated_at

### Order Items Table
- id, order_id, product_id, quantity, unit_price, total_price, created_at, updated_at

## 🐳 Docker Configuration

The application includes a multi-stage Dockerfile and Docker Compose configuration:

- **MySQL**: Database service with persistent volume
- **App**: Spring Boot application
- **Networks**: Isolated network for services
- **Health Checks**: Built-in health monitoring

## 🔧 Configuration

Key configuration files:

- `application.properties`: Main application configuration
- `docker-compose.yml`: Docker services configuration
- `Dockerfile`: Application containerization
- `init.sql`: Database initialization script

## 🧪 Testing

Run tests with Maven:
\`\`\`bash
mvn test
\`\`\`

## 📦 Building

Build the entire project:
\`\`\`bash
mvn clean package
\`\`\`

Build Docker image:
\`\`\`bash
docker build -t ecommerce-backend .
\`\`\`

## 🚀 Deployment

### Production Deployment

1. **Update configuration** for production environment
2. **Build the application**:
   \`\`\`bash
   mvn clean package -Pprod
   \`\`\`
3. **Deploy using Docker**:
   \`\`\`bash
   docker-compose -f docker-compose.prod.yml up -d
   \`\`\`


### Version 1.0.0
- Initial release with modular architecture
- JWT authentication
- Product and category management
- Docker support
- RESTful APIs

```properties file="ecommerce-api/src/main/resources/application-docker.properties"
# Docker-specific configuration
spring.datasource.url=jdbc:mysql://mysql:3306/ecommerce_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=rootpassword

# JPA Configuration for Docker
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Logging for Docker
logging.level.com.ecommerce=INFO
logging.level.org.springframework.security=WARN
