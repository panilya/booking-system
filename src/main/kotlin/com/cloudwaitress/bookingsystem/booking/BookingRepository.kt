package com.cloudwaitress.bookingsystem.booking

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.persistence.Table

interface ClientRepository : JpaRepository<Client, Long> {}

interface RestaurantRepository : JpaRepository<Restaurant, Long> {

    fun findByName(name: String): Optional<Restaurant>

}

interface TableRepository : JpaRepository<com.cloudwaitress.bookingsystem.booking.Table, Long> {}

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
@Table
class Client(

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @OneToOne(mappedBy = "client")
    var table: com.cloudwaitress.bookingsystem.booking.Table? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

@Entity
@Table(name = "tables")
class Table(

    @Column
    var number: Int? = null,

    @Column
    var capacity: Short,

    @Column(columnDefinition = "TEXT")
    var specialEvent: String? = null,

    @Column(columnDefinition = "TEXT")
    var notes: String? = null,

    @Enumerated(EnumType.STRING)
    var tableStatus: TableStatus? = null,

    @Column
    var reserved: Boolean? = null,

    @Column
    var placed: LocalDateTime? = null,

    @OneToOne
    @JoinColumn(name = "client_id")
    var client: Client?,

    @ManyToOne()
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant,

    @ManyToOne()
    @JoinColumn(name = "time_slot_id")
    var timeSlot: TimeSlot? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

@Entity
@Table(name = "time_slot")
class TimeSlot(

    @Column(name = "timeslot")
    var timeSlot: LocalDateTime,

    @OneToMany(mappedBy = "timeSlot")
    var table: MutableList<com.cloudwaitress.bookingsystem.booking.Table> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)

enum class TableStatus { // TODO: add more
    UNCONFIRMED, CONFIRMED, SEATED
}
