package com.mj.catalog_service

import com.mj.catalog_service.domain.Book
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationKtTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `when get request with id then book returned`() {
        val bookIsbn = "1231231230"
        val bookToCreate = Book(bookIsbn, "Title", "Author", 9.90)
        val expectedBook: Book = webTestClient
            .post()
            .uri("/books")
            .bodyValue(bookToCreate)
            .exchange()
            .expectStatus().isCreated
            .expectBody(Book::class.java)
            .value { assertThat(it).isNotNull }
            .returnResult()
            .responseBody as Book

        webTestClient
            .get()
            .uri("/books/$bookIsbn")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(Book::class.java)
            .value {
                assertThat(it).isNotNull
                assertThat(it.isbn).isEqualTo(expectedBook.isbn)
            }
    }

    @Test
    fun `when post request then book created`() {
        val expectedBook = Book("1231231231", "Title", "Author", 9.90)

        webTestClient
            .post()
            .uri("/books")
            .bodyValue(expectedBook)
            .exchange()
            .expectStatus().isCreated
            .expectBody(Book::class.java)
            .value { actualBook ->
                assertThat(actualBook).isNotNull
                assertThat(actualBook.isbn).isEqualTo(expectedBook.isbn)
            }
    }

    @Test
    fun `when put request then book updated`() {
        val bookIsbn = "1231231232"
        val bookToCreate = Book(bookIsbn, "Title", "Author", 9.90)
        val createdBook: Book = webTestClient
            .post()
            .uri("/books")
            .bodyValue(bookToCreate)
            .exchange()
            .expectStatus().isCreated
            .expectBody(Book::class.java)
            .value { book -> assertThat(book).isNotNull }
            .returnResult()
            .responseBody as Book

        val bookToUpdate = Book(createdBook.isbn, createdBook.title, createdBook.author, 7.95)

        webTestClient
            .put()
            .uri("/books/$bookIsbn")
            .bodyValue(bookToUpdate)
            .exchange()
            .expectStatus().isOk
            .expectBody(Book::class.java)
            .value { actualBook ->
                assertThat(actualBook).isNotNull
                assertThat(actualBook.price).isEqualTo(bookToUpdate.price)
            }
    }

    @Test
    fun `when delete request then book deleted`() {
        val bookIsbn = "1231231233"
        val bookToCreate = Book(bookIsbn, "Title", "Author", 9.90)

        webTestClient
            .post()
            .uri("/books")
            .bodyValue(bookToCreate)
            .exchange()
            .expectStatus().isCreated

        webTestClient
            .delete()
            .uri("/books/$bookIsbn")
            .exchange()
            .expectStatus().isNoContent

        webTestClient
            .get()
            .uri("/books/$bookIsbn")
            .exchange()
            .expectStatus().isNotFound
            .expectBody(String::class.java)
            .value { errorMessage ->
                assertThat(errorMessage).isEqualTo("The book with ISBN $bookIsbn was not found.")
            }
    }
}