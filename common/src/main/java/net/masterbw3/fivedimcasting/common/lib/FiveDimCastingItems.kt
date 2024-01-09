package net.masterbw3.fivedimcasting.common.lib


import at.petrak.hexcasting.common.items.ItemStaff
import at.petrak.hexcasting.common.lib.HexItems
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import java.util.function.BiConsumer
import java.util.function.Supplier

object FiveDimCastingItems {
    @JvmStatic
    fun registerItems(r: BiConsumer<Item, Identifier>) {
        for ((key, value) in ITEMS) {
            r.accept(value, key)
        }
    }

    fun registerItemCreativeTab(r: ItemGroup.Entries, tab: ItemGroup) {
        for (item in ITEM_TABS.getOrDefault(tab, listOf())) {
            item.register(r)
        }
    }

    private val ITEMS: MutableMap<Identifier, Item> = LinkedHashMap()

    private val ITEM_TABS: MutableMap<ItemGroup, MutableList<TabEntry>> = LinkedHashMap()

    val GRAND_STAFF = make("grand_staff", ItemStaff(HexItems.unstackable()))


    private fun <T : Item> make(id: Identifier, item: T, tab: ItemGroup?): T {
        val old = ITEMS.put(id, item)
        require(old == null) { "Typo? Duplicate id $id" }
        if (tab != null) {
            ITEM_TABS.computeIfAbsent(tab) { ArrayList() }.add(TabEntry.ItemEntry(item))
        }
        return item
    }

    private fun <T : Item> make(id: String, item: T, tab: ItemGroup?): T {
        return make(FiveDimCastingApi.modLoc(id), item, tab)
    }

    private fun <T : Item> make(id: String, item: T): T {
        return make(FiveDimCastingApi.modLoc(id), item, FiveDimCastingCreativeTabs.FIVE_DIM_CASTING)
    }

    abstract class TabEntry {
        abstract fun register(r: ItemGroup.Entries)
        internal class ItemEntry(private val item: Item) : TabEntry() {
            override fun register(r: ItemGroup.Entries) {
                r.add(item)
            }
        }

        internal class StackEntry(private val stack: Supplier<ItemStack>) : TabEntry() {
            override fun register(r: ItemGroup.Entries) {
                r.add(stack.get())
            }
        }
    }


}