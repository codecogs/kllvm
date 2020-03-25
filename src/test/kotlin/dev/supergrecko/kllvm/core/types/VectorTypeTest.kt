package dev.supergrecko.kllvm.core.types

import dev.supergrecko.kllvm.core.enumerations.TypeKind
import dev.supergrecko.kllvm.factories.TypeFactory
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class VectorTypeTest {
    @Test
    fun `underlying type matches`() {
        val type = TypeFactory.integer(32)
        val vec = TypeFactory.vector(type, 10)

        assertEquals(10, vec.getElementCount())
        assertEquals(type.llvmType, vec.getElementType().llvmType)
    }

    @Test
    fun `subtypes match`() {
        val type = TypeFactory.integer(32)
        val vec = TypeFactory.vector(type, 10)

        assertEquals(10, vec.getSubtypes().size)
        assertEquals(type.llvmType, vec.getSubtypes().first().llvmType)
    }

    @Test
    fun `negative size is illegal`() {
        val type = TypeFactory.float(TypeKind.Float)

        assertFailsWith<IllegalArgumentException> {
            type.toVectorType(-100)
        }

        assertFailsWith<IllegalArgumentException> {
            TypeFactory.vector(-100) {
                this.type = type
            }
        }
    }
}