package net.masterbw3.fivedimcasting.common.lib

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi.modLoc
import net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.CellArithmetic
import net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.QuaternionArithmetic
import net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.StreamArithmetic
import net.minecraft.util.Identifier
import java.util.function.BiConsumer

object FiveDimCastingArithmetics {
    @JvmStatic
    fun register(r: BiConsumer<Arithmetic, Identifier>) {
        for ((key, value) in ARITHMETICS) {
            r.accept(value, key)
        }
    }

    private val ARITHMETICS: MutableMap<Identifier, Arithmetic> = LinkedHashMap()

    val QUATERNION: QuaternionArithmetic = make("quaternion", QuaternionArithmetic)

    val CELL: CellArithmetic = make("cell", CellArithmetic)

    val STREAM: StreamArithmetic = make("stream", StreamArithmetic)

    private fun <T : Arithmetic> make(name: String, arithmetic: T): T {
        val old = ARITHMETICS.put(modLoc(name), arithmetic)
        require(old == null) { "Typo? Duplicate id $name" }
        return arithmetic
    }
}