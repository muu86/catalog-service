package com.mj.catalog_service.web

import com.mj.catalog_service.domain.Book
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.time.Instant

@JsonTest
class BookJsonTest {

    @Autowired
    private lateinit var json: JacksonTester<Book>

    @Test
    fun `test serialize`() {
        val now = Instant.now()
        val book = Book(394L, "1234567890", "Title", "Author", 9.90, now, now, 21)
        val jsonContent = json.write(book)

        assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
            .isEqualTo(book.id?.toInt())
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
            .isEqualTo(book.isbn)
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
            .isEqualTo(book.title)
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
            .isEqualTo(book.author)
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
            .isEqualTo(book.price)
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate")
            .isEqualTo(book.createdDate.toString())
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate")
            .isEqualTo(book.lastModifiedDate.toString())
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version")
            .isEqualTo(book.version)
    }

    @Test
    fun `test deserialize`() {
        val content = """
            {
                "isbn": "1234567890",
                "title": "Title",
                "author": "Author",
                "price": 9.90
            }
        """.trimIndent()

        assertThat(json.parse(content))
            .usingRecursiveComparison()
            .isEqualTo(Book.of("1234567890", "Title", "Author", 9.90))
    }
}