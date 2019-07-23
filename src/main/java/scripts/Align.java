package scripts;

import com.google.gson.Gson;
import core.LogProvider;
import core.ServiceRegister;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;

import java.io.FileReader;
import java.io.IOException;



public class Align {

    public static void setup() throws IOException, ClassNotFoundException {

        ServiceRegister.setup();
        ServiceRegister.getProvider();
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        if(args.length == 0){

            System.out.println("You must provide the path to the json payload");
            return;
        }

        setup();


        Alignment dto = new Gson().fromJson(new FileReader(args[0]), Alignment.class);

        AlignInterpreter executor = new AlignInterpreter();


        try{
            executor.execute(dto);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            LogProvider.info("Disposing map files");

            ServiceRegister.dispose();
        }



    }
}
