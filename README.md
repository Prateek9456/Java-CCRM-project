# Java-CCRM-project
# Campus Course & Records Manager (CCRM)

## Project Overview
CCRM is a comprehensive console-based Java application for managing campus course records, student enrollments, grades, and transcripts. Built with Java SE, it demonstrates OOP principles, design patterns, and modern Java features.

## Prerequisites & Installation

### System Requirements
- Java JDK 17 or higher
- VS Code with Java Extension Pack
- Git (for version control)

### How to Run

#### Using VS Code (Recommended)
1. Clone the repository
2. Open the project folder in VS Code
3. Install the Java Extension Pack if not already installed
4. Navigate to `src/edu/ccrm/Main.java`
5. Click "Run" above the main method or press `F5`

#### Using Command Line
```bash
# Compile
javac -d bin -sourcepath src src/edu/ccrm/**/*.java src/edu/ccrm/Main.java

# Run (with assertions enabled)
java -ea -cp bin edu.ccrm.Main
```

## Evolution of Java

- **1995**: Java 1.0 released by Sun Microsystems
- **1998**: Java 2 (J2SE 1.2) introduces Collections Framework
- **2004**: Java 5 adds generics, annotations, enums
- **2011**: Oracle acquires Sun; Java 7 released
- **2014**: Java 8 introduces lambdas, streams, functional programming
- **2017**: Java 9 adds modules (Project Jigsaw)
- **2018**: Java 11 (LTS) - first long-term support after Java 8
- **2021**: Java 17 (LTS) - pattern matching, sealed classes
- **2023**: Java 21 (LTS) - virtual threads, pattern matching improvements

## Java Platform Comparison

| Feature | Java ME | Java SE | Java EE |
|---------|---------|---------|---------|
| **Target** | Mobile/Embedded devices | Desktop/Server apps | Enterprise applications |
| **Size** | Minimal footprint | Standard libraries | Extended libraries |
| **APIs** | Limited subset | Core Java APIs | Enterprise APIs (Servlets, EJB, JPA) |
| **Use Cases** | IoT, mobile phones | Desktop apps, Android | Web apps, microservices |
| **Memory** | < 1MB | 64MB+ | 512MB+ |
| **Examples** | Smart cards, sensors | IntelliJ IDEA, Minecraft | Banking systems, e-commerce |

## Java Architecture

### JDK (Java Development Kit)
- Complete development environment
- Includes compiler (javac), debugger, profiler
- Contains JRE + development tools
- Required for developing Java applications

### JRE (Java Runtime Environment)
- Runtime execution environment
- Contains JVM + core libraries
- Required to run Java applications
- No development tools included

### JVM (Java Virtual Machine)
- Abstract computing machine
- Executes Java bytecode
- Platform-specific implementation
- Provides memory management, security, portability

**How they interact:**
```
Source Code (.java) → JDK Compiler (javac) → Bytecode (.class) → JRE/JVM → Execution
```

## Installing Java on Windows

### Step 1: Download JDK
1. Visit [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
2. Download JDK 17 or higher for Windows x64

### Step 2: Install JDK
1. Run the installer
2. Follow installation wizard
3. Default path: `C:\Program Files\Java\jdk-17`

### Step 3: Set Environment Variables
1. Open System Properties → Advanced → Environment Variables
2. Add `JAVA_HOME`:
   - Variable name: `JAVA_HOME`
   - Variable value: `C:\Program Files\Java\jdk-17`
3. Update `PATH`:
   - Add: `%JAVA_HOME%\bin`

### Step 4: Verify Installation
```cmd
java -version
javac -version
```

### Screenshot Placeholder
*[Java Installation Verification Screenshot]*

## VS Code Setup

### Step 1: Install VS Code
1. Download from [code.visualstudio.com](https://code.visualstudio.com/)
2. Install with default settings

### Step 2: Install Java Extension Pack
1. Open VS Code
2. Go to Extensions (Ctrl+Shift+X)
3. Search "Extension Pack for Java"
4. Install the pack by Microsoft

### Step 3: Create New Project
1. `Ctrl+Shift+P` → "Java: Create Java Project"
2. Select "No build tools"
3. Choose project location
4. Enter project name: "campus-ccrm"

### Step 4: Configure Run Settings
1. Open `.vscode/launch.json`
2. Add VM arguments for assertions:
```json
{
