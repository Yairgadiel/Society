package com.gy.society

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}