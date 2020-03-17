package dev.supergrecko.kllvm.core.types

import dev.supergrecko.kllvm.core.LLVMType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LLVMPointerTypeTest {
    @Test
    fun `underlying type matches`() {
        val type = LLVMType.createInteger(32)
        val ptr = type.toPointer()

        assertEquals(type.llvmType, ptr.getElementType().llvmType)
    }

    @Test
    fun `address space matches`() {
        val type = LLVMType.createInteger(32)
        val ptr = type.toPointer(100)

        assertEquals(100, ptr.getAddressSpace())
    }
}