package ru.coocos.merge

import java.io.InputStream
import java.io.OutputStream

fun main(args : Array<String>) {
    merge(System.`in`, System.out.buffered())
}

fun merge(inputStream: InputStream, outputStream: OutputStream) {
    val userReader = UserReader(inputStream)
    val merger = Merger()
    val userWriter = UserWriter(outputStream)
    while (userReader.hasNext()) {
        userReader.next()?.let(merger::add)
    }
    merger.getResult().forEach(userWriter::write)
    userWriter.flush()
}