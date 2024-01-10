package net.masterbw3.fivedimcasting.api.utils

import at.petrak.hexcasting.api.casting.math.HexCoord
import at.petrak.hexcasting.api.casting.math.HexPattern
import net.minecraft.util.math.Vec2f
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

fun squareCoordToPx(coord: HexCoord, size: Float, offset: Vec2f): Vec2f {
    val x = (3.0/2.0 * coord.q.toFloat());
    val y = (3.0/2.0 * coord.r.toFloat());
    return Vec2f(x.toFloat(), y.toFloat()).multiply(size).add(offset)
}

fun pxToSquareCoord(px: Vec2f, size: Float, offset: Vec2f): HexCoord {
    val offsetted = px.add(offset.negate())
    var qf = (2.0 / 3.0 * offsetted.x) / size;
    var rf = (2.0 / 3.0 * offsetted.y) / size;

    val q = qf.roundToInt()
    val r = rf.roundToInt()
    qf -= q
    rf -= r
    return if (q.absoluteValue >= r.absoluteValue)
        HexCoord(q + (qf + 0.5f * rf).roundToInt(), r)
    else
        HexCoord(q, r + (rf + 0.5f * qf).roundToInt())
}

fun hexPatternToFlatCoordLines(pattern: HexPattern, hexSize: Float, origin: Vec2f): List<Vec2f> =
    pattern.positions().map { squareCoordToPx(it, hexSize, origin) }

