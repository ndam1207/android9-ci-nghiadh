import sun.awt.image.PixelConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameWindow extends Frame{
    Image backgroundImage;
    Image planeImage;
    private int planeX = 200;
    private int planeY = 700;

    BufferedImage backBufferImage;
    Graphics backbufferGraphics;

    boolean isUpPressed;
    boolean isDownPressed;
    boolean isLeftPressed;
    boolean isRightPressed;

    public GameWindow(){
        setVisible(true);
        setSize(600,800);
        backBufferImage = new BufferedImage(600,800, BufferedImage.TYPE_INT_ARGB);
        backbufferGraphics = backBufferImage.getGraphics();

        //add Listener
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        //add KeyListener
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT) {
                    isRightPressed = true;
                }
                else if (keyCode == KeyEvent.VK_LEFT) {
                    isLeftPressed = true;
                }
                else if (keyCode == KeyEvent.VK_UP) {
                    isUpPressed = true;
                }
                else if (keyCode == KeyEvent.VK_DOWN) {
                    isDownPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT) {
                    isRightPressed = false;
                }
                else if (keyCode == KeyEvent.VK_LEFT) {
                    isLeftPressed = false;
                }
                else if (keyCode == KeyEvent.VK_UP) {
                    isUpPressed = false;
                }
                else if (keyCode == KeyEvent.VK_DOWN) {
                    isDownPressed = false;
                }
            }
        });


        //Load Images Here
        try {
            backgroundImage = ImageIO.read(new File("resources/background.png"));
            planeImage = ImageIO.read(new File("resources/plane3.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Xu ly Logic
                    int dy=0;
                    int dx=0;
                    if(isUpPressed){
                        dy-=10;
                    }

                    else if(isDownPressed){
                        dy+=10;
                    }
                    else if(isLeftPressed){
                        dx-=10;
                    }
                    else if(isRightPressed){
                        dx+=10;
                    }
                    planeX+=dx;
                    planeY+=dy;

                    //Draw
                    repaint();
                }
            }
        });
        thread.start();
    }



    @Override
    public void update(Graphics g) {
        backbufferGraphics.drawImage(backgroundImage,0,0,600,800,null);
        backbufferGraphics.drawImage(planeImage,planeX,planeY,70,51,null);

        g.drawImage(backBufferImage,0,0,null);
    }
}
