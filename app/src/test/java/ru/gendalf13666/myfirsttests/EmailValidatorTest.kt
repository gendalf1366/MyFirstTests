package ru.gendalf13666.myfirsttests

import org.junit.Assert
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        Assert.assertTrue(EmailValidator.isValidEmail("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        Assert.assertTrue(EmailValidator.isValidEmail("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValidEmail("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValidEmail("name@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValidEmail("@email.com"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValidEmail(""))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValidEmail(null))
    }

    @Test
    fun emailValidator_OnlyUnderlineBeforeDogSymbol_ReturnsTrue() {
        Assert.assertTrue(EmailValidator.isValidEmail("_@mail.ru"))
    }

    @Test
    fun emailValidator_SomeDogSymbol_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValidEmail("name@@mail.ru"))
    }

    @Test
    fun emailValidator_OnlyTopLevelDomain_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValidEmail(".ru"))
    }

    @Test
    fun emailValidator_OnlyDogSymbolWithDomain_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValidEmail("@mail"))
    }

    @Test
    fun emailValidator_OnlyName_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValidEmail("name"))
    }

    @Test
    fun emailValidator_UnusedSymbols_ReturnsFalse() {
        Assert.assertFalse(EmailValidator.isValidEmail("name/mail.ru"))
    }
}
