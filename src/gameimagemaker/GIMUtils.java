/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameimagemaker;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import mathnstuff.MeUtils;
import mathnstuff.utils.QueueStream;

/**
 *
 * @author mewer
 */
public class GIMUtils {
    /**
     * coordinates are y, x, {r,g,b,a}.
     * @param image
     * @param cols
     * @param rows
     * @return 
     */
    public static InputStream arrayToPng(int[][][] image, int cols, int rows) {
        ImageInfo ii = new ImageInfo(cols, rows, 8, true);
        QueueStream.QueueInputStream qis = new QueueStream.QueueInputStream();
        QueueStream.QueueOutputStream qos = qis.dual;
        PngWriter pw = new PngWriter(qos, ii);
        ImageLineInt line = new ImageLineInt(ii);
        
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                ImageLineHelper.setPixelRGBA8(line, x, image[y][x][0], image[y][x][1], image[y][x][2], image[y][x][3]);
            }
            pw.writeRow(line);
        }
        pw.end();
       
        try {
            qos.close();
        } catch (IOException ex) {
            Logger.getLogger(GameImageMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qis;
    }
    
    public static void inputStreamToFile(InputStream is, String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            MeUtils.pipeInputStreamToOutputStream(is, fos);
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(GameImageMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
