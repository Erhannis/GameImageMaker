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
                radius = radius * radius * radius;
                double alpha = (radius * 0.7) + 0.3;
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
        double r = Math.max(cols / 2.0, rows / 2.0);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                double radius = Math.sqrt(MeMath.sqr(x - (cols / 2.0)) + MeMath.sqr(y - (rows / 2.0)));
                radius /= Math.max(cols / 2.0, rows / 2.0);
                if (radius >= 1) {
                    heightMap[y][x] = 0;
                } else {
                    heightMap[y][x] = Math.sqrt(MeMath.sqr(r)-MeMath.sqr(x-r)-MeMath.sqr(y-r));
                }
            }
        }
    }
    
    public void scaleHeightMap(double s) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                heightMap[y][x] *= s;
            }
        }
    }

    /**
     * Currently takes Up minus gradient with numerical partial derivatives
     * h(x,y)/dx and h(x,y)/dy to find the normal vector.
     * n = {0,0,1} - h(x,y)/dx - h(x,y)/dy.
     * h(x,y)/dx = h(x+1,y)-h(x,y).
     */
    public void deriveNormalMap() {
        for (int y = 0; y < rows - 1; y++) {
            for (int x = 0; x < cols - 1; x++) {
                double hx = heightMap[y][x+1] - heightMap[y][x];
                double hy = heightMap[y+1][x] - heightMap[y][x];
                normalMap[y][x][0] = -hx;
                normalMap[y][x][1] = -hy;
                normalMap[y][x][2] = 1;
                MeMath.normalizeVectorIP(normalMap[y][x]);
            }
        }
    }
    
    public void addSheen(double[] lightSource, double angle, int r, int g, int b, int a) {
        double[] up = {0, 0, 1};
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                double theta = MeMath.vectorAngle(MeMath.vectorReflect(up, normalMap[y][x]), lightSource);
                if (theta <= angle) {
                    double a1 = buf[y][x][3] / 255.0;
                    double a2 = (a / 255.0) * Math.pow(1 - (theta / angle),0.8);
                    buf[y][x][0] = (int)(((1-a2)*buf[y][x][0]) + (a2*r));
                    buf[y][x][1] = (int)(((1-a2)*buf[y][x][1]) + (a2*g));
                    buf[y][x][2] = (int)(((1-a2)*buf[y][x][2]) + (a2*b));
                    buf[y][x][3] = (int)(0xFF * (a1 + a2 - (a1*a2)));
                }
            }
        }
    }
    
    public void addSheen() {
        for (double i = 0; i < 1; i += 0.1) {
            addSheen(new double[]{-0.6,0.5 - i,0.4}, 0.5, 0xFF, 0xFF, 0xFF, 0x10);
        }
        addSheen(new double[]{0.6,0.4,1}, 0.25, 0xFF, 0xFF, 0xFF, 0x40);
    }
    
    public void addReflectionRing() {
        double maxAlpha = 0.5;
        double limit = 0.2;
        double offset = -0.00;
        for (int y = 0; y < buf.length; y++) {
            for (int x = 0; x < buf[y].length; x++) {
                double radius = Math.sqrt(MeMath.sqr(x - (buf[y].length / 2.0)) + MeMath.sqr(y - (buf.length / 2.0)));
                radius /= Math.max(buf[y].length / 2.0, buf.length / 2.0);
                if ((1 + offset - limit) <= radius && radius <= (1 + offset + limit)) {
                    double f = Math.abs((1 + offset - radius) / limit) * maxAlpha;
                    buf[y][x][0] = (int)(((buf[y][x][0] * ((1 - maxAlpha) + f)) + (0xFF * (maxAlpha - f))) / 1);
                    buf[y][x][1] = (int)(((buf[y][x][1] * ((1 - maxAlpha) + f)) + (0xFF * (maxAlpha - f))) / 1);
                    buf[y][x][2] = (int)(((buf[y][x][2] * ((1 - maxAlpha) + f)) + (0xFF * (maxAlpha - f))) / 1);
                }
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
