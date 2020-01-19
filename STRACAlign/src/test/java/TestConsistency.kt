import org.junit.Before
import org.junit.Test
import strac.align.align.AlignDistance
import strac.align.align.Cell
import strac.align.align.ICellComparer
import strac.align.interpreter.AlignInterpreter
import strac.align.interpreter.dto.Alignment
import strac.align.interpreter.dto.Payload
import strac.align.models.AlignResultDto
import strac.align.utils.AlignServiceProvider
import strac.core.LogProvider
import strac.core.StreamProviderFactory
import strac.core.TestLogProvider
import strac.core.TraceHelper
import strac.core.dto.FileContentDto
import strac.core.utils.ServiceRegister
import java.util.*

class TestConsistency{

    @Before
    fun setup(){

        AlignServiceProvider.setup()
        AlignServiceProvider.getInstance().provider
    }

    @Test
    fun testConsistency() {

        val dto = Alignment()
        dto.distanceFunctionName="dBin"
        dto.outputAlignment = true
        dto.pairs = ArrayList();
        dto.method = Payload.MethodInfo()
        dto.method.name = "FastDTW"
        dto.method.params = Arrays.asList(2000.0) as List<Any>?
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


        val f1 = "/Users/javier/IdeaProjects/STRAC/scripts/chrome_scripts/tiny_test/ten/wiki-8/wikipedia.org.20.bytecode.txt"
        val f3 = "/Users/javier/IdeaProjects/STRAC/scripts/chrome_scripts/tiny_test/ten/wiki-8/wikipedia.org.28.bytecode.txt"
        //val f2 = "/Users/javier/IdeaProjects/STRAC/scripts/chrome_scripts/tiny_test/ten/wiki-9/w6.txt"
        //TestLogProvider.info("#%s".format(site))
        //for(site2 in sites) {

        dto.files = Arrays.asList(f1, f3)


            //AlignDistance distance, double successCount, double mismatchCount, double gaps1Count, double gaps2Count, double traceSize

        interpreter.execute(dto, { d: AlignDistance, s: Double, m: Double, g1: Double, g2: Double, size: Double ->
            TestLogProvider.info("[", d.distance, ",", "\"%s\"".format(1), ",",
                    "\"%s\"".format(2), "],")
        },
                StreamProviderFactory.getInstance())

        AlignServiceProvider.getInstance().allocator.dispose()


    }
}