package com.mj.catalog_service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    @GetMapping("/")
    fun getGreeting(): String {
        return "도서 카탈로그에 오신 것을 환영합니다!"
    }
}