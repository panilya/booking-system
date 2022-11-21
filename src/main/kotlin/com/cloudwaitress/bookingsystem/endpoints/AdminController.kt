package com.cloudwaitress.bookingsystem.endpoints

import com.cloudwaitress.bookingsystem.booking.JdbcReservationRepository
import com.cloudwaitress.bookingsystem.booking.ReservationRepository
import com.cloudwaitress.bookingsystem.booking.RestaurantRepository
import com.cloudwaitress.bookingsystem.booking.TimeSlotRepository
import com.cloudwaitress.bookingsystem.endpoints.dto.TimeSlotDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequestMapping("/admin")
@RestController
class AdminController(
    private val jdbcReservationRepository: JdbcReservationRepository,
    private val reservationRepository: ReservationRepository,
) {

    @GetMapping("/reservations/last")
    fun reservationsLastThisTime(@RequestParam("period") period: Period): ResponseEntity<Long> {
        return ResponseEntity.ok(jdbcReservationRepository.countReservationsLastThisDate(period))
    }

    @GetMapping("/reservations/between")
    fun reservationsDuringSpecifiedPeriod(@RequestParam from: String, @RequestParam to: String): ResponseEntity<Long> {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val formattedFrom = LocalDateTime.parse(from, formatter)
        val formattedTo = LocalDateTime.parse(to, formatter)

        return ResponseEntity.ok(reservationRepository.countByCreatedAtBetween(formattedFrom, formattedTo))
    }

    @GetMapping("/timeslots/{objectId}")
    fun allTimeslots(@PathVariable objectId: String): ResponseEntity<List<TimeSlotDto>> {
        return ResponseEntity.ok(jdbcReservationRepository.findTimeslotsByObjectId(objectId))
    }

}

enum class Period {
    HOUR,
    DAY,
    MONTH,
    YEAR,
    ALL_TIME
}