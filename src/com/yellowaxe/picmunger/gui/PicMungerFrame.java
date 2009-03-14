/*
 * PicMungerFrame.java
 *
 * Created on January 11, 2006, 9:29 AM
 */

package com.yellowaxe.picmunger.gui;

import com.yellowaxe.picmunger.PicMunger;
import com.yellowaxe.picmunger.PicWorker;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author khuang
 */
public class PicMungerFrame extends JFrame {

    private final static Log log = LogFactory.getLog(PicMungerFrame.class);

    /** Creates a new instance of PicMungerFrame */
    public PicMungerFrame() {
        super();

        initialize();
        frameLayout();

        pack();
    }

    private void initialize() {
        setIconImage(getIcon());
        setTitle("PicMunger");

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void frameLayout() {
        JButton open = new JButton("Add File(s)");
        open.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                log.debug(source.getText() + " has been clicked");

                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new ImageFileFilter());
                chooser.setMultiSelectionEnabled(true);
                int retVal = chooser.showOpenDialog(source.getParent());
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    log.debug("user picked some files");

                    PicMunger.getInstance().getFileList()
                    .addListData(chooser.getSelectedFiles());
                }
            }
        });

        JButton clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                log.debug(source.getText() + " has been clicked");

                PicMunger.getInstance().getFileList().clear();
            }
        });

        JButton run = new JButton("Run");
        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                log.debug(source.getText() + " has been clicked");

                Thread t = new Thread(new PicWorker());
                t.start();

            }
        });

        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                log.debug(source.getText() + " has been clicked");

                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(open);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(clear);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(run);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(exit);
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel outputLabel = new JLabel("result size: ");
        JLabel heightLabel = new JLabel("Height");
        JTextField heightField = new JTextField("500", 10);
        heightField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {update(e);}
            public void insertUpdate(DocumentEvent e) {update(e);}
            public void removeUpdate(DocumentEvent e) {update(e);}
            private void update(DocumentEvent e) {
                Document d = e.getDocument();
                try {
                    Integer i = null;
                    try {
                        i = Integer.parseInt(d.getText(0, d.getLength()).trim());
                    } catch (NumberFormatException ex) {
                    }
                    int height = 500;
                    if (i != null) {
                        height = i;
                    }
                    Dimension dim = PicMunger.getInstance().getOutputImageSize();
                    dim.setSize(dim.getWidth(), height);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JLabel widthLabel = new JLabel("Width");
        JTextField widthField = new JTextField("500", 10);
        widthField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {update(e);}
            public void insertUpdate(DocumentEvent e) {update(e);}
            public void removeUpdate(DocumentEvent e) {update(e);}
            private void update(DocumentEvent e) {
                Document d = e.getDocument();
                try {
                    Integer i = null;
                    try {
                        i = Integer.parseInt(d.getText(0, d.getLength()).trim());
                    } catch (NumberFormatException ex) {
                    }
                    int width = 500;
                    if (i != null) {
                        width = i;
                    }
                    Dimension dim = PicMunger.getInstance().getOutputImageSize();
                    dim.setSize(width, dim.getHeight());
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });
        PicMunger.getInstance().setOutputImageSize(new Dimension(500, 500));
        JCheckBox resizeCheck = new JCheckBox("resize", true);
        resizeCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PicMunger.getInstance().setResizePreview(((JCheckBox)e.getSource()).isSelected());
            }
        });
        PicMunger.getInstance().setResizePreview(true);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
        inputPanel.add(outputLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPanel.add(heightLabel);
        inputPanel.add(heightField);
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPanel.add(widthLabel);
        inputPanel.add(widthField);
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPanel.add(resizeCheck);
        inputPanel.add(Box.createHorizontalGlue());
        inputPanel.setAlignmentX(LEFT_ALIGNMENT);

        if (PicMunger.getInstance().getFileList() == null) {
            PicMunger.getInstance().setFileList(new FileList());
        }

        JScrollPane filesPanel = new JScrollPane(PicMunger.getInstance().getFileList());
        filesPanel.setPreferredSize(new Dimension(500, 400));
        filesPanel.setAlignmentX(LEFT_ALIGNMENT);
        filesPanel.setBorder(BorderFactory.createTitledBorder("Files to process"));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

        contentPane.add(buttonPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0,5)));
        contentPane.add(inputPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0,5)));
        contentPane.add(filesPanel);
    }

    public static Image getIcon() {
        URL iconURL = PicMungerFrame.class.getResource("images/icon.png");
        if (iconURL != null) {
            log.debug("icon URL: " + iconURL);
            return new ImageIcon(iconURL).getImage();
        } else {
            log.debug("no icon found");
            return null;
        }
    }

}
