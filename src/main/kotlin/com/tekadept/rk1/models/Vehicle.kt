package com.tekadept.rk1.models

data class Vehicle(
        val _id: org.bson.types.ObjectId,
        val barrels08: Double,
        val city08: Double,
        val comb08: Double,
        val cylinders: Int?,
        val displ: Double?,
        val drive: String,
        val engId: Int,
        val eng_dscr: String,
        val fuelCost08: Double,
        val fuelType: String,
        val highway08: Double,
        val id: Int,
        val make: String,
        val model: String,
        val mpgData: String,
        val trany: String,
        val UCity: Double,
        val UHighway: Double,
        val VClass: String,
        val year: Int,
        val youSaveSpend: Double,
        val guzzler: String,
        val trans_dscr: String,
        val createdOn: String,
        val modifiedOn: String
)
