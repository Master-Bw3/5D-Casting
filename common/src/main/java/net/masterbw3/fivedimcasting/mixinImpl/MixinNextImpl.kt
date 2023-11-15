package net.masterbw3.fivedimcasting.mixinImpl

import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota
import kotlin.math.abs
import kotlin.math.roundToInt

fun Iterator<IndexedValue<Iota>>.replaceNextDouble(argc: Int = 0): Double {
    val (idx, x) = this.next()
    if (x is DoubleIota) {
        return x.double
    } else if (x is QuaternionIota && x.isReal) {
        return x.x0
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "double")
}

fun Iterator<IndexedValue<Iota>>.replaceNextInt(argc: Int = 0): Int {
    val (idx, x) = this.next()
    if (x is DoubleIota) {
        val double = x.double
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE) {
            return rounded
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE) {
            return rounded
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "int")
}

fun Iterator<IndexedValue<Iota>>.replaceNextPositiveIntUnder(max: Int, argc: Int = 0): Int {
    val (idx, x) = this.next()
    if (x is DoubleIota) {
        val double = x.double
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in 0 until max) {
            return rounded
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in 0 until max) {
            return rounded
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "int.positive.less.equal", max)
}

fun Iterator<IndexedValue<Iota>>.replaceNextPositiveIntUnderInclusive(max: Int, argc: Int = 0): Int {
    val (idx, x) = this.next()
    if (x is DoubleIota) {
        val double = x.double
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in 0..max) {
            return rounded
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in 0..max) {
            return rounded
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "int.positive.less.equal", max)
}