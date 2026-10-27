# CommerceFlux: A Distributed AI-Powered E-commerce Platform

## Overview

CommerceFlux is a cloud-native, event-driven e-commerce platform built with microservices architecture. It features AI-powered recommendations, demand forecasting, dynamic pricing, fraud detection, and intelligent inventory optimization. This platform is designed to demonstrate FAANG-level engineering practices and patterns.

## Architecture

The platform follows a microservices architecture with the following key components:

- **API Gateway**: Spring Cloud Gateway for routing and API management
- **Core Business Services**: Catalog, Inventory, Pricing, Cart, Wishlist, Order, Payment, Shipping, Notification, Review
- **Search & Analytics**: Elasticsearch-based search and Apache Spark analytics
- **AI/ML Services**: Recommendation (PyTorch), Demand Forecasting (TensorFlow), Dynamic Pricing (scikit-learn), Fraud Detection (ML models)
- **Infrastructure**: PostgreSQL, Redis, Kafka, Elasticsearch

## Services

### Core Services

| Service | Port | Technology | Description |
|---------|------|------------|-------------|
| api-gateway | 8080 | Spring Cloud Gateway | API Gateway for routing and security |
| catalog-service | 8084 | Spring Boot, JPA | Product catalog management |
| inventory-service | 8085 | Spring Boot, JPA, Kafka | Inventory tracking and management |
| pricing-service | 8086 | Spring Boot, JPA, Redis | Dynamic pricing engine |
| cart-service | 8087 | Spring Boot, WebFlux, Redis | Reactive shopping cart |
| wishlist-service | 8088 | Spring Boot, JPA, Redis | Customer wishlist management |
| order-service | 8089 | Spring Boot, Batch, JPA | Order processing with Spring Batch |
| payment-service | 8090 | Spring Boot, JPA, Kafka | Payment processing |
| shipping-service | 8091 | Spring Boot, JPA | Shipping and tracking |
| notification-service | 8092 | Spring Boot, JPA | Multi-channel notifications |
| review-service | 8093 | Spring Boot, JPA | Product reviews and ratings |
| search-service | 8094 | Spring Boot, Elasticsearch | Product search |
| analytics-service | 8095 | Spring Boot, Spark | Business analytics |

### AI/ML Services

| Service | Port | Technology | Description |
|---------|------|------------|-------------|
| ai-recommendation-service | 8096 | FastAPI, PyTorch | Product recommendations |
| ai-demand-forecast-service | 8097 | FastAPI, TensorFlow | Demand forecasting |
| ai-dynamic-pricing-service | 8098 | FastAPI, scikit-learn | ML-based dynamic pricing |
| ai-fraud-detection-service | 8099 | FastAPI, scikit-learn | Transaction fraud detection |

## Technology Stack

### Backend Frameworks
- **Spring Boot 3.5.16**: Core framework for Java services
- **Spring Cloud Gateway**: API Gateway
- **Spring WebFlux**: Reactive programming (cart-service)
- **Spring Batch**: Batch processing (order-service)
- **FastAPI**: Python-based AI services

### Data & Caching
- **PostgreSQL 17**: Primary database for all services
- **Redis 7**: Caching layer
- **Elasticsearch 8.15**: Search engine
- **Apache Kafka**: Event streaming
- **Apache Spark**: Big data analytics

### AI/ML Frameworks
- **PyTorch 2.5.0**: Deep learning (recommendations)
- **TensorFlow 2.16.1**: Deep learning (demand forecasting)
- **scikit-learn 1.5.1**: Machine learning (pricing, fraud detection)
- **Transformers 4.46.0**: NLP models

### Security
- **Spring Security**: Authentication and authorization
- **OAuth2 Resource Server**: JWT-based security
- **Role-based Access Control**: CUSTOMER, SELLER, ADMIN roles

### DevOps
- **Docker**: Containerization
- **Docker Compose**: Multi-container orchestration
- **Maven**: Dependency management (Java)
- **Python pip**: Dependency management (Python)

## Getting Started

### Prerequisites

- Docker and Docker Compose
- Java 21
- Python 3.11
- Maven 3.9

### Running the Platform

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd CommerceFlux---A-Distributed-AI-Powered-E-commerce-Platform
   ```

2. **Start all services**
   ```bash
   docker-compose up -d
   ```

3. **Verify services are running**
   ```bash
   docker-compose ps
   ```

4. **Access the API Gateway**
   - Gateway URL: http://localhost:8080
   - Health check: http://localhost:8080/actuator/health

### Running Individual Services

Each service can be run independently using its own docker-compose.yml:

```bash
cd <service-name>
docker-compose up -d
```

## API Endpoints

### API Gateway Routes

All services are accessible through the API Gateway at `http://localhost:8080`:

- `/api/v1/catalog/**` → Catalog Service
- `/api/v1/inventory/**` → Inventory Service
- `/api/v1/pricing/**` → Pricing Service
- `/api/v1/cart/**` → Cart Service
- `/api/v1/wishlist/**` → Wishlist Service
- `/api/v1/orders/**` → Order Service
- `/api/v1/payments/**` → Payment Service
- `/api/v1/shipping/**` → Shipping Service
- `/api/v1/notifications/**` → Notification Service
- `/api/v1/reviews/**` → Review Service
- `/api/v1/search/**` → Search Service
- `/api/v1/analytics/**` → Analytics Service

### AI Services

AI services run independently and can be accessed directly:

- AI Recommendations: http://localhost:8096
- Demand Forecast: http://localhost:8097
- Dynamic Pricing: http://localhost:8098
- Fraud Detection: http://localhost:8099

## Architecture Patterns

### Event-Driven Architecture
- Kafka for asynchronous event communication
- Domain events for service decoupling
- Event sourcing for order processing

### CQRS Pattern
- Separate read and write models
- Optimized for high-throughput scenarios

### Circuit Breaker
- Resilience4j for fault tolerance
- Automatic fallback mechanisms

### Batch Processing
- Spring Batch for order processing
- Scheduled job execution

## Security

### Authentication
- JWT-based authentication
- OAuth2 Resource Server configuration

### Authorization
- Role-based access control (RBAC)
- Method-level security annotations

### Roles
- **CUSTOMER**: Can browse, add to cart, place orders, write reviews
- **SELLER**: Can manage products, view analytics, process orders
- **ADMIN**: Full system access

## Monitoring & Observability

### Actuator Endpoints
All services expose Spring Boot Actuator endpoints:
- `/actuator/health` - Health status
- `/actuator/metrics` - Application metrics
- `/actuator/info` - Application information

### Logging
- Structured logging with correlation IDs
- Centralized log aggregation (can be integrated with ELK stack)

## Development

### Adding a New Service

1. Create service directory structure
2. Add pom.xml (Java) or requirements.txt (Python)
3. Implement entities, DTOs, services, controllers
4. Add configuration files (application.yml, Dockerfile)
5. Update root docker-compose.yml
6. Add API Gateway route configuration

### Code Style
- Lombok for reducing boilerplate
- Consistent package structure
- RESTful API design principles

## Testing

### Unit Tests
Each service includes unit tests for business logic.

### Integration Tests
Service-to-service communication testing.

### API Testing
Use Swagger UI available at `/swagger-ui/index.html` for each service.

## Deployment

### Docker Deployment
```bash
docker-compose up -d
```

### Production Considerations
- Configure environment-specific properties
- Enable SSL/TLS for all services
- Set up proper secrets management
- Configure health checks and readiness probes
- Implement distributed tracing
- Set up centralized monitoring

## Performance Optimization

### Caching Strategy
- Redis for frequently accessed data
- Cache TTL: 1 hour (configurable)
- Cache invalidation on data updates

### Database Optimization
- Connection pooling
- Index optimization
- Query optimization

### Async Processing
- Kafka for event-driven communication
- Reactive programming (WebFlux)
- Batch processing for heavy operations

## Scalability

### Horizontal Scaling
- Stateless services for easy scaling
- Load balancer configuration
- Database read replicas

### Vertical Scaling
- Resource allocation per service
- JVM tuning for Java services

## Troubleshooting

### Common Issues

1. **Port conflicts**: Check if ports are already in use
2. **Database connection**: Verify PostgreSQL is running
3. **Kafka connection**: Ensure Zookeeper and Kafka are started
4. **Memory issues**: Increase Docker memory allocation

### Logs
```bash
docker-compose logs <service-name>
```

### Service Health
```bash
curl http://localhost:<port>/actuator/health
```

## Contributing

This is an educational project. Contributions are welcome for:
- Bug fixes
- Feature enhancements
- Documentation improvements
- Performance optimizations

## You have some query?

This is for educational purposes only. If you have some query, feel free to connect with me here -- [Ranjan Kumar Mandal](https://www.linkedin.com/in/ranjan-kumar-m-818367158/)