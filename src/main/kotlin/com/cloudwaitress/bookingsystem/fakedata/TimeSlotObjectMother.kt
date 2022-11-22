package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Reservation
import com.cloudwaitress.bookingsystem.booking.Restaurant
import com.cloudwaitress.bookingsystem.booking.TimeSlot
import net.datafaker.Faker
import java.time.LocalDateTime

object TimeSlotObjectMother {
    private val faker = Faker()

    fun createTimeslots(
        times: Int,
        reservation: Reservation? = null,
        restaurant: Restaurant? = null
    ): MutableList<TimeSlot> {
        val timeslots = mutableListOf<TimeSlot>()
        IntRange(1, times).map {
            timeslots.add(
                TimeSlot(
                    timeslot = LocalDateTime.of(
                        2022,
                        faker.number().numberBetween(1, 12),
                        faker.number().numberBetween(1, 28),
                        faker.number().numberBetween(1, 24),
                        faker.options().option("0", "15", "30", "45").toInt()
                    ),
                    reservation = reservation,
                    restaurant = restaurant
                )
            )
        }
        return timeslots
    }

    fun createTimeslot(
        reservation: Reservation? = null,
        restaurant: Restaurant? = null
    ): TimeSlot {
        return TimeSlot(
            timeslot = LocalDateTime.of(
                2022,
                faker.number().numberBetween(1, 12),
                faker.number().numberBetween(1, 28),
                faker.number().numberBetween(1, 24),
                faker.options().option("0", "15", "30", "45").toInt()
            ),
            reservation = reservation,
            restaurant = restaurant
        )
    }
}