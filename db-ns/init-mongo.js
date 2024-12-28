db = db.getSiblingDB('lotus-db');

db.createUser({
  user: 'kevin',
  pwd: 'k8spass#',
  roles: [
    {
      role: 'readWrite',
      db: 'lotus-db'
    }
  ]
});
