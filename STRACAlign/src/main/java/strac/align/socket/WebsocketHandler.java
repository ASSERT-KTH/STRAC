package strac.align.socket;

import com.google.gson.Gson;
import kotlin.Triple;
import org.webbitserver.BaseWebSocketHandler;
import org.webbitserver.WebSocketConnection;
import strac.align.interpreter.dto.UpdateDTO;
import strac.align.models.AlignResultDto;

import java.util.HashSet;
import java.util.Set;


public class WebsocketHandler extends BaseWebSocketHandler {

    public class Action{
        public String namespace;
    }

    public class Msg{

        public Object data;
        public String namespace;

        public Msg(String namespacee, Object any){
            this.namespace = namespacee;
            this.data = any;
        }
    }

    Set<WebSocketConnection> connections = new HashSet<>();

    static WebsocketHandler _instance;

    private WebsocketHandler(){
        connections = new HashSet<>();
    }

    public static WebsocketHandler getInstance(){
        if(_instance == null)
            _instance = new WebsocketHandler();

        return _instance;
    }

    public void onOpen(WebSocketConnection connection) {
        connections.add(connection);
        connection.send(new Gson().toJson(new Msg("update", UpdateDTO.instance)));
    }

    public void onClose(WebSocketConnection connection) {
        connections.remove(connection);
    }

    public void onMessage(WebSocketConnection connection, String message) {

        Action m = new Gson().fromJson(message, Action.class);

        if(m.namespace.equals("update"))
            sendUpdate();
    }

    public void sendUpdate(){
        for(WebSocketConnection conn: connections){
            conn.send((new Gson()).toJson(new Msg("update", UpdateDTO.instance)));
        }
    }

    public void setSingleProgress(int tr1, int tr2, double progress){
        for(WebSocketConnection conn: connections){
            conn.send((new Gson()).toJson(new Msg("single_update", new Triple<Integer, Integer, Double>(tr1, tr2, progress))));
        }
    }

    public void sendLog(String msg){
        for(WebSocketConnection conn: connections){
            conn.send((new Gson()).toJson(new Msg("log", msg)));
        }
    }
}
