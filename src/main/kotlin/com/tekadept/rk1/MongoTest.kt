package com.tekadept.rk1

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import ratpack.jackson.Jackson.json

fun mongoTest() {
    val connectionString = System.getenv("MONGODB_CONNECTION")
    val mongoClient = MongoClient(MongoClientURI(connectionString))
    val database = mongoClient.getDatabase("users")
    val collection = database.getCollection("vehicle")
    val vehicle = collection.find().take(1)[0]
    println(json(vehicle))
}

