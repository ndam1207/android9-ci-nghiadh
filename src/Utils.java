import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Utils {
    public static Image loadImage(String pathName){
        try {
            return ImageIO.read(new File("resources/"+pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
