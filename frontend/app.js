var express = require("express");
var path = require("path");
var app = express();
var bodyParser = require("body-parser");
var axios = require("axios");
var fs = require("fs");
var FormData = require("form-data");
var multer = require("multer");

var util = require("./utils");

var GUESTBOOK_API_ADDR = process.env.GUESTBOOK_API_ADDR;
var BACKEND_URI = "http://" + GUESTBOOK_API_ADDR + "/messages";
var IMAGE_SERVER_URI = "http://image-server/upload-image";
var DOWNLOAD_IMAGE_URI = "https://image-server.asordk.synology.me/upload-image"

app.set("view engine", "pug");
app.set("views", path.join(__dirname, "views"));

var router = express.Router();
app.use(router);

app.use(express.static("public"));
router.use(bodyParser.urlencoded({ extended: false }));

var upload = multer({ dest: "temp/" });

if (!process.env.PORT) {
  throw new Error("PORT environment variable is not defined");
}
if (!process.env.GUESTBOOK_API_ADDR) {
  throw new Error("GUESTBOOK_API_ADDR environment variable is not defined");
}

var PORT = process.env.PORT;
app.listen(PORT, function () {
  console.log("App listening on port " + PORT);
});

// GET 요청 처리
router.get("/", function (req, res) {
  axios
    .get(BACKEND_URI)
    .then(function (response) {
      console.log("response from " + BACKEND_URI + ": " + response.status);
      var result = util.formatMessages(response.data);
      res.render("home", { messages: result });
    })
    .catch(function (error) {
      console.error("Error fetching messages:", error);
      res.status(500).send("Failed to fetch messages");
    });
});

// POST 요청 처리
router.post("/post", upload.single("image"), async function (req, res) {
  console.log("received request: " + req.method + " " + req.url);

  var name = req.body.name;
  var message = req.body.message;
  var date = req.body.date;

  if (!name || !message || !date) {
    return res.status(400).send("name, message, and date are required");
  }

  let imageUrl = null;
  let filename = null;
  if (req.file) {
    try {
      // 원본 파일 확장자 추출
      const fileExtension = path.extname(req.file.originalname);

      // 새로운 파일명 생성 (multer가 생성한 파일명에 확장자 추가)
      filename = req.file.filename + fileExtension;

      // 이미지 서버에 업로드할 URL 생성
      imageUrl = `${IMAGE_SERVER_URI}/${filename}`;

      // 파일의 원시 데이터를 읽어옴
      const imageStream = fs.createReadStream(req.file.path);

      // 헤더 설정
      const headers = {
        "Content-Type": req.file.mimetype,
        "Content-Length": req.file.size,
      };

      // 이미지 서버에 PUT 요청으로 파일 업로드
      const imageResponse = await axios.put(imageUrl, imageStream, { headers });

      if (imageResponse.status === 201 || imageResponse.status === 200) {
        console.log("Image uploaded successfully.");
      } else {
        console.error("Image upload returned status:", imageResponse.status);
      }

      // 임시 파일 삭제
      fs.unlink(req.file.path, (err) => {
        if (err) console.error("Failed to delete temp file:", err);
      });
    } catch (error) {
      console.error("Error uploading image to image server:", error);
      res.status(500).send("Failed to upload image");
      return;
    }
  }

  // 메시지와 이미지 URL을 백엔드에 전송
  var form = new FormData();
  form.append("name", name);
  form.append("body", message);
  form.append("date", date);
  if (imageUrl) form.append("imageUrl", `${DOWNLOAD_IMAGE_URI}/${filename}`);

  axios
    .post(BACKEND_URI, form, {
      headers: form.getHeaders(),
    })
    .then(function (response) {
      console.log("response from " + BACKEND_URI + ": " + response.status);
      res.redirect("/");
    })
    .catch(function (error) {
      console.error("Error posting to backend:", error);
      res.status(500).send("Failed to post message");
    });
});

module.exports = router;

