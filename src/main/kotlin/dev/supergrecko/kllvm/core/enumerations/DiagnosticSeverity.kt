package dev.supergrecko.kllvm.core.enumerations

import dev.supergrecko.kllvm.contracts.OrderedEnum
import org.bytedeco.llvm.global.LLVM

/**
 * Support type matching LLVMDiagnosticSeverity
 *
 * [Documentation](https://llvm.org/doxygen/group__LLVMCCoreTypes.html)
 */
public enum class DiagnosticSeverity(public override val value: Int) : OrderedEnum<Int> {
    Error(LLVM.LLVMDSError),
    Warning(LLVM.LLVMDSWarning),
    Remark(LLVM.LLVMDSRemark),
    Note(LLVM.LLVMDSNote)
}
