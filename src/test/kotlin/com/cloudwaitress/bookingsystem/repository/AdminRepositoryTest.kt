package com.cloudwaitress.bookingsystem.repository

import com.cloudwaitress.bookingsystem.AbstractIT
import com.cloudwaitress.bookingsystem.booking.*
import com.cloudwaitress.bookingsystem.endpoints.Period
import com.cloudwaitress.bookingsystem.fakedata.ClientObjectMother
import com.cloudwaitress.bookingsystem.fakedata.ReservationObjectMother
import com.cloudwaitress.bookingsystem.fakedata.RestaurantObjectMother
import com.cloudwaitress.bookingsystem.fakedata.TimeSlotObjectMother
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class AdminRepositoryTest : AbstractIT() {

    @Autowired
    lateinit var jdbcReservationRepository: JdbcReservationRepository

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var timeSlotRepository: TimeSlotRepository

    @Autowired
    lateinit var clientRepository: ClientRepository

    @Autowired
    lateinit var reservationRepository: ReservationRepository

    @Test
    fun restaurantCreation() {
        restaurantRepository.save(Restaurant(name = "Fancy Restaurant", openingHours = "8 AM - 9 PM"))
        val restaurant = restaurantRepository.findByName("Fancy Restaurant").get()

        assertThat(restaurant.name).isEqualTo("Fancy Restaurant")
        assertThat(restaurant.openingHours).isEqualTo("8 AM - 9 PM")
    }

    @Test
    fun allTimeslotsEndpoint() {
        val restaurant = restaurantRepository.save(RestaurantObjectMother.createRestaurant(name = "Cool Restaurant", openingHours = "6 AM - 7:30 PM"))
        timeSlotRepository.save(TimeSlot(timeslot = LocalDateTime.of(2022, 11, 11, 11, 30), restaurant = restaurant))
        timeSlotRepository.save(TimeSlot(timeslot = LocalDateTime.of(2022, 11, 21, 10, 0), restaurant = restaurant))
        timeSlotRepository.save(TimeSlot(timeslot = LocalDateTime.of(2022, 11, 22, 12, 30), restaurant = restaurant))

        val timeslots = jdbcReservationRepository.findTimeslotsByObjectId(restaurant.objectId)

        assertThat(timeslots).hasSize(3)
        assertThat(timeslots[0].restaurantName).isEqualTo("Cool Restaurant")
        assertThat(timeslots[0].timeSlot).isEqualTo(LocalDateTime.of(2022, 11, 11, 11, 30))
    }

    @Test
    fun reservationsBetweenSpecifiedTimePeriod() {
        val restaurant = restaurantRepository.save(RestaurantObjectMother.createRestaurant(name = "Cool Restaurant", openingHours = "6 AM - 7:30 PM"))

        val client = clientRepository.save(ClientObjectMother.createClient())
        reservationRepository.save(ReservationObjectMother.createReservation(client, TimeSlotObjectMother.createTimeslot(restaurant = restaurant), restaurant, createdAt = LocalDateTime.of(2022, 11, 18, 11, 30)))
        reservationRepository.save(ReservationObjectMother.createReservation(client, TimeSlotObjectMother.createTimeslot(restaurant = restaurant), restaurant, createdAt = LocalDateTime.of(2022, 11, 21, 10, 0)))
        reservationRepository.save(ReservationObjectMother.createReservation(client, TimeSlotObjectMother.createTimeslot(restaurant = restaurant), restaurant, createdAt = LocalDateTime.of(2022, 11, 28, 12, 30)))

        val reservationNumber = jdbcReservationRepository.countReservationsBetweenDate(
            LocalDateTime.of(2022, 11, 19, 12, 0),
            LocalDateTime.of(2022, 11, 22, 12, 0),
            restaurant.objectId
        )

        assertThat(reservationNumber).isEqualTo(1)
    }

    @Test
    fun lastReservationsMadeInSpecifiedTime() { // Not very good test. How to test `now()` postgres function?
        val restaurant = restaurantRepository.save(RestaurantObjectMother.createRestaurant(name = "Great Restaurant", openingHours = "6 AM - 8 PM"))

        val client = clientRepository.save(ClientObjectMother.createClient())
        reservationRepository.save(ReservationObjectMother.createReservation(client, TimeSlotObjectMother.createTimeslot(restaurant = restaurant), restaurant, createdAt = LocalDateTime.of(2022, 11, 18, 11, 30)))
        reservationRepository.save(ReservationObjectMother.createReservation(client, TimeSlotObjectMother.createTimeslot(restaurant = restaurant), restaurant, createdAt = LocalDateTime.of(2022, 11, 21, 10, 0)))
        reservationRepository.save(ReservationObjectMother.createReservation(client, TimeSlotObjectMother.createTimeslot(restaurant = restaurant), restaurant, createdAt = LocalDateTime.of(2022, 11, 28, 12, 30)))

        val reservationsNumberMonth = jdbcReservationRepository.countReservationsLastThisTimePeriod(restaurant.objectId, Period.ALL_TIME)
        assertThat(reservationsNumberMonth).isEqualTo(3)

    }

}