## Java Project: Library Management System

This is a Java console application that manages a library system including students, books, and loans.  
It uses a Microsoft Access database (`Biblio.mdb`) and the **UCanAccess** JDBC driver to handle database operations.

---

## Features

- Full CRUD operations (Create, Read, Update, Delete) for:
  - **Students**
  - **Books**
  - **Loans**
- Interactive text-based menu system
- Auto-incremented IDs and proper relational handling between tables
- Automatic loan dates using the system's current date
- Centralized database connection through a `Connexion` class

---

## Requirements

- Java 8 or higher
- Visual Studio Code with Java extensions installed
- A valid `Biblio.mdb` file (placed in the `src/` folder)
- The following `.jar` files in the `lib/` folder:
  - `ucanaccess-*.jar`
  - `jackcess-*.jar`
  - `hsqldb.jar`
  - `commons-lang-*.jar`
  - `commons-logging-*.jar`

---

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources  
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

---

## Getting Started

1. Clone or download the project
2. Place your `Biblio.mdb` database file inside the `src/` folder
3. Ensure all required `.jar` files are placed inside the `lib/` folder
4. Open the project in VS Code and run `Library.java`

---

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies.  
More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

---

## Author

- Academic Project — [`David Bruno`]  
- Institution — [`Quisqueya University`]