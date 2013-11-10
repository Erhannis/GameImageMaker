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
