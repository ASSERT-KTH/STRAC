package core.utils;

import align.InsertOperation;
import align.implementations.WindowedDTW;
import core.LogProvider;
import core.data_structures.IArray;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DWTHelper {

    public static boolean existsCell(long i, long j, List<InsertOperation> ops){

        for(InsertOperation op: ops){
            if(op.getTrace1Index() == i && op.getTrace2Index() == j)
                return true;
        }

        return false;
    }



    public static List<InsertOperation> scalePath(List<InsertOperation> ops){

        List<InsertOperation> result = new ArrayList<>();

        //LogProvider.info(ops);
        //factor = 2*factor;


        for(int i = 0; i < ops.size(); i++){

            InsertOperation current = ops.get(i);
            int nI = current.getTrace1Index();
            int nJ = current.getTrace2Index();

            result.add(new InsertOperation(nI*2, nJ*2));
            result.add(new InsertOperation(nI*2, nJ*2 + 1));
            result.add(new InsertOperation(nI*2 + 1, nJ*2));
            result.add(new InsertOperation(nI*2 + 1, nJ*2 + 1));
        }


        /*InsertOperation last = result.get(result.size() - 1);

        if(last.getTrace1Index() != maxI ){
            // Fill to bottom corner

            int deltaX = maxI - last.getTrace1Index();

            for(int i = 1; i < deltaX; i++){
                result.add(new InsertOperation(last.getTrace1Index() + i, last.getTrace2Index()));
            }

        }

        if(last.getTrace2Index() != maxJ){
            int deltaX = maxJ - last.getTrace2Index();

            for(int i = 1; i < deltaX; i++){
                result.add(new InsertOperation( last.getTrace2Index(),last.getTrace2Index() + i));
            }

        }*/


        LogProvider.info(ops);
        LogProvider.info(result);
        return result;
    }

    public static List<InsertOperation> createWindow(List<InsertOperation> ops, int radius, int maxI, int maxJ){
        List<InsertOperation> result = new ArrayList<>();


            for(InsertOperation op: ops){

                result.add(op);
                for(int i = -radius; i <= radius; i++){

                    for(int j = -radius; j <= radius; j++) {
                        int nI = op.getTrace1Index() + i;
                        int nJ = op.getTrace2Index() + j;


                        if (nI >= 0 && nJ >= 0 && !existsCell(nI, nJ, result))
                            result.add(new InsertOperation(nI, nJ));
                    }

                }


        }


        LogProvider.info(result);
        return result;
    }

    public static void draw(WindowedDTW.EmptyMap map, String name, int width, int height){

        int scale = 1;
        File writer = new File(name);
        int pieceSize = 1*scale;

        BufferedImage img = new BufferedImage(width*pieceSize, height*pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(map.existColumn(i) && map.existRow(i, j)){
                    g.setColor(Color.decode("#000000"));
                    g.fillRect(j*pieceSize, i*pieceSize, pieceSize, pieceSize);
                }
                else{
                    g.setColor(Color.decode("#ffffff"));
                    g.fillRect(j*pieceSize, i*pieceSize, pieceSize, pieceSize);
                }
            }
        }

        try {
            ImageIO.write(img, "png", writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void draw(WindowedDTW.WindowMap<T> map, String name, int width, int height, int rX, int rY){

        int scale = 1;
        File writer = new File(name);
        int pieceSize = 1*scale;

        BufferedImage img = new BufferedImage(width*pieceSize, height*pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        for(int j: map.getColumns()){
            for(int i: map.getRow(j)){
                g.setColor(Color.decode("#ff0000"));
                g.fillRect(i*pieceSize, j*pieceSize, pieceSize, pieceSize);

            }
        }
        g.setColor(Color.decode("#0000ff"));
        g.fillRect(rX*pieceSize, rY*pieceSize, pieceSize, pieceSize);


        try {
            ImageIO.write(img, "png", writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static WindowedDTW.EmptyMap
    expandWindow(IArray<InsertOperation> ops, int radius, long lenT1, long lenT2){

        //ops.set(0,new InsertOperation((int)lenT1, (int)lenT2));

        WindowedDTW.EmptyMap grown = new WindowedDTW.EmptyMap();
        WindowedDTW.EmptyMap expansion = new WindowedDTW.EmptyMap();

        TimeUtils utl = new TimeUtils();


        LogProvider.info("Expanding to...", ops.size()*4*radius*radius);

        utl.reset();
        for(InsertOperation op: ops){

            if(op.getTrace1Index() >= 0 && op.getTrace2Index() >= 0)
                expansion.set(op.getTrace1Index(), op.getTrace2Index());

            for(int j  = -radius; j <= radius; j++) {
                for (int i = -radius; i <= radius; i++) {

                    int nI = op.getTrace1Index() + i;
                    int nJ = op.getTrace2Index() + j;

                    //LogProvider.info("Size", expansion.size());
                    if (nI >= 0 && nJ >= 0 && (!expansion.existColumn(nI) || !expansion.existRow(nI, nJ)))
                        expansion.set(nI, nJ);

                }
            }


        }

        //draw(expansion, String.format("%s_%s_grown.png", lenT1, lenT2), (int)lenT2 + 1, (int)lenT1 + 1);
        utl.time("Growing");



        utl.reset();
        for(int i: expansion.getColumns()){
            for(int j: expansion.getRow(i)){

                grown.set(i*2, j*2);

                grown.set(i*2, j*2 + 1);

                grown.set(i*2 + 1, j*2 + 1);

                grown.set(i*2 + 1, j*2);
            }
        }

        utl.time("Scaling");
        //draw(grown, String.format("%s_%s_scale.png", lenT1, lenT2), (int)lenT2 + 1, (int)lenT1 + 1);


       /* WindowedDTW.EmptyMap result = new WindowedDTW.EmptyMap();

        /*int startJ = 0;

        for(int i = 0; i < lenT1; i++){
            int newStartJ = -1;
            for(int j =startJ; j < lenT2; j++){
                if(grown.existColumn(i) && grown.existRow(i, j)){
                    result.set(i, j);

                    if(newStartJ == -1){
                        newStartJ = j;
                    }
                }
                else if (newStartJ != -1)
                    break;
            }

            startJ = newStartJ;
        }

        utl.time("Generating");

        draw(result, String.format("%s%s_final.png", lenT1, lenT2), (int)lenT2, (int)lenT1);*/
        return grown;
    }

}
