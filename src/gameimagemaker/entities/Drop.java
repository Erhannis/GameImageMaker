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
public class Drop extends Entity {
    /**
     * Coordinates [y][x][x,y,z]
     */
    public double[][] heightMap;
    public double[][][] normalMap;
    
    public Drop(int cols, int rows) {
        super(cols, rows);
        heightMap = new double[rows][cols];
        normalMap = new double[rows][cols][3];
    }
    
    public void drawBase(int r, int g, int b) {
        double limit = 0.97;
        
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                double radius = Math.sqrt(MeMath.sqr(x - (cols / 2.0)) + MeMath.sqr(y - (rows / 2.0)));
                radius /= Math.max(cols / 2.0, rows / 2.0);
                double alpha = (radius * 0.9) + 0.1;
                if (radius >= 1) {
                    alpha = 0;
                } else if (radius >= limit) {
                    alpha *= (1 - radius) / (1 - limit);
                }
                int a = (int)(0xFF * alpha);
                buf[y][x][0] = r;
                buf[y][x][1] = g;
                buf[y][x][2] = b;
                buf[y][x][3] = a;
            }
        }
    }
    
    public void heightMapBase() {
        double r2 = MeMath.sqr(Math.max(cols / 2.0, rows / 2.0));
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                double radius = Math.sqrt(MeMath.sqr(x - (cols / 2.0)) + MeMath.sqr(y - (rows / 2.0)));
                radius /= Math.max(cols / 2.0, rows / 2.0);
                if (radius >= 1) {
                    heightMap[y][x] = 0;
                } else {
                    heightMap[y][x] = Math.sqrt(r2-MeMath.sqr(x)-MeMath.sqr(y));
                }
            }
        }
    }

    /**
     * Currently takes cross product of numerical partial derivatives h(x,y)/dx
     * and h(x,y)/dy to find the normal vector.  h(x,y)/dx = h(x+1,y)-h(x,y).
     */
    public void deriveNormalMap() {
        for (int y = 0; y < rows - 1; y++) {
            for (int x = 0; x < cols - 1; x++) {
                
            }
        }
    }
    
    public void setRandBlue() {
        Random rand = new Random();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                buf[y][x][2] = rand.nextInt() & 0xFF;
            }
        }
    }
    
    public void addRing() {
        double limit = 0.04;
        double offset = -0.00;
        for (int y = 0; y < buf.length; y++) {
            for (int x = 0; x < buf[y].length; x++) {
                double radius = Math.sqrt(MeMath.sqr(x - (buf[y].length / 2.0)) + MeMath.sqr(y - (buf.length / 2.0)));
                radius /= Math.max(buf[y].length / 2.0, buf.length / 2.0);
                if ((1 + offset - limit) <= radius && radius <= (1 + offset + limit)) {
                    buf[y][x][0] *= Math.abs((1 + offset - radius) / limit);
                    buf[y][x][1] *= Math.abs((1 + offset - radius) / limit);
                    buf[y][x][2] *= Math.abs((1 + offset - radius) / limit);
                }
            }
        }
    }
}
