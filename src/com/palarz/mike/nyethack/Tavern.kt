package com.palarz.mike.nyethack

import java.io.File

const val TAVERN_NAME = "Taernyl's Folly"

val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniqueNames = mutableSetOf<String>()
val menuList = File("data/tavern-menu-data.txt")
                .readLines()

val patronGold = mutableMapOf<String, Double>()

fun main(args: Array<String>) {
    if (patronList.contains("Eli")){
        println("The tavern master says: Eli's in the back playing cards.")
    } else{
        println("The tavern master says: Eli isn't here.")
    }

    if (patronList.containsAll(listOf("Mordoc", "Sophie"))){
        println("The tavern master says: Yeah, they're seated by the stew kettle.")
    } else{
        println("The tavern master says: Nay, they departed hours ago.")
    }

    (0..9).forEach {
        val first = patronList.shuffled().first()
        val last = lastName.shuffled().first()
        val name = "$first $last"
        uniqueNames += name
    }
    uniqueNames.forEach {
        patronGold[it] = 6.0
    }

    var orderCount = 0
    while (orderCount <= 9){
        placeOrder(uniqueNames.shuffled().first(), menuList.shuffled().first())
        orderCount++
    }

    println(patronGold)
    displayPatronBalances()
}

fun performPurchase(price: Double, patronName: String){
    val totalPurse = patronGold.getValue(patronName)
    patronGold[patronName] = totalPurse - price
}

private fun placeOrder(patronName: String, menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("$patronName speaks with $tavernMaster about their order")

    val (type, name, price) = menuData.split(',')

    val message = "$patronName buys a $name ($type) for $price"
    println(message)

    performPurchase(price.toDouble(), patronName)

    val phrase = if (name == "Dragon's Breath"){
        "$patronName exclaims: ${toDragonSpeak("Ah, delicious $name")}"
//        "Madrigal exclaims: ${com.palarz.mike.nyethack.toDragonSpeak("DRAGON'S BREATH: IT'S GOT WHAT ADVENTURERS CRAVE")}"
    } else {
        "$patronName says: Thanks for the $name"
    }
    println(phrase)
}

private fun toDragonSpeak(phrase: String) =
        phrase.replace(Regex("[aAeEiIoOuU]")) {
            when(it.value) {
                "a", "A" -> "4"
                "e", "E" -> "3"
                "i", "I" -> "1"
                "o", "O" -> "0"
                "u", "U" -> "|_|"
                else -> it.value
            }
        }

private fun displayPatronBalances(){
    patronGold.forEach { patron, balance ->
        println("$patron, balanace: ${"%.2f".format(balance)}")
    }
}