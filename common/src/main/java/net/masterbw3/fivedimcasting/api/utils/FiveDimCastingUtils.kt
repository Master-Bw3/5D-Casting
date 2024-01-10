package net.masterbw3.fivedimcasting.api.utils

import at.petrak.hexcasting.api.casting.math.HexCoord
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.utils.SQRT_3
import at.petrak.hexcasting.client.render.drawLineSeq
import net.minecraft.util.math.Vec2f
import org.joml.Matrix4f
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt


fun drawSpotOutline(mat: Matrix4f, point: Vec2f, radius: Float, color: Int) {
    val (x, y) = Pair(point.x, point.y)
    val points = listOf(
        Vec2f(x - 1f, y),
        Vec2f(x - SQRT_3/4, y - SQRT_3/2),
        Vec2f(x + SQRT_3/4, y - SQRT_3/2),
        Vec2f(x + 1f, y),
        Vec2f(x + SQRT_3/4, y + SQRT_3/2),
        Vec2f(x - SQRT_3/4, y + SQRT_3/2),
        )
    drawLineSeq(mat, points.map { it.add(point).multiply(radius) }, 0.5f, 0f, color, color)

}


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

fun hexPatternToSquareCoordLines(pattern: HexPattern, hexSize: Float, origin: Vec2f): List<Vec2f> =
    pattern.positions().map { squareCoordToPx(it, hexSize, origin) }

