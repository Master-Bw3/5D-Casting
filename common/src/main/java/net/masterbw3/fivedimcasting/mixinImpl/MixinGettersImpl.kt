package net.masterbw3.fivedimcasting.mixinImpl

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import com.mojang.datafixers.util.Either
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota
import net.minecraft.util.math.Vec3d
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.roundToLong

fun List<Iota>.replaceGetDouble(idx: Int, argc: Int = 0): Double {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    return if (x is DoubleIota) {
        x.double
    } else if (x is QuaternionIota && x.isReal) {
        x.x0
    } else {
        throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "double")
    }
}

fun List<Iota>.replaceGetPositiveDouble(idx: Int, argc: Int = 0): Double {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        val double = x.double
        if (0 <= double) {
            return double
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        if (0 <= double) {
            return double
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "double.positive")
}

fun List<Iota>.replaceGetPositiveDoubleUnder(idx: Int, max: Double, argc: Int = 0): Double {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        val double = x.double
        if (0.0 <= double && double < max) {
            return double
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        if (0.0 <= double && double < max)  {
            return double
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "double.positive.less", max)
}

fun List<Iota>.replaceGetPositiveDoubleUnderInclusive(idx: Int, max: Double, argc: Int = 0): Double {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        val double = x.double
        if (double in 0.0..max) {
            return double
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        if (double in 0.0..max) {
            return double
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "double.positive.less.equal", max)
}

fun List<Iota>.replaceGetDoubleBetween(idx: Int, min: Double, max: Double, argc: Int = 0): Double {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        val double = x.double
        if (double in min..max) {
            return double
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        if (double in min..max) {
            return double
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "double.between", min, max)
}

fun List<Iota>.replaceGetInt(idx: Int, argc: Int = 0): Int {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        val double = x.double
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE) {
            return rounded
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE) {
            return rounded
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "int")
}

fun List<Iota>.replaceGetLong(idx: Int, argc: Int = 0): Long {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        val double = x.double
        val rounded = double.roundToLong()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE) {
            return rounded
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        val rounded = double.roundToLong()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE) {
            return rounded
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "int") // shh we're lying
}

fun List<Iota>.replaceGetPositiveInt(idx: Int, argc: Int = 0): Int {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        val double = x.double
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded >= 0) {
            return rounded
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded >= 0) {
            return rounded
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "int.positive")
}

fun List<Iota>.replaceGetPositiveIntUnder(idx: Int, max: Int, argc: Int = 0): Int {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        val double = x.double
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in 0 until max) {
            return rounded
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in 0 until max) {
            return rounded
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "int.positive.less", max)
}

fun List<Iota>.replaceGetPositiveIntUnderInclusive(idx: Int, max: Int, argc: Int = 0): Int {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        val double = x.double
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in 0..max) {
            return rounded
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in 0..max) {
            return rounded
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "int.positive.less.equal", max)
}

fun List<Iota>.replaceGetIntBetween(idx: Int, min: Int, max: Int, argc: Int = 0): Int {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is DoubleIota) {
        val double = x.double
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in min..max) {
            return rounded
        }
    } else if (x is QuaternionIota && x.isReal) {
        val double = x.x0
        val rounded = double.roundToInt()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in min..max) {
            return rounded
        }
    }
    throw MishapInvalidIota.of(x, if (argc == 0) idx else argc - (idx + 1), "double.between", min, max)
}

fun List<Iota>.replaceGetNumOrVec(idx: Int, argc: Int = 0): Either<Double, Vec3d> {
    var datum = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (datum is QuaternionIota && datum.isReal) {
        datum = DoubleIota(datum.x0)
    }

    return when (datum) {
        is DoubleIota -> Either.left(datum.double)
        is Vec3Iota -> Either.right(datum.vec3)
        else -> throw MishapInvalidIota.of(
            datum,
            if (argc == 0) idx else argc - (idx + 1),
            "numvec"
        )
    }
}

fun List<Iota>.replaceGetLongOrList(idx: Int, argc: Int = 0): Either<Long, SpellList> {
    val datum = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }


    if (datum is DoubleIota) {
        val double = datum.double
        val rounded = double.roundToLong()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE) {
            return Either.left(rounded)
        }
    } else if (datum is QuaternionIota && datum.isReal) {
        val double = datum.x0
        val rounded = double.roundToLong()
        if (abs(double - rounded) <= DoubleIota.TOLERANCE) {
            return Either.left(rounded)
        }
    } else if (datum is ListIota) {
        return Either.right(datum.list)
    }
    throw MishapInvalidIota.of(
        datum,
        if (argc == 0) idx else argc - (idx + 1),
        "numlist"
    )
}