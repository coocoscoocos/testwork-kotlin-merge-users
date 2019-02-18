package ru.coocos.merge

import org.junit.jupiter.api.Test
import kotlin.random.Random

class MergerTest {

    // не срабатывает BeforeEach
    private lateinit var merger : Merger

    @Test
    fun testEmptyInput() {
        merger = Merger()
        assert(merger.getResult().isEmpty())
    }

    @Test
    fun testOneUserOneMail() {
        merger = Merger()
        merger.add(User("user1", hashSetOf("mail1")))
        assert(merger.getResult().size == 1)
    }

    @Test
    fun testOneUserTwoMails() {
        merger = Merger()
        merger.add(User("user1", hashSetOf("mail1", "mail2")))
        assert(merger.getResult().size == 1)
    }

    @Test
    fun testTwoUsersOneMail() {
        merger = Merger()
        merger.add(User("user1", hashSetOf("mail1")))
        merger.add(User("user2", hashSetOf("mail1")))
        assert(merger.getResult().size == 1)
    }

    @Test
    fun testTwoUsersTwoMails() {
        merger = Merger()
        merger.add(User("user1", hashSetOf("mail1")))
        merger.add(User("user2", hashSetOf("mail2")))
        assert(merger.getResult().size == 2)
    }

    @Test
    fun testThreeUsersChain() {
        merger = Merger()
        merger.add(User("user1", hashSetOf("mail1", "mail2")))
        merger.add(User("user2", hashSetOf("mail3", "mail4")))
        merger.add(User("user3", hashSetOf("mail2", "mail4")))
        assert(merger.getResult().size == 1)
    }

    @Test
    fun testSixUsersChainTwoChains() {
        merger = Merger()
        merger.add(User("user1", hashSetOf("mail1", "mail2")))
        merger.add(User("user2", hashSetOf("mail3", "mail4")))
        merger.add(User("user5", hashSetOf("mail33", "mail44")))
        merger.add(User("user3", hashSetOf("mail2", "mail4")))
        merger.add(User("user4", hashSetOf("mail11", "mail22")))
        merger.add(User("user6", hashSetOf("mail22", "mail44")))
        assert(merger.getResult().size == 2)
    }
    @Test
    fun testAllMailsInResult() {
        merger = Merger()
        val userCount = 100
        val mailCount = 1000
        genUsers(userCount, mailCount).forEach(merger::add)
        val result = merger.getResult()
        val resultMails = result.flatMap { user -> user.mails }.toSet()
        (1..mailCount).forEach { assert(resultMails.contains("mail$it")) }
    }

    fun genUsers(userCount : Int, mailCount : Int) : Set<User> {
        val users : Set<User> = (1..userCount).map { User("user$it", hashSetOf()) }.toSet()
        (1..mailCount).forEach {
            users.random().mails.add("mail$it")
        }
        return users
    }

}