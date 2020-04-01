package dev.supergrecko.kllvm.core.values

import dev.supergrecko.kllvm.core.typedefs.Value
import org.bytedeco.llvm.LLVM.LLVMValueRef

public class PointerValue internal constructor() : Value() {
    public constructor(llvmValue: LLVMValueRef) : this() {
        ref = llvmValue
    }

    public constructor(value: Value) : this(value.ref)
}