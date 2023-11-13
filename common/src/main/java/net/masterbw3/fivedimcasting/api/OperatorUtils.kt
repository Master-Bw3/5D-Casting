package net.masterbw3.fivedimcasting.api

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import net.masterbw3.fivedimcasting.api.casting.iota.CellIota
import net.masterbw3.fivedimcasting.api.casting.iota.ContinuumIota

fun List<Iota>.getContinuum(idx: Int, argc: Int = 0): ContinuumIota {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is ContinuumIota)
        return x

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "continuum")
}

fun List<Iota>.getCell(idx: Int, argc: Int = 0): CellIota {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is CellIota)
        return x

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "cell")
}