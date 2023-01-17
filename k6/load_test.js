import http from 'k6/http';
import { check, group, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '2m', target: 10 },
        { duration: '3m', target: 100 },
        { duration: '3m', target: 500 },
        { duration: '3m', target: 0 }, // ramp-down to 0 users
    ],

};

const BASE_URL = 'http://35.189.232.237';

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
          Authorization: `Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ii1LSTNROW5OUjdiUm9meG1lWm9YcWJIWkdldyJ9.eyJhdWQiOiI1ZDRkZjA5OS1iYjcwLTQ4ODctODYxNy1lZTAyZTAwY2VkOWEiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vNjQ0NjE3ZTctMTgwMS00ZWUzLTgzOGMtMjc2NWUzNzNjZmRlL3YyLjAiLCJpYXQiOjE2NzMzOTA1MTcsIm5iZiI6MTY3MzM5MDUxNywiZXhwIjoxNjczMzk1ODA4LCJhaW8iOiJBWFFBaS84VEFBQUFyQ1IwSFhtekdOcjRmYis2TmU3Mi91YlZjcHZ6c2REbDNHZ3pCUkRmVzdWbjJvWWJpK1JERCtXWnh5SklLM25aVUtMQStGNDlkb3JHVDlxK2hBNTV4TjMzL3RTQm1LWWhyUVpxWXZ3dGx2ZXlGUVg2S3JkMk51elU2c1ZWeVFoM3BpM09maUZNSUtRYjM3Vm9DYWpiUFE9PSIsImF6cCI6IjVkNGRmMDk5LWJiNzAtNDg4Ny04NjE3LWVlMDJlMDBjZWQ5YSIsImF6cGFjciI6IjEiLCJpZHAiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9jNjZiNjc2NS1iNzk0LTRhMmItODRlZC04NDViMzQxYzA4NmEvIiwibmFtZSI6IkZyZWVrIHZhbiBMaWVyIiwib2lkIjoiMTNjMjU1NzMtNmNlNi00NDRhLTk0M2EtZjg3YmNmYTU3MTQ4IiwicHJlZmVycmVkX3VzZXJuYW1lIjoiNDU0NDQ5QHN0dWRlbnQuZm9udHlzLm5sIiwicmgiOiIwLkFVNEE1eGRHWkFFWTQwNkRqQ2RsNDNQUDNwbndUVjF3dTRkSWhoZnVBdUFNN1pxREFOMC4iLCJyb2xlcyI6WyJBZG1pbiJdLCJzY3AiOiJVc2VyLlJlYWQiLCJzdWIiOiJEOTRHcjdsQU5MT2o1czMyb2F5TnRCQ2ZrOWpfaFc0bnViNmFoSDVSSGhrIiwidGlkIjoiNjQ0NjE3ZTctMTgwMS00ZWUzLTgzOGMtMjc2NWUzNzNjZmRlIiwidXRpIjoiTWxZNmx3Wk8tRW1XQWtRV05laWtBQSIsInZlciI6IjIuMCJ9.byfgKhSMWCzINdPXETUPKfMXSB43hdxQQ-9z-wytlHQyXS19J2JxsJ61DgRnbzp4f_TbGfrpD6qXi4iBcVTGwuTvXKuOtTw6ckUackwWvUcKEcc_eGBtux3S_ZAaVXJdE-f7ETHlk14425WCr3kff9DH36VMooNYmXPQPGo7p1Zm9AhSfBQhwotJpSxLZA_OpQthcyJYs53bUxhlm0rJ9BMyWntv_gQSOr4MnhSDunZYj-A2KSI16yzOFXWqFKKeGV4-yH4ff1PqY-akN3571f1mki2Zu7GEm2YuW4BvASutjJfjmnhNo8pNmGpRwUGE3pnbGDKw7PM5tN5C8AQbfw`,
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