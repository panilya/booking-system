package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.*
import net.datafaker.Faker

object ReservationObjectMother {
    private val faker = Faker()

    fun createReservation(
        client: Client,
        timeSlot: TimeSlot
    ): Reservation {
        return Reservation(
            status = faker.options().option(TableStatus::class.java),
            paymentStatus = faker.options().option(PaymentStatus::class.java),
            partySize = faker.number().numberBetween(1, 6),
            client = client,
            timeSlot = timeSlot,
        )
    }
}