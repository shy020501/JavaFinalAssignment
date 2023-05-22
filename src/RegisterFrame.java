import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RegisterFrame extends JFrame {
    private int[] frameSize = {0, 0}; // Stores size of the frame
    private String frameTitle; // Stores name of the frame
    public RegisterFrame(int[] size, String title)
    {
        // Get frame's size and title as parameter and use them for setting the frame
        frameSize[0] = size[0];
        frameSize[1] = size[1];
        frameTitle = title;

        JLabel IDLabel = new JLabel("ID"); // Store label for ID
        // Set bound based on frame size
        IDLabel.setBounds(
                0,
                frameSize[1] * 3 / 10,
                frameSize[0] / 5,
                frameSize[1] / 20
        );
        IDLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, frameSize[0] / 50)); // Create margin to the label
        IDLabel.setFont(new Font("Arial", Font.BOLD, 25)); // Change font
        IDLabel.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right
        IDLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        add(IDLabel);

        JTextField IDField = new JTextField(); // Store text field where the user can input their ID
        // Set bound based on frame size
        IDField.setBounds(
                frameSize[0] / 5,
                frameSize[1] * 3 / 10,
                frameSize[0] * 3 / 5,
                frameSize[1] / 20
        );
        IDField.setFont(new Font("Arial", Font.PLAIN, 20)); // Change font
        add(IDField);

        JLabel PWLabel = new JLabel("PW"); // Store label for PW
        // Set bound based on frame size
        PWLabel.setBounds(
                0,
                frameSize[1] * 4 / 10,
                frameSize[0] / 5,
                frameSize[1] / 20
        );
        PWLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, frameSize[0] / 50)); // Create margin to the label
        PWLabel.setFont(new Font("Arial", Font.BOLD, 25)); // Change font
        PWLabel.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right
        PWLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        add(PWLabel);

        JTextField PWField = new JTextField(); // Store text field where the user can input their PW
        // Set bound based on frame size
        PWField.setBounds(
                frameSize[0] / 5,
                frameSize[1] * 4 / 10,
                frameSize[0] * 3 / 5,
                frameSize[1] / 20
        );
        PWField.setFont(new Font("Arial", Font.PLAIN, 20)); // Change font
        add(PWField);

        JLabel RePWLabel = new JLabel("PW"); // Store label for Re-entering PW
        // Set bound based on frame size
        RePWLabel.setBounds(
                0,
                frameSize[1] * 47 / 100,
                frameSize[0] / 5,
                frameSize[1] / 20
        );
        RePWLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, frameSize[0] / 50)); // Create margin to the label
        RePWLabel.setFont(new Font("Arial", Font.BOLD, 25)); // Change font
        RePWLabel.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right
        RePWLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        add(RePWLabel);

        JTextField RePWField = new JTextField("Re-enter password"); // Store text field where the user can input their PW
        // Set bound based on frame size
        RePWField.setBounds(
                frameSize[0] / 5,
                frameSize[1] * 47 / 100,
                frameSize[0] * 3 / 5,
                frameSize[1] / 20
        );
        RePWField.setFont(new Font("Arial", Font.PLAIN, 20)); // Change font
        RePWField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { // Deletes contents in the text field if the user clicks the field and has not written anything yet
                if(RePWField.getText().equals("Re-enter password"))
                {
                    RePWField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) { // Create the message in the text field if nothing has been entered and user clicks other components
                if(RePWField.getText().equals(""))
                {
                    RePWField.setText("Re-enter password");
                }
            }
        });
        add(RePWField);

        JButton RegisterButton = new JButton("Register"); // Store button for registering
        // Set bound based on frame size
        RegisterButton.setBounds(
                frameSize[0] * 3 / 5,
                frameSize[1] * 54 / 100,
                frameSize[0] / 5,
                frameSize[1] / 25
        );
        RegisterButton.setFont(new Font("Arial", Font.BOLD, 20)); // Change font
        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogInFrame(frameSize, title); // Create register frame
                dispose(); // Get rid of the frame
            }
        });
        add(RegisterButton);

        // Settings for frame
        setTitle(frameTitle);
        setSize(frameSize[0], frameSize[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
    }
}
