import './globals.css';
import "@cloudscape-design/global-styles/index.css"
// 만약 public/css/style.css 를 쓰고 싶다면 아래처럼 import할 수도 있습니다.
// import '../public/css/style.css';

export const metadata = {
  title: '우리동네 연근마켓',
  description: 'For AWS Cloud School 7th',
};

export default function RootLayout({ children }) {
  return (
    <html lang="ko">
      <body>
        <header className="header">
          <div className="container">
            <h1>
              <a href="/">우리동네 연근마켓</a>
            </h1>
            <p className="text-muted">
              For AWS Cloud School 7th
            </p>
          </div>
        </header>
        <main>{children}</main>
      </body>
    </html>
  );
}