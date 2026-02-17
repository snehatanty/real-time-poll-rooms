\# Real-Time Poll Rooms



A full-stack real-time polling web application built using Spring Boot, MySQL, WebSocket (STOMP), and deployed on Railway.



---



\##Live Demo



Deployed URL:

https://real-time-poll-rooms-production.up.railway.app/



---



\##Tech Stack



Backend:

\- Spring Boot

\- Spring Data JPA

\- MySQL

\- WebSocket (STOMP + SockJS)



Frontend:

\- HTML

\- CSS

\- JavaScript (Fetch API)



Deployment:

\- Railway



---



\##Features



\- Create polls with multiple options

\- Optional expiration time

\- Shareable poll link

\- Real-time vote updates using WebSocket

\- Persistent data storage in MySQL

\- Anti-abuse voting mechanisms



---



\##Fairness / Anti-Abuse Mechanisms



1\. IP-based restriction

   Each IP address can vote only once per poll.



2\. Client-based protection

   Vote records are stored in database to prevent duplicate voting.



---



\##Edge Cases Handled



\- Minimum 2 options validation

\- Invalid option ID handling

\- Option must belong to the correct poll

\- Poll not found (404)

\- Expired poll vote prevention

\- Duplicate voting detection



---



\##Known Limitations



\- IP-based restriction may block users sharing same network.

\- No authentication system.

\- No vote editing or deletion.

\- Basic UI (not styled with frameworks).

 to Run Locally



1\. Clone repository:

   git clone https://github.com/snehatanty/real-time-poll-rooms.git



2\. Navigate to project:

   cd realtimepoll



3\. Configure database in application.properties:

   spring.datasource.url=jdbc:mysql://localhost:3306/realtime\_poll

   spring.datasource.username=root

   spring.datasource.password=yourpassword



4\. Run application:

   mvn spring-boot:run



5\. Open browser:

   http://localhost:8080

---



\#API Endpoints



Create Poll:

POST /api/polls



Get Poll:

GET /api/polls/{pollId}



Vote:

POST /api/polls/{pollId}/vote



WebSocket Topic:

Subscribe to /topic/polls/{pollId}



---



Developed as part of assignment.

