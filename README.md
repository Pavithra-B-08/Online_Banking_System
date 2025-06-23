# 💳 Java Banking System (MySQL + Swing)

This is a simple **Java-based banking system** that allows users to register, login, check balance, deposit, and withdraw money. It uses:

- Java SE (JDK 11+)
- Swing GUI
- MySQL Database
- JDBC

## 💡 Features

- Register/Login
- Deposit and Withdraw
- Balance Display
- Data stored in MySQL

## 🛠️ Setup

1. Import project in Eclipse
2. Add `mysql-connector.jar` to build path
3. Create DB using `DB_Setup.sql` file
4. Run `LoginForm.java` to start UI

## 📂 Structure
src/
├── com.bank.db/
├── dao/
├── model/
├── gui/


## 📊 DB Setup

```sql
CREATE DATABASE bankdb;
USE bankdb;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    balance DOUBLE DEFAULT 0
);

## 📷 Screenshots

### 🔐 Login Screen
![Login](screenshots/Login.png)

### 📊 User Dashboard
![Dashboard](screenshots/Dashboard.png)


