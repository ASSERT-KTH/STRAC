import com.sun.net.httpserver.spi.HttpServerProvider
import org.junit.Before
import org.junit.Test
import strac.align.socket.ServerResourceHandler
import strac.align.utils.AlignServiceProvider
import java.io.FileNotFoundException
import java.net.InetSocketAddress


class TestUI{

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

        // wait 5 seconds for messages from websocket
        // wait 5 seconds for messages from websocket
        //Thread.sleep(5000)

        readLine()
    }

    //@Test
    fun testServer(){

        readLine()
    }


}