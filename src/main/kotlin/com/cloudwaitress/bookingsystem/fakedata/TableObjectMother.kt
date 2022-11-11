package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.*
import net.datafaker.Faker
import java.sql.Timestamp
import java.time.Instant

object TableObjectMother {
    private val faker = Faker()

    fun createTable(
        number: Int? = null,
        client: Client? = null,
        restaurant: Restaurant,
        timeSlot: TimeSlot? = null
    ): Table {
        return Table(
            number = number,
            capacity = faker.number().numberBetween(1, 8).toShort(),
            reserved = faker.bool().bool(),
            placed = faker.date().between(Timestamp.from(Instant.now().minusSeconds(432000)), Timestamp.from(Instant.now())).toLocalDateTime(), // Between now and minus 5 days from now
            specialEvent = faker.options().option("Birthday", "Anniversary", "Wedding"),
            tableStatus = faker.options().option(TableStatus::class.java),
            client = client,
            restaurant = restaurant,
            timeSlot = timeSlot
        )
    }
}