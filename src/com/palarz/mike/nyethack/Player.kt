package com.palarz.mike.nyethack

import com.palarz.mike.nyethack.extensions.random as randomizer
import java.io.File

class Player(_name: String,
             override var healthPoints: Int = 100,
             val isBlessed: Boolean,
             private val isImmortal: Boolean) : Fightable{

    override val diceCount = 3
    override val diceSides = 6

    override fun attack(opponent: Fightable): Int {
        val damageDealt = if (isBlessed){
            damageRoll * 2
        } else{
            damageRoll
        }
        opponent.healthPoints -= damageDealt
        return damageDealt
    }

    var name = _name
    get() = "${field.capitalize()} of $hometown"
    private set(value) {
        field = value.trim()
    }

    constructor(name: String) : this(name,isBlessed = true, isImmortal = false) {
        if (name.toLowerCase() == "kar") healthPoints = 40
    }

    val hometown by lazy { selectHometown() }
    var currentPosition = Coordinate(0, 0)

    init {
        require(healthPoints > 0) {"healthPoints must be greater than zero."}
        require(name.isNotBlank()) {"Player must have a name"}
    }

    fun auraColor(): String {
        val auraVisible = (isBlessed && healthPoints > 50) || isImmortal
        val auraColor = if (auraVisible) "GREEN" else "NONE"
        return auraColor
    }

    fun formatHealthStatus() =
            when (healthPoints) {
                100 -> "is in excellent condition"
                in 90..99 -> "has a few scratches."
                in 75..89 -> if (isBlessed) {
                    "has some minor wounds but is healing quite quickly!"
                } else {
                    "has some minor wounds."
                }
                in 15..74 -> "looks pretty hurt."
                else -> "is in awful condition!"
            }

    fun castFireball(numberOfFireballs: Int = 2) =
            println("A glass of Fireball springs into existence. (x$numberOfFireballs)")

    private fun selectHometown(): String = File("data/towns.txt").readLines().randomizer()
}