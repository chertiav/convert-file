# Convert File

A utility for converting plan (budget) and fact data files into CSV format. The application scans a
directory for files in the `YYYY-MM.json` (for plans) or `YYYY-MM.txt` (for facts) format, processes
them, and saves the results into a single CSV file.

## Tech Stack

- **Language:** Java 25
- **Build Tool:** Maven
- **Libraries:**
    - [Jackson](https://github.com/FasterXML/jackson) — JSON parsing.
    - [Lombok](https://projectlombok.org/) — boilerplate code generation.
    - [MapStruct](https://mapstruct.org/) — object mapping.

## Requirements

- Java Development Kit (JDK) 25 or higher.
- Apache Maven 3.6 or higher.

## Build and Installation

To build the project into an executable JAR file, run the following:

1. **Clone the repository** (if not already done):
   ```bash
   git clone <repository-url>
   cd convert-file
   ```

2. **Build the project**:
   ```bash
   mvn clean package
   ```

After the build, a `convert-file.jar` file containing all necessary dependencies will be created in
the `target/` directory.

## Running the Application

The application supports two modes of operation: processing plans and processing fact data.

**Command-line arguments:**

1. Mode flag: `--plan` or `--fact`.
2. Path to the directory with input files.
3. Path to the directory for saving the output CSV file.

### Usage Examples

**Processing plan data (JSON):**

```bash
java -jar target/convert-file.jar --plan "/path/to/input-json-dir/" "/path/to/output-dir/"
```

*The result will be saved to `result-plan.csv`.*

**Processing fact data (TXT):**

```bash
java -jar target/convert-file.jar --fact "/path/to/input-txt-dir/" "/path/to/output-dir/"
```

*The result will be saved to `result-fact.csv`.*

**Help:**

```bash
java -jar target/convert-file.jar --help
```

## Input Data Examples

### Plan (JSON)

Files must have the `.json` extension and follow this format:

```json
[
  {
    "id": 1234567,
    "title": "08:00 To 09:00 \nMock Task Description\nJohn Doe\n1 hour(s) and 0 min.\nTask Comment!",
    "start": "2025-01-01T08:00:00",
    "end": "2025-01-01T09:00:00",
    "url": "func/seopg.php?ID=1234567",
    "color": "#080480",
    "textColor": "#ffffff",
    "overlayOpacity": 0.6
  }
]
```

### Fact (TXT)

Files must have the `.txt` extension and contain tab-separated records:

```text
Task Name	Package Name	Company Name	Type	01/01-2025 - Kl. 08:00	1h 00m	User Name		100,00	0,00	0,00	0,00	0,00
```

## Maven Commands

- `mvn clean` — clean the build directory.
- `mvn compile` — compile the source code.
- `mvn package` — build the project into a JAR file.
- `mvn checkstyle:check` — check code style.

## Project Structure

```text
src/main/java/com/chertiavdev/
├── config/             # Configuration (ObjectMapper, etc.)
├── domain/             # Enums and domain entities (Mode, OperationType)
├── dto/                # Data Transfer Objects (OperationDto, ResultDto)
├── exceptions/         # Custom exceptions
├── export/             # Export logic (CSV formatting)
├── factory/            # Factory for service creation (AppFactory)
├── mapper/             # MapStruct interfaces for object transformation
├── parser/             # Specific format parsers
├── service/            # Service layer (file processing logic)
├── strategy/           # Strategies for reading, filtering, and writing data
├── util/               # Utilities (DateTimeHelper)
└── Main.java           # Application entry point
```
