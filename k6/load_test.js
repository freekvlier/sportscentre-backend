//k6 run load_test.js
import http from 'k6/http';
import { check, group, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '3m', target: 500 },
        { duration: '3m', target: 1000 },
        { duration: '3m', target: 0 }, // ramp-down to 0 users
    ],

};

const BASE_URL = 'http://34.79.143.12';

export default () => {
    const payload = JSON.stringify({
                                        "name": "testworkout",
                                        "exercises": [
                                            {
                                                "exerciseType": "testexercise",
                                                "weight": "100",
                                                "sets": "2",
                                                "reps": "10"
                                            },
                                            {
                                                "exerciseType": "testexercise2",
                                                "weight": "110",
                                                "sets": "1",
                                                "reps": "5"
                                            }
                                        ],
                                        "date":"2024-01-10T13:45:00.000Z"
                                    });

    const params = {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ii1LSTNROW5OUjdiUm9meG1lWm9YcWJIWkdldyJ9.eyJhdWQiOiI1ZDRkZjA5OS1iYjcwLTQ4ODctODYxNy1lZTAyZTAwY2VkOWEiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vNjQ0NjE3ZTctMTgwMS00ZWUzLTgzOGMtMjc2NWUzNzNjZmRlL3YyLjAiLCJpYXQiOjE2NzM5OTQxNTMsIm5iZiI6MTY3Mzk5NDE1MywiZXhwIjoxNjczOTk5MjIyLCJhaW8iOiJBVFFBeS84VEFBQUFvd0liTmVNM3doZXZQSVZNbEZWckxWeG9JOWZFU2NhYk9Yc011OG45SFBibHBOM2RhT3BHNTZOS242cTh5NUwyIiwiYXpwIjoiNWQ0ZGYwOTktYmI3MC00ODg3LTg2MTctZWUwMmUwMGNlZDlhIiwiYXpwYWNyIjoiMSIsIm5hbWUiOiJ0ZXN0Iiwib2lkIjoiNzcyYWJjNmMtYTk3Yy00Nzc2LWJlMmYtNjg1ODk3ZWFlNzNjIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidGVzdEBzcG9ydHNjZW50cmVhcHAub25taWNyb3NvZnQuY29tIiwicmgiOiIwLkFVNEE1eGRHWkFFWTQwNkRqQ2RsNDNQUDNwbndUVjF3dTRkSWhoZnVBdUFNN1pxREFKTS4iLCJyb2xlcyI6WyJBZG1pbiJdLCJzY3AiOiJVc2VyLlJlYWQiLCJzdWIiOiJqenRXYlRzUVUxWTBUNDVSSGhMQ2FzWnJuNUFmT19jdmI0ZElZOGJKWTZVIiwidGlkIjoiNjQ0NjE3ZTctMTgwMS00ZWUzLTgzOGMtMjc2NWUzNzNjZmRlIiwidXRpIjoiNnZWanVyekM4VUNfMXBCU0MzQThBQSIsInZlciI6IjIuMCJ9.f4ALBLaxHXMw6EC_BAnBbkbfoyunfQu1mwjS79L9B2Kw6re8NTIgfdjkj--3SkGn51LeXviQb7G5qwOE95aAqE8sTORX87_g1NFcpQI2Z6N_iWxz0UIZ-Iulaj6h_dbWULuc7yAoWakvDTuzjSOWRh3vUui0YpTKehhtyahw5uxlS-j3HQutHH8yIw8hu0gSAg6Lhlkwzg1Cg9NHv4h_HW4ajP64x-luXZ6GCSZ91Wqn4N6OF53sFmyxaSnDl-OOZ8n09LquSO16OEgPMWeiiK3VTOI4ixAvhBhHrGwlh8h1Y2tGRUZx3oCsOGjkrI9_kxNpf8NWt-dYjVVHQ-E5IA`,
        },
      };

    const res = http.post(`${BASE_URL}/workout/`, payload, params);

    //Logging
    for (const p in res.headers) {
        if (res.headers.hasOwnProperty(p)) {
          console.log(p + ' : ' + res.headers[p]);
        }
    }

    sleep(1);
};