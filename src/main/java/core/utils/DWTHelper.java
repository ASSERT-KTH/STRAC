package core.utils;

import align.Cell;
import align.implementations.WindowedDTW;
import core.LogProvider;
import core.data_structures.IArray;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DWTHelper {

    public static boolean existsCell(long i, long j, List<Cell> ops){

        for(Cell op: ops){
            if(op.getTrace1Index() == i && op.getTrace2Index() == j)
                return true;
        }

        return false;
    }




    public static void draw(WindowedDTW.Window map, String name, int width, int height){

        int scale = 1;
        File writer = new File(name);
        int pieceSize = 1*scale;

        BufferedImage img = new BufferedImage(width*pieceSize, height*pieceSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        g.setColor(Color.decode("#ff0000"));
        g.fillRect(0, 0, width, height);
        for(int i = 0; i < height; i++){
            for(int j: map.iterator(i)){
                g.setColor(Color.decode("#ffffff"));
                g.fillRect(j*pieceSize, i*pieceSize, pieceSize, pieceSize);
            }
        }

        try {
            ImageIO.write(img, "png", writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static WindowedDTW.Window expandWindow(IArray<Cell> ops, int radius,
                                                  long lenT1, long lenT2, long opCount,
                                                  int minI, int minJ){

        //ops.set(0,new Cell((int)lenT1, (int)lenT2));

        WindowedDTW.Window scale = new WindowedDTW.Window(lenT1, lenT2);

        TimeUtils utl = new TimeUtils();


        LogProvider.info("Expanding to...", ops.size()*4*radius*radius);

        utl.reset();

        int lastWarpedI = Integer.MAX_VALUE;
        int lastWarpedJ = Integer.MAX_VALUE;

        int currentI = minI;
        int currentJ = minJ;

        for(long k = opCount - 1; k >= 0; k--){

            Cell op = ops.read(k);

            final int warpedI = op.getTrace1Index();
            final int warpedJ = op.getTrace2Index();

            final int blockISize = 2; //k == 0? (lenT1%2 == 0? 2: 3): 2;
            final int blockJSize = 2; //k == 0? (lenT2%2 == 0? 2: 3): 2;

            if(warpedI > lastWarpedI)
                currentI += blockISize;

            if(warpedJ > lastWarpedJ)
                currentJ += blockISize;

            //Diag patch
            if ((warpedJ>lastWarpedJ) && (warpedI>lastWarpedI))
            {
                scale.set(currentI - 1, currentJ);
                scale.set(currentI, currentJ - 1);
            }

            for (int x=0; x<blockISize; x++)
            {
                scale.set(currentI+x, currentJ);
                scale.set(currentI+x, currentJ+blockJSize-1);
            }  // end for loop

            // Record the last position in the warp path so the direction of the path can be determined when the next
            //    position of the path is evaluated.
            lastWarpedI = warpedI;
            lastWarpedJ = warpedJ;
        }

        if(lenT1%2 == 1 && lenT1 > 0){
            scale.set((int)lenT1 - 1, scale.getMin((int)lenT1 - 2));
            scale.set((int)lenT1 - 1, scale.getMax((int)lenT1 - 2));
        }

        utl.time("Scaling");

        scale.expand(radius);

        utl.time("Growing");

        //draw(scale, String.format("%s_%s_grown.png", lenT1, lenT2), (int)lenT2, (int)lenT1);

        return scale;
    }

}
