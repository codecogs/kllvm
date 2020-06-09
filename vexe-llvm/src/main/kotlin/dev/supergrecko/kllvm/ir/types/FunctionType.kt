package dev.supergrecko.kllvm.ir.types

import dev.supergrecko.kllvm.internal.util.fromLLVMBool
import dev.supergrecko.kllvm.internal.util.map
import dev.supergrecko.kllvm.internal.util.toLLVMBool
import dev.supergrecko.kllvm.ir.Type
import dev.supergrecko.kllvm.ir.TypeKind
import org.bytedeco.javacpp.PointerPointer
import org.bytedeco.llvm.LLVM.LLVMTypeRef
import org.bytedeco.llvm.global.LLVM

public class FunctionType internal constructor() : Type() {
    /**
     * Construct a new Type from an LLVM pointer reference
     */
    public constructor(llvmType: LLVMTypeRef) : this() {
        ref = llvmType
        requireKind(TypeKind.Function)
    }

    /**
     * Create a function types
     *
     * This will construct a function types which returns the types provided in
     * [returns] which expects to receive parameters of the types provided in
     * [types]. You can mark a function types as variadic by setting the
     * [variadic] arg to true.
     */
    public constructor(
        returns: Type,
        types: List<Type>,
        variadic: Boolean
    ) : this() {
        val arr = ArrayList(types.map { it.ref }).toTypedArray()

        ref = LLVM.LLVMFunctionType(
            returns.ref,
            PointerPointer(*arr),
            arr.size,
            variadic.toLLVMBool()
        )
    }

    //region Core::Types::FunctionTypes
    public fun isVariadic(): Boolean {
        return LLVM.LLVMIsFunctionVarArg(ref).fromLLVMBool()
    }

    public fun getParameterCount(): Int {
        return LLVM.LLVMCountParamTypes(ref)
    }

    public fun getReturnType(): Type {
        val type = LLVM.LLVMGetReturnType(ref)

        return Type(type)
    }

    public fun getParameterTypes(): List<Type> {
        val dest = PointerPointer<LLVMTypeRef>(getParameterCount().toLong())
        LLVM.LLVMGetParamTypes(ref, dest)

        return dest.map { Type(it) }
    }
    //endregion Core::Types::FunctionTypes
}
