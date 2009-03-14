/*
 * PicWorker.java
 *
 * Created on January 12, 2006, 12:25 PM
 */

package com.yellowaxe.picmunger;

import javax.swing.ListModel;

/**
 *
 * @author khuang
 */
public class PicWorker implements Runnable {
    
    private int currentIndex;
    private int totalFiles;
    
    /** Creates a new instance of PicWorker */
    public PicWorker() {
        currentIndex = 0;
        totalFiles = 0;
    }
    
    public void run() {
        ListModel model = PicMunger.getInstance().getFileList().getModel();
        totalFiles = model.getSize();
        
        for (int i = 0; i < model.getSize(); i++) {
            currentIndex = i;
            String filename = (String)model.getElementAt(i);
            PicMunger.getInstance().getPreviewFrame().displayImageByAbsoluteFilename(filename);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }
    
}
