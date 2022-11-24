package com.cloudwaitress.bookingsystem.endpoints.dto

import java.time.LocalDateTime

//fun TimeSlot.toDto(): TimeSlotDto {
//    return TimeSlotDto(
//        objectId = this.objectId,
//        timeSlot = this.timeslot,
//        restaurantName = this.restaurant.name,
//    )
//}


data class TimeSlotDto(
    val timeslotObjectId: String,
    val timeSlot: LocalDateTime,
    val restaurantObjectId: String,
    val restaurantName: String,
    val openingHours: String
)

