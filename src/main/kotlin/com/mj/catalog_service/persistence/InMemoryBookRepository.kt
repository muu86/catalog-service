package com.mj.catalog_service.persistence

import com.mj.catalog_service.domain.Book
import com.mj.catalog_service.domain.BookRepository
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryBookRepository : BookRepository {

    companion object {
        private val books = ConcurrentHashMap<String, Book>()
    }

    override fun findAll(): Iterable<Book> = books.values

    override fun findByIsbn(isbn: String): Optional<Book> =
        if (existsByIsbn(isbn)) Optional.of(books[isbn]!!) else Optional.empty()

    override fun existsByIsbn(isbn: String): Boolean = books[isbn] != null

    override fun save(book: Book): Book {
        books[book.isbn] = book
        return book
    }

    override fun deleteByIsbn(isbn: String) {
        books.remove(isbn)
    }
}