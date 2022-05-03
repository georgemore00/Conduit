# Overview
**Conduit** is a platform inspired by **Medium.com** where users can create articles, follow other authors, like articles, comment, edit their profiles etc.

## Technologies

- Spring Boot
- Spring WEB 
- Spring Data JPA 
- Spring Security for stateless JWT authentication & authorization

## Design Principels

- Always keep field variables private for encapsulation
- Follow JavaBean for domain objects (getters/setters etc excluding Serializable)
- Try to follow modern best practices and OOP principels
- Always use DTO's when sending API responses to not expose internal models.
- Follow n-tier/layered architecture 
- Strive for normalized database
- Create custom exceptions when suited
- Throw exceptions only in exceptional situations

## Diagram
![Conduit - Entities drawio](Conduit%20-%20Entities.drawio.png)
