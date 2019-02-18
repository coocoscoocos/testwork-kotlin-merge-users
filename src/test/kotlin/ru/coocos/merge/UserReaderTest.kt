package ru.coocos.merge

import org.junit.jupiter.api.Test
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream

class UserReaderTest {

    @Test
    fun testEmpty() {
        val inputData = "\n"
        val userReader = UserReader(inputData.byteInputStream())
        assert(!userReader.hasNext())
    }

    @Test
    fun testEmptyNullOnNextWithHasNext() {
        val inputData = "\n"
        val userReader = UserReader(inputData.byteInputStream())
        assert(!userReader.hasNext())
        assert(userReader.next() == null)
    }

    @Test
    fun testEmptyNullOnNextWithoutHasNext() {
        val inputData = "\n"
        val userReader = UserReader(inputData.byteInputStream())
        assert(userReader.next() == null)
    }

    @Test
    fun testUserWithoutMails() {
        val inputData = "user1:\n"
        val userReader = UserReader(inputData.byteInputStream())
        assert(userReader.hasNext())
        val user = userReader.next()
        assert(user != null)
        assert(user?.name.equals("user1"))
    }

    @Test
    fun testOneUserOneMail() {
        val inputData = "user1: mail1\n"
        val userReader = UserReader(inputData.byteInputStream())
        assert(userReader.hasNext())
        val user = userReader.next()
        assert(user != null)
        assert(user?.name.equals("user1"))
        assert(user?.mails?.first().equals("mail1"))
    }

    @Test
    fun testOneUserTwoMails() {
        val inputData = "user1: mail1, mail2\n"
        val userReader = UserReader(inputData.byteInputStream())
        assert(userReader.hasNext())
        val user = userReader.next()
        assert(user != null)
        assert(user!!.name.equals("user1"))
        assert(user.mails.containsAll(setOf("mail1", "mail2")))
    }

    @Test
    fun testTwoUsersTwoMails() {
        val inputData =
                "user1: mail1, mail2\n" +
                "user2: mail3, mail4\n"

        val userReader = UserReader(inputData.byteInputStream())
        assert(userReader.hasNext())
        val user1 = userReader.next()
        assert(user1 != null)
        assert(user1!!.name.equals("user1"))
        assert(user1.mails.containsAll(setOf("mail1", "mail2")))
        assert(userReader.hasNext())
        val user2 = userReader.next()
        assert(user2 != null)
        assert(user2!!.name.equals("user2"))
        assert(user2.mails.containsAll(setOf("mail3", "mail4")))
    }

    @Test
    fun testTermination() {
        val inputData =
                "user1: mail1, mail2\n\n" +
                "user2: mail4, mail5\n"
        val userReader = UserReader(inputData.byteInputStream())
        assert(userReader.hasNext())
        userReader.next()
        assert(!userReader.hasNext())
    }

}