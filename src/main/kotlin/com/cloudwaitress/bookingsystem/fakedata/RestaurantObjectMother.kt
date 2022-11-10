package com.cloudwaitress.bookingsystem.fakedata

import com.cloudwaitress.bookingsystem.booking.Restaurant
import com.cloudwaitress.bookingsystem.booking.Table
import net.datafaker.Faker

object RestaurantObjectMother {
    private val faker = Faker()

    fun createRestaurant(table: MutableList<Table>? = null): Restaurant {
        return Restaurant(
            name = faker.name().fullName(),
            openingHours = faker.options().option("8 AM : 6 PM", "6 AM : 6 PM", "10 AM : 10 PM"),
            table = table
        )
    }

    fun createRestaurant(table: MutableList<Table>? = null, id: Long? = null): Restaurant {
        return Restaurant(
            name = faker.name().fullName(),
            openingHours = faker.options().option("8 AM : 6 PM", "6 AM : 6 PM", "10 AM : 10 PM"),
            table = table,
            id = id
        )
    }
}