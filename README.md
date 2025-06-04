# 🧾 Excel-Based Full Stack User CRUD App

This full-stack application allows users to **Create**, **Read**, **Update**, and **Delete** (CRUD) personal information.  
User data is saved to an **Excel file** on the backend using **Java Spring Boot**, and managed through a **React frontend**.

---

## 📁 Project Structure

anubhav_project/
├── backend/ # Java Spring Boot app (Excel file as DB)
└── frontend/ # ReactJS app (user interface)


---

## 🚀 How to Run the Project

### 🧩 Prerequisites

#### Frontend:
- Node.js (v18 or newer)
- npm

#### Backend:
- Java 24
- Maven
- Excel-compatible file system (no DB needed)

---

## 🧱 Backend Setup (Spring Boot)

### 🔧 Steps:

1. Open terminal in the `backend/` directory.
2. Install dependencies and run:

```bash
cd backend
mvn clean install
mvn spring-boot:run
```


## 🧱 Frontend Setup (React JS)

### 🔧 Steps:

1. Open terminal in the `frontend/` directory.
2. Install dependencies and run:

```bash
cd frontend
npm install
npm run dev
```
