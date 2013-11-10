/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameimagemaker;

import gameimagemaker.entities.Drop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import mathnstuff.MeUtils;

/**
 *
 * @author mewer
 */
public class GameImageMaker {
 /*
    public InputStream generateTest(byte r, byte g, byte b, int cols, int rows) {
        ImageInfo imi = new ImageInfo(cols, rows, 8, true); // 8 bits per channel, no alpha
        // open image for writing to a output stream
        QueueInputStream qis = new QueueInputStream();
        QueueOutputStream qos = qis.dual;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("test-fos.png");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameImageMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
        PngWriter png = new PngWriter(qos, imi);
        // add some optional metadata (chunks)
        png.getMetadata().setDpi(100.0);
        png.getMetadata().setTimeNow(0); // 0 seconds fron now = now
        png.getMetadata().setText(PngChunkTextVar.KEY_Title, "just a text image");
        png.getMetadata().setText("my key", "my text");
        ImageLineInt iline = new ImageLineInt(imi);
        for (int y = 0; y < imi.rows; y++) {
        for (int x = 0; x < imi.cols; x++) { // this line will be written to all rows
//            int r = 255;
//            int g = 127;
//            int b = 255 * col / imi.cols;
                double radius = Math.sqrt(MeMath.sqr(x - (cols / 2.0)) + MeMath.sqr(y - (rows / 2.0)));
                radius /= Math.max(cols / 2.0, rows / 2.0);
                if (radius > 1) {
                    radius = 0;
                }
                byte a = (byte)(0xFF * radius);
            ImageLineHelper.setPixelRGBA8(iline, x, r, g, b, a); // orange-ish gradient
        }
            png.writeRow(iline);
        }
//        for (int row = 0; row < png.imgInfo.rows; row++) {
//            png.writeRow(iline);
//        }
        png.end();
        try {
            qos.close();
        } catch (IOException ex) {
            Logger.getLogger(GameImageMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qis;
    }
/**/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Drop drop = new Drop();
        int cols = 100;
        int rows = 100;
        Random rand = new Random();
        InputStream is = drop.generateDrop((byte)0x10, (byte)0xD0, (byte)0x10, cols, rows);
        //InputStream is = gim.generateTest((byte)0x10, (byte)0xF0, (byte)0x10, cols, rows);
        //InputStream is = gim.generateTest(cols, rows);
        try {
            FileOutputStream fos = new FileOutputStream("drop-green.png");
            MeUtils.pipeInputStreamToOutputStream(is, fos);
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(GameImageMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
