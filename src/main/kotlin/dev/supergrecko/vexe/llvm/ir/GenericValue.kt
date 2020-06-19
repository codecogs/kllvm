package dev.supergrecko.vexe.llvm.ir

import dev.supergrecko.vexe.llvm.internal.contracts.ContainsReference
import dev.supergrecko.vexe.llvm.internal.contracts.Disposable
import dev.supergrecko.vexe.llvm.internal.contracts.Validatable
import dev.supergrecko.vexe.llvm.internal.util.toLLVMBool
import dev.supergrecko.vexe.llvm.internal.util.wrap
import dev.supergrecko.vexe.llvm.ir.types.FloatType
import dev.supergrecko.vexe.llvm.ir.types.IntType
import org.bytedeco.javacpp.Pointer
import org.bytedeco.llvm.LLVM.LLVMGenericValueRef
import org.bytedeco.llvm.global.LLVM

public class GenericValue internal constructor() : Validatable, Disposable,
    ContainsReference<LLVMGenericValueRef> {
    public override lateinit var ref: LLVMGenericValueRef
    public override var valid: Boolean = true

    public constructor(engine: LLVMGenericValueRef) {
        ref = engine
    }

    //region ExecutionEngine
    /**
     * Create a generic value of an integer
     *
     * @see LLVM.LLVMCreateGenericValueOfInt
     */
    public constructor(
        type: IntType,
        number: Long,
        isSigned: Boolean
    ) : this() {
        ref = LLVM.LLVMCreateGenericValueOfInt(
            type.ref,
            number,
            isSigned.toLLVMBool()
        )
    }

    /**
     * Create a generic value of a float
     *
     * @see LLVM.LLVMCreateGenericValueOfFloat
     */
    public constructor(type: FloatType, value: Double) : this() {
        ref = LLVM.LLVMCreateGenericValueOfFloat(type.ref, value)
    }

    /**
     * Create a generic value of a pointer
     *
     * @see LLVM.LLVMCreateGenericValueOfPointer
     */
    public constructor(pointer: Pointer) : this() {
        ref = LLVM.LLVMCreateGenericValueOfPointer(pointer)
    }

    /**
     * Get the bit width of an integer Generic value
     *
     * @see LLVM.LLVMGenericValueIntWidth
     */
    public fun getIntWidth(): Int? {
        return LLVM.LLVMGenericValueIntWidth(ref)
    }

    /**
     * Get the generic value as an integer
     *
     * @see LLVM.LLVMGenericValueToInt
     */
    public fun toInt(isSigned: Boolean): Long? {
        return LLVM.LLVMGenericValueToInt(ref, isSigned.toLLVMBool())
    }

    /**
     * Get the generic value as a float
     *
     * @see LLVM.LLVMGenericValueToFloat
     */
    public fun toFloat(type: FloatType): Double? {
        return LLVM.LLVMGenericValueToFloat(type.ref, ref)
    }

    /**
     * Get the generic value as a pointer
     *
     * @see LLVM.LLVMGenericValueToPointer
     */
    public fun toPointer(): Pointer? {
        return LLVM.LLVMGenericValueToPointer(ref)
    }
    //endregion ExecutionEngine

    override fun dispose() {
        require(valid) { "This execution engine has already been disposed" }

        LLVM.LLVMDisposeGenericValue(ref)
    }
}