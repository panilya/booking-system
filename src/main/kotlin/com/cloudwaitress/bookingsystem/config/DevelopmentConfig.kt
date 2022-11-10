package com.cloudwaitress.bookingsystem.config

import com.cloudwaitress.bookingsystem.booking.ClientRepository
import com.cloudwaitress.bookingsystem.booking.RestaurantRepository
import com.cloudwaitress.bookingsystem.booking.TableRepository
import com.cloudwaitress.bookingsystem.fakedata.ClientObjectMother.createClient
import com.cloudwaitress.bookingsystem.fakedata.RestaurantObjectMother.createRestaurant
import com.cloudwaitress.bookingsystem.fakedata.TableObjectMother.createTable
import net.datafaker.Faker
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("dev")
class DevelopmentConfig {
    private val faker = Faker()

    @Bean
    fun init(
        clientRepository: ClientRepository,
        tableRepository: TableRepository,
        restaurantRepository: RestaurantRepository
    ) = CommandLineRunner {

        val reset = true
        val fillDatabase = true

        if (reset) {
            clientRepository.deleteAll()
            tableRepository.deleteAll()
            restaurantRepository.deleteAll()
        }

        if (fillDatabase) {

            restaurantRepository.save(createRestaurant(name = "Ilya's Restaurant"))

            repeat(10) {
                restaurantRepository.save(createRestaurant())
            }

            repeat(10) {
                tableRepository.save(createTable(restaurant = restaurantRepository.findByName("Ilya's Restaurant").get()))
            }

            repeat(5) {
                clientRepository.save(createClient())
            }
        }

    }
}