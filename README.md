# Full-Stack URL Shortener Service

A high-performance, full-stack URL shortening service built with Spring Boot and Vanilla JavaScript. It allows users to convert long URLs into concise, shareable links with support for custom aliases, link expiration, and automated background cleanup.

## 🚀 Features

- **RESTful API:** Clean, well-documented endpoints for link generation and redirection.
- **Integrated Frontend:** Responsive HTML/CSS/JS user interface served directly from the backend.
- **Caching Layer:** Redis integration to optimize read performance for high-traffic links.
- **Data Persistence:** PostgreSQL for reliable, long-term storage of link mappings.
- **Robust Validation:** Strict input validation and custom global exception handling (400/404/500 responses).
- **Automated Cleanup:** Scheduled background cron jobs to purge expired links and free up database space.

## 🛠️ Tech Stack

- **Backend:** Java 21, Spring Boot 3, Spring Data JPA, Hibernate
- **Frontend:** HTML5, CSS3, Vanilla JavaScript
- **Database:** PostgreSQL
- **Cache:** Redis
- **Build Tool:** Maven

## ⚙️ Prerequisites

Before running this application, ensure you have the following installed and running:

- **Java 21** or higher
- **PostgreSQL** (Running on port `5432`)
- **Redis** (Running on port `6379`)
  - Quick start via Docker: `docker run -d -p 6379:6379 --name redis-local redis`

## 🚀 Quick Start Setup

1. **Clone the repository**

   `git clone https://github.com/Kartik12082004/urlShortener.git`  
   `cd urlShortener`

2. **Configure the Database**

   Update the `src/main/resources/application.properties` file with your PostgreSQL credentials.

3. **Build and Run**

   `./mvnw clean spring-boot:run`

4. **Access the Application**

   Open your browser and navigate to `http://localhost:8080/` to use the UI.

## 📡 API Documentation

1. **Shorten URL**
   - Endpoint: `POST /api/shorten`
   - Description: Creates a new shortened URL. Accepts an optional custom alias and expiration date.

   Request Body:
   {
   "longUrl": "https://www.example.com/some/very/long/path",
   "customCode": "my-link",
   "expiresAt": "2026-12-31T23:59:59"
   }

   Success Response (201 Created):
   {
   "shortUrl": "http://localhost:8080/my-link",
   "longUrl": "https://www.example.com/some/very/long/path",
   "expiresAt": "2026-12-31T23:59:59"
   }

   Error Response: `400 Bad Request` (Invalid URL format or custom code already taken)

2. **Redirect URL**
   - Endpoint: `GET /{code}`
   - Description: Redirects the user to the original long URL associated with the short code. Uses Redis for high-speed lookups.

   Success Response: `302 Found` (Redirects to destination)  
   Error Response: `404 Not Found` (Link does not exist or has expired)
