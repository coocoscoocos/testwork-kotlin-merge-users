package ru.coocos.merge

data class User(
        val name : String,
        val mails : HashSet<String>,
        var isProcessed : Boolean = false
)