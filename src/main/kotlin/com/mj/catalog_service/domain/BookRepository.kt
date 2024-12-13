package com.mj.catalog_service.domain

import java.util.*

interface BookRepository {
    fun findAll(): Iterable<Book>

    fun findByIsbn(isbn: String): Optional<Book>

    fun existsByIsbn(isbn: String): Boolean

    fun save(book: Book): Book

    fun deleteByIsbn(isbn: String)
}