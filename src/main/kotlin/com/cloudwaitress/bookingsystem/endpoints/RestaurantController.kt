package com.cloudwaitress.bookingsystem.endpoints

import com.cloudwaitress.bookingsystem.booking.RestaurantRepository
import com.cloudwaitress.bookingsystem.endpoints.dto.GenericResponse
import com.cloudwaitress.bookingsystem.fakedata.RestaurantObjectMother
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/admin/restaurant")
@RestController
class RestaurantController(
    private val restaurantRepository: RestaurantRepository
) {

    @PostMapping("/create")
    fun createRestaurant(@RequestBody restaurant: CreateRestaurantCommand): ResponseEntity<GenericResponse> {
        restaurantRepository.save(RestaurantObjectMother.createRestaurant(restaurant.name, restaurant.openingHours))
        return ResponseEntity.ok(GenericResponse("Restaurant successfully created!"))
    }

}

data class CreateRestaurantCommand(
    val name: String, val openingHours: String
)