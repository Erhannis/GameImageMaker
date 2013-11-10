/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameimagemaker.entities;

import mathnstuff.MeMath;

/**
 *
 * @author mewer
 */
public class Mote extends Entity {
    public Mote(int cols, int rows) {
        super(cols, rows);
    }
    
    public void drawBase(int r, int g, int b) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                double radius = Math.sqrt(MeMath.sqr(x - (cols / 2.0)) + MeMath.sqr(y - (rows / 2.0)));
                radius /= Math.max(cols / 2.0, rows / 2.0);
                double alpha = 1 - radius;
                if (alpha < 0) {
                    alpha = 0;
                }
                int a = (int)(0xFF * alpha);
                buf[y][x][0] = r;
                buf[y][x][1] = g;
                buf[y][x][2] = b;
                buf[y][x][3] = a;
            }
        }
    }
}
