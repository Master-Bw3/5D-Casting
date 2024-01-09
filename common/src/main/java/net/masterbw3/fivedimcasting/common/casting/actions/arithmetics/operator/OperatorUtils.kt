package net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import net.masterbw3.fivedimcasting.mixinImpl.IMixinIota
import net.masterbw3.fivedimcasting.api.casting.iota.CellIota
import net.masterbw3.fivedimcasting.api.utils.Complex
import net.masterbw3.fivedimcasting.api.utils.Quaternion
import net.masterbw3.fivedimcasting.common.lib.FiveDimCastingIotaTypes.CELL
import net.masterbw3.fivedimcasting.common.lib.FiveDimCastingIotaTypes.QUATERNION

fun Iterator<IndexedValue<Iota>>.nextQuaternion(argc: Int = 0): Quaternion {
    val (idx, x) = this.next()
    val iota = (x as IMixinIota).tryCastTo(QUATERNION)
    if (iota.isPresent)
        return iota.get().quaternion
    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "quaternion")
}

fun Iterator<IndexedValue<Iota>>.nextComplexNumber(argc: Int = 0): Complex {
    val (idx, x) = this.next()
    val iota = (x as IMixinIota).tryCastTo(QUATERNION)
    if (iota.isPresent) {
        val q = iota.get()
        if (q.x2 == 0.0 && q.x3 == 0.0)
            return Complex(q.x0, q.x1)
    }
    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "complex number")
}

fun Iterator<IndexedValue<Iota>>.nextRealNumber(argc: Int = 0): Double {
    val (idx, x) = this.next()
    val iota = (x as IMixinIota).tryCastTo(QUATERNION)
    if (iota.isPresent) {
        val q = iota.get()
        if (q.isReal)
            return q.x0
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "double")
}

fun Iterator<IndexedValue<Iota>>.nextCell(argc: Int = 0): CellIota {
    val (idx, x) = this.next()
    val iota = (x as IMixinIota).tryCastTo(CELL)
    if (iota.isPresent)
        return iota.get()
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "cell")
}