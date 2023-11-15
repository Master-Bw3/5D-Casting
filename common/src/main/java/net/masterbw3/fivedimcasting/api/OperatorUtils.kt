package net.masterbw3.fivedimcasting.api

import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import net.masterbw3.fivedimcasting.api.casting.iota.CellIota
import net.masterbw3.fivedimcasting.api.casting.iota.ContinuumIota
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota
import net.masterbw3.fivedimcasting.api.utils.Complex

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

fun List<Iota>.getQuaternion(idx: Int, argc: Int = 0): QuaternionIota {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is QuaternionIota)
        return x
    if (x is DoubleIota)
        return QuaternionIota(x.double, 0.0, 0.0, 0.0)

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "quaternion")
}

fun List<Iota>.getRealNumber(idx: Int, argc: Int = 0): Double {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is QuaternionIota && x.isReal)
        return x.x0
    if (x is DoubleIota)
        return x.double

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "double")
}

fun List<Iota>.getComplexNumber(idx: Int, argc: Int = 0): Complex {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is QuaternionIota && x.x2 == 0.0 && x.x3 == 0.0)
        return Complex(x.x0, x.x1)
    if (x is DoubleIota)
        return Complex(x.double, 0.0)

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "double")
}

