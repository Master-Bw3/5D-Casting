package net.masterbw3.fivedimcasting.lib.hex

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi.modLoc
import net.masterbw3.fivedimcasting.api.casting.iota.CellIota
import net.masterbw3.fivedimcasting.api.casting.iota.ContinuumIota
import net.minecraft.util.Identifier
import org.jetbrains.annotations.ApiStatus
import java.util.function.BiConsumer

object FiveDimCastingIotaTypes {
    @JvmStatic
    @ApiStatus.Internal
    fun registerTypes(r: BiConsumer<IotaType<*>, Identifier>) {
        for ((key, value) in TYPES) {
            r.accept(value, key)
        }
    }

    private val TYPES: MutableMap<Identifier, IotaType<*>> = LinkedHashMap()

    @JvmField
    val CONTINUUM: IotaType<ContinuumIota> = type("continuum", ContinuumIota.TYPE)

    @JvmField
    val CELL: IotaType<CellIota> = type("cell", CellIota.TYPE)


    private fun <U : Iota, T : IotaType<U>> type(name: String, type: T): T {
        val old = TYPES.put(modLoc(name), type)
        require(old == null) { "Typo? Duplicate id $name" }
        return type
    }

}