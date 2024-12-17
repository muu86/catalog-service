package com.mj.catalog_service

import com.mj.catalog_service.config.PolarProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(private val polarProperties: PolarProperties) {
    @GetMapping("/")
    fun getGreeting(): String {
        return polarProperties.greeting ?: "Default Greeting"
    }
}