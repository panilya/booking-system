package com.cloudwaitress.bookingsystem.config

import com.cloudwaitress.bookingsystem.booking.*
import com.cloudwaitress.bookingsystem.fakedata.ClientObjectMother.createClient
import com.cloudwaitress.bookingsystem.fakedata.ReservationObjectMother
import com.cloudwaitress.bookingsystem.fakedata.RestaurantObjectMother.createRestaurant
import com.cloudwaitress.bookingsystem.fakedata.RoomObjectMother.createRoom
import com.cloudwaitress.bookingsystem.fakedata.TableObjectMother
import com.cloudwaitress.bookingsystem.fakedata.TimeSlotObjectMother.createTimeslots
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
            timeSlotRepository.deleteAll()
            restaurantRepository.deleteAll()
        }

        if (fillDatabase) {
            val ilyaRestaurant = restaurantRepository.save(createRestaurant(name = "Ilya's Restaurant"))
            clientRepository.save(createClient())
            timeSlotRepository.saveAll(createTimeslots(20, restaurant = ilyaRestaurant))

            IntRange(1, 20).map {
//                val client = clientRepository.save(createClient())
                val restaurant = restaurantRepository.save(createRestaurant())
                val timeslots = timeSlotRepository.saveAll(createTimeslots(20, restaurant = restaurant))
                for (i in 1..19) {
                    val client = clientRepository.save(createClient())
                    reservationRepository.save(ReservationObjectMother.createReservation(client,
                        timeslots[i], restaurant
                    ))
                }
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