/*
 * ImageFileFilter.java
 *
 * Created on January 11, 2006, 12:11 PM
 */

package com.yellowaxe.picmunger.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author khuang
 */
public class ImageFileFilter extends FileFilter {
    
    private static final Log log = LogFactory.getLog(ImageFileFilter.class);

    // a list of all the extensions that PicMunger accepts
    private static final String[] filetypes = 
    {
            "bmp", "jpg", "jpeg", "png", "gif"
    };
    

    // this is the description that shows up in the file chooser type line
    public String getDescription() {
        return "Image Files";
    }
    
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String filename = f.getName();
        log.debug("    check against file filter for: " + filename);
        
        // check the lowercase filename with all the supported filetypes
        for (String s : filetypes) {
            if (filename.toLowerCase().endsWith("."+s)) {
                return true;
            }
        }
        return false;
    }

}
