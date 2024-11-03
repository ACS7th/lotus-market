const express = require('express');
const bodyParser = require('body-parser');
const multer = require('multer');
const Message = require('./messages');

const router = express.Router();

// `bodyParser.urlencoded`와 `bodyParser.json` 모두 사용하여 다양한 형식의 데이터를 처리
router.use(bodyParser.json());
router.use(bodyParser.urlencoded({ extended: true }));

// `multer` 설정
const upload = multer(); // 메모리에 파일 저장

// GET 요청 처리
router.get('/messages', (req, res) => {
    console.log(`received request: ${req.method} ${req.url}`);

    // 메시지 조회
    try {
        Message.messageModel.find({}, null, { sort: { '_id': -1 } }, (err, messages) => {
            const list = messages.map((message) => ({
                name: message.name,
                body: message.body,
                date: message.date,
                imageUrl: message.imageUrl,
                timestamp: message._id.getTimestamp(),
            }));
            res.status(200).json(list);
        });
    } catch (error) {
        res.status(500).json(error);
    }
});

// POST 요청 처리 - `upload.none()` 사용하여 `multipart/form-data` 처리
router.post('/messages', upload.none(), async (req, res) => {
    const { name, body, date, imageUrl } = req.body;

    if (!name || !body || !date) {
        return res.status(400).json({ error: "name, body, and date are required fields" });
    }

    try {
        await Message.create({ name, body, date, imageUrl });
        res.status(200).send({ message: "Message created successfully" });
    } catch (err) {
        if (err.name === "ValidationError") {
            console.error('Validation error:', err);
            res.status(400).json({ error: 'Validation error', details: err });
        } else {
            console.error('Error saving message:', err);
            res.status(500).json({ error: 'Failed to save message', details: err });
        }
    }
});

module.exports = router;
