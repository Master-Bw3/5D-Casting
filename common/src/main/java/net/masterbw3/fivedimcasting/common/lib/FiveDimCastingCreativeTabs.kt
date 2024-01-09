package net.masterbw3.fivedimcasting.common.lib

import at.petrak.hexcasting.common.lib.HexItems
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.function.BiConsumer

object FiveDimCastingCreativeTabs {
    fun registerCreativeTabs(r: BiConsumer<ItemGroup?, Identifier>) {
        for ((key, value) in TABS) {
            r.accept(value, key)
        }
    }

    private val TABS: MutableMap<Identifier, ItemGroup?> = LinkedHashMap()

    val FIVE_DIM_CASTING = register("fivedimcasting", ItemGroup.create(ItemGroup.Row.TOP, 7)
        .icon { ItemStack(HexItems.SPELLBOOK) })

    private fun register(name: String, tabBuilder: ItemGroup.Builder): ItemGroup {
        val tab = tabBuilder.displayName(Text.translatable("itemGroup.$name")).build()
        val old = TABS.put(FiveDimCastingApi.modLoc(name), tab)
        require(old == null) { "Typo? Duplicate id $name" }
        return tab
    }
}