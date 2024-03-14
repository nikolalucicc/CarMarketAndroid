package com.carmarket.utils

import android.content.Context
import android.widget.ArrayAdapter
import com.carmarket.R

object SpinnerAdapters {

    fun brandAdapter(context: Context): ArrayAdapter<String> {
        val brandsArray = context.resources.getStringArray(R.array.brands)
        val brandAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, brandsArray)
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return brandAdapter
    }

    fun carBodyAdapter(context: Context): ArrayAdapter<String> {
        val carBodyArray = context.resources.getStringArray(R.array.car_body)
        val carBodyAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, carBodyArray)
        carBodyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return carBodyAdapter
    }

    fun yearAdapter(context: Context): ArrayAdapter<String> {
        val yearsArray = context.resources.getStringArray(R.array.years)
        val yearsAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, yearsArray)
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return yearsAdapter
    }

    fun conditionAdapter(context: Context): ArrayAdapter<String> {
        val conditionArray = context.resources.getStringArray(R.array.car_conditions)
        val conditionAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, conditionArray)
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return conditionAdapter
    }

    fun fuelAdapter(context: Context): ArrayAdapter<String> {
        val fuelArray = context.resources.getStringArray(R.array.fuel_type)
        val fuelAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, fuelArray)
        fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return fuelAdapter
    }

    fun transmissionAdapter(context: Context): ArrayAdapter<String> {
        val transmissionArray = context.resources.getStringArray(R.array.transmission)
        val transmissionAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, transmissionArray)
        transmissionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return transmissionAdapter
    }

    fun driveAdapter(context: Context): ArrayAdapter<String> {
        val driveArray = context.resources.getStringArray(R.array.drive)
        val driveAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, driveArray)
        driveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return driveAdapter
    }

    fun doorsNumberAdapter(context: Context): ArrayAdapter<String> {
        val doorsNumberArray = context.resources.getStringArray(R.array.doors_number)
        val doorsNumberAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, doorsNumberArray)
        doorsNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return doorsNumberAdapter
    }

    fun seatsNumberAdapter(context: Context): ArrayAdapter<String> {
        val seatsNumberArray = context.resources.getStringArray(R.array.seats_number)
        val seatsNumberAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, seatsNumberArray)
        seatsNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return seatsNumberAdapter
    }

    fun wheelSideAdapter(context: Context): ArrayAdapter<String> {
        val wheelSideArray = context.resources.getStringArray(R.array.wheel_side)
        val wheelSideAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, wheelSideArray)
        wheelSideAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return wheelSideAdapter
    }

    fun airConditioningAdapter(context: Context): ArrayAdapter<String> {
        val airConditioningArray = context.resources.getStringArray(R.array.air_conditioning)
        val airConditioningAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, airConditioningArray)
        airConditioningAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return airConditioningAdapter
    }

    fun colorAdapter(context: Context): ArrayAdapter<String> {
        val colorArray = context.resources.getStringArray(R.array.colors)
        val colorAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, colorArray)
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return colorAdapter
    }

    fun interiorColorAdapter(context: Context): ArrayAdapter<String> {
        val interiorColorArray = context.resources.getStringArray(R.array.interior_colors)
        val interiorColorAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, interiorColorArray)
        interiorColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return interiorColorAdapter
    }

}