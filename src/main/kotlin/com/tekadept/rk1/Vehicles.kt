package com.tekadept.rk1

import org.litote.kmongo.find
import org.litote.kmongo.findOne
import org.litote.kmongo.sort
import org.litote.kmongo.util.KMongoUtil
import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.jackson.Jackson.json


fun vehicleHandler(chain: Chain) {
    chain
            .get("/:id", ::getVehicleByIdOrMake)
            .get("/:make/:model", ::getVehicleByMakeModel)
            .get(::getAllVehicles)
}

fun getAllVehicles(context: Context) {
    val collection = Database.getVehicles()
    val vehicles = collection.find(KMongoUtil.EMPTY_JSON).sort(SORT_YMM).take(PAGE_COUNT)
    context.render(json(vehicles))
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
        val collection = Database.getVehicles()
        val vehicle = collection.findOne("{id: $parsedId}")
        context.render(json(vehicle))
    }
}

fun getVehicleByMake(context: Context, make: String) {
    val collection = Database.getVehicles()
    val vehicles = collection.find("{make: '$make'}")
            .sort(SORT_YMM)
            .take(PAGE_COUNT)
    context.render(json(vehicles))
}

fun getVehicleByMakeModel(context: Context) {
    val make = context.pathTokens["make"]?.capitalize()
    val model = context.pathTokens["model"]?.capitalize()
    if (make == null || model == null) {
        context.response.status(400)
    } else {
        val collection = Database.getVehicles()
        val vehicles = collection.find("{make: '$make', model: '$model'}")
                .sort(SORT_YMM)
                .take(PAGE_COUNT)
        context.render(json(vehicles))
    }
}

