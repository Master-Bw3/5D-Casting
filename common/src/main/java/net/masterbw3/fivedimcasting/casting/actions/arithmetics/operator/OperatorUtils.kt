package net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator

import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota
import net.masterbw3.fivedimcasting.api.utils.Complex
import net.masterbw3.fivedimcasting.api.utils.Quaternion

fun Iterator<IndexedValue<Iota>>.nextQuaternion(argc: Int = 0): Quaternion {
    val (idx, x) = this.next()
    if (x is QuaternionIota)
        return x.quaternion
    if (x is DoubleIota)
        return Quaternion(x.double, 0.0, 0.0, 0.0)

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "quaternion")
}

fun Iterator<IndexedValue<Iota>>.nextComplexNumber(argc: Int = 0): Complex {
    val (idx, x) = this.next()
    if (x is QuaternionIota && x.x2 == 0.0 && x.x3 == 0.0)
        return Complex(x.x0, x.x1)
    if (x is DoubleIota)
        return Complex(x.double, 0.0)

    throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "complex number")
}