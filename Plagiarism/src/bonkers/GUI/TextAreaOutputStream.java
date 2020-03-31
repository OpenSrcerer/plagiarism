package bonkers.GUI;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class TextAreaOutputStream extends OutputStream {
    private JTextArea jTextArea1;

    public TextAreaOutputStream(JTextArea textArea)
    {
        this.jTextArea1 = textArea;
    }

    public void write (int b)
    {
        jTextArea1.append(String.valueOf((char)b));
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }

    public void write(char[] cbuf, int off, int len) throws IOException
    {
        jTextArea1.append(new String(cbuf, off, len));
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }
}