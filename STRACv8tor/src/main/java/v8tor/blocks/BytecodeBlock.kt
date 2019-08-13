package v8tor.blocks

class BytecodeBlock(val address: Int, val blockType: BlockType){

    val instructions: ArrayList<BytecodeInstruction> = ArrayList()
}

enum class BlockType{
    Statement,
    Expression
}