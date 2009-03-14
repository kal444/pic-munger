/*
 * FileListFileTransferHandler.java
 *
 * Created on January 11, 2006, 2:15 PM
 */

package com.yellowaxe.picmunger.gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author khuang
 *
 *  this can only handle the DnD operation for my own FileList
 */
public class FileListFileTransferHandler extends TransferHandler {
    
    private static final Log log = LogFactory.getLog(FileListFileTransferHandler.class);
    
    private DataFlavor flavor;
    private FileList fileList;
    
    /** Creates a new instance of FileListFileTransferHandler */
    public FileListFileTransferHandler(FileList fileList) {
        this.fileList = fileList;
        flavor = DataFlavor.javaFileListFlavor;
    }

    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
        for (DataFlavor f : transferFlavors) {
            if (flavor.equals(f)) {
                return true;
            }
        }
        return false;
    }

    public boolean importData(JComponent comp, Transferable t) {
        if (!canImport(comp, t.getTransferDataFlavors())) {
            return false;
        }

        try {
            List<File> files = (List<File>) t.getTransferData(flavor);
            
            ImageFileFilter filter = new ImageFileFilter();
            
            for (File f : files) {
                if (filter.accept(f) && f.exists()) {
                    fileList.addListData(f);
                }
            }

            return true;

        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return false;
    }
    
}
