package ru.coocos.merge

import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

class UserWriterTest {

    @Test
    fun testEmpty() {
        val outputStream = ByteArrayOutputStream()
        val userWriter = UserWriter(outputStream)
        userWriter.flush()
        assert(outputStream.toByteArray().isEmpty())
    }

    @Test
    fun testOneUserOneMail() {
        val outputStream = ByteArrayOutputStream()
        val userWriter = UserWriter(outputStream)
        userWriter.write(User("user1", hashSetOf("mail1")))
        userWriter.flush()
        assert(outputStream.toString("UTF-8").equals("user1: mail1\n"))
    }

    @Test
    fun testOneUserTwoMails() {
        val outputStream = ByteArrayOutputStream()
        val userWriter = UserWriter(outputStream)
        userWriter.write(User("user1", hashSetOf("mail1", "mail2")))
        userWriter.flush()
        val result = outputStream.toString("UTF-8")
        assert(result.equals("user1: mail1, mail2\n") || result.equals("user1: mail2, mail1\n"))
    }

    @Test
    fun testOneWithoutMails() {
        val outputStream = ByteArrayOutputStream()
        val userWriter = UserWriter(outputStream)
        userWriter.write(User("user1", hashSetOf()))
        userWriter.flush()
        assert(outputStream.toString("UTF-8").equals("user1: \n"))
    }

    @Test
    fun testTwoUsers() {
        val outputStream = ByteArrayOutputStream()
        val userWriter = UserWriter(outputStream)
        userWriter.write(User("user1", hashSetOf("mail1")))
        userWriter.write(User("user2", hashSetOf("mail2")))
        userWriter.flush()
        assert(outputStream.toString("UTF-8").equals(
                "user1: mail1\n" +
                "user2: mail2\n"
        ))
    }
}