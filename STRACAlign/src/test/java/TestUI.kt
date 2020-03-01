import com.sun.net.httpserver.spi.HttpServerProvider
import org.junit.Before
import org.junit.Test
import org.webbitserver.WebServer
import org.webbitserver.WebServers
import org.webbitserver.WebSocketHandler
import org.webbitserver.handler.StaticFileHandler
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
        val handler = ServerResourceHandler()
        val server = HttpServerProvider.provider().createHttpServer(InetSocketAddress(8080), 8080)
        server.createContext("/", handler);

        server.start()


        readLine()
    }


}