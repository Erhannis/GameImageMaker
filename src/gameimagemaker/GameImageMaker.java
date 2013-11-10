/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameimagemaker;

import gameimagemaker.entities.Canvas;
import gameimagemaker.entities.Drop;
import gameimagemaker.entities.Mote;
import java.io.InputStream;
import java.util.Random;

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
        Random rand = new Random();
        switch (3) {
            case 0:
            {
                int cols = 100;
                int rows = 100;
                Drop drop = new Drop(cols, rows);
                drop.drawBase((int) 0x10, (int) 0xD0, (int) 0x10);
                drop.setRandBlue();
                drop.addRing();
                InputStream is = drop.generate();
                GIMUtils.inputStreamToFile(is, "drop-green.png");
                break;
            }
            case 1:
            {
                int cols = 10;
                int rows = 10;
                Mote mote = new Mote(cols, rows);
                mote.drawBase((int) 0x10, (int) 0xD0, (int) 0x10);
                InputStream is = mote.generate();
                GIMUtils.inputStreamToFile(is, "mote-green.png");
                break;
            }
            case 2:
            {
                int cCols = 1000;
                int cRows = 1000;
                Canvas canvas = new Canvas(cCols, cRows);
                //canvas.drawFlatBackground(0x00, 0x20, 0x50, 0xFF);
                
                int motes = 20;
                int maxMoteSize = 30;
                for (int i = 0; i < motes; i++) {
                    int cols = rand.nextInt(30) + 1;
                    int rows = cols;
                    Mote mote = new Mote(cols, rows);
                    mote.drawBase(rand.nextInt(0x100), rand.nextInt(0x100), rand.nextInt(0x100));
                    canvas.setEntity(mote, rand.nextInt(cRows), rand.nextInt(cCols));
                }
                InputStream is = canvas.generate();
                GIMUtils.inputStreamToFile(is, "canvas-motes.png");
                break;
            }
            case 3:
            {
                int ccols = 1000;
                int crows = 1000;
                Canvas canvas = new Canvas(ccols, crows);

                int dcols = 100;
                int drows = 100;
                Drop drop = new Drop(dcols, drows);
                drop.drawBase((int) 0x10, (int) 0xD0, (int) 0x10);
                drop.setRandBlue();
                drop.addRing();
                
                canvas.setEntity(drop, (ccols / 2) - (dcols / 2), (crows / 2) - (drows / 2));

                int motes = 50;
                int maxMoteSize = 10;
                for (int i = 0; i < motes; i++) {
                    int cols = rand.nextInt(maxMoteSize) + 1;
                    int rows = cols;
                    Mote mote = new Mote(cols, rows);
                    mote.drawBase(rand.nextInt(0x100), rand.nextInt(0x100), rand.nextInt(0x100));
                    canvas.setEntity(mote, rand.nextInt(crows), rand.nextInt(ccols));
                }
                InputStream is = canvas.generate();
                GIMUtils.inputStreamToFile(is, "canvas-motes-w_drop.png");
                break;
}
        }
    }
}
