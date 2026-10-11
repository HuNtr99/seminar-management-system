# Seminar Management System (SMS)

A standalone **Java Swing** desktop application developed for the Faculty of Computing and Informatics (FCI) to streamline annual **Postgraduate Academic Research Seminars**.

Designed following **Object-Oriented Analysis and Design (OOAD)** principles and persistent file-based data structures.

---

## 👥 System Architecture & User Roles

The system implements a centralized `User` inheritance hierarchy with role-specific dashboards:

### 1. 🎓 Student Module (`StudentDashboard.java`)
- **Research Registration:** Submit presentation details (Title, Abstract, Supervisor, and Presentation Type: *Oral* vs *Poster*).
- **Presentation Upload:** Register and link presentation files/slides.
- **Visual Status Tracking:** Modern split-pane Swing layout with real-time feedback.

### 2. 👨‍🏫 Evaluator Module (`EvaluatorDashboard.java`)
- **Review Submissions:** Access assigned student research abstracts and materials.
- **Rubric Scoring System:** Interactive numerical scoring criteria across:
  - Methodology & Technical Depth
  - Presentation Clarity & Structure
  - Research Significance
  - Q&A Defense
- **Feedback Generation:** Calculate total scores out of 40 and record qualitative feedback comments.

### 3. 🏛️ Coordinator Module (`CoordinatorDashboard.java`)
- **Session Scheduling:** Assign examination dates, halls, students, and evaluators.
- **Symposium Overview:** Track live progress of all registered presentations.
- **CSV Report Export:** Automatically compile and export comprehensive symposium results to `Seminar_Report.csv`.

---

## 📐 UML Class Architecture

The complete system architecture and class diagrams are included in [`docs/UML_Class_Diagram.pdf`](docs/UML_Class_Diagram.pdf):
- **Base Class:** `User` (`username`, `password`, `name`, `email`, `role`)
- **Derived Classes:** `Student`, `Evaluator`, `Coordinator`
- **Persistence Layer:** `FileHandler` (`users.txt`, `sessions.txt`, `submissions.txt`, `evaluations.txt`)
- **Controller/UI:** `LoginFrame`, `StudentDashboard`, `EvaluatorDashboard`, `CoordinatorDashboard`

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
