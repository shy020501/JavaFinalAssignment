import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInFrame extends JFrame {

    private int[] frameSize = {0, 0}; // Stores size of the frame
    private String frameTitle; // Stores name of the frame

    public LogInFrame(int[] size, String title) // Constructor for login frame
    {
        // Get frame's size and title as parameter and use them for setting the frame
        frameSize[0] = size[0];
        frameSize[1] = size[1];
        frameTitle = title;

        JLabel titleLabel = new JLabel("<html><body><center>Financial<br> <br>Ledger</center></body></html>"); // Stores label for the title of application
        // Set bound based on frame size
        titleLabel.setBounds(
                0,
                frameSize[1] / 10,
                frameSize[0] / 1,
                frameSize[1] / 5
                );
        titleLabel.setFont(new Font("Arial", Font.BOLD, 35)); // Change font
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // Set horizontal alignment to center so that the title shows in the middle of the screen
        add(titleLabel);

        JLabel IDLabel = new JLabel("ID"); // Store label for ID
        // Set bound based on frame size
        IDLabel.setBounds(
                0,
                frameSize[1] * 4 / 10,
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
                frameSize[1] * 4 / 10,
                frameSize[0] * 3 / 5,
                frameSize[1] / 20
        );
        IDField.setFont(new Font("Arial", Font.PLAIN, 20)); // Change font
        add(IDField);

        JLabel PWLabel = new JLabel("PW"); // Store label for PW
        // Set bound based on frame size
        PWLabel.setBounds(
                0,
                frameSize[1] / 2 ,
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
                frameSize[1] / 2,
                frameSize[0] * 3 / 5,
                frameSize[1] / 20
        );
        PWField.setFont(new Font("Arial", Font.PLAIN, 20)); // Change font
        add(PWField);

        JButton LogInButton = new JButton("Log In"); // Store button for logging in
        // Set bound based on frame size
        LogInButton.setBounds(
                frameSize[0] * 3 / 5,
                frameSize[1] * 57 / 100,
                frameSize[0] / 5,
                frameSize[1] / 25
        );
        LogInButton.setFont(new Font("Arial", Font.BOLD, 20)); // Change font
        LogInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If user exists
                new MainFrame(frameSize, frameTitle);
                dispose();
                // else
                // error log
            }
        });
        add(LogInButton);

        JButton RegisterButton = new JButton("Register"); // Store button for registering
        // Set bound based on frame size
        RegisterButton.setBounds(
                frameSize[0] * 3 / 5,
                frameSize[1] * 63 / 100,
                frameSize[0] / 5,
                frameSize[1] / 25
        );
        RegisterButton.setFont(new Font("Arial", Font.BOLD, 20)); // Change font
        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame(frameSize, title); // Create register frame
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

