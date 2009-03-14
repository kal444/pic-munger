/*
 * ImagePanel.java
 *
 * Created on January 11, 2006, 3:23 PM
 */

package com.yellowaxe.picmunger.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author khuang
 */
public class ImagePanel extends JPanel {
    
    private static final Log log = LogFactory.getLog(ImagePanel.class);
    
    private BufferedImage image;
    
    /** Creates a new instance of ImagePanel */
    public ImagePanel() {
        super();
    }
    
    public void paint(Graphics g) {
        if (image != null) {
            Rectangle bounds = this.getBounds();
            g.clearRect(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
            g.drawImage(image, 0, 0, this);
        }
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        revalidate();
        repaint();
    }

    public BufferedImage getImage() {
        return image;
    }
    
    public void outputValues() {
        if (image != null) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int i = image.getRGB(x,y);

                    int a = i>>24 & 0xFF;
                    int r = i>>16 & 0xFF;
                    int g = i>>8  & 0xFF;
                    int b = i     & 0xFF;

                    System.out.printf("[%1$x,%2$x,%3$x(%4$x)]", r, g, b, a);
                }
                System.out.println();
            }
        }
    }

}
