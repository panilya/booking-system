package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Calendar
import com.cloudwaitress.bookingsystem.booking.TimeSlot
import net.datafaker.Faker
import java.time.LocalTime

object TimeSlotObjectMother {
    private val faker = Faker()

    fun createTimeslots(
        times: Int,
        calendar: Calendar? = null,
    ): MutableList<TimeSlot> {
        val timeslots = mutableListOf<TimeSlot>()
        IntRange(1, times).map {
            timeslots.add(
                TimeSlot(
                    timeslot = LocalTime.of(faker.number().numberBetween(1, 24), faker.number().numberBetween(1, 60), faker.number().numberBetween(1, 60)),
                    calendar = calendar
                )
            )
        }
        return timeslots
    }

    fun createTimeslot(
        calendar: Calendar? = null,
    ): TimeSlot {
        return TimeSlot(
            timeslot = LocalTime.of(faker.number().numberBetween(1, 24), faker.number().numberBetween(1, 60), faker.number().numberBetween(1, 60)),
            calendar = calendar
        )
    }
}