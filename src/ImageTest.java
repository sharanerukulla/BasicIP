import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTest {

	public static void main(String[] args) {
        int width = 640;
        int height = 480;
        //BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
        BufferedImage image = null;
        try
        {
            File input_file = new File("/Users/sharanerukulla/eclipse-workspace/MyTestProject/src/image.jpg"); 
            image = ImageIO.read(input_file);
            System.out.println("Image reading complete.");
            System.out.println("Dimensions of the image are " + image.getWidth() + "x" + image.getHeight() + "." );
        }
        catch(IOException e)
        {
            System.out.println("Error: "+e);
        }
        //Cropping
        BufferedImage cropped_image = image.getSubimage(0, 0, 320, 240);
        //Rotating
        AffineTransform aTransform = new AffineTransform();
        aTransform.rotate(Math.PI/8, image.getWidth()/2, image.getHeight()/2);
        AffineTransformOp op = new AffineTransformOp(aTransform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotated_image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
        op.filter(image, rotated_image);
        //Converting to Grayscale
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);  
        ColorConvertOp ccop = new ColorConvertOp(cs, null);  
        BufferedImage grayscale_image = ccop.filter(image, null);
        //Resizing
        BufferedImage resize_image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(0.75, 0.75);
        AffineTransformOp scaleOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        scaleOp.filter(image, resize_image);
        
        try
        {
            ImageIO.write(image, "jpg", new File("/Users/sharanerukulla/eclipse-workspace/MyTestProject/src/original_image.jpg"));
            ImageIO.write(cropped_image, "jpg", new File("/Users/sharanerukulla/eclipse-workspace/MyTestProject/src/cropped_image.jpg"));
            ImageIO.write(rotated_image, "jpg", new File("/Users/sharanerukulla/eclipse-workspace/MyTestProject/src/rotated_image.jpg"));
            ImageIO.write(grayscale_image, "jpg", new File("/Users/sharanerukulla/eclipse-workspace/MyTestProject/src/grayscale_image.jpg"));
            ImageIO.write(resize_image, "jpg", new File("/Users/sharanerukulla/eclipse-workspace/MyTestProject/src/resize_image.jpg"));
            System.out.println("Image writing complete.");
        }
        catch(IOException e)
        {
            System.out.println("Error: "+e);
        }
    }

}
