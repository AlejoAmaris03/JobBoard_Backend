# 💼 Job Board Backend API (Spring Boot)

This is the backend API for a **Job Board Management System** developed using **Spring Boot**. The platform facilitates the creation, publication, and management of job offers while allowing users (recruiters and applicants) to interact through a streamlined hiring process.

> 💡 You can find the frontend here: [Job Board Frontend (Angular)](https://github.com/AlejoAmaris03/JobBoard_Frontend)

---

## 📌 Features

- 👥 User management:
  - Roles: **Admin**, **Recruiter** and **Applicant**
  - Secure authentication via JWT
  - 🔐 Google Authentication integration for easy login
- 📢 Recruiter can:
  - Post and manage job offers
  - Review applicant submissions
- 📝 Applicants can:
  - View and apply to job offers
  - Manage their own applications
- 📂 Admins can manage users and oversee platform activity
- 🔍 Filter and search job offers
- 📄 RESTful API architecture

---

## ⚙️ Tech Stack

- **Java 23+**
- **Spring Boot 3**
  - Spring Web
  - Spring Security (JWT/OAuth2)
  - Spring Data JPA
- **PostgreSQL**
- **Lombok**
- **Maven**

---

## 📁 Project Structure
<img width="259" height="244" alt="image" src="https://github.com/user-attachments/assets/f8ab0d86-b1a1-4f66-baec-af46cae7479a" />

---

## 🛠️ Installation

1. **Clone the repo**
   ```bash
   git clone https://github.com/AlejoAmaris03/JobBoard_Backend.git
   cd JobBoard_Backend-main

2. **Configure DB in application.properties**
   ```bash
    spring.datasource.username=your_user
    spring.datasource.password=your_password
    jwt.key=your_generated_key
  To generate a secure JWT key, open a terminal (CMD o PowerShell) and run:
  ```bash
    openssl rand -base64 32
  ```
  Copy the generated key and replace *your_generated_key* with it.

4. Run the project

5. The app should be running at: http://localhost:8080

### Example Endpoints
- 🟢 **GET** /job-posts – Retrieve all job offers
- 🟡 **POST** /auth/register – Register new user

### Access with Default Admin Credentials
  > 📝 You can log in with the following default administrator account, which is automatically created when the application starts.

👤 Email: admin@example.com  
🔑 Password: 123
 
- This account has full access to manage users and companies
