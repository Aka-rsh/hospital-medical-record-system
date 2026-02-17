# 🏥 Hospital Medical Record System

**A robust Spring Boot REST API designed to manage the lifecycle of a patient's visit—from booking an appointment to issuing a prescription. This system is built with a focus on Data Integrity and Strict Business Workflows.**

---

## 🚀 Overview
This project simulates a real-world hospital environment where data must follow a strict logical sequence. Unlike basic CRUD applications, this system enforces healthcare regulations through a "Chain of Validation."



## 🛠 Business Logic & Rules
To ensure data accuracy, I implemented the following constraints:

1. **Appointment-First Rule**: A `Medical Record` can ONLY be created if the system verifies a `COMPLETED` appointment for that specific Patient and Doctor.
2. **Prescription Gatekeeper**: A `Prescription` cannot exist in isolation; it must be linked to an existing `Medical Record`.
3. **Integrity Mapping**: Utilized `@OneToOne` and `@ManyToOne` JPA mappings with unique constraints to prevent duplicate prescriptions or "ghost" records.

## 💻 Tech Stack
- **Framework:** Spring Boot 3.x
- **Language:** Java 17
- **Database:** PostgreSQL
- **Persistence:** Spring Data JPA (Hibernate)
- **API Testing:** Postman
- **IDE:** Eclipse / STS



## 📋 API Endpoints

### Appointments
- `POST /appointments/add` - Schedule a new visit.
- `PUT /appointments/complete/{id}` - Mark an appointment as finished.

### Medical Records
- `POST /medical-records/create` - Generate a diagnosis (Requires completed appointment).
- `GET /medical-records/all` - Fetch all history.

### Prescriptions
- `POST /prescriptions/add/{recordId}` - Assign medicine to a specific record.

## ⚙️ How to Run
1. Clone the repository.
2. Update `src/main/resources/application.properties` with your PostgreSQL credentials.
3. Run the project as a **Spring Boot App** from Eclipse.
4. Use the provided API endpoints via Postman.
