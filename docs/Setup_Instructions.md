# Setup Instructions - LearnTrack Course Management System

This guide provides step-by-step instructions to set up, compile, and run the LearnTrack application.

---

## Prerequisites

### Required Software

1. **Java Development Kit (JDK)**
   - **Minimum Version**: JDK 21
   - **Recommended**: OpenJDK 25.0.1 or later
   - Download from: [OpenJDK](https://openjdk.org/) or [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)

2. **Gradle** (Optional - project includes wrapper)
   - Version 9.2.0 or later
   - The project includes Gradle wrapper scripts, so manual installation is not required

3. **IDE** (Optional but recommended)
   - IntelliJ IDEA (Community or Ultimate)
   - Eclipse
   - VS Code with Java extensions

### Verify Java Installation

Check your Java version:
```bash
java -version
```

Expected output (example):
```
openjdk version "25.0.1" 2025-10-14
OpenJDK Runtime Environment (build 25.0.1+8-27)
OpenJDK 64-Bit Server VM (build 25.0.1+8-27, mixed mode, sharing)
```

**Minimum requirement**: Java version 21 or higher

---

## Project Setup

### 1. Clone or Download the Project

If using Git:
```bash
git clone <repository-url>
cd LearnTrack
```

Or download and extract the ZIP file, then navigate to the project directory.

### 2. Verify Project Structure

Ensure the following structure exists:
```
LearnTrack/
├── src/main/java/com/airtribe/learntrack/
│   ├── entity/
│   ├── repository/
│   ├── service/
│   ├── exception/
│   ├── enums/
│   ├── util/
│   └── Main.java
├── docs/
├── gradle/
├── gradlew           (Unix/Mac)
├── gradlew.bat       (Windows)
├── build.gradle
└── settings.gradle
```

### 3. Make Gradle Wrapper Executable (Unix/Mac only)

```bash
chmod +x gradlew
```

---

## Compilation

### Method 1: Using Gradle Wrapper (Recommended)

**On macOS/Linux:**
```bash
./gradlew clean build
```

**On Windows:**
```cmd
gradlew.bat clean build
```

**What this does:**
- Cleans previous build artifacts
- Compiles all Java source files
- Runs validation checks
- Creates JAR file in `build/libs/`

**Expected output:**
```
> Task :compileJava
> Task :processResources NO-SOURCE
> Task :classes
> Task :jar
> Task :assemble
> Task :compileTestJava NO-SOURCE
> Task :processTestResources NO-SOURCE
> Task :testClasses UP-TO-DATE
> Task :test NO-SOURCE
> Task :check UP-TO-DATE
> Task :build

BUILD SUCCESSFUL in 1s
2 actionable tasks: 2 executed
```

### Method 2: Using Gradle Directly (if installed)

```bash
gradle clean build
```

### Method 3: Using javac (Manual compilation)

```bash
# Create output directory
mkdir -p build/classes

# Compile all Java files
javac -d build/classes -sourcepath src/main/java src/main/java/com/airtribe/learntrack/**/*.java
```

**Note**: Method 1 (Gradle wrapper) is recommended for consistency and ease of use.

---

## Running the Application

### Method 1: Using Gradle Run Task (Recommended)

**On macOS/Linux:**
```bash
./gradlew run
```

**On Windows:**
```cmd
gradlew.bat run
```

**Advantages:**
- Automatically compiles if needed
- Sets up classpath correctly
- Handles dependencies

### Method 2: Using the JAR File

First, build the project:
```bash
./gradlew build
```

Then run the JAR:
```bash
java -jar build/libs/LearnTrack-1.0-SNAPSHOT.jar
```

### Method 3: Using java Command (After manual compilation)

```bash
java -cp build/classes com.airtribe.learntrack.Main
```

### Method 4: Using an IDE

#### IntelliJ IDEA:
1. Open the project folder in IntelliJ IDEA
2. Navigate to `src/main/java/com/airtribe/learntrack/Main.java`
3. Right-click on the file
4. Select **Run 'Main.main()'**

#### Eclipse:
1. Import the project as a Gradle project
2. Navigate to `Main.java`
3. Right-click → **Run As** → **Java Application**

#### VS Code:
1. Open the project folder
2. Ensure Java Extension Pack is installed
3. Open `Main.java`
4. Click the **Run** button above the `main` method

---

## Application Overview

### Entry Point

**File**: `src/main/java/com/airtribe/learntrack/Main.java`

**Main Method** (line 23):
```java
public static void main(String[] args) {
    initializeServices();
    ConsoleUtils.printHeader("LearnTrack - Course Management System");
    // ... menu system ...
}
```

### Application Flow

1. **Initialization** (line 53):
   - Creates repository instances (StudentRepository, CourseRepository, EnrollmentRepository)
   - Creates service instances with dependency injection
   - Services: StudentService, CourseService, EnrollmentService

2. **Main Menu Loop** (lines 27-49):
   - Displays interactive menu
   - Handles user input
   - Delegates to appropriate management modules
   - Continues until user selects Exit (0)

3. **Management Modules**:
   - **Student Management** (lines 75-186): Add, view, search, deactivate students
   - **Course Management** (lines 190-312): Add, view, search, activate/deactivate courses
   - **Enrollment Management** (lines 316-448): Enroll students, view enrollments, update status
   - **Statistics** (lines 452-470): View system statistics

---

## Expected Output on Startup

When you run the application, you should see:

```
============================================================
  LearnTrack - Course Management System
============================================================

--- Main Menu ---
1. Student Management
2. Course Management
3. Enrollment Management
4. View Statistics
0. Exit
------------------------------------------------------------
Enter your choice:
```

### Menu Options Explained

**1. Student Management**
- Add New Student (with/without email, batch tracking)
- View All Students (formatted table display)
- Search Student by ID
- Deactivate Student (soft delete)

**2. Course Management**
- Add New Course (name, description, duration)
- View All Courses (formatted table display)
- Search Course by ID
- Activate Course (make available for enrollment)
- Deactivate Course (make unavailable)

**3. Enrollment Management**
- Enroll Student in Course (with validation)
- View Enrollments by Student
- View All Enrollments
- Update Enrollment Status (ACTIVE → COMPLETED/CANCELLED)

**4. View Statistics**
- Total and active student count
- Total and active course count
- Enrollment counts by status

**0. Exit**
- Closes the application and releases resources

---

## Testing the Application

### Quick Test Workflow

1. **Add a Student**:
   - Main Menu → 1 (Student Management)
   - Choose 1 (Add New Student)
   - Enter: First Name = "John", Last Name = "Doe", Batch = "2024-A"
   - Include email? yes
   - Email = "john.doe@example.com"
   - Student created with ID 1000

2. **Add a Course**:
   - Main Menu → 2 (Course Management)
   - Choose 1 (Add New Course)
   - Course Name = "Java Programming"
   - Description = "Introduction to Java"
   - Duration = 12 weeks
   - Course created with ID 2000

3. **Enroll Student in Course**:
   - Main Menu → 3 (Enrollment Management)
   - Choose 1 (Enroll Student in Course)
   - Student ID = 1000
   - Course ID = 2000
   - Enrollment created with ID 3000, status = ACTIVE

4. **View Statistics**:
   - Main Menu → 4 (View Statistics)
   - Should show: 1 total student, 1 active student, 1 total course, 1 active course, 1 active enrollment

5. **Exit**:
   - Main Menu → 0 (Exit)
   - See goodbye message and application closes

---

## Troubleshooting

### Build Fails with "Cannot find symbol"

**Problem**: Missing or incorrect Java version

**Solution**:
```bash
# Check Java version
java -version

# Ensure JDK 21 or higher is installed
# Update JAVA_HOME environment variable if needed
export JAVA_HOME=/path/to/jdk-21  # Unix/Mac
set JAVA_HOME=C:\path\to\jdk-21   # Windows
```

### "Permission denied" when running gradlew (Unix/Mac)

**Problem**: Gradle wrapper not executable

**Solution**:
```bash
chmod +x gradlew
./gradlew build
```

### Application Doesn't Start After Build

**Problem**: Wrong JAR file or missing main class

**Solution**:
```bash
# Verify JAR was created
ls -l build/libs/

# Run with explicit main class
java -cp build/libs/LearnTrack-1.0-SNAPSHOT.jar com.airtribe.learntrack.Main
```

### Input Validation Errors

**Problem**: Application rejects valid input

**Solution**: Check input format requirements:
- IDs must be integers in valid ranges
- Names cannot be empty
- Email must be valid format or omitted
- Duration must be positive number

### "Scanner closed" Exception

**Problem**: Application crashes when reading input

**Solution**: Don't call `scanner.close()` manually - let ConsoleUtils manage it. Only call `ConsoleUtils.closeScanner()` on application exit.

---

## ID Range Reference

The application uses predefined ID ranges to prevent collisions between entity types:

| Entity Type | ID Range | Capacity | First ID | Last ID |
|-------------|----------|----------|----------|---------|
| Persons     | 100-999  | 900 IDs  | 100      | 999     |
| Students    | 1000-1999 | 1000 IDs | 1000     | 1999    |
| Courses     | 2000-2999 | 1000 IDs | 2000     | 2999    |
| Enrollments | 3000-3999 | 1000 IDs | 3000     | 3999    |
| Trainers    | 4000-4999 | 1000 IDs | 4000     | 4999    |

**Important Notes**:
- IDs are auto-generated by `IdGenerator` utility class
- Cannot manually assign IDs - system enforces range allocation
- Warning issued when 90% capacity reached in any range
- Exception thrown when range is exhausted

**Example IDs**:
- First student: 1000
- Second student: 1001
- First course: 2000
- First enrollment: 3000

---

## Performance Notes

### Memory Usage
- **In-Memory Storage**: All data stored in ArrayList (non-persistent)
- **Data Loss**: All data is lost when application exits
- **Capacity**: Can handle hundreds of entities efficiently

### Recommended Limits (for performance)
- Students: Up to 1000 (hard limit based on ID range)
- Courses: Up to 1000 (hard limit based on ID range)
- Enrollments: Up to 1000 (hard limit based on ID range)
- Trainers: Up to 1000 (hard limit based on ID range)

---

## Development Workflow

### Making Changes

1. **Edit Source Code**:
   ```bash
   # Edit files in src/main/java/com/airtribe/learntrack/
   ```

2. **Rebuild**:
   ```bash
   ./gradlew clean build
   ```

3. **Run**:
   ```bash
   ./gradlew run
   ```

### Viewing Build Output

**Class Files**: `build/classes/java/main/`
**JAR File**: `build/libs/LearnTrack-1.0-SNAPSHOT.jar`
**Build Reports**: `build/reports/`

### Cleaning Build Artifacts

```bash
./gradlew clean
```

This removes the `build/` directory.

---

## IDE-Specific Setup

### IntelliJ IDEA

1. **Open Project**:
   - File → Open → Select `LearnTrack` folder
   - IntelliJ auto-detects Gradle configuration

2. **Set JDK**:
   - File → Project Structure → Project SDK → Select JDK 21+

3. **Run Configuration**:
   - Already configured via Gradle
   - Or manually: Run → Edit Configurations → Add → Application
   - Main class: `com.airtribe.learntrack.Main`

### Eclipse

1. **Import Project**:
   - File → Import → Gradle → Existing Gradle Project
   - Select `LearnTrack` folder

2. **Set Java Version**:
   - Right-click project → Properties → Java Build Path
   - Ensure JRE System Library is JDK 21+

### VS Code

1. **Install Extensions**:
   - Java Extension Pack (by Microsoft)
   - Gradle for Java

2. **Open Folder**:
   - File → Open Folder → Select `LearnTrack`
   - VS Code auto-configures Java project

---

## Next Steps

After successful setup:

1. **Read Documentation**:
   - [README.md](../README.md) - Project overview
   - [Design_Notes.md](Design_Notes.md) - Design decisions
   - [Repository_Design.md](Repository_Design.md) - Repository architecture

2. **Explore Code**:
   - Start with `Main.java` to understand flow
   - Review entity classes (Person, Student, Course)
   - Study repository pattern implementation
   - Examine service layer business logic

3. **Experiment**:
   - Add students, courses, enrollments
   - Test validation (try invalid inputs)
   - Explore different menu options
   - View statistics after operations

4. **Extend**:
   - Add new features (e.g., Trainer management)
   - Implement file persistence (save/load data)
   - Add search and filtering capabilities
   - Create reports

---

## Support

For issues or questions:
- Review documentation in `docs/` folder
- Check code comments for implementation details
- Refer to JavaDoc in source files
- Contact your instructor or course materials

---

## Summary

**Quick Start Commands**:
```bash
# Build
./gradlew clean build

# Run
./gradlew run

# Clean
./gradlew clean
```

**Entry Point**: `src/main/java/com/airtribe/learntrack/Main.java`

**System**: Console-based, interactive menu-driven application

**Data Storage**: In-memory (non-persistent)

**Architecture**: Layered (Entity → Repository → Service → Presentation)

Happy coding! 🚀