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

@Entity
@Table
class Restaurant(

    @Column(columnDefinition = "TEXT", nullable = false)
    var name: String,

    @Column(columnDefinition = "TEXT", nullable = false)
    var openingHours: String,

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
    var capacity: Short,

    @Column
    var availability: Boolean,

    @Column
    var placed: LocalDateTime? = null,

    @Column
    var bookingFor: LocalDateTime? = null,

    @Column(columnDefinition = "TEXT")
    var specialEvent: String? = null,

    @Column(columnDefinition = "TEXT")
    var notes: String? = null,

    @Column
    var clientStatus: String, // TODO: Confirmed, Un-confirmed, Seated... ?

    @OneToOne
    @JoinColumn(name = "client_id")
    var client: Client?,

    @ManyToOne()
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)
