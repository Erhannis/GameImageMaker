/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameimagemaker.entities;

import gameimagemaker.GIMUtils;
import java.io.InputStream;
import java.util.Random;
import mathnstuff.MeMath;

/**
 *
 * @author mewer
 */
public class Drop {
    public InputStream generateDrop(byte r, byte g, byte b, int cols, int rows) {
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
        
        return GIMUtils.arrayToPng(buf, cols, rows);
    }

    public InputStream generateDropRandBlue(byte r, byte g, byte b, int cols, int rows) {
        int[][][] buf = new int[rows][cols][4];
        
        Random rand = new Random();
        
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
                buf[y][x][2] = rand.nextInt() & 0xFF;
                buf[y][x][3] = a;
            }
        }
        
        return GIMUtils.arrayToPng(buf, cols, rows);
    }
}
