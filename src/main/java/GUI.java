import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JTextField textField1;
    public JButton postButton;
    private JComboBox comboBox1;
    public JButton changeButton;
    private JPanel panel;
    private JLabel label1;
    public static volatile boolean ParseError = false;
    public static volatile String post = "";


    GUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(new Dimension(600,400));
        add(panel);
        setVisible(true);


        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private static void parseErrorHandler(){
        //TODO: eror windows generate
        ParseError = false;
    }

    public String getUrl(){
        String url = textField1.getText();

        return url;
    }

}
