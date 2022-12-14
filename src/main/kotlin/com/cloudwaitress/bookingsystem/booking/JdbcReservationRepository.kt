package com.cloudwaitress.bookingsystem.booking

import com.cloudwaitress.bookingsystem.endpoints.Period
import com.cloudwaitress.bookingsystem.endpoints.dto.TimeSlotDto
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class JdbcReservationRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {

    fun countReservationsBetweenDate(createdAtStart: LocalDateTime, createdAtEnd: LocalDateTime, objectId: String): Long {
        println(createdAtStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        val sqlQuery = """
            SELECT count(r.id) FROM restaurant JOIN reservation r ON restaurant.id = r.restaurant_id 
            WHERE r.created_at BETWEEN '${createdAtStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}'::timestamp 
            AND '${createdAtEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}'::timestamp 
            AND restaurant.object_id = :objectId
        """.trimIndent()

        val namedParameters: SqlParameterSource = MapSqlParameterSource()
//            .addValue("createdAtStart", createdAtStart.toString())
//            .addValue("createdAtEnd", createdAtEnd.toString())
            .addValue("objectId", objectId)

        return jdbcTemplate.queryForObject(sqlQuery, namedParameters, Long::class.java)!!
    }

    fun countReservationsLastThisTimePeriod(objectId: String, period: Period): Long {
        val sql = if (period == Period.ALL_TIME) {
            "SELECT count(r.id) FROM restaurant JOIN reservation r ON restaurant.id = r.restaurant_id WHERE restaurant.object_id = :objectId"
        } else {
            "SELECT count(r.id) FROM restaurant JOIN reservation r ON restaurant.id = r.restaurant_id WHERE created_at >= now() - '1 ${period.name.lowercase()}'::INTERVAL AND restaurant.object_id = :objectId"
        }

        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("objectId", objectId)

        return jdbcTemplate.queryForObject(sql, namedParameters, Long::class.java)!!
    }

    fun findTimeslotsByObjectId(objectId: String): List<TimeSlotDto> {
        val sql =
        """
            SELECT time_slot.object_id AS timeslotObjectId, time_slot.timeslot AS timeSlot, restaurant.object_id AS restaurantObjectId, restaurant.name AS restaurantName, restaurant.opening_hours AS openingHours
            FROM time_slot JOIN restaurant ON time_slot.restaurant_id = restaurant.id
            WHERE restaurant.object_id = :objectId
        """.trimIndent()

        val namedParameters: SqlParameterSource = MapSqlParameterSource()
            .addValue("objectId", objectId)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val timeslots = jdbcTemplate.query(
            sql,
            namedParameters,
            RowMapper { rs: ResultSet, _: Int ->
                TimeSlotDto(
                    rs.getString("timeslotObjectId"),
                    LocalDateTime.parse(rs.getString("timeSlot"), formatter),
                    rs.getString("restaurantObjectId"),
                    rs.getString("restaurantName"),
                    rs.getString("openingHours")
                )
            }
        )

        return timeslots
    }
}
