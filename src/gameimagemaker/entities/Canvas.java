/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameimagemaker.entities;

/**
 *
 * @author mewer
 */
public class Canvas extends Entity {
    public Canvas(int cols, int rows) {
        super(cols, rows);
    }
    
    public void drawFlatBackground(int r, int g, int b, int a) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                buf[y][x][0] = r;
                buf[y][x][1] = g;
                buf[y][x][2] = b;
                buf[y][x][3] = a;
            }
        }
    }
    
    public void drawEntity(Entity e, int x, int y) {
        
    }
    
    public void setEntity(Entity e, int x, int y) {
        for (int j = 0; j < e.rows && j+y < rows; j++) {
            for (int i = 0; i < e.cols && i+x < cols; i++) {
                buf[j+y][i+x][0] = e.buf[j][i][0];
                buf[j+y][i+x][1] = e.buf[j][i][1];
                buf[j+y][i+x][2] = e.buf[j][i][2];
                buf[j+y][i+x][3] = e.buf[j][i][3];
            }
        }
    }
}
