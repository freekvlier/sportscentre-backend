//k6 run load_test.js
import http from 'k6/http';
import { check, group, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '3m', target: 10000 },
    ],

};

const BASE_URL = 'http://35.195.43.5';

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
          Authorization: `Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ii1LSTNROW5OUjdiUm9meG1lWm9YcWJIWkdldyJ9.eyJhdWQiOiI1ZDRkZjA5OS1iYjcwLTQ4ODctODYxNy1lZTAyZTAwY2VkOWEiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vNjQ0NjE3ZTctMTgwMS00ZWUzLTgzOGMtMjc2NWUzNzNjZmRlL3YyLjAiLCJpYXQiOjE2NzM5OTk5OTcsIm5iZiI6MTY3Mzk5OTk5NywiZXhwIjoxNjc0MDA1NDg0LCJhaW8iOiJBVFFBeS84VEFBQUFSbm5QMjBmTGg4NTNoN3k3SU1FWGt5RWVmRmxJNHI2WmM3c29MakdvL0YvUlM1TWJ5MHRBTm1RR2N0d0E2R1N6IiwiYXpwIjoiNWQ0ZGYwOTktYmI3MC00ODg3LTg2MTctZWUwMmUwMGNlZDlhIiwiYXpwYWNyIjoiMSIsIm5hbWUiOiJ0ZXN0Iiwib2lkIjoiNzcyYWJjNmMtYTk3Yy00Nzc2LWJlMmYtNjg1ODk3ZWFlNzNjIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidGVzdEBzcG9ydHNjZW50cmVhcHAub25taWNyb3NvZnQuY29tIiwicmgiOiIwLkFVNEE1eGRHWkFFWTQwNkRqQ2RsNDNQUDNwbndUVjF3dTRkSWhoZnVBdUFNN1pxREFKTS4iLCJyb2xlcyI6WyJBZG1pbiJdLCJzY3AiOiJVc2VyLlJlYWQiLCJzdWIiOiJqenRXYlRzUVUxWTBUNDVSSGhMQ2FzWnJuNUFmT19jdmI0ZElZOGJKWTZVIiwidGlkIjoiNjQ0NjE3ZTctMTgwMS00ZWUzLTgzOGMtMjc2NWUzNzNjZmRlIiwidXRpIjoibU9xWXFXQmgxVWlIWHJsdjVZWjBBQSIsInZlciI6IjIuMCJ9.CuhExN3U4BgXhj9HWybburx2vfz8k03sfAnyqYj3lD-Xvmg9B566sUR2_MNfBhBAIBuwaTH8X4RHeexhTVfUe9YZU0zzSh0ov9LLwcQBsXZZHHSguS2iPv6qs57pLg4TwnqKvd7iMbDJFCl3OarKw86mInP_9qtyTlKmEIGtbqCetY4TCWiFXAHUcK6b34yXFrECLHMxgYjDDrVWeyxU7glgq0Ds7NdFWmkpX25s6grVdBso9tXy-9LlJQzh0yu2d48zXFfbOaIabXP0LCAwWEYu5XmjrI4ZB-5sLvEQhiv2EjvET7d-72ZbeKI9sLSLYggN5x6LbMf1EBYAWYmN5Q`,
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