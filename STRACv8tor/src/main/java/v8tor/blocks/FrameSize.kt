package v8tor.blocks

class FrameSize(instruction: String){

    val count = Integer.parseInt(instruction.removePrefix("Frame size "))

}