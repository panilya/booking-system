package com.cloudwaitress.bookingsystem.endpoints

import com.cloudwaitress.bookingsystem.booking.JdbcReservationRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/admin")
@RestController
class AdminController(
    private val jdbcReservationRepository: JdbcReservationRepository
) {

    @GetMapping("/reservations")
    fun reservationLastThisTime(@RequestParam("period") period: Period): ResponseEntity<Long> {
        return ResponseEntity.ok(jdbcReservationRepository.countReservationsLastThisDate(period))
    }

}

enum class Period {
    HOUR,
    DAY,
    MONTH,
    YEAR,
    ALL_TIME
}