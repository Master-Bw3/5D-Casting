package net.masterbw3.fivedimcasting.mixinImpl

import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota

object DoubleCompatibilityMixins {
    fun List<Iota>.replaceQuaternionsWithDoubles(): List<Iota> {
        val newList: MutableList<Iota> = mutableListOf()
        for (iota in this) {
            if (iota is QuaternionIota && iota.isReal) {
                newList.add(DoubleIota(iota.x0))
            } else {
                newList.add(iota)
            }
        }
    return newList
    }
}