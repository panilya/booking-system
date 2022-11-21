package com.cloudwaitress.bookingsystem.endpoints

import com.cloudwaitress.bookingsystem.booking.JdbcReservationRepository
import com.cloudwaitress.bookingsystem.booking.ReservationRepository
import com.cloudwaitress.bookingsystem.booking.RestaurantRepository
import com.cloudwaitress.bookingsystem.endpoints.dto.GenericResponse
import com.cloudwaitress.bookingsystem.endpoints.dto.TimeSlotDto
import com.cloudwaitress.bookingsystem.fakedata.RestaurantObjectMother
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequestMapping("/admin")
@Tag(name = "Admin")
@RestController
class AdminController(
    private val jdbcReservationRepository: JdbcReservationRepository,
    private val reservationRepository: ReservationRepository,
    private val restaurantRepository: RestaurantRepository
) {

    @Operation(summary = "Get reservations made in the last specified period")
    @GetMapping("/reservations/{restaurantObjectId}/last")
    fun reservationsLastThisTime(@PathVariable restaurantObjectId: String, @RequestParam("period") period: Period): ResponseEntity<Long> {
        return ResponseEntity.ok(jdbcReservationRepository.countReservationsLastThisDate(restaurantObjectId, period))
    }

    @Operation(summary = "Find reservations made between specified period")
    @GetMapping("/reservations/{restaurantObjectId}/between")
    fun reservationsDuringSpecifiedPeriod(@PathVariable restaurantObjectId: String, @RequestParam from: String, @RequestParam to: String): ResponseEntity<Long> {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val formattedFrom = LocalDateTime.parse(from, formatter)
        val formattedTo = LocalDateTime.parse(to, formatter)

        return ResponseEntity.ok(jdbcReservationRepository.countReservationsBetweenDate(formattedFrom, formattedTo, restaurantObjectId))
    }

    @Operation(summary = "Get all timeslots in the restaurant")
    @GetMapping("/timeslots/{restaurantObjectId}")
    fun allTimeslots(@PathVariable restaurantObjectId: String): ResponseEntity<List<TimeSlotDto>> {
        return ResponseEntity.ok(jdbcReservationRepository.findTimeslotsByObjectId(restaurantObjectId))
    }

    @Operation(summary = "Create a restaurant")
    @PostMapping("/create")
    fun createRestaurant(@RequestBody restaurant: CreateRestaurantCommand): ResponseEntity<GenericResponse> {
        restaurantRepository.save(RestaurantObjectMother.createRestaurant(restaurant.name, restaurant.openingHours))
        return ResponseEntity.ok(GenericResponse("Restaurant successfully created!"))
    }

}

data class CreateRestaurantCommand(
    val name: String, val openingHours: String
)

enum class Period {
    HOUR,
    DAY,
    MONTH,
    YEAR,
    ALL_TIME
}