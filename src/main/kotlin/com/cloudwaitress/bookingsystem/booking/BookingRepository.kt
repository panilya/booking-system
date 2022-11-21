package com.cloudwaitress.bookingsystem.booking

import com.cloudwaitress.bookingsystem.endpoints.dto.TimeSlotDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.persistence.Table

@Repository
interface ClientRepository : JpaRepository<Client, Long> {}

@Repository
interface RestaurantRepository : JpaRepository<Restaurant, Long> {

    fun findByName(name: String): Optional<Restaurant>

}

@Repository
interface TableRepository : JpaRepository<com.cloudwaitress.bookingsystem.booking.Table, Long> {}

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {

    @Query
    fun countByCreatedAtBetween(createdAtStart: LocalDateTime, createdAtEnd: LocalDateTime): Long

}

@Repository
interface TimeSlotRepository : JpaRepository<TimeSlot, Long> {



}

@Repository
interface RoomRepository : JpaRepository<Room, Long> {}

@Entity
@Table
class Restaurant(

    @Column(columnDefinition = "TEXT", nullable = false)
    var name: String,

    @Column(columnDefinition = "TEXT", nullable = false)
    var openingHours: String,

    @Column
    var objectId: String = UUID.randomUUID().toString(),

    @OneToMany(mappedBy = "restaurant")
    var rooms: MutableList<Room>? = null,

    @OneToMany(mappedBy = "restaurant")
    var table: MutableList<com.cloudwaitress.bookingsystem.booking.Table>? = null,

    @OneToMany(mappedBy = "restaurant")
    var timeSlots: MutableList<TimeSlot>? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

@Entity
@Table(name = "tables")
class Table(

    @Column
    var capacity: Int? = null,

    @Column
    var objectId: String = UUID.randomUUID().toString(),

    @ManyToOne
    var restaurant: Restaurant? = null,

    @ManyToOne
    @JoinColumn(name = "room_id")
    var room: Room,

    @OneToOne(mappedBy = "table")
    var reservation: Reservation? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

@Entity
@Table
class Client(

    @Column
    var objectId: String = UUID.randomUUID().toString(),

    @Column
    var name: String,

    @Column(columnDefinition = "TEXT")
    var phoneNumber: String? = null,

    @Column(columnDefinition = "TEXT")
    var email: String? = null,

    @OneToOne(mappedBy = "client")
    var reservation: Reservation? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

@Entity
@Table
class TimeSlot(

    @Column(columnDefinition = "timestamp")
    var timeslot: LocalDateTime,

    @OneToOne(mappedBy = "timeSlot")
    var reservation: Reservation? = null,

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant? = null,

    @Column
    var objectId: String = UUID.randomUUID().toString(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

@Entity
@Table
class Room(

    @Column(columnDefinition = "TEXT")
    var name: String,

    @Column
    var objectId: String = UUID.randomUUID().toString(),

    @OneToMany(mappedBy = "room")
    var tables: MutableList<com.cloudwaitress.bookingsystem.booking.Table>? = null,

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

@Entity
@Table
class Reservation(

    @Column
    var objectId: String = UUID.randomUUID().toString(),

    @Enumerated(EnumType.STRING)
    var status: TableStatus,

    @Enumerated(EnumType.STRING)
    var paymentStatus: PaymentStatus,

    @Column
    var partySize: Int,

    @Column
    var createdAt: LocalDateTime,

    @OneToOne
    @JoinColumn(name = "table_id")
    var table: com.cloudwaitress.bookingsystem.booking.Table? = null,

    @OneToOne
    @JoinColumn(name = "client_id")
    var client: Client,

    @OneToOne
    @JoinColumn(name = "time_slot_id")
    var timeSlot: TimeSlot,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

enum class TableStatus {
    ON_TIME, LATE, OVER_TIME
}

enum class PaymentStatus {
    PRE_PAID, NOT_PRE_PAID
}