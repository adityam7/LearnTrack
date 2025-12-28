# JVM Basics

## What is JDK, JRE, and JVM?

### JVM (Java Virtual Machine)
The JVM is like a translator that runs your Java programs. It's the engine that executes Java code on your computer. Think of it as a virtual computer that runs inside your actual computer. Different operating systems (Windows, Mac, Linux) have their own versions of the JVM, but they all understand the same Java code.

### JRE (Java Runtime Environment)
The JRE is a package that includes the JVM plus all the libraries and files needed to run Java applications. If you only want to run Java programs (not develop them), you just need the JRE. It's like having a media player that can play videos but can't create them.

### JDK (Java Development Kit)
The JDK is the complete toolkit for Java developers. It includes the JRE (which contains the JVM) plus development tools like the compiler (`javac`), debugger, and other utilities needed to write, compile, and debug Java programs. If you want to create Java applications, you need the JDK.

**Simple Hierarchy:**
```
JDK (Development tools + JRE)
  └── JRE (Runtime libraries + JVM)
        └── JVM (Execution engine)
```

## What is Bytecode?

Bytecode is an intermediate form of your Java code that sits between human-readable source code and machine code that computers understand.

When you write Java code in `.java` files, the Java compiler (`javac`) converts it into bytecode stored in `.class` files. This bytecode is not specific to any particular operating system or hardware - it's a universal format that any JVM can understand.

Think of bytecode like a universal language that the JVM knows how to read. Your computer's processor can't directly run bytecode, but the JVM translates it into machine code that your specific processor can execute.

**The Flow:**
```
Java Source Code (.java) → Compiler → Bytecode (.class) → JVM → Machine Code
```

## What Does "Write Once, Run Anywhere" Mean?

"Write once, run anywhere" (WORA) is Java's promise of platform independence. It means you can write your Java program on one computer and run it on any other computer that has a JVM installed, regardless of the operating system or hardware.

This works because Java code is compiled into bytecode, not directly into machine code for a specific processor. When you write a Java program on Windows and compile it, the resulting bytecode can run on Mac, Linux, or any other platform with a JVM. The JVM acts as a bridge between your bytecode and the specific machine it's running on, handling all the platform-specific details automatically.

For example, if you create a Java application on your Mac and give the `.class` files to someone using Windows or Linux, they can run it without any modifications - as long as they have a JVM installed. This is different from languages like C or C++, where you typically need to recompile your code for each different operating system.