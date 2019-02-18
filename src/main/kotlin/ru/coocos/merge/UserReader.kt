package ru.coocos.merge

import java.io.InputStream
import java.util.*

class UserReader(inputStream : InputStream) {

    private val scanner = Scanner(inputStream)
    private var nextValue = parseNext()

    fun next() : User? {
        if (!hasNext()) {
            return null
        }
        val currentValue = nextValue
        nextValue = null
        return currentValue
    }

    fun hasNext() : Boolean {
        if (nextValue != null) {
            return true
        }
        nextValue = parseNext()
        return nextValue != null
    }

    private fun parseNext() : User? {
        val splittedLine = getNextLine().split(":")
        if (splittedLine.size == 2) {
            val username = splittedLine[0].trim()
            val mails = splittedLine[1].trim().split(",").map { mail -> mail.trim() }
            if (mails.isNotEmpty()) {
                return User(username, mails.toHashSet(), false)
            }
        }
        return null
    }

    private fun getNextLine() : String {
        try {
            return scanner.nextLine()
        } catch (e : NoSuchElementException) {
            return ""
        }
    }
}