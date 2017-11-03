package com.tekadept.rk1

import com.mongodb.MongoClientURI
import com.mongodb.client.MongoDatabase
import com.tekadept.rk1.models.Vehicle
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection


object Database {
    private lateinit var database: MongoDatabase

    init {
        makeConnection()
    }

    fun makeConnection() {
        try {
            // pull the connection from the environmental variables
            val connectionString = System.getenv(CONNECTION)
            val clientUri = MongoClientURI(connectionString)
            val client = KMongo.createClient(clientUri)
            database = client.getDatabase(DEFAULT_DB)
        } catch (ex: Exception) {
            // TODO: log this exception
            println("Got an exception")
        }
    }

    fun getVehicles() = database.getCollection<Vehicle>()
}