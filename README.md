# Academic Research Presentation & Evaluation System

A multi-role academic assessment management system built with **Java Swing**, designed to streamline research symposium submissions, evaluation scoring rubrics, and session coordination.

---

## 👥 Role-Based Architecture

### 1. Student Portal (`StudentDashboard.java`)
- Register research topic, abstract, presentation type (Oral vs Poster), and designated supervisor.
- Attach presentation slides/documents with real-time submission confirmation.
- Modern dual-panel layout with custom styled Swing components.

### 2. Evaluator Portal (`EvaluatorDashboard.java`)
- Review student research submissions and evaluate assigned presentations.
- Interactive rubric scoring criteria (Methodology, Clarity, Technical Depth, Q&A).
- Automated grade aggregation with qualitative written feedback.

### 3. Coordinator Dashboard (`CoordinatorDashboard.java`)
- Manage evaluation sessions, timetable allocations, and hall assignments.
- Real-time tracking of submission statuses and evaluator assignments.
- Session scheduling connecting students with evaluators.

---

## 🔑 Demo Accounts

| Role | Username | Password | Full Name |
| :--- | :--- | :--- | :--- |
| **Coordinator** | `admin` | `admin123` | Prof. Davis |
| **Student** | `ST001` | `student123` | John Doe |
| **Evaluator** | `EV001` | `eval123` | Dr. Sarah Smith |

---

## 🚀 How to Run

### Prerequisites
- **Java JDK 11+** installed

### Execution
```bash
chmod +x run.sh compile.sh
./run.sh
```

Or manually:
```bash
mkdir -p bin
javac -d bin *.java
java -cp bin LoginFrame
```
