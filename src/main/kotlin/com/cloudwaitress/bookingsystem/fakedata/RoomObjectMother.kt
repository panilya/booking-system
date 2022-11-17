package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Restaurant
import com.cloudwaitress.bookingsystem.booking.Room
import com.cloudwaitress.bookingsystem.booking.Table
import net.datafaker.Faker

object RoomObjectMother {
    private val faker = Faker()

    fun createRoom(
        tables: MutableList<Table>? = null,
        restaurant: Restaurant
    ): Room {
        return Room(
            faker.lorem().word(),
            tables = tables,
            restaurant = restaurant
        )
    }
}