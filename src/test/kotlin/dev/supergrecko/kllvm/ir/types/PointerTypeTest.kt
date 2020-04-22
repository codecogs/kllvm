package dev.supergrecko.kllvm.ir.types

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class PointerTypeTest {
    @Test
    fun `underlying type matches`() {
        val type = IntType(32)
        val ptr = type.toPointerType()

        assertEquals(type.ref, ptr.getElementType().ref)
    }

    @Test
    fun `subtype matches`() {
        val type = IntType(32)
        val ptr = type.toPointerType()

        assertEquals(type.getTypeKind(), ptr.getSubtypes().first().getTypeKind())
    }

    @Test
    fun `count matches`() {
        val type = IntType(32).toPointerType()

        assertEquals(1, type.getElementCount())
    }

    @Test
    fun `address space matches`() {
        val type = IntType(32)
        val ptr = type.toPointerType(100)

        assertEquals(100, ptr.getAddressSpace())
    }
}
