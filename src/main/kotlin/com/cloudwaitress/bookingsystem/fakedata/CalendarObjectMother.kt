package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Calendar
import com.cloudwaitress.bookingsystem.booking.Reservation
import com.cloudwaitress.bookingsystem.booking.Restaurant
import com.cloudwaitress.bookingsystem.booking.TimeSlot
import net.datafaker.Faker
import java.time.LocalDate

object CalendarObjectMother {
    private val faker = Faker()

    fun createCalendar(
        restaurant: Restaurant,
        timeslot: TimeSlot,
        reservation: Reservation? = null
    ): Calendar {
        return Calendar(
            restaurant = restaurant,
            timeslot = timeslot,
            reservation = reservation,
            date = LocalDate.of(
                faker.number().numberBetween(2022, 2023),
                faker.number().numberBetween(1, 12),
                faker.number().numberBetween(1, 28)
            ),
            availabilityStatus = faker.options().option("AVAILABLE", "NOT_AVAILABLE"),
        )
    }
}