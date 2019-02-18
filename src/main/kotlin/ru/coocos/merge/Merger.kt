package ru.coocos.merge

class Merger {

    private val mailIndex : HashMap<String, HashSet<String>> = hashMapOf()
    private val usersIndex : HashMap<String, User> = hashMapOf()

    fun add(user : User) {
        usersIndex.put(user.name, user)
        for (mail in user.mails) {
            mailIndex.putIfAbsent(mail, hashSetOf())
            mailIndex[mail]!!.add(user.name)
        }
    }

    fun getResult() : List<User> {
        usersIndex.values.forEach { it.isProcessed = false }
        val mergedUserList : MutableList<User> = arrayListOf()
        for ((_, user) in usersIndex) {
            if (!user.isProcessed) {
                mergedUserList.add(User(user.name, findAllMails(user.mails), true))
                user.isProcessed = true
            }
        }
        return mergedUserList
    }

    private fun findAllMails(mails : Set<String>) : HashSet<String> {
        val allMails : HashSet<String> = hashSetOf()
        val unprocessedMails : HashSet<String> = mails.toHashSet()
        while (unprocessedMails.isNotEmpty()) {
            val mail = unprocessedMails.first()
            unprocessedMails.remove(mail)
            if (!allMails.contains(mail)) {
                val users = mailIndex[mail]!!.map { usersIndex[it]!! }
                for (user in users) {
                    if (!user.isProcessed) {
                        user.isProcessed = true
                        user.mails
                                .filter { !allMails.contains(it) }
                                .let { unprocessedMails.addAll(it) }
                    }
                }
                allMails.add(mail)
            }
        }
        return allMails
    }
}