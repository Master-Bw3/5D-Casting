@file:Suppress("NOTHING_TO_INLINE")
package net.masterbw3.fivedimcasting.api.utils


import net.minecraft.nbt.*

// https://github.com/TeamWizardry/LibrarianLib/blob/9cfb2cf3e35685568942ad41395265a2edc27d30/modules/core/src/main/kotlin/com/teamwizardry/librarianlib/core/util/kotlin/NbtBuilder.kt

@DslMarker
internal annotation class NBTDslMarker

@NBTDslMarker
object NBTBuilder {
    inline operator fun invoke(block: NbtCompoundBuilder.() -> Unit) = compound(block)
    inline operator fun invoke(tag: NbtCompound, block: NbtCompoundBuilder.() -> Unit) = use(tag, block)

    inline fun use(tag: NbtCompound, block: NbtCompoundBuilder.() -> Unit): NbtCompound =
            NbtCompoundBuilder(tag).apply(block).tag

    inline fun compound(block: NbtCompoundBuilder.() -> Unit): NbtCompound =
            NbtCompoundBuilder(NbtCompound()).apply(block).tag

    inline fun list(block: NbtListBuilder.() -> Unit): NbtList =
            NbtListBuilder(NbtList()).apply(block).tag

    inline fun list(vararg elements: NbtElement, block: NbtListBuilder.() -> Unit): NbtList =
            NbtListBuilder(NbtList()).also {
                it.addAll(elements.toList())
                it.block()
            }.tag

    inline fun list(vararg elements: NbtElement): NbtList = NbtList().also { it.addAll(elements) }
    inline fun list(elements: Collection<NbtElement>): NbtList = NbtList().also { it.addAll(elements) }
    inline fun <T> list(elements: Collection<T>, mapper: (T) -> NbtElement): NbtList = NbtList().also { it.addAll(elements.map(mapper)) }

    inline fun double(value: Number): NbtDouble = NbtDouble.of(value.toDouble())
    inline fun float(value: Number): NbtFloat = NbtFloat.of(value.toFloat())
    inline fun long(value: Number): NbtLong = NbtLong.of(value.toLong())
    inline fun int(value: Number): NbtInt = NbtInt.of(value.toInt())
    inline fun short(value: Number): NbtShort = NbtShort.of(value.toShort())
    inline fun byte(value: Number): NbtByte = NbtByte.of(value.toByte())

    inline fun string(value: String): NbtString = NbtString.of(value)

    inline fun byteArray(value: Collection<Number>): NbtByteArray = NbtByteArray(value.map { it.toByte() })
    inline fun byteArray(vararg value: Int): NbtByteArray = NbtByteArray(ByteArray(value.size) { value[it].toByte() })
    inline fun byteArray(vararg value: Byte): NbtByteArray = NbtByteArray(value)
    inline fun byteArray(): NbtByteArray = NbtByteArray(byteArrayOf()) // avoiding overload ambiguity
    inline fun longArray(value: Collection<Number>): NbtLongArray = NbtLongArray(value.map { it.toLong() })
    inline fun longArray(vararg value: Int): NbtLongArray = NbtLongArray(LongArray(value.size) { value[it].toLong() })
    inline fun longArray(vararg value: Long): NbtLongArray = NbtLongArray(value)
    inline fun longArray(): NbtLongArray = NbtLongArray(longArrayOf()) // avoiding overload ambiguity
    inline fun intArray(value: Collection<Number>): NbtIntArray = NbtIntArray(value.map { it.toInt() })
    inline fun intArray(vararg value: Int): NbtIntArray = NbtIntArray(value)
}

@JvmInline
@NBTDslMarker
value class NbtCompoundBuilder(val tag: NbtCompound) {
    // configuring this tag

    inline operator fun String.remAssign(nbt: NbtElement) {
        tag.put(this, nbt)
    }

    inline operator fun String.remAssign(str: String) {
        tag.put(this, string(str))
    }

    inline operator fun String.remAssign(num: Int) {
        tag.put(this, int(num))
    }

    inline operator fun String.remAssign(num: Long) {
        tag.put(this, long(num))
    }

    inline operator fun String.remAssign(num: Double) {
        tag.put(this, double(num))
    }

    inline operator fun String.remAssign(num: Float) {
        tag.put(this, float(num))
    }

    inline operator fun String.remAssign(bool: Boolean) {
        tag.put(this, byte(if (bool) 1 else 0))
    }

    // creating new tags

    inline fun compound(block: NbtCompoundBuilder.() -> Unit): NbtCompound =
            NbtCompoundBuilder(NbtCompound()).apply(block).tag

    inline fun list(block: NbtListBuilder.() -> Unit): NbtList =
            NbtListBuilder(NbtList()).apply(block).tag

    inline fun list(vararg elements: NbtElement, block: NbtListBuilder.() -> Unit): NbtList =
            NbtListBuilder(NbtList()).also {
                it.addAll(elements.toList())
                it.block()
            }.tag

    inline fun list(vararg elements: NbtElement): NbtList = NbtList().also { it.addAll(elements) }
    inline fun list(elements: Collection<NbtElement>): NbtList = NbtList().also { it.addAll(elements) }
    inline fun <T> list(elements: Collection<T>, mapper: (T) -> NbtElement): NbtList = NbtList().also { it.addAll(elements.map(mapper)) }

    inline fun double(value: Number): NbtDouble = NbtDouble.of(value.toDouble())
    inline fun float(value: Number): NbtFloat = NbtFloat.of(value.toFloat())
    inline fun long(value: Number): NbtLong = NbtLong.of(value.toLong())
    inline fun int(value: Number): NbtInt = NbtInt.of(value.toInt())
    inline fun short(value: Number): NbtShort = NbtShort.of(value.toShort())
    inline fun byte(value: Number): NbtByte = NbtByte.of(value.toByte())

    inline fun string(value: String): NbtString = NbtString.of(value)

    inline fun byteArray(value: Collection<Number>): NbtByteArray = NbtByteArray(value.map { it.toByte() })
    inline fun byteArray(vararg value: Int): NbtByteArray = NbtByteArray(ByteArray(value.size) { value[it].toByte() })
    inline fun byteArray(vararg value: Byte): NbtByteArray = NbtByteArray(value)
    inline fun byteArray(): NbtByteArray = NbtByteArray(byteArrayOf()) // avoiding overload ambiguity
    inline fun longArray(value: Collection<Number>): NbtLongArray = NbtLongArray(value.map { it.toLong() })
    inline fun longArray(vararg value: Int): NbtLongArray = NbtLongArray(LongArray(value.size) { value[it].toLong() })
    inline fun longArray(vararg value: Long): NbtLongArray = NbtLongArray(value)
    inline fun longArray(): NbtLongArray = NbtLongArray(longArrayOf()) // avoiding overload ambiguity
    inline fun intArray(value: Collection<Number>): NbtIntArray = NbtIntArray(value.map { it.toInt() })
    inline fun intArray(vararg value: Int): NbtIntArray = NbtIntArray(value)
}

@JvmInline
@NBTDslMarker
value class NbtListBuilder(val tag: NbtList) {
    // configuring this tag

    /**
     * Add the given tag to this list
     */
    inline operator fun NbtElement.unaryPlus() {
        tag.add(this)
    }

    /**
     * Add the given  tags to this list
     */
    inline operator fun Collection<NbtElement>.unaryPlus() {
        tag.addAll(this)
    }

    /**
     * Add the given tag to this list. This is explicitly defined for [NbtList] because otherwise there is overload
     * ambiguity between the [NbtElement] and [Collection]<NbtElement> methods.
     */
    inline operator fun NbtList.unaryPlus() {
        tag.add(this)
    }

    inline fun addAll(nbt: Collection<NbtElement>) {
        this.tag.addAll(nbt)
    }

    inline fun add(nbt: NbtElement) {
        this.tag.add(nbt)
    }

    // creating new tags

    inline fun compound(block: NbtCompoundBuilder.() -> Unit): NbtCompound =
            NbtCompoundBuilder(NbtCompound()).apply(block).tag

    inline fun list(block: NbtListBuilder.() -> Unit): NbtList =
            NbtListBuilder(NbtList()).apply(block).tag

    inline fun list(vararg elements: NbtElement, block: NbtListBuilder.() -> Unit): NbtList =
            NbtListBuilder(NbtList()).also {
                it.addAll(elements.toList())
                it.block()
            }.tag

    inline fun list(vararg elements: NbtElement): NbtList = NbtList().also { it.addAll(elements) }
    inline fun list(elements: Collection<NbtElement>): NbtList = NbtList().also { it.addAll(elements) }
    inline fun <T> list(elements: Collection<T>, mapper: (T) -> NbtElement): NbtList = NbtList().also { it.addAll(elements.map(mapper)) }

    inline fun double(value: Number): NbtDouble = NbtDouble.of(value.toDouble())
    inline fun float(value: Number): NbtFloat = NbtFloat.of(value.toFloat())
    inline fun long(value: Number): NbtLong = NbtLong.of(value.toLong())
    inline fun int(value: Number): NbtInt = NbtInt.of(value.toInt())
    inline fun short(value: Number): NbtShort = NbtShort.of(value.toShort())
    inline fun byte(value: Number): NbtByte = NbtByte.of(value.toByte())

    inline fun string(value: String): NbtString = NbtString.of(value)

    inline fun byteArray(value: Collection<Number>): NbtByteArray = NbtByteArray(value.map { it.toByte() })
    inline fun byteArray(vararg value: Int): NbtByteArray = NbtByteArray(ByteArray(value.size) { value[it].toByte() })
    inline fun byteArray(vararg value: Byte): NbtByteArray = NbtByteArray(value)
    inline fun byteArray(): NbtByteArray = NbtByteArray(byteArrayOf()) // avoiding overload ambiguity
    inline fun longArray(value: Collection<Number>): NbtLongArray = NbtLongArray(value.map { it.toLong() })
    inline fun longArray(vararg value: Int): NbtLongArray = NbtLongArray(LongArray(value.size) { value[it].toLong() })
    inline fun longArray(vararg value: Long): NbtLongArray = NbtLongArray(value)
    inline fun longArray(): NbtLongArray = NbtLongArray(longArrayOf()) // avoiding overload ambiguity
    inline fun intArray(value: Collection<Number>): NbtIntArray = NbtIntArray(value.map { it.toInt() })
    inline fun intArray(vararg value: Int): NbtIntArray = NbtIntArray(value)

    inline fun doubles(vararg value: Int): List<NbtDouble> = value.map { NbtDouble.of(it.toDouble()) }
    inline fun doubles(vararg value: Double): List<NbtDouble> = value.map { NbtDouble.of(it) }
    inline fun floats(vararg value: Int): List<NbtFloat> = value.map { NbtFloat.of(it.toFloat()) }
    inline fun floats(vararg value: Float): List<NbtFloat> = value.map { NbtFloat.of(it) }
    inline fun longs(vararg value: Int): List<NbtLong> = value.map { NbtLong.of(it.toLong()) }
    inline fun longs(vararg value: Long): List<NbtLong> = value.map { NbtLong.of(it) }
    inline fun ints(vararg value: Int): List<NbtInt> = value.map { NbtInt.of(it) }
    inline fun shorts(vararg value: Int): List<NbtShort> = value.map { NbtShort.of(it.toShort()) }
    inline fun shorts(vararg value: Short): List<NbtShort> = value.map { NbtShort.of(it) }
    inline fun bytes(vararg value: Int): List<NbtByte> = value.map { NbtByte.of(it.toByte()) }
    inline fun bytes(vararg value: Byte): List<NbtByte> = value.map { NbtByte.of(it) }

    fun strings(vararg value: String): List<NbtString> = value.map { NbtString.of(it) }
}
