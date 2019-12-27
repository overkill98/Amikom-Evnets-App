package com.belajar.amikomevent.network

class ApiEndPoint {

    companion object {
        private val BASE_URL = "http://192.168.100.83/amikom_event/"
        val CREATE = BASE_URL + "create.php"
        val READ = BASE_URL + "read.php"
        val DELETE = BASE_URL + "delete.php"
        val UPDATE = BASE_URL + "update.php"
    }
}