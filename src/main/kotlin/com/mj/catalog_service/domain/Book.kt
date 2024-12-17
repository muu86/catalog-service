package com.mj.catalog_service.domain

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.time.Instant

data class Book(
    @Id val id: Long? = null,

    @field:NotBlank(message = "The book ISBN must be defined.") @field:Pattern(
        regexp = "^([0-9]{10}|[0-9]{13})$",
        message = "The ISBN format must be valid."
    ) val isbn: String,

    @field:NotBlank(message = "The book title must be defined.") val title: String,

    @field:NotBlank(message = "The book author must be defined.") val author: String,

    val publisher: String,

    @field:NotNull(message = "The book price must be defined.") @field:Positive(message = "The book price must be greater than zero.") val price: Double?,

    @CreatedDate val createdDate: Instant? = null,

    @LastModifiedDate val lastModifiedDate: Instant? = null,

    @Version val version: Int = 0
) {
    companion object {
        fun of(
            isbn: String,
            title: String,
            author: String,
            publisher: String,
            price: Double?,
        ): Book {
            return Book(
                isbn = isbn, title = title, author = author, publisher = publisher, price = price,
            )
        }
    }
}
