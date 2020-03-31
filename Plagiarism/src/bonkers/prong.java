package bonkers;

import bonkers.GUI.GUI;
import bonkers.GUI.TextAreaOutputStream;
import bonkers.GUI.outWindow;

import java.io.PrintStream;

public class prong
{
    public static void main (String[] args)
    {
        GUI gui = new GUI();

        PrintStream outStream = new PrintStream(new TextAreaOutputStream(outWindow.j1));

        System.setOut(outStream);
        System.setErr(outStream);
    }
}
