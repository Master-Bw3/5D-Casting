package net.masterbw3.fivedimcasting.mixinImpl
//
//import at.petrak.hexcasting.client.ClientTickCounter
//import at.petrak.hexcasting.client.gui.GuiSpellcasting
//import at.petrak.hexcasting.client.render.DEFAULT_READABILITY_OFFSET
//import at.petrak.hexcasting.client.render.drawPatternFromPoints
//import at.petrak.hexcasting.client.render.drawSpot
//import at.petrak.hexcasting.client.render.findDupIndices
//import com.mojang.blaze3d.systems.RenderSystem
//import net.minecraft.client.gui.DrawContext
//import net.minecraft.client.render.GameRenderer
//import net.minecraft.util.math.MathHelper
//import net.minecraft.util.math.Vec2f
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
//import kotlin.math.sin
//
//fun render(this_: GuiSpellcasting, graphics: DrawContext, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
//
//    //(this_ as IMixinGuiSpellCasting).ambianceSoundInstance?.mousePosX = pMouseX / this_.width.toDouble()
//    //(this_ as IMixinGuiSpellCasting).ambianceSoundInstance?.mousePosY = pMouseX / this_.width.toDouble()
//
//    val ps = graphics.matrices // TODO: Determine if this is still necessary.
//
//    val mat = ps.peek().positionMatrix
//    val prevShader = RenderSystem.getShader()
//    RenderSystem.setShader(GameRenderer::getPositionColorProgram)
//    RenderSystem.disableDepthTest()
//    RenderSystem.disableCull()
//
//    // Draw guide dots around the cursor
//    val mousePos = Vec2f(pMouseX.toFloat(), pMouseY.toFloat())
//    // snap it to the center
//    val mouseCoord = this_.pxToCoord(mousePos)
//    val radius = 3
//    for (dotCoord in mouseCoord.rangeAround(radius)) {
//        if (!(this_ as IMixinGuiSpellCasting).usedSpots.contains(dotCoord)) {
//            val dotPx = this_.coordToPx(dotCoord)
//            val delta = dotPx.add(mousePos.negate()).length()
//            // when right on top of the cursor, 1.0
//            // when at the full radius, 0! this is so we don't have dots suddenly appear/disappear.
//            // we subtract size from delta so there's a little "island" of 100% bright points by the mouse
//            val scaledDist = MathHelper.clamp(
//                1.0f - ((delta - this_.hexSize()) / (radius.toFloat() * this_.hexSize())),
//                0f,
//                1f
//            )
//            drawSpot(
//                mat,
//                dotPx,
//                scaledDist * 2f,
//                MathHelper.lerp(scaledDist, 0.4f, 0.5f),
//                MathHelper.lerp(scaledDist, 0.8f, 1.0f),
//                MathHelper.lerp(scaledDist, 0.7f, 0.9f),
//                scaledDist
//            )
//        }
//    }
//    RenderSystem.defaultBlendFunc()
//
//    for ((idx, elts) in (this_ as IMixinGuiSpellCasting).patterns.withIndex()) {
//        val (pat, origin, valid) = elts
//        drawPatternFromPoints(
//            mat,
//            pat.toLines(
//                this_.hexSize(),
//                this_.coordToPx(origin)
//            ),
//            findDupIndices(pat.positions()),
//            true,
//            valid.color or (0xC8 shl 24),
//            valid.fadeColor or (0xC8 shl 24),
//            if (valid.success) 0.2f else 0.9f,
//            DEFAULT_READABILITY_OFFSET,
//            1f,
//            idx.toDouble()
//        )
//    }
//
//    // Now draw the currently WIP pattern
//    if ((this_ as IMixinGuiSpellCasting).drawState !is GuiSpellcasting.PatternDrawState.BetweenPatterns) {
//        val points = mutableListOf<Vec2>()
//        var dupIndices: Set<Int>? = null
//
//        if ((this_ as IMixinGuiSpellCasting).drawState is GuiSpellcasting.PatternDrawState.JustStarted) {
//            val ds = (this_ as IMixinGuiSpellCasting).drawState as GuiSpellcasting.PatternDrawState.JustStarted
//            points.add(this_.coordToPx(ds.start))
//        } else if ((this_ as IMixinGuiSpellCasting).drawState is GuiSpellcasting.PatternDrawState.Drawing) {
//            val ds = (this_ as IMixinGuiSpellCasting).drawState as GuiSpellcasting.PatternDrawState.Drawing
//            dupIndices = findDupIndices(ds.wipPattern.positions())
//            for (pos in ds.wipPattern.positions()) {
//                val pix = this_.coordToPx(pos + ds.start)
//                points.add(pix)
//            }
//        }
//
//        points.add(mousePos)
//        // Use the size of the patterns as the seed so that way when this one is added the zappies don't jump
//        drawPatternFromPoints(mat,
//            points,
//            dupIndices,
//            false,
//            0xff_64c8ff_u.toInt(),
//            0xff_fecbe6_u.toInt(),
//            0.1f,
//            DEFAULT_READABILITY_OFFSET,
//            1f,
//            this_.patterns.size.toDouble())
//    }
//
//    RenderSystem.enableDepthTest()
//
//    val mc = Minecraft.getInstance()
//    val font = mc.font
//    ps.pushPose()
//    ps.translate(10.0, 10.0, 0.0)
//
////        if (this_.parenCount > 0) {
////            val boxHeight = (this_.parenDescs.size + 1f) * 10f
////            RenderSystem.setShader(GameRenderer::getPositionColorShader)
////            RenderSystem.defaultBlendFunc()
////            drawBox(ps, 0f, 0f, (this_.width * LHS_IOTAS_ALLOCATION + 5).toFloat(), boxHeight, 7.5f)
////            ps.translate(0.0, 0.0, 1.0)
////
////            val time = ClientTickCounter.getTotal() * 0.16f
////            val opacity = (Mth.map(cos(time), -1f, 1f, 200f, 255f)).toInt()
////            val color = 0x00_ffffff or (opacity shl 24)
////            RenderSystem.setShader { prevShader }
////            for (desc in this_.parenDescs) {
////                font.draw(ps, desc, 10f, 7f, color)
////                ps.translate(0.0, 10.0, 0.0)
////            }
////            ps.translate(0.0, 15.0, 0.0)
////        }
//
//    if (this_.stackDescs.isNotEmpty()) {
//        val boxHeight = (this_.stackDescs.size + 1f) * 10f
//        RenderSystem.setShader(GameRenderer::getPositionColorShader)
//        RenderSystem.enableBlend()
//        GuiSpellcasting.drawBox(
//            ps,
//            0f,
//            0f,
//            (this_.width * GuiSpellcasting.LHS_IOTAS_ALLOCATION + 5).toFloat(),
//            boxHeight
//        )
//        ps.translate(0.0, 0.0, 1.0)
//        RenderSystem.setShader { prevShader }
//        for (desc in this_.stackDescs) {
//            graphics.drawString(font, desc, 5, 7, -1) // TODO: Confirm this works
//            ps.translate(0.0, 10.0, 0.0)
//        }
//    }
//
//    ps.popPose()
//    if (this_.ravenmind != null) {
//        val kotlinBad = this_.ravenmind!!
//        ps.pushPose()
//        val boxHeight = 15f
//        val addlScale = 1.5f
//        ps.translate(this_.width * (1.0 - GuiSpellcasting.RHS_IOTAS_ALLOCATION * addlScale) - 10, 10.0, 0.0)
//        RenderSystem.setShader(GameRenderer::getPositionColorShader)
//        RenderSystem.enableBlend()
//        GuiSpellcasting.drawBox(
//            ps, 0f, 0f,
//            (this_.width * GuiSpellcasting.RHS_IOTAS_ALLOCATION * addlScale).toFloat(), boxHeight * addlScale,
//        )
//        ps.translate(5.0, 5.0, 1.0)
//        ps.scale(addlScale, addlScale, 1f)
//
//        val time = ClientTickCounter.getTotal() * 0.2f
//        val opacity = (Mth.map(sin(time), -1f, 1f, 150f, 255f)).toInt()
//        val color = 0x00_ffffff or (opacity shl 24)
//
//        RenderSystem.setShader { prevShader }
//        graphics.drawString(font, kotlinBad, 0, 0, color) // TODO: Confirm this works
//        ps.popPose()
//    }
//
//    RenderSystem.setShader { prevShader }
//}
//}