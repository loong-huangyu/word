db.getCollection("transitRequest").createIndex({
    expiryTime: NumberInt("1")
}, {
    name: "expiryTime",
    background: true,
    sparse: true,
    expireAfterSeconds: NumberInt("1")
});

db.getCollection("transitRequestHistory").createIndex({
    expiryTime: NumberInt("1")
}, {
    name: "expiryTime",
    background: true,
    sparse: true,
    expireAfterSeconds: NumberInt("1")
});