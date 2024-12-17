package com.mj.catalog_service.domain

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import jakarta.validation.ValidatorFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class BookValidationTest {
    companion object {
        private lateinit var validator: Validator

        @JvmStatic
        @BeforeAll
        fun setUp() {
            val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
            validator = factory.validator
        }
    }

    @Test
    fun `when all fields correct then validation succeeds`() {
        val book = Book.of("1234567890", "Title", "Author", 9.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).isEmpty()
    }

    @Test
    fun `when ISBN not defined then validation fails`() {
        val book = Book.of("", "Title", "Author", 9.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(2)
        val constraintViolationMessages = violations.map { it.message }
        assertThat(constraintViolationMessages)
            .contains("The book ISBN must be defined.")
            .contains("The ISBN format must be valid.")
    }

    @Test
    fun `when ISBN defined but incorrect then validation fails`() {
        val book = Book.of("a234567890", "Title", "Author", 9.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The ISBN format must be valid.")
    }

    @Test
    fun `when title is not defined then validation fails`() {
        val book = Book.of("1234567890", "", "Author", 9.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The book title must be defined.")
    }

    @Test
    fun `when author is not defined then validation fails`() {
        val book = Book.of("1234567890", "Title", "", 9.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The book author must be defined.")
    }

    @Test
    fun `when price is not defined then validation fails`() {
        val book = Book.of("1234567890", "Title", "Author", null)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The book price must be defined.")
    }

    @Test
    fun `when price defined but zero then validation fails`() {
        val book = Book.of("1234567890", "Title", "Author", 0.0)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The book price must be greater than zero.")
    }

    @Test
    fun `when price defined but negative then validation fails`() {
        val book = Book.of("1234567890", "Title", "Author", -9.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The book price must be greater than zero.")
    }
}