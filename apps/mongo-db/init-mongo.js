db = db.getSiblingDB('lotus-db');

db.createUser({
    user: "kevin",
    pwd: "k8spass",
    roles: [
        {
            role: "readWrite",
            db: "lotus-db"
        }
    ]
});

db = db.getSiblingDB('lotus-db');

print("lotus-db 생성완료");
