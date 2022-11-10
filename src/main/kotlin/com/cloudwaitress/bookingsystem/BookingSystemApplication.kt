package com.cloudwaitress.bookingsystem

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookingSystemApplication

fun main(args: Array<String>) {
    initDatabase()
    runApplication<BookingSystemApplication>(*args)
}

fun initDatabase(){
    val hikariConfig = HikariConfig("migration.properties")
    val dataSource = HikariDataSource(hikariConfig)

    val flyway = Flyway.configure().dataSource(dataSource).load()
    flyway.migrate()
}