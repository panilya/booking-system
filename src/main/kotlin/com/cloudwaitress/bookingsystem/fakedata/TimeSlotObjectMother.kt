package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Table
import com.cloudwaitress.bookingsystem.booking.TimeSlot
import net.datafaker.Faker
import java.time.LocalDateTime

object TimeSlotObjectMother {
    private val faker = Faker()

    fun createTimeslot(
        table: MutableList<Table> = mutableListOf(),
    ): TimeSlot {
        return TimeSlot(
            timeSlot = LocalDateTime.of(2022,
                faker.number().numberBetween(1, 12),
                faker.number().numberBetween(1, 28),
                faker.number().numberBetween(1, 24),
                faker.options().option("0", "15", "30", "45").toInt()),
            table = table
        )
    }
}