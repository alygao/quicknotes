package com.example.a4_basic
import java.util.StringTokenizer
import kotlin.collections.ArrayList

// Generate random title/body/importance
object Util{
    private val loremIpsum : ArrayList<String> = ArrayList()
    private var totalWords: Int = 0
    init {
        val loremIpsumPassage = "Lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua Ut enim ad minim veniam quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur Excepteur sint occaecat cupidatat non proident sunt in culpa qui officia deserunt mollit anim id est laborum"
        val st = StringTokenizer(loremIpsumPassage.lowercase())
        while(st.hasMoreTokens()) {
            loremIpsum.add(st.nextToken())
        }
        totalWords = loremIpsum.size
    }

    // Each random note has a 1 to 3 word title (in Title Case)
    fun getRandomTitle() : String {
        val numWords = (0..2).random()

        return buildString {
            for (i in 0..numWords) {
                val index = (0 until totalWords).random()
                append("${loremIpsum[index].substring(0, 1).uppercase() + loremIpsum[index].substring(1)} ")
            }
        }.trim()
    }

    // Each random note has a 2 to 5 sentence body (each sentence is 3 to 10 words,
    // first letter capitalized with period and space at end)
    fun getRandomBody() : String {
        val numSentences = (2..5).random()
        return buildString {
            for (i in 0..numSentences) {
                var index = (0 until totalWords).random()
                append(loremIpsum[index].substring(0, 1).uppercase() + loremIpsum[index].substring(1))
                val numWords = (2..9).random()

                for (j in 0..numWords) {
                    index = (0 until totalWords).random()
                    append(" ${loremIpsum[index]}")
                }
                append(". ")
            }
        }
    }

    // Each random note has a 1 in 5 chance that it is flagged as important.
    fun getRandomImportance() : Boolean {
        return (1..5).random() == 1
    }
}
