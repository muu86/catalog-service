package com.mj.catalog_service.domain

class BookNotFoundException(isbn: String)
    : RuntimeException("The book with ISBN $isbn was not found.")