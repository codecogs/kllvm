package dev.supergrecko.kllvm.core.values

import dev.supergrecko.kllvm.core.typedefs.Value
import org.bytedeco.llvm.LLVM.LLVMValueRef

// TODO: Learn how to use/implement this
public class BasicValueUse(llvmValue: LLVMValueRef) : Value(llvmValue) {
    public constructor(value: Value) : this(value.ref)
}