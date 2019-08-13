import org.junit.Test
import strac.core.stream_providers.CommandStdInputProvider
import v8tor.BytecodeParser
import java.io.*
import java.util.*

class TestGrouping{

    fun getFile(name: String): FileInputStream {

        val classLoader = javaClass.classLoader
        try {
            return FileInputStream(classLoader.getResource(name)!!.file)
        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }

    }


    @Test
    fun testGroping(){

        val st = CommandStdInputProvider();
        st.setMaxTime(10)

        val st1 = ByteArrayInputStream(st.getStream("/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome \\ --no-sandbox -user-data-dir=temp --js-flags=\"--print-bytecode\"  http://www.youtube.com").readAllBytes())

        //val writer = FileWriter("out.txt")
        //writer.write(String(st1.readAllBytes()))
        //writer.close()

        val p = BytecodeParser(Scanner(st1).useDelimiter("\n"))
        val bck = p.parseCompleteBytecode()

        val map = bck.flatMap { it.instructions }
                .groupBy { it.opcode }
                .map { Pair(it.key, it.value.map { 1 }.sum()) }
                .sortedBy { it.first }

        println(map.size)

        print("[")
        map.forEach {
            val self = it
            print("%s,".format(IntArray(it.second){self.first}.joinToString(",")))
        }

        print("]")

    }


    @Test
    fun testGropingBlocks(){

        val st = CommandStdInputProvider();
        st.setMaxTime(10)

        val st1 = ByteArrayInputStream(st.getStream("/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome \\ --no-sandbox -user-data-dir=temp --js-flags=\"--print-bytecode\"  http://www.codepen.io").readAllBytes())

        //val writer = FileWriter("out.txt")
        //writer.write(String(st1.readAllBytes()))
        //writer.close()

        val p = BytecodeParser(Scanner(st1).useDelimiter("\n"))
        val bck = p.parseCompleteBytecode()

        val map = bck.flatMap { it.blocks }
                .groupBy { "%s %s".format(it.blockType, it.instructions.map { it.opcode }.joinToString(" "))}
                .map { Pair(it.key, it.value.map { 1 }.sum()) }

        println(map.size)

        map.forEach {
            println("%s, %s".format(it.first, it.second))
        }


    }


    @Test
    fun testGroping2(){

        val st = CommandStdInputProvider();
        st.setMaxTime(4)

        val st1 = ByteArrayInputStream(st.getStream("/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome \\ --no-sandbox -user-data-dir=temp --js-flags=\"--print-bytecode\"  http://www.google.com").readAllBytes())

        //val writer = FileWriter("out.txt")
        //writer.write(String(st1.readAllBytes()))
        //writer.close()

        val p = BytecodeParser(Scanner(st1).useDelimiter("\n"))
        val bck = p.parseCompleteBytecode()

        val map = bck.map { it.name }


        println(map.size)

        print("[")
        map.forEach {
            print("%s,".format(it))
        }

        print("]")

    }

}