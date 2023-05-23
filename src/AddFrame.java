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
import java.io.FileWriter;

public class AddFrame extends JFrame {
    private int[] frameSize = {0, 0}; // Stores size of the frame
    private String frameTitle; // Stores name of the frame
    String userID; // Stores ID of the user so that the folder could be accessed
    private Color themeColor = new Color(240, 135, 132);
    private int isSpending = 1; // 0: Saving, 1: Spending

    private JButton savingButton; // Store button for moving on to saving screen
    private JButton spendingButton; // Store button for moving on to spending screen
    private JPanel savingPanel; // Store panel that has components for saving record
    private JPanel spendingPanel; // Store panel that has components for spending record
    // Store categories for spending record
    private String[] categories = {"Food", "Transport", "Entertainment", "Clothes", "Healthcare", "Education", "Housing", "Condolences", "Etc"};

    private boolean isValidDate(String date)
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

    private boolean isValidDigit(String amount)
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

        spendingPanel = new JPanel(); // Store components for spending record
        spendingPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        spendingPanel.setBounds(
                0,
                frameSize[1] / 10,
                frameSize[0] * 193 / 200,
                frameSize[1] * 9 / 10
        );

        JLabel dateLabel1 = new JLabel("Date"); // Label for displaying date
        // Set bound based on frame size
        dateLabel1.setBounds(
                frameSize[0] / 10,
                frameSize[1] / 10,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        dateLabel1.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        dateLabel1.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right

        JTextField dateField1 = new JTextField(); // Text field for entering date
        // Set bound based on frame size
        dateField1.setBounds(
                frameSize[0] * 7 / 20,
                frameSize[1] * 23 / 200,
                frameSize[0] * 27 / 60,
                frameSize[1] / 25
        );
        dateField1.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel categoryLabel = new JLabel("Category"); // Label for displaying category
        // Set bound based on frame size
        categoryLabel.setBounds(
                frameSize[0] / 10,
                frameSize[1] * 2 / 10 ,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        categoryLabel.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right

        JComboBox categoryBox = new JComboBox(categories); // Text field for entering category
        // Set bound based on frame size
        categoryBox.setBounds(
                frameSize[0] * 7 / 20,
                frameSize[1] * 23 / 200 + frameSize[1] / 10,
                frameSize[0] * 27 / 60,
                frameSize[1] / 25
        );
        categoryBox.setFont(new Font("Arial", Font.PLAIN, 20)); // Set Font

        JLabel descriptionLabel1 = new JLabel("Description"); // Label for displaying description
        // Set bound based on frame size
        descriptionLabel1.setBounds(
                frameSize[0] / 10,
                frameSize[1] * 3 / 10 ,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        descriptionLabel1.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        descriptionLabel1.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right

        JTextField descriptionField1 = new JTextField(); // Text field for entering description
        // Set bound based on frame size
        descriptionField1.setBounds(
                frameSize[0] * 7 / 20,
                frameSize[1] * 23 / 200 + frameSize[1] * 2 / 10,
                frameSize[0] * 27 / 60,
                frameSize[1] / 25
        );
        descriptionField1.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font

        JLabel amountLabel1 = new JLabel("Amount"); // Label for displaying amount
        // Set bound based on frame size
        amountLabel1.setBounds(
                frameSize[0] / 10,
                frameSize[1] * 4 / 10 ,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        amountLabel1.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        amountLabel1.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right

        JTextField amountField1 = new JTextField(); // Text field for entering amount
        // Set bound based on frame size
        amountField1.setBounds(
                frameSize[0] * 7 / 20,
                frameSize[1] * 23 / 200 + frameSize[1] * 3 / 10,
                frameSize[0] * 27 / 60,
                frameSize[1] / 25
        );
        amountField1.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font

        JButton saveButton1 = new JButton("Save"); // Store button for saving the record
        // Set bound based on frame size
        saveButton1.setBounds(
                frameSize[0] * 39 / 60,
                frameSize[1] * 23 / 200 + frameSize[1] * 4 / 10,
                frameSize[0] * 9 / 60,
                frameSize[1] / 23
        );
        saveButton1.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        saveButton1.setBackground(themeColor);
        saveButton1.setForeground(Color.white);
        saveButton1.setBorder(BorderFactory.createLineBorder(Color.white));
        saveButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Save spending records
                String errorMsg = "";
                int errorCnt = 0;
                try {
                    if(!isValidDate(dateField1.getText())) { // Check if date is in correct format
                        throw new InvalidDateException(); // If not, throw exception
                    }
                } catch (InvalidDateException ex) {
                    errorCnt += 1; // Increment error count
                    errorMsg += (String.valueOf(errorCnt) + ". Date must be in mm/dd/yyyy format!\n");
                }

                try {
                    if(!isValidDigit(amountField1.getText())) { // Check if amount is in correct format
                        throw new InvalidDigitException(); // If not, throw exception
                    }
                } catch (InvalidDigitException ex) {
                    errorCnt += 1; // Increment error count
                    errorMsg += (String.valueOf(errorCnt) + ". Amount must be digit only!\n");
                }

                if(errorCnt != 0) // If there is an error
                {
                    // Show error msg
                    JOptionPane.showMessageDialog(null, errorMsg, "Record error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String userDir = "src/userInfo/" + userID + "/"; // User's directory

                String date[] = dateField1.getText().split("/"); // mm-dd-yyyy

                String monthDir = userDir + date[2] + "/" + date[0] + "/"; // year/month dirctory, day.txt
                try {
                    Files.createDirectories(Paths.get(monthDir)); // Create directory if it doesn't exist
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                String fileDir = monthDir + date[1] + ".txt"; // Directory of text file (based on day)
                File file = new File(fileDir);
                if(!file.exists()) // Create file if it doesn't exist
                {
                    try {
                        file.createNewFile();
                    } catch(IOException ex) {
                        ex.printStackTrace();
                    }
                }

                // Record format: isSpending description amount category(optional)
                String record = String.valueOf(isSpending) + " " + descriptionField1.getText() + " " + amountField1.getText() + " " + categoryBox.getSelectedItem().toString() + "\n";

                try(FileWriter fw = new FileWriter(fileDir, true)) // Create file writer, write from the end
                {
                    fw.write(record); // Write the record
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                new MainFrame(frameSize, frameTitle, ID);
                dispose();
            }
        });

        // Add components of spending field
        spendingPanel.add(dateLabel1);
        spendingPanel.add(dateField1);
        spendingPanel.add(categoryLabel);
        spendingPanel.add(categoryBox);
        spendingPanel.add(descriptionLabel1);
        spendingPanel.add(descriptionField1);
        spendingPanel.add(amountLabel1);
        spendingPanel.add(amountField1);
        spendingPanel.add(saveButton1);

        savingPanel = new JPanel();
        savingPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        savingPanel.setBounds(
                0,
                frameSize[1] / 10,
                frameSize[0] * 193 / 200,
                frameSize[1] * 9 / 10
        );

        JLabel dateLabel2 = new JLabel("Date"); // Label for displaying date (saving screen)
        // Set bound based on frame size
        dateLabel2.setBounds(
                frameSize[0] / 10,
                frameSize[1] * 2 / 10 ,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        dateLabel2.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        dateLabel2.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right

        JTextField dateField2 = new JTextField(); // Text field for entering date (saving screen)
        // Set bound based on frame size
        dateField2.setBounds(
                frameSize[0] * 7 / 20,
                frameSize[1] * 23 / 200 + frameSize[1] / 10,
                frameSize[0] * 27 / 60,
                frameSize[1] / 25
        );
        dateField2.setFont(new Font("Arial", Font.PLAIN, 20)); // Set Font

        JLabel descriptionLabel2 = new JLabel("Description"); // Label for displaying description (saving screen)
        // Set bound based on frame size
        descriptionLabel2.setBounds(
                frameSize[0] / 10,
                frameSize[1] * 3 / 10 ,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        descriptionLabel2.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        descriptionLabel2.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right

        JTextField descriptionField2 = new JTextField(); // Text field for entering description (saving screen)
        // Set bound based on frame size
        descriptionField2.setBounds(
                frameSize[0] * 7 / 20,
                frameSize[1] * 23 / 200 + frameSize[1] * 2 / 10,
                frameSize[0] * 27 / 60,
                frameSize[1] / 25
        );
        descriptionField2.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font

        JLabel amountLabel2 = new JLabel("Amount"); // Label for displaying amount (saving screen)
        // Set bound based on frame size
        amountLabel2.setBounds(
                frameSize[0] / 10,
                frameSize[1] * 4 / 10 ,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        amountLabel2.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        amountLabel2.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right

        JTextField amountField2 = new JTextField(); // Text field for entering amount (saving screen)
        // Set bound based on frame size
        amountField2.setBounds(
                frameSize[0] * 7 / 20,
                frameSize[1] * 23 / 200 + frameSize[1] * 3 / 10,
                frameSize[0] * 27 / 60,
                frameSize[1] / 25
        );
        amountField2.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font

        JButton saveButton2 = new JButton("Save"); // Store button for saving the record (saving screen)
        // Set bound based on frame size
        saveButton2.setBounds(
                frameSize[0] * 39 / 60,
                frameSize[1] * 23 / 200 + frameSize[1] * 4 / 10,
                frameSize[0] * 9 / 60,
                frameSize[1] / 23
        );
        saveButton2.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        saveButton2.setBackground(themeColor);
        saveButton2.setForeground(Color.white);
        saveButton2.setBorder(BorderFactory.createLineBorder(Color.white));
        saveButton2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) { // Save spending records
            String errorMsg = "";
            int errorCnt = 0;
            try {
                if(!isValidDate(dateField2.getText())) { // Check if date is in correct format
                    throw new InvalidDateException(); // If not, throw exception
                }
            } catch (InvalidDateException ex) {
                errorCnt += 1; // Increment error count
                errorMsg += (String.valueOf(errorCnt) + ". Date must be in mm/dd/yyyy format!\n");
            }

            try {
                if(!isValidDigit(amountField2.getText())) { // Check if amount is in correct format
                    throw new InvalidDigitException(); // If not, throw exception
                }
            } catch (InvalidDigitException ex) {
                errorCnt += 1; // Increment error count
                errorMsg += (String.valueOf(errorCnt) + ". Amount must be digit only!\n");
            }

            if(errorCnt != 0) // If there is an error
            {
                // Show error msg
                JOptionPane.showMessageDialog(null, errorMsg, "Record error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String userDir = "src/userInfo/" + userID + "/"; // User's directory

            String date[] = dateField2.getText().split("/"); // mm-dd-yyyy

            String monthDir = userDir + date[2] + "/" + date[0] + "/"; // year/month dirctory, day.txt
            try {
                Files.createDirectories(Paths.get(monthDir)); // Create directory if it doesn't exist
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String fileDir = monthDir + date[1] + ".txt"; // Directory of text file (based on day)
            File file = new File(fileDir);
            if(!file.exists()) // Create file if it doesn't exist
            {
                try {
                    file.createNewFile();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }

            // Record format: isSpending description amount category(optional)
            String record = String.valueOf(isSpending) + " " + descriptionField2.getText() + " " + amountField2.getText() + "\n";

            try(FileWriter fw = new FileWriter(fileDir, true)) // Create file writer, write from the end
            {
                fw.write(record); // Write the record
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            new MainFrame(frameSize, frameTitle, ID);
            dispose();
        }
    });

        savingPanel = new JPanel(); // Store components for spending record
        savingPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        savingPanel.setBounds(
                0,
                frameSize[1] / 10,
                frameSize[0] * 193 / 200,
                frameSize[1] * 9 / 10
        );

        // Add components of saving field
        savingPanel.add(dateLabel2);
        savingPanel.add(dateField2);
        savingPanel.add(descriptionLabel2);
        savingPanel.add(descriptionField2);
        savingPanel.add(amountLabel2);
        savingPanel.add(amountField2);
        savingPanel.add(saveButton2);

        add(spendingPanel); // Show spending panel first

        JPanel headerPanel = new JPanel(); // Store buttons for choosing if the record is spending or saving
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
        spendingButton.setEnabled(false); // Disable spending button as current state is spending
        spendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spendingButton.setEnabled(false); // Disable spending button as current page is spending button
                savingButton.setEnabled(true); // Enable saving button as current page is spending button
                isSpending = 1; // Change the state to spending

                // Reset fields of saving panel when moving onto spending panel
                dateField2.setText("");
                descriptionField2.setText("");
                amountField2.setText("");

                remove(savingPanel); // Remove saving panel
                add(spendingPanel); // Show spending panel
                repaint();
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
                spendingButton.setEnabled(true); // Enable spending button as current page is saving button
                savingButton.setEnabled(false); // Disable saving button as current page is saving button
                isSpending = 0; // Change the state to saving

                // Reset fields of spending panel when moving onto saving panel
                dateField1.setText("");
                descriptionField1.setText("");
                amountField1.setText("");

                remove(spendingPanel); // Remove spending panel
                add(savingPanel); // Show saving panel
                repaint();
            }
        });
        headerPanel.add(savingButton);

        // Settings for frame
        setTitle(frameTitle);
        setSize(frameSize[0], frameSize[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
    }
}
