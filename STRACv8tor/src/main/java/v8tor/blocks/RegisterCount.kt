package v8tor.blocks

class RegisterCount(instruction: String){


    val count = Integer.parseInt(instruction.removePrefix("Register count "))

}