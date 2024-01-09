package net.masterbw3.fivedimcasting.api

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE
import net.masterbw3.fivedimcasting.mixinImpl.IMixinIota
import net.masterbw3.fivedimcasting.api.casting.iota.CellIota
import net.masterbw3.fivedimcasting.api.casting.iota.StreamIota
import net.masterbw3.fivedimcasting.api.utils.Complex
import net.masterbw3.fivedimcasting.api.utils.Quaternion
import net.masterbw3.fivedimcasting.common.lib.FiveDimCastingIotaTypes.CELL
import net.masterbw3.fivedimcasting.common.lib.FiveDimCastingIotaTypes.QUATERNION
import net.masterbw3.fivedimcasting.common.lib.FiveDimCastingIotaTypes.STREAM

fun List<Iota>.getStream(idx: Int, argc: Int = 0): StreamIota {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    val iota = (x as IMixinIota).tryCastTo(STREAM)
    if (iota.isPresent)
        return iota.get()

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "stream")
}

fun List<Iota>.getCell(idx: Int, argc: Int = 0): CellIota {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    val iota = (x as IMixinIota).tryCastTo(CELL)
    if (iota.isPresent)
        return iota.get()

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "cell")
}

fun List<Iota>.getQuaternion(idx: Int, argc: Int = 0): Quaternion {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    val iota = (x as IMixinIota).tryCastTo(QUATERNION)
    if (iota.isPresent)
        return iota.get().quaternion

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "quaternion")
}

//this is probably exactly the same as getDouble
fun List<Iota>.getRealNumber(idx: Int, argc: Int = 0): Double {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    val iota = (x as IMixinIota).tryCastTo(DOUBLE)
    if (iota.isPresent)
        return iota.get().double
    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "double")
}

fun List<Iota>.getComplexNumber(idx: Int, argc: Int = 0): Complex {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    val iota = (x as IMixinIota).tryCastTo(QUATERNION)
    if (iota.isPresent) {
        val q = iota.get()
        if (q.x2 == 0.0 && q.x3 == 0.0)
            return Complex(q.x0, q.x1)
    }
    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "double")
}

