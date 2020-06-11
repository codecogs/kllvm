package dev.supergrecko.vexe.llvm.unit.ir

import dev.supergrecko.vexe.llvm.ir.Builder
import dev.supergrecko.vexe.llvm.ir.Module
import dev.supergrecko.vexe.llvm.ir.types.FunctionType
import dev.supergrecko.vexe.llvm.ir.types.IntType
import dev.supergrecko.vexe.llvm.ir.types.VoidType
import dev.supergrecko.vexe.llvm.ir.values.constants.ConstantInt
import dev.supergrecko.vexe.llvm.utils.cleanup
import dev.supergrecko.vexe.test.TestSuite
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.junit.jupiter.api.Test

internal class BuilderTest : TestSuite({
    describe("Should be able to position after basic block") {
        val builder = Builder()
        val module = Module("utils.ll")
        val function = module.addFunction(
            "utils",
            FunctionType(
                VoidType(),
                listOf(),
                false
            )
        )
        val basicBlock = function.createBlock("entry")
        assertNull(builder.getInsertBlock())

        builder.positionAtEnd(basicBlock)
        // A simple comparison won't do because even though the
        // underlying reference is the same, the Builder object
        // that holds the reference is different
        // TODO?: Implement equals/hashCode for Builder by comparing underlying
        //   refs?
        assertEquals(builder.getInsertBlock()?.ref, basicBlock.ref)

        builder.clearInsertPosition()

        assertNull(builder.getInsertBlock())

        cleanup(builder, module)
    }

    describe("Attempting to dispose twice fails") {
        val builder = Builder()
        builder.dispose()

        assertFailsWith<IllegalArgumentException> {
            builder.dispose()
        }
    }

    describe("Creation of return instruction") {
        val builder = Builder()
        val boolTy = IntType(1)

        val instruction = builder.getInstructionBuilder().createRet(
            ConstantInt(boolTy, value = 1, signExtend = false)
        )

        assertEquals("ret i1 true", instruction.dumpToString().trim())

        val instruction1 = builder.getInstructionBuilder().createRet(
            ConstantInt(boolTy, value = 0, signExtend = false)
        )

        assertEquals("ret i1 false", instruction1.dumpToString().trim())

        cleanup(builder)
    }

    describe("Creation of call instruction") {
        val boolType = IntType(1)
        val module = Module("utils.ll").apply {
            addFunction(
                "utils",
                FunctionType(
                    boolType,
                    listOf(boolType, boolType),
                    false
                )
            )
        }
        val externFunc = module.getFunction("utils")
        val builder = Builder()
        val falseValue = ConstantInt(boolType, 0, false)
        val trueValue = ConstantInt(boolType, 1, false)
        val caller = module.addFunction(
            "caller",
            FunctionType(
                boolType,
                listOf(boolType, boolType),
                false
            )
        )
        val basicBlock = caller.createBlock("entry")

        builder.positionAtEnd(basicBlock)

        assertNotNull(externFunc)

        builder.getInstructionBuilder().createCall(
            externFunc, listOf(
                falseValue,
                trueValue
            ), "util"
        )
    }
}
)