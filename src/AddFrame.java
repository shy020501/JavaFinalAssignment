import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddFrame extends JFrame {
    protected int[] frameSize = {0, 0}; // Stores size of the frame
    protected String frameTitle; // Stores name of the frame
    protected String userID; // Stores ID of the user so that the folder could be accessed
    protected Color themeColor = new Color(240, 135, 132);
    private int isSpending; // 0: Saving, 1: Spending
    protected JPanel headerPanel = new JPanel(); // Store buttons for choosing if the record is spending or saving
    protected JButton savingButton; // Store button for moving on to saving screen
    protected JButton spendingButton; // Store button for moving on to spending screen

    protected JTextField dateField = new JTextField(); // Text field for inputting date
    protected JTextField descriptionField = new JTextField(); // Text field for inputting description of the record
    protected JTextField amountField = new JTextField(); // Text field for inputting amount of money spent / saved

    public void setFieldText(String date, String description, String amount) // Setter for setting text of text fields in spending panel
    {
        dateField.setText(date);
        descriptionField.setText(description);
        amountField.setText(amount);
    }

    protected boolean isValidDate(String date)
    {
        boolean valid = false;
        String regex = "^(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])/(\\d{4})$"; // Format of date (mm/dd/yyyy)
        Pattern pat = Pattern.compile(regex); // Compile regular expression to pattern
        Matcher mat = pat.matcher(date); // Check if date pattern matches
        if(mat.matches()) // If so, change valid to true
        {
            valid = true;
        }
        return valid;
    }

    protected boolean isValidDigit(String amount)
    {
        boolean valid = false;
        String regex = "[0-9]+"; // Format of amount (only digits)
        Pattern pat = Pattern.compile(regex); // Compile regular expression to pattern
        Matcher mat = pat.matcher(amount); // Check if date pattern matches
        if(mat.matches()) // If so, change valid to true
        {
            valid = true;
        }
        return valid;
    }

    public AddFrame(int[] size, String title, String ID)
    {
        // Get frame's size, title, and userID as parameter and use them for setting the frame
        frameSize[0] = size[0];
        frameSize[1] = size[1];
        frameTitle = title;
        userID = ID;

        // Set bounds based on frame size
        headerPanel.setBounds(
                0,
                0,
                frameSize[0] * 193 / 200,
                frameSize[1] / 10
        );
        headerPanel.setBackground(themeColor); // Set background to theme color
        headerPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        add(headerPanel);

        spendingButton = new JButton("Spending"); // Button for spending option
        // Set bounds based on frame size
        spendingButton.setBounds(
                frameSize[0] * 2 / 10,
                frameSize[1] / 40,
                frameSize[0] * 3 / 10,
                frameSize[1] / 20
        );
        spendingButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        spendingButton.setBackground(Color.white); // Set background color of button to white
        spendingButton.setBorder(BorderFactory.createLineBorder(themeColor)); // Set line border of the button with theme color
        spendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isSpending = 1; // Change the state to spending

                new AddSpendingFrame(frameSize, frameTitle, ID); // Show spending frame
                dispose();
            }
        });
        headerPanel.add(spendingButton);

        savingButton = new JButton("Saving"); // Button for saving option
        // Set bounds based on frame size
        savingButton.setBounds(
                frameSize[0] / 2,
                frameSize[1] / 40,
                frameSize[0] * 3 / 10,
                frameSize[1] / 20
        );
        savingButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        savingButton.setBackground(Color.white); // Set background color of button to white
        savingButton.setBorder(BorderFactory.createLineBorder(themeColor)); // Set line border of the button with theme color
        savingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isSpending = 0; // Change the state to saving

                new AddSavingFrame(frameSize, frameTitle, ID); // Show saving frame
                dispose();
            }
        });
        headerPanel.add(savingButton);

        JButton backButton = new JButton("<"); // Stores button for moving on to the previous page
        // Set bound based on frame size
        backButton.setBounds(
                frameSize[1] * 12 / 340,
                frameSize[1] * 7 / 340,
                frameSize[1] / 17,
                frameSize[1] / 17
        );
        backButton.setFont(new Font("Arial", Font.BOLD, 20)); // Change font
        backButton.setBackground(themeColor); // Set background to white
        backButton.setForeground(Color.white); // Set text color to white
        backButton.setBorder(BorderFactory.createLineBorder(themeColor)); // Set border of the button to theme color
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame(frameSize, frameTitle, ID);
                dispose();
            }
        });
        headerPanel.add(backButton);

        // Get current date
        String month = java.time.LocalDate.now().toString().split("-")[1];
        String year = java.time.LocalDate.now().toString().split("-")[0];
        String day = java.time.LocalDate.now().toString().split("-")[2];
        String currentDate = month + "/" + day + "/" + year; // Save date in mm/dd/yyyy form

        dateField.setText(currentDate); // Set default value of date field to current date
        dateField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { // Deletes contents in the text field if the user clicks the field and has not written anything yet
                if(dateField.getText().equals(currentDate))
                {
                    dateField.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) { // Show current date in the text field if nothing has been entered and user clicks other components
                if(dateField.getText().equals(""))
                {
                    dateField.setText(currentDate);
                }
            }
        });

        // Settings for frame
        setTitle(frameTitle);
        setSize(frameSize[0], frameSize[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
    }
}
