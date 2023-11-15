package net.masterbw3.fivedimcasting.casting.actions.arithmetics

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic.*
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.math.HexPattern
import net.masterbw3.fivedimcasting.api.utils.Complex
import net.masterbw3.fivedimcasting.api.utils.Quaternion
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.complex.BinaryOperatorComplex
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.complex.BinaryOperatorRealAndComplex
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.complex.UnaryOperatorComplex
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.quaternion.BinaryOperatorQuaternion
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.quaternion.OperatorGetQuaternionComponent
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.quaternion.UnaryOperatorQuaternion
import kotlin.math.ceil
import kotlin.math.floor

object QuaternionArithmetic : Arithmetic {
    private val OPS = listOf(
            ADD,
            SUB,
            MUL,
            DIV,
            ABS,
            FLOOR,
            CEIL,
            POW,
            LOG,
            SIN,
            COS,
            TAN,
            INDEX,
    )

    override fun arithName(): String = "quaternion_ops"


    override fun opTypes(): Iterable<HexPattern> = OPS


    override fun getOperator(pattern: HexPattern?): Operator = when (pattern) {

        ADD -> BinaryOperatorQuaternion { a, b -> a.plus(b) }
        SUB -> BinaryOperatorQuaternion { a, b -> a.minus(b) }
        MUL -> BinaryOperatorQuaternion { a, b -> a.times(b) }
        DIV -> BinaryOperatorQuaternion { a, b -> a.divides(b) }
        ABS -> UnaryOperatorQuaternion { x -> Quaternion.fromDouble(x.norm()) }
        FLOOR -> UnaryOperatorQuaternion { x -> x.applyToEachComponent { y -> floor(y) } }
        CEIL -> UnaryOperatorQuaternion { x -> x.applyToEachComponent { y -> ceil(y) } }
        POW -> BinaryOperatorRealAndComplex {a, b -> Complex.pow(a, b)}
        LOG -> BinaryOperatorComplex {a, b -> a.logBase(b)}
        SIN -> UnaryOperatorComplex {x -> x.sin()}
        COS -> UnaryOperatorComplex {x -> x.cos()}
        TAN -> UnaryOperatorComplex {x -> x.tan()}
        INDEX -> OperatorGetQuaternionComponent

        else -> throw InvalidOperatorException("$pattern is not a valid operator in Arithmetic $this.")

    }
}