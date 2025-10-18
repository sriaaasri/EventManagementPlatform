🎟️ Event Ticket Platform

A web-based event management system that enables organizers to create events, manage ticket sales, and generate QR-coded tickets for attendees — streamlining event creation, ticket distribution, and on-site validation.
 
📖 Table of Contents:
📜 Project Summary
📘 Key Definitions
👤 User Stories
✅ Acceptance Criteria
⚙️ Tech Stack
🚀 How to Run
🧩 Folder Structure
📈 Future Enhancements
📄 License

📜 Project Summary
Event Ticket Platform is a full-stack web application that simplifies event organization and attendance management.
It provides tools for:

1) Creating and managing events
2) Selling digital tickets
3) Generating QR-coded tickets
4) Validating ticket authenticity at entry

This platform is designed for organizers, attendees, and event staff to collaboratively manage events efficiently.

📘 Key Definitions
__________________________________________________________________________________________________________________________________
| Term        | Description                                                                                                       |
| ----------- | ----------------------------------------------------------------------------------------------------------------- |
| **Event**   | A planned gathering or occasion with a specific date, time, and venue requiring tickets for entry.                |
| **Ticket**  | A digital document granting access to an event, containing event details and a **unique QR code** for validation. |
| **QR Code** | A machine-readable code used to store and verify ticket information during entry validation.                      |
___________________________________________________________________________________________________________________________________


👤 User Stories

🧩 Create Event
As an event organizer,
I want to create and configure a new event with details like date, venue, and ticket types
So that I can start selling tickets to attendees.

✅ Acceptance Criteria
Organizer can input event name, date, time, and venue.
Organizer can set multiple ticket types with different prices.
Organizer can specify total available tickets per type.
Created events are visible on the platform.

💳 Purchase Event Ticket
As an event goer,
I want to purchase the correct ticket for an event
So that I can attend and experience the event.

✅ Acceptance Criteria
Event goer can search for available events.
Can browse and select ticket types.
Can purchase tickets securely.

🧾 Validate Tickets
As an event staff member,
I want to scan attendee QR codes at entry
So that I can verify ticket authenticity.

✅ Acceptance Criteria
Staff can scan QR codes using mobile or tablet devices.
System displays valid/invalid ticket status instantly.
Prevents duplicate use of tickets.
Staff can manually input ticket numbers if scanning fails.

⚙️ Tech Stack
_______________________________________________________
| Layer                  | Technology                  |
| ---------------------- | --------------------------- |
| **Frontend**           | React.js, Tailwind CSS      |
| **Backend**            | Spring Boot (Java)          |
| **Database**           | MySQL                       |
| **Authentication**     | JWT / OAuth (optional)      |
| **QR Code Generation** | `ZXing` library             |
| **Containerization**   | Docker, Docker Compose      |
| **Version Control**    | Git + GitHub                |
________________________________________________________

🚀 How to Run
🧩 Run Locally

Backend
cd Backend
.\mvnw clean package

java -jar <jar_file_name>.jar

or you can run manually using applicaion class

Frontend
cd frontend
npm install
npm run dev

Access

Frontend: http://localhost:5173
Backend: http://localhost:8080

🐳 Run with Docker

Only backend and keycloak applications are configured in docker compose file , we have to run frontend appliation manually.

1) build Backend image

cd Backend
docker build -t event .

2) cd ..
docker compose up -d

now backend and keycloak containers will be up and running. 


🧩 Folder Structure

event-ticket-platform/
    backend/           # Spring Boot application
        src/                 
        pom.xml
        Dockerfile
    frontend/           # React.js application
        src/
        package.json
        Dockerfile
    docker-compose.yml
    README.md


📈 Future Enhancements
Email notifications for ticket purchases
Payment gateway integration (Razorpay/Stripe)
Admin panel for platform-level management
Advanced analytics dashboard
Multi-language and timezone support



