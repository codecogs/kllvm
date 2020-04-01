package dev.supergrecko.kllvm.core.typedefs

import org.bytedeco.llvm.LLVM.LLVMBasicBlockRef

public class BasicBlock internal constructor() {
    internal lateinit var ref: LLVMBasicBlockRef

    public constructor(block: LLVMBasicBlockRef) : this() {
        ref = block
    }
}
