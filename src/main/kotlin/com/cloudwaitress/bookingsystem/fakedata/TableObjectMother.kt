package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Client
import com.cloudwaitress.bookingsystem.booking.Restaurant
import com.cloudwaitress.bookingsystem.booking.Table
import net.datafaker.Faker
import java.sql.Timestamp
import java.time.Instant
import java.util.concurrent.TimeUnit

object TableObjectMother {
    private val faker = Faker()

    fun createTable(
        client: Client? = null,
        restaurant: Restaurant
    ): Table {
        return Table(
            capacity = faker.number().numberBetween(1, 8).toShort(),
            availability = faker.bool().bool(),
            placed = faker.date().between(Timestamp.from(Instant.now()), faker.date().future(298000, 10000, TimeUnit.MINUTES)).toLocalDateTime(),
            bookingFor = faker.date().future(1000000, 1000, TimeUnit.HOURS).toLocalDateTime(),
            specialEvent = faker.options().option("Birthday", "Anniversary"),
            clientStatus = faker.options().option("Confirmed", "Unconfirmed", "Seated"),
            client = client,
            restaurant = restaurant
        )
    }
}