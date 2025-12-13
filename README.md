# DOCore Platform: Distributed Observability Core

### *A Real-Time, Event-Driven Log Aggregation System*

**Status:** 🚧 In Development (Phase 1 Complete)

## 📖 The "Why" Behind This Project

During my journey of building complex microservices architectures (like my recent *Fit-Pilot* project), I encountered a significant operational bottleneck: **Observability**.

When a request failed in a distributed ecosystem of 6+ services, debugging was a nightmare. I found myself manually SSH-ing into different containers, grepping through isolated console logs, and trying to mentally stitch together timestamps to find the root cause. It was slow, inefficient, and unscalable.

**I realized that building microservices is only half the battle; managing them is the real challenge.**

I started this project, **DOCore**, to solve that specific pain point. My goal is to move from "hunting for logs" to "having logs come to me." This platform is my deep dive into building a production-grade, centralized logging infrastructure using Event-Driven Architecture.

---

## 🏗️ Architecture Overview

The DOCore Platform is designed to decouple log generation from log storage, ensuring high throughput and fault tolerance.

### The Pipeline (Data Flow)
1.  **Log Generation:** Microservices produce structured logs (JSON).
2.  **Transport Layer:** **Apache Kafka** acts as a high-speed buffer, receiving logs asynchronously.
3.  **Ingestion & Storage (Next Phase):** A consumer service reads from Kafka and indexes data into **Elasticsearch**.
4.  **Visualization (Next Phase):** **Kibana** provides real-time dashboards for analysis.

---

## 🛠️ Technology Stack

I selected this stack to mirror industry-standard observability platforms used by companies like Netflix and Uber.

* **Core Backend:** Java 21, Spring Boot 4.0.0
* **Message Broker:** Apache Kafka (Event Streaming) & Zookeeper
* **Infrastructure:** Docker & Docker Compose
* **Data Format:** JSON (Serialized via Jackson)

---

## 🚀 Current Progress: Phase 1 (The Pipeline Core)

Currently, the **Data Transport Layer** is fully operational.

### ✅ Achievements
- [x] **Infrastructure Orchestration:** Successfully containerized the entire EEK stack (Elasticsearch, Kafka, Kibana, Zookeeper) using `docker-compose`.
- [x] **Log Producer Service:** Built a Spring Boot microservice acting as a log emitter.
- [x] **Kafka Integration:** Implemented `KafkaTemplate` with custom `JsonSerializer` to handle Java POJOs.
- [x] **Verification:** Validated that structured JSON logs (`{ "level": "INFO", "message": "..." }`) are successfully landing in the Kafka Topic `docore-logs-topic`.

---

## 💻 Getting Started (Local Setup)

This project uses a hybrid development setup: Infrastructure runs on **WSL/Ubuntu**, while code is developed in **IntelliJ (Windows)**.

### Prerequisites
* Docker Desktop (configured with WSL 2)
* Java 21 (JDK)
* Maven

### 1. Launch Infrastructure
The backend services are defined in `docker-compose.yml`.

```bash
# From the project root
docker compose up -d
```
Verifying : Run `docker ps` to ensure Kafka,Zookeeper, Elasticsearch and Kibana are runnning.

### 2. Run the Log  Procducer
Navigate to the `log-producer-service` directory and run the Spring Boot application.
* Port:6000
* Endpoint: /api/logs

### 3. Test the Pipeline
Send the `POST` request to trigger a log event:

```bash
curl -X POST http://localhost:6000/api/logs \
     -H "Content-Type: application/json" \
     -d '{
            "serviceName": "user-service",
            "logLevel": "ERROR",
            "message": "Database connection timeout",
            "statusCode": "500"}'
```
---
## 🔮 What's Next? (Phase 2)
The logs are currently safe in the Kafka queue. The next steps focus on consumption and intelligence.

    1. Build Log Consumer Servie: Create a listener to consume message from the Kafka topic.
    2. Elasticsereach Integration: Index these JSON logs into the search engine.
    3. Kibana Dashboards: Visualize error rates and latency in real time.
---
Author
Dhinithya Verma

Documenting my journey into Distributed Systems & Observability Engineering.