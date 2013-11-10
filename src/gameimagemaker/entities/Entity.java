/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameimagemaker.entities;

import gameimagemaker.GIMUtils;
import java.io.InputStream;

/**
 *
 * @author mewer
 */
public class Entity {
    public int cols = 0;
    public int rows = 0;
    public int[][][] buf = null;

    public Entity(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        buf = new int[rows][cols][4];
    }
    
    public InputStream generate() {
        return GIMUtils.arrayToPng(buf, cols, rows);
    }
}
