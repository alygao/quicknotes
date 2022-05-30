package com.example.a4_basic

class Note () {
    // title (a single line), the body (multiple lines that wrap), and the importance
    var id: Int = -1
    var title: String = ""
    var body: String = ""
    var important: Boolean = false

    constructor (title: String,
                 body: String,
                 important: Boolean) : this() {
        this.title = title
        this.body = body
        this.important = important
    }

    constructor (id :Int,
                 title: String,
                 body: String,
                 important: Boolean) : this() {
        this.id = id
        this.title = title
        this.body = body
        this.important = important
    }

}
