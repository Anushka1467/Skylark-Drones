# Skylark-Drones

#  Business Intelligence Agent

An AI-powered Business Intelligence Agent built using **Java 21**, **Spring Boot**, **Monday.com GraphQL API**, **Groq LLM**, and **Thymeleaf**.

The application fetches business data from Monday.com boards, analyzes it using an AI model, and provides intelligent business insights through a simple chat interface.

---

##  Live Demo

**Application:** https://skylark-drones-4.onrender.com

---

##  Features

-  Fetches Deals and Work Orders from Monday.com
-  AI-powered business insights using Groq LLM
-  Interactive chat interface
-  Intelligent filtering before sending data to the LLM
-  Business summaries and recommendations
-  Dockerized application
-  Deployed on Render
-  
---

##  Tech Stack

### Backend
- Java 21
- Spring Boot 3
- Spring MVC
- Maven
- RestTemplate
- Jackson

### Frontend
- HTML
- CSS
- JavaScript
- Thymeleaf

### APIs
- Monday.com GraphQL API
- Groq LLM API

### Deployment
- Docker
- Render

---

#  Project Structure

```
business-intelligence-agent/
│
├── src/
│   ├── main/
│   │
│   ├── java/
│   │   └── com/
│   │       └── skylark/
│   │           └── business_intelligence_agent/
│   │
│   │               ├── controller/
│   │               │     ├── ChatController.java
│   │               │     └── HomeController.java
│   │               │
│   │               ├── dto/
│   │               │     ├── ChatRequest.java
│   │               │     ├── ChatResponse.java
│   │               │     ├── DealDTO.java
│   │               │     ├── WorkOrderDTO.java
│   │               │     └── groq/
│   │               │            ├── GroqRequest.java
│   │               │            └── Message.java
│   │               │
│   │               ├── service/
│   │               │     ├── BusinessInsightService.java
│   │               │     ├── MondayService.java
│   │               │     ├── GroqService.java
│   │               │     ├── OpenAIService.java
│   │               │     └── DataNormalizer.java
│   │               │
│   │               └── BusinessIntelligenceAgentApplication.java
│   │
│   └── resources/
│       ├── static/
│       │     ├── css/
│       │     │      └── style.css
│       │     │
│       │     └── js/
│       │            └── app.js
│       │
│       ├── templates/
│       │      └── index.html
│       │
│       └── application.properties
│
├── Dockerfile
├── .dockerignore
├── pom.xml
├── mvnw
└── README.md
```

---

#  How It Works

```
User Question
      │
      ▼
Spring Boot Controller
      │
      ▼
Monday.com GraphQL API
      │
      ▼
Normalize & Filter Data
      │
      ▼
Groq LLM
      │
      ▼
Business Insights
      │
      ▼
Frontend (Chat Interface)
```

---

#  Getting Started

## Clone the Repository

```bash
git clone https://github.com/Anushka1467/Skylark-Drones.git
```

---

## Navigate to the Project

```bash
cd Skylark-Drones/business-intelligence-agent
```

---

## Configure Environment

Update the following values in `application.properties`:

```properties
monday.api.token=YOUR_MONDAY_API_TOKEN
monday.deals.board.id=YOUR_DEALS_BOARD_ID
monday.workorders.board.id=YOUR_WORKORDER_BOARD_ID
groq.api.key=YOUR_GROQ_API_KEY
```

---

## Run the Application

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

---

# Docker

Build the Docker image

```bash
docker build -t business-intelligence-agent .
```

Run the container

```bash
docker run -p 8080:8080 business-intelligence-agent
```

---

#  Application

The application provides a conversational interface where users can ask questions such as:

- Show ongoing work orders
- Which deals have high closure probability?
- Summarize all business activities
- Show pending executions
- Provide business recommendations

The AI processes the relevant data and returns meaningful business insights.

---

#  Security

Sensitive credentials such as API keys and tokens are **not included** in the repository.

Configure them locally before running the application.


 
