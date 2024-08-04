Transaction Producer:
Spring Boot Application: Implement a Spring Boot application that simulates credit card transactions.
Data Format: Produce transaction data to a Kafka topic. Each transaction should include fields: transactionId, cardNumber, amount, timestamp, and merchantId.
Production Frequency: Generate random transactions at a regular interval (e.g., every second).
Transaction Consumer:
Spring Boot Application: Implement a Spring Boot application that consumes transactions from the Kafka topic.
Authorization Service: Implement a service that performs basic credit card authorization checks. The service should validate whether the transaction amount is within the card’s credit limit.
Database: Store transaction data and authorization results in a local Postgres database. Use tables to track transactions and authorization status.
Implement the DLQ (Dead letter queue), and provide the steps on how to test it.
Spring Authorizer:
Authorization Logic: Implement a Spring Security-based authorization system that checks whether a transaction is valid based on predefined rules (e.g., maximum allowed amount per transaction).
Endpoint Security: Secure the endpoints using Spring Security and JWT-based authentication.
Database:
Schema: Design a schema for storing transaction and authorization data in Postgres.
Tables: Include tables for transactions, card limits, and authorization logs.
Local Deployment:
Docker Setup: Use Docker to containerize the producer, consumer, and Postgres database.
Docker Compose: Provide Docker Compose scripts to run Kafka, Zookeeper, the applications, and Postgres locally.
Provide the readme to set your project locally to run and test with detailed guidelines
Proper exception handling should be there in all services
Monitoring:
Logging: Implement logging for transaction processing and authorization decisions.
Metrics: Expose basic metrics using Spring Boot Actuator.
Distributed tracing with any SDK of your choice
Steps to reproduce run:

Insert an entry in :

The system has database transaction limit:

user_credit_limit : 
"id",  "user_id",  "total_limit",   "current_spending"

Add data in user:
"id", "name", "email", "password", "card_number", "phone_number" 

add data in user table:

Use url Producer to produce transaction:

Steps to test DLQ:

set you spending limit and keep adding the data till the time the credit limit is expired,

when the spending limit is expired the data goes to DLQ and will be logged in.

For authorization token login using login url and generate JWT token,

to access all the urls in the system use JWT token
