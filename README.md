# Job Scheduler & Automation System

A full-stack Job Scheduling Dashboard that simulates a background task automation engine. This system allows users to create jobs, track their status through a lifecycle (Pending → Running → Completed), and automatically trigger external webhooks upon completion.

---

## 🚀 Live Demo & Repositories

| Component | Live Link | Source Code |
| :--- | :--- | :--- |
| **Frontend** | [View Live Dashboard](https://job-scheduler-frontend-nine.vercel.app) | [GitHub Repo](https://github.com/arun3122/job-scheduler-frontend) |
| **Backend** | [View API Base](https://job-scheduler-backend-fkyd.onrender.com) | [GitHub Repo](https://github.com/arun3122/job-scheduler-backend) |

> **Note:** The backend is hosted on Render's free tier. Please allow up to **60 seconds** for the server to wake up on the very first request.

---

## 🛠 Tech Stack

### Frontend
- **Framework:** React.js (Create React App)
- **Styling:** Tailwind CSS
- **Icons:** Lucide React
- **Deployment:** Vercel

### Backend
- **Framework:** Java Spring Boot (v3.x)
- **Build Tool:** Maven
- **Database:** H2 In-Memory Database
- **Deployment:** Render (Dockerized)

> **Architectural Decision:** While the assignment suggested Node.js/MySQL, I opted for **Java Spring Boot** and **H2** to demonstrate strong type safety and robust threading capabilities for the job execution simulation. The H2 database was chosen to ensure the application is portable and requires zero configuration for the reviewer to run locally.

---

## ✨ Key Features
1. **Create Jobs:** Users can define tasks with a Name, Priority (Low/Medium/High), and JSON Payload.
2. **Dashboard:** A clean UI to view all jobs, capable of filtering by Status and Priority.
3. **Job Simulation:**
   - **Trigger:** Clicking "Run" initiates the process.
   - **Processing:** The backend simulates a 3-second delay (representing heavy computation).
   - **Completion:** Status updates to "Completed" automatically.
4. **Webhook Integration:** Upon job completion, the system sends a real POST request to an external URL (e.g., `webhook.site`) containing the job results.

---

## 🏗 System Architecture

### Database Schema (Jobs Table)
| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | BIGINT | Primary Key (Auto-increment) |
| `taskName` | VARCHAR | Name of the task |
| `priority` | VARCHAR | Low, Medium, or High |
| `status` | VARCHAR | pending, running, completed |
| `payload` | TEXT | JSON data required for the job |
| `createdAt` | TIMESTAMP | Creation time |
| `updatedAt` | TIMESTAMP | Last update time |

### API Endpoints
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/jobs` | Create a new job |
| `GET` | `/jobs` | List all jobs (supports `?status=` & `?priority=`) |
| `GET` | `/jobs/:id` | Get details of a specific job |
| `POST` | `/run-job/:id` | Trigger job execution & webhook |

---

## ⚙️ Local Setup Instructions

### Prerequisites
- Node.js & npm
- Java JDK 17+
- Maven (optional, wrapper included)

### 1. Backend Setup
```bash
git clone [https://github.com/arun3122/job-scheduler-backend.git](https://github.com/arun3122/job-scheduler-backend.git)
cd job-scheduler-backend
./mvnw spring-boot:run