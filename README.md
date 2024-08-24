# Pokemon Battle API

This project is a simple Pokemon battle API built with Spring Boot. It allows users to simulate battles between Pokemon and retrieve battle results.

## Features

- Load Pokemon data from a CSV file.
- Retrieve Pokemon details by name.
- Simulate battles between two Pokemon and determine the winner.

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Gradle

### Steps to Run

1. **Clone the repository**


2. **Build the project:**

   ```bash
   ./gradlew clean build
   ```

3. **Run the application:**

   ```bash
   ./gradlew bootRun
   ```

### API Endpoints

- **GET `/api/attack?pokemonAName=NAME1&pokemonBName=NAME2`**

  Simulate a battle between two Pokemon by name and return the battle result.

  Example:
  ```bash
  curl "http://localhost:8080/api/attack?pokemonAName=Pikachu&pokemonBName=Charmander"
  ```

  Response:
  ```json
  {
    "winner": "Pikachu",
    "remainingHitPoints": 20
  }
  ```