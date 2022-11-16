package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Reservation
import com.cloudwaitress.bookingsystem.booking.Restaurant
import com.cloudwaitress.bookingsystem.booking.Table
import net.datafaker.Faker

object TableObjectMother {
    private val faker = Faker()

    fun createTable(
        restaurant: Restaurant,
        reservation: Reservation? = null,
    ): Table {
        return Table(
            capacity = faker.number().numberBetween(1,6),
            restaurant = restaurant,
            reservation = reservation
        )
    }
}