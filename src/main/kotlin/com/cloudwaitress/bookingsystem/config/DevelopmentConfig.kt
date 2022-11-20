package com.cloudwaitress.bookingsystem.config

import com.cloudwaitress.bookingsystem.booking.*
import com.cloudwaitress.bookingsystem.fakedata.ClientObjectMother.createClient
import com.cloudwaitress.bookingsystem.fakedata.ReservationObjectMother
import com.cloudwaitress.bookingsystem.fakedata.RestaurantObjectMother.createRestaurant
import com.cloudwaitress.bookingsystem.fakedata.RoomObjectMother.createRoom
import com.cloudwaitress.bookingsystem.fakedata.TableObjectMother
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
        reservationRepository: ReservationRepository,
        clientRepository: ClientRepository,
        tableRepository: TableRepository,
        restaurantRepository: RestaurantRepository,
        timeSlotRepository: TimeSlotRepository,
        roomRepository: RoomRepository
    ) = CommandLineRunner {

        val reset = true
        val fillDatabase = true

        if (reset) {
            reservationRepository.deleteAll()
            clientRepository.deleteAll()
            tableRepository.deleteAll()
            roomRepository.deleteAll()
            restaurantRepository.deleteAll()
            timeSlotRepository.deleteAll()
        }

        if (fillDatabase) {
            restaurantRepository.save(createRestaurant(name = "Ilya's Restaurant"))

            repeat(10) {
                restaurantRepository.save(createRestaurant())
            }

            IntRange(1, 20).map {
                val client = clientRepository.save(createClient())
                val timeSlot = timeSlotRepository.save(createTimeslot())
                reservationRepository.save(ReservationObjectMother.createReservation(client, timeSlot))
            }

            repeat(5) {
                clientRepository.save(createClient())
            }

            val room = roomRepository.save(createRoom(restaurant = restaurantRepository.findByName("Ilya's Restaurant").get()))

            IntRange(1, 20).map {
                tableRepository.save(TableObjectMother.createTable(room = room, restaurant = restaurantRepository.findByName("Ilya's Restaurant").get()))
            }
        }
    }
}