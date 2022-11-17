package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Reservation
import com.cloudwaitress.bookingsystem.booking.Restaurant
import com.cloudwaitress.bookingsystem.booking.Room
import com.cloudwaitress.bookingsystem.booking.Table
import net.datafaker.Faker

object TableObjectMother {
    private val faker = Faker()

    fun createTables(
        times: Int,
        restaurant: Restaurant,
        reservation: Reservation? = null,
        room: Room
    ): MutableList<Table> {
        val tables = mutableListOf<Table>()
        IntRange(1, times).map {
            tables.add(Table(
                capacity = faker.number().numberBetween(1,6),
                restaurant = restaurant,
                reservation = reservation,
                room = room
                )
            )
        }

        return tables
    }

    fun createTable(
        restaurant: Restaurant,
        reservation: Reservation? = null,
        room: Room
    ): Table {
        return Table(
            capacity = faker.number().numberBetween(1,6),
            restaurant = restaurant,
            reservation = reservation,
            room = room
        )
    }
}