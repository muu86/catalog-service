package com.mj.catalog_service.domain

import com.mj.catalog_service.config.DataConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.test.context.ActiveProfiles

@Disabled
@DataJdbcTest
@Import(DataConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryJdbcTest @Autowired constructor(
    private val bookRepository: BookRepository,
    private val jdbcAggregateTemplate: JdbcAggregateTemplate
) {

    @Test
    fun `find a book by isbn when existing`() {
        val bookIsbn = "1234561237"
        val book = Book.of(bookIsbn, "Title", "Author", "Pub_A", 12.90)
        jdbcAggregateTemplate.insert(book)

        val actualBook = bookRepository.findByIsbn(bookIsbn)

        assertThat(actualBook).isPresent
        assertThat(actualBook.get().isbn).isEqualTo(book.isbn)
    }
}