package bonkers.GUI;

import bonkers.fileReader.Processfile;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener
{
    private JLabel
            l1 = new JLabel("File 1:"),
            l2 = new JLabel("File 2:"),
            l3 = new JLabel(),
            l4 = new JLabel(),
            l5 = new JLabel();

    private static String
            f1Name = null,
            f2Name = null;

    private JButton
            f1 = new JButton("Browse"),
            f2 = new JButton("Browse"),
            execute = new JButton("Run");

    public GUI()
    {
        super("Plagiarism Detector");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,150);
        setLocationRelativeTo(null);

        f1.addActionListener(this);
        f2.addActionListener(this);
        execute.addActionListener(this);

        add(l1);
        add(f1);
        add(l2);
        add(f2);
        add(execute);
        add(l3);
        add(l4);
        add(l5);

        setVisible(true);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent ev)
    {
        if (f1 == ev.getSource())
        {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION)
            {
                l3.setText("File 1: " + j.getSelectedFile().getAbsolutePath());
                f1Name = j.getSelectedFile().getAbsolutePath();
            }
        }

        if (f2 == ev.getSource())
        {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION)
            {
                l4.setText("File 2: " + j.getSelectedFile().getAbsolutePath());
                f2Name = j.getSelectedFile().getAbsolutePath();
            }
        }

        if (execute == ev.getSource())
        {
            Processfile.run(f1Name, f2Name);
            if (!Processfile.outputCreated)
            {
                l5.setText("Error creating output.");
            }
            else
            {
                outWindow out = new outWindow();
            }
        }
    }
}
