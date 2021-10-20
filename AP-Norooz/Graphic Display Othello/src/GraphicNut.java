import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GraphicNut extends Rectangle {

    private Image nutPicture;
    private char color;

    public GraphicNut(int x,int y,int width,int height,String pictureFileName,char color){
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.color = color;

        try{
            nutPicture = ImageIO.read(new File("D:\\Java Programing Files/AP-Norooz/Graphic Display Othello/src/Image/"+pictureFileName));
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void draw(Graphics g, Component C){
        g.drawImage(nutPicture, x, y, width, height, C);
    }

    public char getColor() {
        return color;
    }
}
