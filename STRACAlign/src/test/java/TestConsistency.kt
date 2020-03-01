import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.webbitserver.HttpControl
import org.webbitserver.HttpRequest
import org.webbitserver.HttpResponse
import org.webbitserver.WebServer
import org.webbitserver.netty.NettyWebServer
import org.webbitserver.rest.Rest
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

class TestConsistency{

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

        // Initializing web socket
        // Initializing web socket

        Align.setup()


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


        val f1 = getFile("bytecodes/wikipedia.1.org.bytecode")
        val f3 = getFile("bytecodes/wikipedia.org.bytecode")
        val f2 = getFile("bytecodes/xxxxxxx.bytecode")
        //val f2 = "/Users/javier/IdeaProjects/STRAC/scripts/chrome_scripts/tiny_test/ten/wiki-9/w6.txt"
        //TestLogProvider.info("#%s".format(site))
        //for(site2 in sites) {

        dto.files = Arrays.asList(f1, f2, f3, f1, f2, f2, f3)


            //AlignDistance distance, double successCount, double mismatchCount, double gaps1Count, double gaps2Count, double traceSize

        interpreter.execute(dto, { d: AlignDistance, s: Double, m: Double, g1: Double, g2: Double, size: Double ->
            TestLogProvider.info("[", d.distance, ",", "\"%s\"".format(1), ",",
                    "\"%s\"".format(2), "],")
        },
                StreamProviderFactory.getInstance())

        AlignServiceProvider.getInstance().allocator.dispose()


    }


    @Test
    fun testParallelProcessing() {

        val dto = Alignment()
        dto.distanceFunctionName="dBin"
        dto.outputAlignment = true
        dto.pairs = ArrayList();
        dto.method = Payload.MethodInfo()
        dto.threadPoolCount = 10;
        dto.method.name = "FastDTW"
        dto.method.params = Arrays.asList(2000.0) as List<Any>?
        dto.outputDir = "outDemo";
        dto.exportImage = true;
        dto.separator = "[\r\n]"
        dto.outputAlignmentMap = "map.json";
        dto.clean = arrayOf(
                "^( )*\\d+ [ES]>",
                "0x\\w+ @",
                "\\w+ : ",
                " [A-Z](.*)"
        )

        val interpreter = AlignInterpreter()


        val f1 = getFile("tourney/t1.txt")
        val f2 = getFile("tourney/t2.txt")
        val f3 = getFile("tourney/t3.txt")
        val f4 = getFile("tourney/t4.txt")
        val f5 = getFile("tourney/t5.txt")
        val f6 = getFile("tourney/t6.txt")
        val f7 = getFile("tourney/t7.txt")
        val f8 = getFile("tourney/t8.txt")
        val f9 = getFile("tourney/t9.txt")
        val f10 = getFile("tourney/10.txt")
        //val f2 = "/Users/javier/IdeaProjects/STRAC/scripts/chrome_scripts/tiny_test/ten/wiki-9/w6.txt"
        //TestLogProvider.info("#%s".format(site))
        //for(site2 in sites) {

        dto.files = Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10)


        //AlignDistance distance, double successCount, double mismatchCount, double gaps1Count, double gaps2Count, double traceSize

        interpreter.execute(dto, { d: AlignDistance, s: Double, m: Double, g1: Double, g2: Double, size: Double ->
            TestLogProvider.info("[", d.distance, ",", "\"%s\"".format(1), ",",
                    "\"%s\"".format(2), "],")
        },
                StreamProviderFactory.getInstance())

        AlignServiceProvider.getInstance().allocator.dispose()


    }
}