/*
 * PicMunger.java
 *
 * Created on January 11, 2006, 9:17 AM
 */

package com.yellowaxe.picmunger;

import com.yellowaxe.picmunger.gui.FileList;
import com.yellowaxe.picmunger.gui.PicMungerFrame;
import com.yellowaxe.picmunger.gui.PicMungerPreviewFrame;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author khuang
 */
public class PicMunger {
    
    private final static Log log = LogFactory.getLog(PicMunger.class);
    
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
    }
    
    static PicMunger instance;
    
    public static PicMunger getInstance() {
        if (instance == null) {
            instance = new PicMunger();
        }
        return instance;
    }
    
    /** Creates a new instance of PicMunger */
    private PicMunger() {
    }
    
    private PicMungerFrame mainFrame;
    private PicMungerPreviewFrame previewFrame;
    private FileList fileList;
    private Dimension outputImageSize;
    private boolean resizePreview;
    private BufferedImage outputImage;

    public PicMungerFrame getMainFrame() { return mainFrame; }
    public void setMainFrame(PicMungerFrame mainFrame) { this.mainFrame = mainFrame; }
    
    public PicMungerPreviewFrame getPreviewFrame() { return previewFrame; }
    public void setPreviewFrame(PicMungerPreviewFrame previewFrame) { this.previewFrame = previewFrame; }
    
    public FileList getFileList() { return fileList; }
    public void setFileList(FileList fileList) { this.fileList = fileList; }

    public Dimension getOutputImageSize() { return outputImageSize; }
    public void setOutputImageSize(Dimension outputImageSize) { this.outputImageSize = outputImageSize; }

    public boolean isResizePreview() { return resizePreview; }
    public void setResizePreview(boolean resizePreview) { this.resizePreview = resizePreview; }

    public BufferedImage getOutputImage() { return outputImage; }
    public void setOutputImage(BufferedImage outputImage) { this.outputImage = outputImage; }

    public void start() {
        mainFrame = new PicMungerFrame();
        previewFrame = new PicMungerPreviewFrame();

        mainFrame.setLocation(0,0);
        
        // place the preview window next to the normal window
        Dimension bounds = mainFrame.getSize();
        previewFrame.setLocation((int)bounds.getWidth(),0);

        mainFrame.setVisible(true);
        previewFrame.setVisible(true);

        log.debug("frame created");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        getInstance().start();
    }

}
