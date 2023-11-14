package net.masterbw3.fivedimcasting.fabric.mixinImpl

import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota

fun List<Iota>.replaceGetDouble(idx: Int, argc: Int = 0): Double {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        return x.double
    } else {
        // TODO: I'm not sure this calculation is correct
        throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "double")
    }
}