package dev.supergrecko.kllvm.core.typedefs

import dev.supergrecko.kllvm.contracts.Disposable
import dev.supergrecko.kllvm.contracts.Validatable
import org.bytedeco.llvm.LLVM.LLVMMemoryBufferRef
import org.bytedeco.llvm.global.LLVM

public class MemoryBuffer internal constructor() :
    AutoCloseable, Validatable, Disposable {
    internal lateinit var ref: LLVMMemoryBufferRef
    public override var valid: Boolean = true

    public constructor(buffer: LLVMMemoryBufferRef) : this() {
        ref = buffer
    }

    override fun dispose() {
        require(valid) { "This buffer has already been disposed." }

        valid = false

        LLVM.LLVMDisposeMemoryBuffer(ref)
    }

    override fun close() = dispose()
}
