/*
 * PicMungerPreviewFrame.java
 *
 * Created on January 11, 2006, 2:54 PM
 */

package com.yellowaxe.picmunger.gui;

import com.yellowaxe.picmunger.PicMunger;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author khuang
 */
public class PicMungerPreviewFrame extends JFrame {

    private static final Log log = LogFactory.getLog(PicMungerPreviewFrame.class);
    private ImagePanel imagePanel;

    /** Creates a new instance of PicMungerPreviewFrame */
    public PicMungerPreviewFrame() {
        super();

        imagePanel = new ImagePanel();

        initialize();
        frameLayout();

        pack();
    }

    private void initialize() {
        setIconImage(PicMungerFrame.getIcon());
        setTitle("Image");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void frameLayout() {
        Container contentPane = getContentPane();

        JScrollPane image = new JScrollPane(imagePanel);
        image.setPreferredSize(new Dimension(500, 500));
        image.setBorder(BorderFactory.createEtchedBorder());

        contentPane.add(image);
    }

    public void displayImageByAbsoluteFilename(String filename) {
        File file = new File(filename);
        ImageFileFilter filter = new ImageFileFilter();
        if (file.isFile() && file.canRead() && filter.accept(file)) {
            try {
                log.debug("start read");
                BufferedImage bi = ImageIO.read(file);
                log.debug("done reading, start display");
                displayImage(bi);
                log.debug("done display");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void displayImage(BufferedImage bi) {
        if (PicMunger.getInstance().isResizePreview()) {
            // resizing will maintain aspect ratio
            double factor = Math.min(
                    imagePanel.getBounds().getWidth()/bi.getWidth(),
                    imagePanel.getBounds().getHeight()/bi.getHeight());
            AffineTransform trans = AffineTransform.getScaleInstance(factor, factor);
            AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BICUBIC);
            BufferedImage newbi = op.filter(bi, null);
            imagePanel.setImage(newbi);
        } else {
            imagePanel.setImage(bi);
        }
    }
}
