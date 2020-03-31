package bonkers.GUI;

import bonkers.fileReader.Processfile;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class outWindow extends JFrame
{
    public static JTextArea
            j1 = new JTextArea(16, 90);

    private JScrollPane
            scroll = new JScrollPane(j1);

    public outWindow()
    {
        super("Output");
        j1.setBorder(new TitledBorder(new EtchedBorder(), "Output Analysis" ));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(50, 50));
        setSize(700,350);
        setLocationRelativeTo(null);

        j1.setEditable(false);
        j1.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(100, 100);

        add(scroll);
        addWindowListener(new WindowEventHandler());

        setVisible(true);
        setResizable(false);
    }
}

class WindowEventHandler extends WindowAdapter
{
    public void windowClosing(WindowEvent ev)
    {
        int dialogButton = JOptionPane.showConfirmDialog(null, "Would you like to save this analysis?", "Save Dialog", JOptionPane.YES_NO_OPTION);
        if (dialogButton == JOptionPane.YES_OPTION)
        {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showSaveDialog(null);

            if (r == JFileChooser.APPROVE_OPTION)
            {
                Processfile.writeStatistics(j.getSelectedFile().getAbsolutePath());
            }
        }
    }
}

