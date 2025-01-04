import http from 'k6/http';
import { check } from 'k6';

export const options = {
  vus: 10,
  duration: '30s',
};

const fileData = open('./longhorn.png', 'b');

export default function () {
  const url = 'http://localhost:30080/api/post';

  const payload = {
    title: 'Test Title',
    content: 'This is test content.',
    purchaseDate: '2025-01-01',
    item: 'Test Item',
    images: http.file(fileData, 'longhorn.png'), // 파일 데이터 추가
  };

  const res = http.post(url, payload);

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response contains success message': (r) =>
      r.body.includes('등록 완료'),
  });
}
