package dev.supergrecko.kllvm.core.typedefs

import dev.supergrecko.kllvm.contracts.Disposable
import dev.supergrecko.kllvm.contracts.Validatable
import org.bytedeco.llvm.LLVM.LLVMBinaryRef
import org.bytedeco.llvm.global.LLVM

public class Binary internal constructor() : AutoCloseable,
    Validatable, Disposable {
    internal lateinit var ref: LLVMBinaryRef
    public override var valid: Boolean = true

    internal constructor(binary: LLVMBinaryRef) : this() {
        ref = binary
    }

    override fun dispose() {
        require(valid) { "This binary has already been disposed." }

        valid = false

        LLVM.LLVMDisposeBinary(ref)
    }

    override fun close() = dispose()
}
