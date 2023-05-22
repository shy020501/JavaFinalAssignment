import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFrame extends JFrame {
    private int[] frameSize = {0, 0}; // Stores size of the frame
    private String frameTitle; // Stores name of the frame

    private boolean isValidID(String ID)
    {
        boolean valid = false;
        String regex = "[a-zA-z0-9]{8,12}"; // Format of user ID (8-12 characters and numbers)
        Pattern pat = Pattern.compile(regex); // Compile regular expression to pattern
        Matcher mat = pat.matcher(ID); // Check if pattern matches the user input
        if(mat.matches()) // If so,
        {
            valid = true; // Set valid to true
        }
        return valid;
    }

    private boolean isValidPW(String PW)
    {
        boolean valid = false;
        // Format of user PW (8-12 characters and numbers, at least 1 alphabet/number/special characters)
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,12}$";
        Pattern pat = Pattern.compile(regex); // Compile regular expression to pattern
        Matcher mat = pat.matcher(PW); // Check if pattern matches the user input
        if(mat.matches()) // If so,
        {
            valid = true; // Set valid to true
        }
        return valid;
    }

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

        JPasswordField PWField = new JPasswordField(); // Store text field where the user can input their PW
        // Set bound based on frame size
        PWField.setBounds(
                frameSize[0] / 5,
                frameSize[1] * 4 / 10,
                frameSize[0] * 3 / 5,
                frameSize[1] / 20
        );
        PWField.setFont(new Font("Arial", Font.PLAIN, 20)); // Change font
        PWField.setEchoChar('•'); // Hide password
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

        JPasswordField RePWField = new JPasswordField("Re-enter password"); // Store text field where the user can input their PW
        // Set bound based on frame size
        RePWField.setBounds(
                frameSize[0] / 5,
                frameSize[1] * 47 / 100,
                frameSize[0] * 3 / 5,
                frameSize[1] / 20
        );
        RePWField.setFont(new Font("Arial", Font.PLAIN, 20)); // Change font
        RePWField.setEchoChar('\u0000'); // Set password visible so that instruction is shown on the field
        RePWField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { // Deletes contents in the text field if the user clicks the field and has not written anything yet
                if(RePWField.getText().equals("Re-enter password"))
                {
                    RePWField.setText("");
                    RePWField.setEchoChar('•'); // Hide password
                }
            }
            @Override
            public void focusLost(FocusEvent e) { // Create the message in the text field if nothing has been entered and user clicks other components
                if(RePWField.getText().equals(""))
                {
                    RePWField.setText("Re-enter password");
                    RePWField.setEchoChar('\u0000'); // Set password visible so that instruction is shown on the field
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
                try {
                    if(!isValidID(IDField.getText())) {
                        throw new InvalidInputException(); // Throw exception if user input (ID) is not valid
                    }
                } catch (InvalidInputException ex) {
                    JOptionPane.showMessageDialog(null, "User ID must be 8-12 characters/numbers!", "ID Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // If user ID is in valid format
                File dir = new File("src/userInfo"); // Get folder where users' information is stored
                String[] users = dir.list(); // Get list of users' ID (directories)
                boolean userExists = false; // Used to check if user ID exists
                for(String user : users) // Loop through users' ID
                {
                    if(user.equals(IDField.getText())) // If ID already exists,
                    {
                        userExists = true; // Turn userExist variable to true
                        break;
                    }
                }
                if(!userExists) // If user ID doesn't exist, create directory and text file that contains user's password
                {
                    try {
                        if(!isValidPW(PWField.getText())) {
                            throw new InvalidInputException(); // Throw exception if user input (ID) is not valid
                        }
                    } catch (InvalidInputException ex) {
                        String errorMsg = "1. Password must be between 8-12 characters\n2. Password must contain at least 1 alphabet/number\n3. Password must contain at least 1 special character";
                        JOptionPane.showMessageDialog(null, errorMsg, "Password Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if(PWField.getText().equals(RePWField.getText())) // If user entered PW correctly twice
                    {
                        String path = "src/userInfo/" + IDField.getText();
                        try {
                            Files.createDirectory(Paths.get(path));
                        } catch (IOException ex) {
                            System.err.println("Failed to create directory!" + ex.getMessage());
                        }

                        try {
                            // Create file to store password
                            path = path + "/password.txt";
                            Files.createFile(Paths.get(path));

                            // Write the password in file
                            FileOutputStream fileOutputStream = new FileOutputStream(path);
                            PrintWriter writer = new PrintWriter(fileOutputStream);
                            writer.println(PWField.getText());
                            writer.close();
                            fileOutputStream.close();
                        } catch (IOException ex) {
                            System.err.println("Failed to create file!" + ex.getMessage());
                        }
                    }
                    else // If user did not enter two passwords correctly
                    {
                        JOptionPane.showMessageDialog(null, "Passwords are not the same!", "Password Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                else // If user entered an existing ID
                {
                    JOptionPane.showMessageDialog(null, "ID already exists!", "ID Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
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
