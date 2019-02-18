package ru.coocos.merge

import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream

class MergeTest {

    @Test
    fun testEmpty() {
        val inputData = "\n\n"
        val outputStream = ByteArrayOutputStream()
        merge(inputData.byteInputStream(), outputStream)
        assert(outputStream.toString("UTF-8").isEmpty())
    }

    @Test
    fun testWork() {
        val inputData =
                "user1: mail1, mail2\n" +
                "user2: mail3, mail4\n" +
                "user3: mail2, mail4\n" +
                "user4: mail5\n\n"
        val outputStream = ByteArrayOutputStream()
        merge(inputData.byteInputStream(), outputStream)
        println(outputStream.toString("UTF-8"))
    }
}