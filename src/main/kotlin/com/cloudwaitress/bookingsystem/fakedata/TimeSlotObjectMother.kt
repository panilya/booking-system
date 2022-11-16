package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Reservation
import com.cloudwaitress.bookingsystem.booking.TimeSlot
import net.datafaker.Faker
import java.time.LocalDateTime

object TimeSlotObjectMother {
    private val faker = Faker()

    fun createTimeslot(
        reservation: Reservation? = null
    ): TimeSlot {
        return TimeSlot(
            timeslot = LocalDateTime.of(2022,
                faker.number().numberBetween(1, 12),
                faker.number().numberBetween(1, 28),
                faker.number().numberBetween(1, 24),
                faker.options().option("0", "15", "30", "45").toInt()),
            reservation = reservation
        )
    }
}