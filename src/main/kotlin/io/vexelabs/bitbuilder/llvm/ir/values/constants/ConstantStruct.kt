package io.vexelabs.bitbuilder.llvm.ir.values.constants

import io.vexelabs.bitbuilder.internal.toLLVMBool
import io.vexelabs.bitbuilder.internal.toPointerPointer
import io.vexelabs.bitbuilder.llvm.ir.Context
import io.vexelabs.bitbuilder.llvm.ir.Value
import io.vexelabs.bitbuilder.llvm.ir.types.StructType
import io.vexelabs.bitbuilder.llvm.ir.values.ConstantValue
import io.vexelabs.bitbuilder.llvm.ir.values.traits.AggregateValue
import io.vexelabs.bitbuilder.llvm.ir.values.traits.CompositeValue
import org.bytedeco.llvm.LLVM.LLVMValueRef
import org.bytedeco.llvm.global.LLVM

public class ConstantStruct internal constructor() :
    ConstantValue(),
    AggregateValue,
    CompositeValue {
    public constructor(llvmRef: LLVMValueRef) : this() {
        ref = llvmRef
    }

    /**
     * Create a constant struct of a list of values
     *
     * @see LLVM.LLVMConstStructInContext
     */
    public constructor(
        values: List<Value>,
        packed: Boolean,
        context: Context = Context.getGlobalContext()
    ) : this() {
        val ptr = values.map { it.ref }.toPointerPointer()

        ref = LLVM.LLVMConstStructInContext(
            context.ref,
            ptr,
            values.size,
            packed.toLLVMBool()
        )

        ptr.deallocate()
    }

    /**
     * Create a struct of a [type] and initialize it with the provided [values]
     *
     * @see LLVM.LLVMConstNamedStruct
     */
    public constructor(type: StructType, values: List<Value>) : this() {
        val ptr = values.map { it.ref }.toPointerPointer()

        ref = LLVM.LLVMConstNamedStruct(type.ref, ptr, values.size)

        ptr.deallocate()
    }
}
