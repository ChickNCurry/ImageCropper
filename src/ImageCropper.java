import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCropper extends JFrame {

    final private String DESKTOP = "C:/Users/maxib/Desktop/";
    JPanel panel;
    JButton inputButton;
    JButton cropButton;
    JFileChooser inputChooser;
    File imageFile;
    BufferedImage image;

    public ImageCropper() {
        this.initFrame();
        panel = new JPanel();

        inputChooser = new JFileChooser();
        inputChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        inputChooser.setFileFilter(new FileNameExtensionFilter("PNG", "png"));
        inputChooser.setCurrentDirectory(new File(DESKTOP));

        inputButton = new JButton("Choose Image");
        inputButton.addActionListener(e -> {
            if(inputChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                imageFile = inputChooser.getSelectedFile();
            }
        });

        cropButton = new JButton("Crop Image");
        cropButton.addActionListener(e -> {
            try {
                image = ImageIO.read(imageFile);
                BufferedImage upperSubImage = image.getSubimage(0, 0, 64, 32);
                BufferedImage lowerSubImage = image.getSubimage(0, 32, 64, 32);
                String name = imageFile.getName().replaceFirst("[.][^.]+$", "");
                File upperSubImageFile = new File( DESKTOP + name + "_layer_1.png");
                File lowerSubImageFile = new File(DESKTOP + name + "_layer_2.png");
                ImageIO.write(upperSubImage, "png", upperSubImageFile);
                ImageIO.write(lowerSubImage, "png", lowerSubImageFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setLayout(new BorderLayout(15, 15));
        panel.add(inputButton, BorderLayout.NORTH);
        panel.add(cropButton, BorderLayout.SOUTH);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    private void initFrame() {
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - this.getSize().width/2, dim.height/2 - this.getSize().height/2);
    }

    public static void main(String[] args) {
        ImageCropper frame = new ImageCropper();
    }

}
