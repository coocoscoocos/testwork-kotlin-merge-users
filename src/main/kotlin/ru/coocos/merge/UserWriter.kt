package ru.coocos.merge

import java.io.OutputStream
import java.io.OutputStreamWriter

class UserWriter(outputStream : OutputStream) {

    val outputStreamWriter = OutputStreamWriter(outputStream)

    fun write(user : User) {
        outputStreamWriter.write(user.name + ": " + user.mails.joinToString(", ") + "\n")
    }

    fun flush() {
        outputStreamWriter.flush()
    }
}