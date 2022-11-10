package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Client
import com.cloudwaitress.bookingsystem.booking.Table
import net.datafaker.Faker

object ClientObjectMother {
    private val faker = Faker()

    fun createClient(
        name: String = faker.name().fullName(),
        phoneNumber: String = faker.phoneNumber().phoneNumber(),
        email: String = faker.internet().emailAddress(),
        table: Table? = null
    ): Client {
        return Client(
            name,
            phoneNumber,
            email,
            table
        )
    }
}