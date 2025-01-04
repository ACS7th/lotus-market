import http from 'k6/http';
import { check } from 'k6';

export const options = {
  vus: 30,
  duration: '30s',
};

export default function () {
  const url = 'http://localhost:30080/api/post';

  const res = http.get(url);

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response contains success message': (r) =>
      r.body.includes('조회 완료'),
  });
}
