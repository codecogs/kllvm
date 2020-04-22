package dev.supergrecko.kllvm.ir

import dev.supergrecko.kllvm.ir.types.FunctionType
import dev.supergrecko.kllvm.ir.types.VoidType
import dev.supergrecko.kllvm.support.VerifierFailureAction
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ModuleTest {
    @Test
    fun `setting a module identifier`() {
        val mod = Module("test.ll")
        mod.setModuleIdentifier("test")

        assertEquals("test", mod.getModuleIdentifier())

        mod.dispose()
    }

    @Test
    fun `cloning a module with a module identifier`() {
        val mod = Module("test.ll")
        mod.setModuleIdentifier("test")

        val clone = mod.clone()

        assertEquals(mod.getModuleIdentifier(), clone.getModuleIdentifier())

        mod.dispose()
        clone.dispose()
    }

    @Test
    fun `modifying the module source name`() {
        val mod = Module("test.ll")

        assertEquals("test.ll", mod.getSourceFileName())

        mod.setSourceFileName("test2.ll")

        assertEquals("test2.ll", mod.getSourceFileName())

        mod.dispose()
    }

    @Test
    fun `pulling an unknown function from a module is null`() {
        val module = Module("test.ll")

        assertNull(module.getFunction("test"))

        module.dispose()
    }

    @Test
    fun `pulling a function returns function object when function added`() {
        val module = Module("test.ll")

        module.addFunction(
            "test",
            FunctionType(
                VoidType(),
                listOf(),
                false
            )
        )

        assertNotNull(module.getFunction("test"))

        module.dispose()
    }

    @Test
    fun `writing module to byte code file`() {
        val file = File("./out.bc")

        val module = Module("test.ll")

        module.toFile(file)

        assertTrue { file.exists() }

        file.delete()
        module.dispose()
    }

    @Test
    fun `writing module to byte code by file path`() {
        val file = File("./out.bc")

        val module = Module("test.ll")

        module.toFile(file.absolutePath)

        assertTrue { file.exists() }

        file.delete()
        module.dispose()
    }

    @Test
    fun `writing module to buffer and reading the buffer`() {
        val context = Context()
        val module = Module("test.ll", context)

        val buf = module.toMemoryBuffer()
        val mod = buf.parse(context)

        assertEquals("test.ll", mod.getSourceFileName())

        module.dispose()
        context.dispose()
    }

    @Test
    fun `getting a module from buffer`() {
        val context = Context()
        val module = Module("test.ll", context)

        val buf = module.toMemoryBuffer()

        val mod = buf.getModule(context)

        assertEquals("test.ll", mod.getSourceFileName())
    }

    @Test
    fun `dumping module`() {
        val context = Context()
        val module = Module("test.ll", context)

        module.dump()

        module.dispose()
    }

    @Test
    fun `verifying valid module`() {
        val context = Context()
        val module = Module("test.ll", context)

        val res = module.verify(VerifierFailureAction.ReturnStatus)

        assertEquals(true, res)

        module.dispose()
    }
}
