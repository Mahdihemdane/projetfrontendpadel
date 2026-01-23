# Padel Reservation System - Developed by Mahdi Hemdane

This document guides you through setting up, running, and using the Padel Court Reservation system.

## 1. Prerequisites

Ensure you have the following installed:
*   **Java Development Kit (JDK) 17** or higher.
*   **Node.js** (v18+) and **npm**.
*   **MySQL Server**.
*   **Maven** (optional, usually bundled with IDE or use `mvnw`).

## 2. Database Setup

1.  Open your MySQL client (Workbench, Command Line, etc.).
2.  Create the database:
    ```sql
    CREATE DATABASE padel_reservation;
    ```
3.  **Database Creds**:
    *   **Username**: `root`
    *   **Password**: `Root`
    *   **Database**: `padel_reservation` (Auto-created if needed).

## 3. Backend Setup (Spring Boot)

1.  Navigate to the `backend` directory.
2.  Run the application:
    ```bash
    mvn spring-boot:run
    ```
    *   Server starts on `http://localhost:8080`.

## 4. Frontend Setup (Angular)

1.  Navigate to the `frontend` directory.
2.  Install dependencies (if not done):
    ```bash
    npm install
    ```
3.  Start the development server:
    ```bash
    npm start
    ```
    *   The app will be available at `http://localhost:4200`.
    *   **Proxy**: API requests are forwarded to `localhost:8080` via `proxy.conf.json`.

## 5. User Guide & Features

### Authentication
*   **Register**: Go to `/register` to create a new account.
    *   Default role is `USER` (Member).
*   **Login**: Access `/login` to authenticate.

### Admin Features (Role: ADMIN)
To test Admin features, you may need to manually update a user's role in the database after registration:
```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'your-admin@email.com';
```

*   **Terrains Management** (`/terrains`):
    *   View list of padel courts.
    *   Add new courts (Name, Price, Location, State).
    *   Edit or Delete existing courts.
*   **Reservations Management** (`/reservations`):
    *   View all bookings from all users.
    *   **Validate** pending reservations (changes status to `CONFIRMED`).
    *   **Reject** reservations (changes status to `REJECTED`).

### Member Features (Role: USER)
*   **My Reservations** (`/reservations`):
    *   View history of your own bookings.
    *   Check status (Pending, Confirmed, Rejected).

## 6. Project Structure

*   **Backend**:
    *   `Entity`: Database models.
    *   `Repository`: Data access layer.
    *   `Service`: Business logic.
    *   `Controller`: REST API endpoints.
    *   `Security`: JWT configuration.
*   **Frontend**:
    *   `_services`: API communication (Auth, Terrain, Reservation).
    *   `layouts`: Admin/Auth layouts.
    *   `pages`: UI Components (Login, Register, Terrains, Reservations).

## 7. Troubleshooting

*   **CORS Issues**: Check `proxy.conf.json` in Angular or `@CrossOrigin` in Spring Controllers.
    *   **Error**: `Communications link failure` or `Access denied`.
    *   **Fix**:
        1.  **Crucial Step**: Run the SQL script `backend/database_setup.sql` in MySQL Workbench.
        2.  Check Firewall: Ensure port 3306 is allowed for Java.
        3.  Restart Backend: `mvn spring-boot:run`
*   **White Screen**: Check browser console (F12) for Angular errors.

