package v8tor.blocks

class ParameterCount(instruction: String){

    val count = Integer.parseInt(instruction.removePrefix("Parameter count "))
}