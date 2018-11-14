package com.palarz.mike.nyethack

fun main(args: Array<String>) {
    val player = Player("Madrigal")
    player.castFireball()

    var currentRoom: Room = Townsquare()
    println(currentRoom.description())
    println(currentRoom.load())

    val auraColor = player.auraColor()

    // Printing the resulting player status
    printPlayerStatus(player)
    player.auraColor()
}

private fun printPlayerStatus(player: Player) {
    println("(Aura: ${player.auraColor()}) " +
            "(Blessed: ${if (player.isBlessed) "YES" else "NO"})")
    println("${player.name} ${player.formatHealthStatus()}")
}
