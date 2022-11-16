package com.cloudwaitress.bookingsystem.booking

import org.springframework.data.jpa.repository.JpaRepository
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
interface ReservationRepository : JpaRepository<Reservation, Long> {}

@Repository
interface TimeSlotRepository : JpaRepository<TimeSlot, Long> {}

@Entity
@Table
class Restaurant(

    @Column(columnDefinition = "TEXT", nullable = false)
    var name: String,

    @Column(columnDefinition = "TEXT", nullable = false)
    var openingHours: String,

    @OneToMany(mappedBy = "restaurant")
    var table: MutableList<com.cloudwaitress.bookingsystem.booking.Table>? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

@Entity
@Table(name = "tables")
class Table(

    @Column
    var capacity: Int? = null,

    @ManyToOne
    var restaurant: Restaurant? = null,

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

@Entity
@Table
class Reservation(

    @Enumerated(EnumType.STRING)
    var status: TableStatus,

    @Enumerated(EnumType.STRING)
    var paymentStatus: PaymentStatus,

    @Column
    var partySize: Int,

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