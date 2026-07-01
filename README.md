# Pomodoro Study Tracker — Spring Boot REST API

A  study-session tracker built around the Pomodoro technique. Users run
timed study sessions against a chosen topic; the backend persists every session and
serves aggregate statistics. Built as a Spring Boot REST API.

**Stack:** Java 21 · Spring Boot 3.5 · Spring Security 6 (JWT) · Spring Data JPA ·
H2  · springdoc-openapi, swagger

## Features
- **JWT authentication** permission based authorities enforced by http methods
- **Session CRUD** with bean validation (<=50 min and startTime<stopTime)
- **Server-side pagination & filtering** — 15 per page filterable by topic and date
- **Statistics endpoint** — total sessions, average session in hours(da,y week, total), longest daily streak, favorite topic
- **OpenAPI / Swagger UI** docs with the bearer scheme included

## Quick Start

API at `pomodoro-api-production-9301.up.railway.app`, Swagger UI at `https://pomodoro-api-production-9301.up.railway.app/swagger-ui/index.html`.

Get a token by POSTing to `/token` with `{"subject":"demo","permissions":["READ","WRITE"]}`,
then click authorize and introduce the valid token
<img width="2880" height="1024" alt="image" src="https://github.com/user-attachments/assets/5398c7e6-88ef-468a-83f1-8030eb6d85f3" />
## Example Requests

**Create a session**
POST `/api/sessions`

Request body:
```json
{ "startTime": 1719800000000, "stopTime": 1719801500000, "duration": 1500000, "topicId": 1 }
```

**List sessions** (paginated, filterable)
GET `/api/sessions

Response:
```json
{
  "content": [
    { "id": 1, "startTime": 1719800000000, "stopTime": 1719801500000, "duration": 1500000, "topicId": 1 }
  ],
  "totalElements": 1,
  "totalPages": 1
}
```

**Replace a session**
PUT `/api/sessions/1`

Request body:
```json
{ "startTime": 1719800000000, "stopTime": 1719803000000, "duration": 3000000, "topicId": 1 }
```

**Partially update a session**
PATCH `/api/sessions/1`

Request body:
```json
{ "duration": 1800000 }
```

**Delete a session**
DELETE `/api/sessions/1`

Response: `204 No Content`

**Get statistics**
GET `/api/statistics`

Example of a statistics response:
```json
{
  "totalSessions": 94,
  "studyHours": 41.5,
  "averageSession": 26,
  "today": 5,
  "thisWeek": 21,
  "longestStreak": 12,
  "favoriteTopic": "Algorithms"
}
```


Users pick a duration of 25, 30 or 50 min, then select a topic by clicking. Start begins the session with
the current session and stop ends it and records it. After three short breaks the long break is activated. History is filterable by date and/or topic.
The statistics display is a weekly daily minutes in a chart. Theme preference is stored in localStorage persisting across visits.
