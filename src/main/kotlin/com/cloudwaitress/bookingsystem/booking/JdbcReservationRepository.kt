package com.cloudwaitress.bookingsystem.booking

import com.cloudwaitress.bookingsystem.endpoints.Period
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Component

@Component
class JdbcReservationRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {

    fun countReservationsLastThisDate(period: Period): Long {
        val sql = if (period == Period.ALL_TIME) {
            "SELECT count(id) FROM Reservation"
        } else {
            "SELECT count(id) FROM Reservation WHERE created_at >= now() - '1 ${period.name.lowercase()}'::INTERVAL"
        }

        val namedParameters: SqlParameterSource = MapSqlParameterSource()

        return jdbcTemplate.queryForObject(sql, namedParameters, Long::class.java)!!
    }

}