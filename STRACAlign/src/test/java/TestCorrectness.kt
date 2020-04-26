/**
@author Javier Cabrera-Arteaga on 2020-04-26
 */
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import strac.align.align.AlignDistance
import strac.align.interpreter.AlignInterpreter
import strac.align.interpreter.dto.Alignment
import strac.align.interpreter.dto.Payload
import strac.align.interpreter.dto.UpdateDTO
import strac.align.scripts.Align
import strac.align.utils.AlignServiceProvider
import strac.core.StreamProviderFactory
import strac.core.TestLogProvider
import java.io.FileNotFoundException
import java.util.*
import java.util.concurrent.ExecutionException

class TestCorrectness{

    protected fun getFile(name: String?): String {
        val classLoader = javaClass.classLoader
        return try {
            classLoader.getResource(name).file
        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }
    }

    @Before
    fun setup(){

        AlignServiceProvider.setup()
        AlignServiceProvider.getInstance().provider
    }

    @Test
    fun testConsistency() {

        Align.setup()

        val dto = Alignment()
        dto.distanceFunctionName="dBin"
        dto.outputAlignment = true
        dto.pairs = ArrayList();
        dto.method = Payload.MethodInfo()
        dto.method.name = "PureDTW"
        dto.method.params = Arrays.asList()
        dto.outputDir = "outDemo";
        dto.exportImage = true;
        dto.separator = "[\r\n]"
        dto.clean = arrayOf(
                "^( )*\\d+ [ES]>",
                "0x\\w+ @",
                "\\w+ : ",
                " [A-Z](.*)"
        )

        val interpreter = AlignInterpreter()


        val f1 = getFile("bytecodes/wikipedia.1.org.bytecode")
        val f2 = getFile("bytecodes/wikipedia.org.bytecode")

        dto.files = Arrays.asList(f1, f2)


        //AlignDistance distance, double successCount, double mismatchCount, double gaps1Count, double gaps2Count, double traceSize

        var d1 = 0.0
        var d2 = 0.0

        interpreter.execute(dto, { d: AlignDistance, s: Double, m: Double, g1: Double, g2: Double, size: Double ->
            println(d.getDistance())
            d1 = d.getDistance()
        }, StreamProviderFactory.getInstance())

        AlignServiceProvider.getInstance().allocator.dispose()

        dto.method.name = "SIMD"

        interpreter.execute(dto, { d: AlignDistance, s: Double, m: Double, g1: Double, g2: Double, size: Double ->
            println(d.getDistance())
            d2 = d.getDistance()
        }, StreamProviderFactory.getInstance())

        AlignServiceProvider.getInstance().allocator.dispose()

        assert(d1 == d2)
    }

}