package com.tekadept.rk1

import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.tekadept.rk1.models.Vehicle
import org.litote.kmongo.*
import org.litote.kmongo.util.KMongoUtil
import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.jackson.Jackson.json

val PAGE_COUNT = 20

fun vehicleHandler(chain: Chain) {
    chain.
            get("/:id", ::getVehicleByIdOrMake).
            get("/:make/:model", ::getVehicleByMakeModel).
            get(::getAllVehicles)
}

fun getCollection(): MongoCollection<Vehicle> {
    val connectionString = System.getenv("MONGODB_CONNECTION").toString()
    val clientUri = MongoClientURI(connectionString)
    val client = KMongo.createClient(clientUri)
    val database = client.getDatabase("users")
    return database.getCollection<Vehicle>()
}

fun getVehicleByIdOrMake(context: Context) {
    val id = context.pathTokens["id"] ?: ""
    val parsedId: Int? = id.toIntOrNull()
    if (parsedId == null || id == "") {
        if (id != "") {
            return getVehicleByMake(context, id.capitalize())
        }
        context.response.status(400)
    } else {
        val collection = getCollection()
        val vehicle = collection.findOne("{id: $parsedId}")
        context.render(json(vehicle))
    }
}

fun getVehicleByMake(context: Context, make: String) {
    val collection = getCollection()
    val vehicles = collection.find("{make: '$make'}").sort("{year: -1, make: 1, model: 1}").take(PAGE_COUNT)
    context.render(json(vehicles))
}

fun getVehicleByMakeModel(context: Context) {
    val make = context.pathTokens["make"]?.capitalize()
    val model = context.pathTokens["model"]?.capitalize()
    if (make == null || model == null) {
        context.response.status(400)
    } else {
        val collection = getCollection()
        val vehicles = collection.find("{make: '$make', model: '$model'}").sort("{year: -1, make: 1, model: 1}").take(PAGE_COUNT)
        context.render(json(vehicles))
    }
}

fun getAllVehicles(context: Context) {
    val collection = getCollection()
    val vehicles = collection.find(KMongoUtil.EMPTY_JSON).sort("{year: -1, make: 1, model: 1}").skip(150).take(PAGE_COUNT)
    context.render(json(vehicles))
}
