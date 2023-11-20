package net.masterbw3.fivedimcasting.api.casting.mishaps

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import at.petrak.hexcasting.api.pigment.FrozenPigment
import net.minecraft.text.Text
import net.minecraft.util.DyeColor

class MishapCantMutateCell : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context): FrozenPigment =
        dyeColor(DyeColor.GRAY)

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context): Text =
        Text.translatable("mishap.mutate_cell")

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {    }
}