# 🎓 Online Result Management System (CI/CD with Jenkins)

A full-stack **Result Management System** built using **Java Spring Boot** that allows administrators to manage and publish student results.  
This project demonstrates **DevOps practices** by integrating **Docker and Jenkins CI/CD pipelines** for automated build, testing, and deployment.

---

# 🚀 Features

- Add, update, and manage student results
- View student academic records
- REST API based backend using **Spring Boot**
- Responsive user interface using **Bootstrap**
- Database integration with **MySQL**
- **Docker containerization** for easy deployment
- **Jenkins CI/CD pipeline** for automated build and deployment

---

# 🛠 Tech Stack

## Backend
- Java
- Spring Boot
- Hibernate
- REST APIs

## Frontend
- HTML
- CSS
- Bootstrap
- Angular

## Database
- MySQL
- MySQL Workbench

## DevOps
- Docker
- Jenkins
- CI/CD Pipelines

## Tools
- Git
- GitHub
- IntelliJ IDEA / Eclipse
- VS Code

---

# 📂 Project Structure

```
Project_03_Jenkins
│
├── src
│   ├── main
│   │   ├── java
│   │   ├── resources
│   │   └── templates
│
├── Dockerfile
├── Jenkinsfile
├── pom.xml
└── README.md
```

---

# ⚙️ Installation & Setup

## 1️⃣ Clone Repository

```bash
git clone https://github.com/ChaitanyaBhatt147/Project_03_Jenkins.git
cd Project_03_Jenkins
```

---

## 2️⃣ Configure Database

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/resultdb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

---

## 3️⃣ Build Project

```bash
mvn clean install
```

---

## 4️⃣ Run Application

```bash
mvn spring-boot:run
```

Application will run at:

```
http://localhost:8080
```

---

# 🐳 Docker Setup

## Build Docker Image

```bash
docker build -t result-system .
```

## Run Docker Container

```bash
docker run -p 8080:8080 result-system
```

---

# ⚙️ Jenkins CI/CD Pipeline

This project uses **Jenkins Pipeline Automation** to:

- Pull latest code from GitHub
- Build project using Maven
- Run automated tests
- Build Docker image
- Deploy application automatically

Pipeline configuration file:

```
Jenkinsfile
```

---

# 📸 Screenshots

You can add screenshots here:

```
screenshots/
 ├── login-page.png
 ├── dashboard.png
 └── result-page.png
```

---

# 🌐 Repository

GitHub:  
https://github.com/ChaitanyaBhatt147/Project_03_Jenkins

---

# 👨‍💻 Author

**Chaitanya Bhatt**

📧 Email: bhattchaitanya43@gmail.com  
💼 LinkedIn: https://linkedin.com/in/chaitanya-bhatt  
💻 GitHub: https://github.com/ChaitanyaBhatt147

---

⭐ If you like this project, consider giving it a **star**!
