package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.*
import net.datafaker.Faker
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object ReservationObjectMother {
    private val faker = Faker()

    fun createReservation(
        client: Client,
        timeSlot: TimeSlot,
        restaurant: Restaurant
    ): Reservation {
        return Reservation(
            status = faker.options().option(TableStatus::class.java),
            paymentStatus = faker.options().option(PaymentStatus::class.java),
            partySize = faker.number().numberBetween(1, 6),
            createdAt = faker.date().between(faker.date().past(90, TimeUnit.DAYS), Timestamp.valueOf(LocalDateTime.now())).toLocalDateTime(),
            client = client,
            timeSlot = timeSlot,
            restaurant = restaurant
        )
    }
}