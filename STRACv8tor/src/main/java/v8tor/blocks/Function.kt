package v8tor.blocks

import java.awt.Frame
import java.util.*
import kotlin.collections.ArrayList

class FunctionBlock(instruction: String){

    var name = instruction

    var parameterCount: ParameterCount = ParameterCount("0")
    var registerCount: RegisterCount = RegisterCount("0")
    var frameSize: FrameSize = FrameSize("0")

    var blocks: ArrayList<BytecodeBlock> = ArrayList()
    var instructions: ArrayList<BytecodeInstruction> = ArrayList()


    var openeed: BytecodeBlock = BytecodeBlock(-1, BlockType.Expression)

    fun openBlock(type: BlockType, position: Int){

        this.openeed = BytecodeBlock(position, type)

        this.blocks.add(this.openeed)

    }

    fun addInstruction(instruction: BytecodeInstruction){

        if(!instruction.equals("-1")) {
            openeed.instructions.add(instruction)
            this.instructions.add(instruction)
        }
    }

}