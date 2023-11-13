package net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator

import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota

fun Iterator<IndexedValue<Iota>>.nextNumber(argc: Int = 0): QuaternionIota {
    val (idx, x) = this.next()
    if (x is QuaternionIota)
        return x
    if (x is DoubleIota)
        return QuaternionIota(x.double, 0.0, 0.0, 0.0)

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "number")
}