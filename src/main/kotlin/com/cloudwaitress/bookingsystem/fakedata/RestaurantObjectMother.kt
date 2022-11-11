package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Restaurant
import net.datafaker.Faker

object RestaurantObjectMother {
    private val faker = Faker()

    fun createRestaurant(): Restaurant {
        return Restaurant(
            name = faker.name().fullName(),
            openingHours = faker.options().option("8 AM : 6 PM", "6 AM : 6 PM", "10 AM : 10 PM"),
        )
    }

    fun createRestaurant(name: String): Restaurant {
        return Restaurant(
            name = name,
            openingHours = faker.options().option("8 AM : 6 PM", "6 AM : 6 PM", "10 AM : 10 PM"),
        )
    }
}