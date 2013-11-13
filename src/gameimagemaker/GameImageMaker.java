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
import mathnstuff.MeMath;

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
        switch (5) {
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
            case 4:
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
                
                canvas.drawFlatBackground(0x00, 0x20, 0x60, 0xFF);

                int motes = 100;
                int maxMoteSize = 15;
                for (int i = 0; i < motes; i++) {
                    int cols = rand.nextInt(maxMoteSize) + 1;
                    int rows = cols;
                    Mote mote = new Mote(cols, rows);
                    mote.drawBase(rand.nextInt(0x100), rand.nextInt(0x100), rand.nextInt(0x100));
                    canvas.drawEntity(mote, rand.nextInt(crows), rand.nextInt(ccols));
                }
                
                canvas.drawEntity(drop, (ccols / 2) - (dcols / 2), (crows / 2) - (drows / 2));
                
                InputStream is = canvas.generate();
                GIMUtils.inputStreamToFile(is, "canvas-motes-w_drop.png");
                break;
            }
            case 5:
            {
                int ccols = 1000;
                int crows = 1000;
                Canvas canvas = new Canvas(ccols, crows);

                int dcols = 100;
                int drows = 100;
                Drop drop = new Drop(dcols, drows);
                drop.drawBase((int) 0xF0, (int) 0x10, (int) 0x10);
                drop.drawBase((int) 0x10, (int) 0xF0, (int) 0x10);
                //drop.drawBase((int) 0x10, (int) 0x10, (int) 0xF0);
                //drop.drawBase((int) 0xF0, (int) 0xF0, (int) 0x10);
                //drop.drawBase((int) 0x10, (int) 0xF0, (int) 0xF0);
                //drop.drawBase((int) 0xF0, (int) 0x10, (int) 0xF0);
                //drop.drawBase((int) 0xF0, (int) 0xF0, (int) 0xF0);
                
                //drop.drawBase((int) 0x00, (int) 0x00, (int) 0x00);
                //drop.drawBase((int) 0xF0, (int) 0x10, (int) 0xF0);
                drop.heightMapBase();
                drop.deriveNormalMap();
                //drop.setRandBlue();
//                drop.addShadow();
                drop.addReflectionRing();
                drop.addSheen();
                drop.addShadow();
                drop.addRing();
                drop.trim();
                
                canvas.drawFlatBackground(0x00, 0x20, 0x60, 0xFF);

                int motes = 200;
                int maxMoteSize = 5;
                for (int i = 0; i < motes; i++) {
                    int cols = rand.nextInt(maxMoteSize) + 1;
                    int rows = cols;
                    Mote mote = new Mote(cols, rows);
                    mote.drawBase(rand.nextInt(0x100), rand.nextInt(0x100), rand.nextInt(0x100));
                    canvas.drawEntity(mote, rand.nextInt(crows), rand.nextInt(ccols));
                }

                motes /= 2;
                maxMoteSize *= 2;
                for (int i = 0; i < motes; i++) {
                    int cols = rand.nextInt(maxMoteSize) + 1;
                    int rows = cols;
                    Mote mote = new Mote(cols, rows);
                    mote.drawBase(rand.nextInt(0x100), rand.nextInt(0x100), rand.nextInt(0x100));
                    canvas.drawEntity(mote, rand.nextInt(crows), rand.nextInt(ccols));
                }
                
                canvas.drawEntity(drop, (ccols / 2) - (dcols / 2), (crows / 2) - (drows / 2));

                motes /= 2;
                maxMoteSize *= 2;
                for (int i = 0; i < motes; i++) {
                    int cols = rand.nextInt(maxMoteSize) + 1;
                    int rows = cols;
                    Mote mote = new Mote(cols, rows);
                    mote.drawBase(rand.nextInt(0x100), rand.nextInt(0x100), rand.nextInt(0x100));
                    canvas.drawEntity(mote, rand.nextInt(crows), rand.nextInt(ccols));
                }
                
                InputStream is = canvas.generate();
                GIMUtils.inputStreamToFile(is, "canvas-motes_flurry-w_drop-w_sheen.png");
                break;
            }
            case 6:
            {
                int ccols = 1000;
                int crows = 1000;
                Canvas canvasL = new Canvas(ccols, crows);
                Canvas canvasR = new Canvas(ccols, crows);

                int dcols = 100;
                int drows = 100;
                Drop drop = new Drop(dcols, drows);
                drop.drawBase((int) 0x10, (int) 0xD0, (int) 0x10);
                drop.setRandBlue();
                drop.addRing();
                
                canvasL.drawFlatBackground(0x00, 0x20, 0x60, 0xFF);
                canvasR.drawFlatBackground(0x00, 0x20, 0x60, 0xFF);

                int motes = 500;
                int maxMoteSize = 5;
                int parallax = -20;
                for (int i = 0; i < motes; i++) {
                    int cols = rand.nextInt(maxMoteSize) + 1;
                    int rows = cols;
                    Mote mote = new Mote(cols, rows);
                    mote.drawBase(rand.nextInt(0x100), rand.nextInt(0x100), rand.nextInt(0x100));
                    int x = rand.nextInt(ccols);
                    int y = rand.nextInt(crows);
                    canvasL.drawEntity(mote, x + parallax, y);
                    canvasR.drawEntity(mote, x, y);
                }

                motes /= 2;
                maxMoteSize *= 2;
                parallax += 10;
                for (int i = 0; i < motes; i++) {
                    int cols = rand.nextInt(maxMoteSize) + 1;
                    int rows = cols;
                    Mote mote = new Mote(cols, rows);
                    mote.drawBase(rand.nextInt(0x100), rand.nextInt(0x100), rand.nextInt(0x100));
                    int x = rand.nextInt(ccols);
                    int y = rand.nextInt(crows);
                    canvasL.drawEntity(mote, x + parallax, y);
                    canvasR.drawEntity(mote, x, y);
                }
                
                canvasL.drawEntity(drop, (ccols / 2) - (dcols / 2), (crows / 2) - (drows / 2));
                canvasR.drawEntity(drop, (ccols / 2) - (dcols / 2), (crows / 2) - (drows / 2));

                motes /= 2;
                maxMoteSize *= 2;
                parallax += 30;
                for (int i = 0; i < motes; i++) {
                    int cols = rand.nextInt(maxMoteSize) + 1;
                    int rows = cols;
                    Mote mote = new Mote(cols, rows);
                    mote.drawBase(rand.nextInt(0x100), rand.nextInt(0x100), rand.nextInt(0x100));
                    int x = rand.nextInt(ccols);
                    int y = rand.nextInt(crows);
                    canvasL.drawEntity(mote, x + parallax, y);
                    canvasR.drawEntity(mote, x, y);
                }
                
                InputStream is = canvasL.generate();
                // Got 'em backwards.
                GIMUtils.inputStreamToFile(is, "canvas-motes_flurry-w_drop-right.png");
                is = canvasR.generate();
                GIMUtils.inputStreamToFile(is, "canvas-motes_flurry-w_drop-left.png");
                break;
            }
            case 7:
            {
                int cols = 100;
                int rows = 100;
                Drop drop = new Drop(cols, rows);
                drop.drawBase((int) 0x10, (int) 0xD0, (int) 0x10);
                drop.heightMapBase();
                drop.deriveNormalMap();
                drop.setRandBlue();
                drop.addSheen();
                drop.addRing();
                InputStream is = drop.generate();
                GIMUtils.inputStreamToFile(is, "drop-green-w_sheen.png");
                break;
            }
            case 8:
            {
                double[] u = {(rand.nextDouble() - 0.5) * 10, (rand.nextDouble() - 0.5) * 10};
                double[] v = {(rand.nextDouble() - 0.5) * 10, (rand.nextDouble() - 0.5) * 10};
                //double[] u = {1, 0};
                //double[] v = {1, 1};
                //MeMath.vectorNormalizeIP(u);
                //MeMath.vectorNormalizeIP(v);
                System.out.println("u: {" + u[0] + ", " + u[1] + "}");
                System.out.println("v: {" + v[0] + ", " + v[1] + "}");
                double theta = MeMath.vectorAngle(u, v);
                System.out.println("u-v: " + theta);
                double lastT = 0;
                double[] lastW = {0, 0};
                for (double alpha = 0; alpha <= 1; alpha += 0.1) {
                    double[] w = MeMath.vectorAngleInterpolate(u, v, alpha);
                    System.out.println("----");
                    System.out.println("a: " + alpha);
                    System.out.println("at: " + (alpha * theta));
                    System.out.println("w: {" + w[0] + ", " + w[1] + "}");
                    double[] dw = {w[0] - lastW[0], w[1] - lastW[1]};                    
                    System.out.println("dw: {" + dw[0] + ", " + dw[1] + "} " + MeMath.vectorLength(dw));
                    lastW = w;
                    double thisT = MeMath.vectorAngle(u, w);
                    System.out.println("u-w: " + thisT);
                    System.out.println("dt: " + (thisT - lastT));
                    lastT = thisT;
                }
                break;
            }
        }
    }
}
