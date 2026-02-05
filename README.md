# Convert File

A utility for converting JSON plan (budget) files into CSV format. The application scans a directory
for files in the `YYYY-MM.json` format, filters planOperations, and saves the results into a single
CSV
file.

## Tech Stack

- **Language:** Java 17
- **Build Tool:** Maven
- **Libraries:**
    - [Jackson](https://github.com/FasterXML/jackson) — for JSON parsing.
    - [Lombok](https://projectlombok.org/) — to reduce boilerplate code.

## Requirements

- Java Development Kit (JDK) 17 or higher.
- Apache Maven 3.6 or higher.

## Build and Installation

To build the project into an executable JAR file, follow these steps:

1. **Clone the repository** (if not already done):
   ```bash
   git clone <repository-url>
   cd convert-file
   ```

2. **Build the project using Maven**:
   ```bash
   mvn clean package
   ```

After the build is complete, a `convert-file.jar` file will be created in the `target/` directory,
containing all necessary dependencies.

## Run

The application accepts two command-line arguments:

1. Path to the directory with input JSON files.
2. Path to the output CSV file.

### Run Example

```bash
java -jar target/convert-file.jar "/path/to/monthly-plan-json/" "/path/to/output/"
```

## Scripts and Commands

- `mvn clean` — clean the build directory (removes the `target` folder).
- `mvn compile` — compile the source code.
- `mvn package` — build the project and create a JAR file (including dependencies via
  `maven-shade-plugin`).
- `mvn -q -DskipTests package` — fast build without logs and skipping tests.
- `mvn install` — build and install the artifact into the local Maven repository (
  `~/.m2/repository`).

## Project Structure

```text
src/main/java/com/chertiavdev/
├── exceptions/         # Custom exceptions (FileNotOpenedException, etc.)
├── models/             # Data models (Operation, OperationDataResult)
├── service/            # Interfaces and implementation of file processing logic
├── util/               # Utility classes (ServiceUtils)
└── Main.java           # Application entry point
```
