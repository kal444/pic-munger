/*
 * FileList.java
 *
 * Created on January 11, 2006, 12:32 PM
 */

package com.yellowaxe.picmunger.gui;

import com.yellowaxe.picmunger.PicMunger;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author khuang
 */
public class FileList extends JList {
    
    private static final Log log = LogFactory.getLog(FileList.class);

    private int currentIndex = -1;
    
    /** Creates a new instance of FileList */
    public FileList () {
        super();
        initialize();
    }

    public FileList(File [] files) {
        super();
        initialize();

        addListData(files);
    }
    
    private void initialize() {
        setModel(new DefaultListModel());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        setDragEnabled(true);
        setTransferHandler(new FileListFileTransferHandler(this));
        
        addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                final FileList list = (FileList)e.getSource();
                if (list.getSelectedIndex() != list.getCurrentIndex()) {
                    list.setCurrentIndex(list.getSelectedIndex());
                    if (list.getModel().getSize() > 0) {
                        log.debug("  user picked: " + list.getModel().getElementAt(list.getCurrentIndex()));
                        final PicMunger pm = PicMunger.getInstance();
                        if (pm.getPreviewFrame() != null) {
                            
                            Thread t = new Thread(new Runnable() {
                                public void run() {
                                    pm.getPreviewFrame().displayImageByAbsoluteFilename((String)list.getModel().getElementAt(list.getCurrentIndex()));
                                }
                            });
                            t.start();
                        }
                    }
                }
            }
        });
    }
    
    public void setListData(File [] files) {
        addListData(files);
    }
    
    public void addListData(File [] files) {
        for (File f : files) {
            addListData(f);
        }
    }
    
    public void addListData(File f) {
        DefaultListModel model = (DefaultListModel) getModel();
        model.addElement(f.getAbsolutePath());
    }
    
    public void clear() {
        DefaultListModel model = (DefaultListModel) getModel();
        model.clear();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

}
