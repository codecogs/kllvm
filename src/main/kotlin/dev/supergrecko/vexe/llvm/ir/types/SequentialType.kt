package dev.supergrecko.vexe.llvm.ir.types

import dev.supergrecko.vexe.llvm.internal.contracts.ContainsReference
import dev.supergrecko.vexe.llvm.ir.Type
import org.bytedeco.javacpp.PointerPointer
import org.bytedeco.llvm.LLVM.LLVMTypeRef
import org.bytedeco.llvm.global.LLVM

public interface SequentialType : CompositeType,
    ContainsReference<LLVMTypeRef> {
    //region Core::Types::SequentialTypes
    /**
     * Returns types's subtypes
     *
     * @see LLVM.LLVMGetSubtypes
     */
    public open fun getSubtypes(): List<Type> {
        val dest = PointerPointer<LLVMTypeRef>(getElementCount().toLong())
        LLVM.LLVMGetSubtypes(ref, dest)

        val res = mutableListOf<LLVMTypeRef>()

        for (i in 0 until dest.capacity()) {
            res += LLVMTypeRef(dest.get(i))
        }

        return res.map { Type(it) }
    }

    /**
     * Obtain the types of elements within a sequential types
     *
     * @see LLVM.LLVMGetElementType
     */
    public open fun getElementType(): Type {
        val type = LLVM.LLVMGetElementType(ref)

        return Type(type)
    }
    //endregion Core::Types::SequentialTypes
}
