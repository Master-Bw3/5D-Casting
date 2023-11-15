package net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import net.masterbw3.fivedimcasting.api.casting.iota.CellIota
import net.masterbw3.fivedimcasting.api.utils.Complex
import net.masterbw3.fivedimcasting.api.utils.Quaternion
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes.CELL
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes.QUATERNION

fun Iterator<IndexedValue<Iota>>.nextQuaternion(argc: Int = 0): Quaternion {
    val (idx, x) = this.next()
    if (x.isCastableTo(QUATERNION))
        return x.castTo(QUATERNION).quaternion
    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "quaternion")
}

fun Iterator<IndexedValue<Iota>>.nextComplexNumber(argc: Int = 0): Complex {
    val (idx, x) = this.next()
    if (x.isCastableTo(QUATERNION)) {
        val q = x.castTo(QUATERNION)
        if (q.x2 == 0.0 && q.x3 == 0.0)
            return Complex(q.x0, q.x1)
    }
    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "complex number")
}

fun Iterator<IndexedValue<Iota>>.nextRealNumber(argc: Int = 0): Double {
    val (idx, x) = this.next()
    if (x.isCastableTo(QUATERNION)) {
        val q = x.castTo(QUATERNION)
        if (q.isReal)
            return q.x0
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "double")
}

fun Iterator<IndexedValue<Iota>>.nextCell(argc: Int = 0): CellIota {
    val (idx, x) = this.next()
    if (x.isCastableTo(CELL))
        return x.castTo(CELL)
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "cell")
}