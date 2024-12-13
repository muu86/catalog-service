package com.mj.catalog_service.domain

class BookAlreadyExistsException(isbn: String)
    : RuntimeException("A book with ISBN $isbn already exists.")