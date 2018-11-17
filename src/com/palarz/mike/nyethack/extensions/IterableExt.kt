package com.palarz.mike.nyethack.extensions

fun <T> Iterable<T>.random(): T = this.shuffled().first()