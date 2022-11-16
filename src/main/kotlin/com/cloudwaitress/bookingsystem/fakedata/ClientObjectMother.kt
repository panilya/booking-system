package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Client
import com.cloudwaitress.bookingsystem.booking.Reservation
import net.datafaker.Faker

object ClientObjectMother {
    private val faker = Faker()

    fun createClient(
        name: String = faker.name().fullName(),
        phoneNumber: String = faker.phoneNumber().phoneNumber(),
        email: String = faker.internet().emailAddress(),
        reservation: Reservation? = null
    ): Client {
        return Client(
            name,
            phoneNumber,
            email,
            reservation = reservation
        )
    }
}