package com.cloudwaitress.bookingsystem.config

import com.cloudwaitress.bookingsystem.booking.*
import com.cloudwaitress.bookingsystem.fakedata.ClientObjectMother.createClient
import com.cloudwaitress.bookingsystem.fakedata.RestaurantObjectMother.createRestaurant
import com.cloudwaitress.bookingsystem.fakedata.TableObjectMother.createTable
import com.cloudwaitress.bookingsystem.fakedata.TimeSlotObjectMother.createTimeslot
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
        restaurantRepository: RestaurantRepository,
        timeSlotRepository: TimeSlotRepository
    ) = CommandLineRunner {

        val reset = true
        val fillDatabase = true

        if (reset) {
            clientRepository.deleteAll()
            tableRepository.deleteAll()
            restaurantRepository.deleteAll()
            timeSlotRepository.deleteAll()
        }

        if (fillDatabase) {
            restaurantRepository.save(createRestaurant(name = "Ilya's Restaurant"))

            repeat(10) {
                restaurantRepository.save(createRestaurant())
            }

            IntRange(1, 20).map {
                val timeSlot = timeSlotRepository.save(createTimeslot())
                tableRepository.save(
                    createTable(
                        number = it,
                        restaurant = restaurantRepository.findByName("Ilya's Restaurant").get(),
                        timeSlot = timeSlot
                    )
                )
            }

            repeat(5) {
                clientRepository.save(createClient())
            }
        }

    }
}