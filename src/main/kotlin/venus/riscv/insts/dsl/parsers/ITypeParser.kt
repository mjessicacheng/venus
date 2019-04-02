package venus.riscv.insts.dsl.parsers

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.riscv.insts.dsl.relocators.ImmAbsRelocator
import venus.riscv.insts.dsl.getImmediate
import venus.riscv.isNumeral
import venus.riscv.labelOffsetPart
import venus.riscv.symbolPart


object ITypeParser : InstructionParser {
    const val I_TYPE_MIN = -2048
    const val I_TYPE_MAX = 2047
    override operator fun invoke(prog: Program, mcode: MachineCode, args: List<String>) {
        checkArgsLength(args.size, 3)

        mcode[InstructionField.RD] = regNameToNumber(args[0])
        mcode[InstructionField.RS1] = regNameToNumber(args[1])
        if (isNumeral(args[2])) {
            mcode[InstructionField.IMM_11_0] =
                getImmediate(args[2], I_TYPE_MIN, I_TYPE_MAX)
        } else {
            prog.addRelocation(ImmAbsRelocator, symbolPart(args[2]),
                               labelOffsetPart(args[2]))
        }
    }
}
