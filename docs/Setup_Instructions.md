# Setup Instructions

## JDK Version

This project uses **OpenJDK version 25.0.1**.

- **OpenJDK Runtime Environment**: build 25.0.1+8-27
- **OpenJDK 64-Bit Server VM**: build 25.0.1+8-27, mixed mode, sharing

## Hello World Program Explanation

The main entry point of the application is located in `src/main/java/com/airtribe/learntrack/Main.java`.

### Program Overview

The program demonstrates basic Java syntax with:

1. **Welcome Message**: Prints "Hello and welcome!" to the console using `System.out.printf()`

2. **Loop Demonstration**: Uses a for loop to iterate from 1 to 5, printing each number with the format "i = [number]"

### How to Run

To run the Hello World program:

```bash
# Using Gradle wrapper
./gradlew run

# Or compile and run directly
javac src/main/java/com/airtribe/learntrack/Main.java
java -cp src/main/java com.airtribe.learntrack.Main
```

### Expected Output

```
Hello and welcome!
i = 1
i = 2
i = 3
i = 4
i = 5
```

### Code Structure

```java
public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
    }
}
```

The program follows standard Java conventions with a `main` method as the entry point, demonstrating basic output operations and control flow structures.