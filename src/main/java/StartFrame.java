import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;

public class StartFrame {

    private JTextField xTextField;
    private JTextField yTextField;
    private JTextField targetFolderPathTextField;
    private JButton targetBrButton;
    private JButton startButton;
    private JTextField saveFilePathTextField;
    private JButton createButton;
    private JLabel resultLabel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panelMain;


    JFrame frame;
    private ImageHandler imageHandler = new ImageHandler();

    public StartFrame() {

        //window initiate
        frame = new JFrame("图片合成器");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        //start
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x;
                int y;
                String targetFolderPath;
                String saveFilePath;
                try {
                    x = Integer.parseInt(xTextField.getText());
                    y = Integer.parseInt(yTextField.getText());
                    targetFolderPath = targetFolderPathTextField.getText();
                    saveFilePath = saveFilePathTextField.getText();
                    imageHandler.createImage(x, y, targetFolderPath, saveFilePath);
                    info("创建成功");
                } catch (NumberFormatException numberFormatException) {
                    error("错误的x,y格式");
                } catch (RuntimeException runtimeException) {
                    error(runtimeException.getMessage());
                }
            }
        });
        targetFolderPathTextField.addVetoableChangeListener(new VetoableChangeListener() {
            @Override
            public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
            }
        });

        //add listener on first text field
        targetFolderPathTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                copy();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                copy();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });


        targetBrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int r = chooser.showOpenDialog(frame);
                if (r == JFileChooser.APPROVE_OPTION) {
                    targetFolderPathTextField.setText(chooser.getSelectedFile().getPath());
                }
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int r = chooser.showOpenDialog(frame);
                if (r == JFileChooser.APPROVE_OPTION) {
                    saveFilePathTextField.setText(chooser.getSelectedFile().getPath());
                }
            }
        });
    }

    public void error(String message) {
        resultLabel.setText(message);
        resultLabel.setForeground(new Color(187, 70, 0));
    }

    public void info(String message) {
        resultLabel.setText(message);
        resultLabel.setForeground(new Color(22, 187, 24));
    }

    public void copy() {
        saveFilePathTextField.setText(targetFolderPathTextField.getText() + File.separator + "temp.png");
    }

}
