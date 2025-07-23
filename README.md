# 🗂️ Java Task Manager Application

A desktop-based **Task Manager App** built with **Java Swing** and **MySQL** that allows users to register, log in, and manage tasks efficiently using a professional interface with calendar-based deadlines and smart status control.

---

## 📌 Features

- 🔐 **User Registration & Login** with email validation  
- 📅 **Task Creation** with a calendar-based deadline picker  
- ✅ **Status Management** (Pending, In Progress, Completed)  
- 🚫 **Status Rules Enforcement**  
  - Can't move from In Progress → Pending  
  - Only Completed tasks can be deleted  
- 🔄 **Change Status** via dropdown  
- 🟥 **Highlight Overdue Tasks**  
- 🔒 **Non-editable Task Table** (read-only display)  
- 🔘 **Logout Functionality**  
- 🖼️ **Modern GUI Design** inspired by JeruHub  
- ❌ **No past dates allowed** when setting task deadlines  

---

## 🚀 How It Works

1. **User opens the application**  
2. **Registers** (with name, email, and password – email must be valid)  
3. **Logs in**  
4. **Adds a task** using title, description, status, and calendar deadline  
5. Task is always added as **Pending**  
6. Can change task status to **In Progress** or **Completed**  
7. Can **delete tasks** only if they are **Completed**

---

## 🧰 Tech Stack

- **Language:** Java  
- **Database:** MySQL  
- **GUI:** Java Swing  
- **Calendar:** JDateChooser (Toedter Library)  
- **Tools:** IntelliJ IDEA / VS Code  

---

## 🖼️ UI Overview

- **Login Page**: Email & password fields with validation  
- **Registration Page**: Full form with name, email, and password  
- **Task Manager Panel**:  
  - Input fields for task  
  - JDateChooser for deadlines  
  - JTable displaying tasks (read-only)  
  - Buttons: `Add Task`, `Change Status`, `Delete`, `Logout`

---

## ⚙️ Installation

### 1. Clone the Repo

```bash
git clone https://github.com/your-username/task-manager-java.git
