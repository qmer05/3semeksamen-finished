### 3.3.2 Test the endpoints using a dev.http file. Document the output in your README.md file to verify the functionality.

GET http://localhost:7000/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:29:47 GMT
Content-Type: application/json
Content-Length: 659

[
{
"id": 1,
"starttime": [
9,
0
],
"endtime": [
12,
0
],
"startposition": "Montreal",
"name": "Hiking",
"price": 1000,
"category": "FOREST",
"guide": null
},
{
"id": 2,
"starttime": [
10,
0
],
"endtime": [
13,
0
],
"startposition": "Quebec",
"name": "Biking",
"price": 2000,
"category": "CITY",
"guide": null
},
{
"id": 3,
"starttime": [
11,
0
],
"endtime": [
14,
0
],
"startposition": "Paris",
"name": "Skiing",
"price": 3000,
"category": "SNOW",
"guide": null
},
{
"id": 4,
"starttime": [
12,
0
],
"endtime": [
15,
0
],
"startposition": "Berlin",
"name": "Swimming",
"price": 4000,
"category": "LAKE",
"guide": null
},
{
"id": 5,
"starttime": [
13,
0
],
"endtime": [
16,
0
],
"startposition": "Copenhagen",
"name": "Fishing",
"price": 5000,
"category": "SEA",
"guide": null
}
]
Response file saved.
> 2024-11-04T102947.200.json

GET http://localhost:7000/api/trips/2

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:30:56 GMT
Content-Type: application/json
Content-Length: 129

{
"id": 2,
"starttime": [
10,
0
],
"endtime": [
13,
0
],
"startposition": "Quebec",
"name": "Biking",
"price": 2000,
"category": "CITY",
"guide": null
}
Response file saved.
> 2024-11-04T103056.200.json

Response code: 200 (OK); Time: 24ms (24 ms); Content length: 129 bytes (129 B)

POST http://localhost:7000/api/trips/

HTTP/1.1 201 Created
Date: Mon, 04 Nov 2024 09:34:56 GMT
Content-Type: application/json
Content-Length: 128

{
"id": 6,
"starttime": [
11,
0
],
"endtime": [
15,
0
],
"startposition": "Rome",
"name": "Running",
"price": 2000,
"category": "CITY",
"guide": null
}
Response file saved.
> 2024-11-04T103456.201.json

Response code: 201 (Created); Time: 65ms (65 ms); Content length: 128 bytes (128 B)

PUT http://localhost:7000/api/trips/2

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:39:13 GMT
Content-Type: application/json
Content-Length: 131

{
"id": 2,
"starttime": [
16,
0
],
"endtime": [
17,
0
],
"startposition": "Quebec",
"name": "Crossfit",
"price": 1000,
"category": "CITY",
"guide": null
}
Response file saved.
> 2024-11-04T103913.200.json

Response code: 200 (OK); Time: 80ms (80 ms); Content length: 131 bytes (131 B)

DELETE http://localhost:7000/api/trips/4

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 09:39:50 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 23ms (23 ms); Content length: 0 bytes (0 B)


PUT http://localhost:7000/api/trips/1/guides/2

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:46:37 GMT
Content-Type: text/plain
Content-Length: 0

<Response body is empty>

Response code: 200 (OK); Time: 150ms (150 ms); Content length: 0 bytes (0 B)

### 3.3.5
### Why use PUT instead of POST for adding a guide to a trip?

Fordi PUT er idempoten, som betyder at man kan lave flere identiske requests og det vil
have det samme effekt som en enkelt request. Det er mere hensigtsmæssigt for at opdatere eller
oprette en specifik ressource på en kendt URL. I modsætning er POST ikke idempotent
og bruges typisk til at oprette nye ressourcer.

### 8.3
Når brugeren f.eks. logger ind skal der laves en token, som skal være med i headeren 
for at kunne komme ind på de beskyttede endpoints (i routes klassen den angivne rolle). 
Restassured testen skal således have dette med i testen:

.header("Authorization", "Bearer " + token)
