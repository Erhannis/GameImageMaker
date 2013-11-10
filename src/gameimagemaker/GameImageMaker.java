/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameimagemaker;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import mathnstuff.MeMath;
import mathnstuff.MeUtils;
import mathnstuff.utils.QueueStream.QueueInputStream;
import mathnstuff.utils.QueueStream.QueueOutputStream;

/**
 *
 * @author mewer
 */
public class GameImageMaker {
    
    /**
     * last coordinate is {r,g,b,a}.
     * @param image
     * @param cols
     * @param rows
     * @return 
     */
    public static InputStream arrayToPng(int[][][] image, int cols, int rows) {
        ImageInfo ii = new ImageInfo(cols, rows, 8, true);
        QueueInputStream qis = new QueueInputStream();
        QueueOutputStream qos = qis.dual;
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
    
    
    public InputStream generateBlob(byte r, byte g, byte b, int cols, int rows) {
        int[][][] buf = new int[rows][cols][4];
        
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                double radius = Math.sqrt(MeMath.sqr(x - (cols / 2.0)) + MeMath.sqr(y - (rows / 2.0)));
                radius /= Math.max(cols / 2.0, rows / 2.0);
                double alpha = (radius * 0.9) + 0.1;
                if (radius > 1) {
                    alpha = 0;
                }
                byte a = (byte)(0xFF * alpha);
                buf[y][x][0] = r;
                buf[y][x][1] = g;
                buf[y][x][2] = b;
                buf[y][x][3] = a;
            }
        }
        
        return arrayToPng(buf, cols, rows);
    }
    
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameImageMaker gim = new GameImageMaker();
        int cols = 100;
        int rows = 100;
        Random rand = new Random();
        InputStream is = gim.generateBlob((byte)0x10, (byte)0xD0, (byte)0x10, cols, rows);
        //InputStream is = gim.generateTest((byte)0x10, (byte)0xF0, (byte)0x10, cols, rows);
        //InputStream is = gim.generateTest(cols, rows);
        try {
            FileOutputStream fos = new FileOutputStream("blob-pixs.png");
            MeUtils.pipeInputStreamToOutputStream(is, fos);
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(GameImageMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
