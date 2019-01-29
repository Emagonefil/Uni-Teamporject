package game;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.File;



public class Renderer {

    static Image img;
    static {
        img = loadImage("Resources/img/sprites_without_border.png");
    }

    public static Image loadImage(String path) {
        File file = new File(path);
        System.out.println("Loading Sprite sheet " + file.exists());
        String imagePath = file.getAbsolutePath();
        System.out.println("Before Imagepath " + imagePath);
        if (File.separatorChar == '\\') {
            // From Windows to Linux/Mac
            imagePath=imagePath.replace('/', File.separatorChar);
            imagePath = imagePath.replace("\\", "\\\\");
        } else {
            // From Linux/Mac to Windows
            imagePath=imagePath.replace('\\', File.separatorChar);

        }
        imagePath="file:"+imagePath;
        System.out.println("After Imagepath " + imagePath);

        return new Image(imagePath);
    }
    public static Image crop(Image img,int x,int y,int w,int h){
        PixelReader reader = img.getPixelReader();
        WritableImage newImage = new WritableImage(reader, x, y, w, h);
        return newImage;
    }
}