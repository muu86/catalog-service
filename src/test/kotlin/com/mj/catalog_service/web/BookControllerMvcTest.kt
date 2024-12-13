package com.mj.catalog_service.web

import com.mj.catalog_service.domain.BookNotFoundException
import com.mj.catalog_service.domain.BookService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookController::class)
class BookControllerMvcTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var bookService: BookService

    @Test
    fun `when get book not existing then should return 404`() {
        val isbn = "73737313940"
        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException::class.java)

        mockMvc.perform(get("/books/$isbn"))
            .andExpect(status().isNotFound)
    }
}