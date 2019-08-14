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
        dto.pairs = ArrayList()
        dto.method = Payload.MethodInfo()
        dto.method.name = "FastDTW"
        dto.method.params = Arrays.asList(2000.0) as List<Any>?

        dto.separator = "[\r\n]"
        dto.clean = arrayOf(
                "^( )*\\d+ [ES]>",
                "0x\\w+ @",
                "\\w+ : "
                //" [A-Z](.*)"
        )

        dto.include = FileContentDto.Include()
        dto.include.pattern = "^([0-9a-f]{2})"
        dto.include.group = 0

        val sites = arrayOf("google.com", "github.com", "wikipedia.org", "kth.se", "youtube.com", "2019.splashcon.org")

        val interpreter = AlignInterpreter()

        for(site in sites){

            val f1 = "/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome \\ --no-sandbox -user-data-dir=temp --js-flags=\"--print-bytecode\"  %s".format(site)
            TestLogProvider.info("#%s".format(site))
            for(site2 in sites) {
                val f2 = "/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome \\ --no-sandbox -user-data-dir=temp --js-flags=\"--print-bytecode\"  %s".format(site2)

                dto.files = Arrays.asList(f1, f2)

                for (i in 1..10) {

                    //AlignDistance distance, double successCount, double mismatchCount, double gaps1Count, double gaps2Count, double traceSize

                    interpreter.execute(dto, { d: AlignDistance, s: Double, m: Double, g1: Double, g2: Double, size: Double ->
                        TestLogProvider.info("[", d.distance, ",", "\"%s\"".format(site), ",",
                                "\"%s\"".format(site2), "],")
                    },
                            StreamProviderFactory.getInstance())

                    AlignServiceProvider.getInstance().allocator.dispose()
                }
            }

        }
    }
}