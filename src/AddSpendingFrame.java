import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AddSpendingFrame extends AddFrame {
    // Store categories for spending record
    private String[] categories = {"Food", "Transport", "Entertainment", "Clothes", "Healthcare", "Education", "Housing", "Condolences", "Etc"};
    private int isSpending = 1; // 0: Saving, 1: Spending

    public AddSpendingFrame(int[] size, String title, String ID)
    {
        super(size, title, ID);
        savingButton.setEnabled(true); // Enable saving button
        spendingButton.setEnabled(false); // Disable spending button

        JLabel dateLabel = new JLabel("Date"); // Label for displaying date
        // Set bound based on frame size
        dateLabel.setBounds(
                frameSize[0] / 10,
                frameSize[1] / 10,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        dateLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        dateLabel.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right

        // Set bound based on frame size
        dateField.setBounds(
                frameSize[0] * 7 / 20,
                frameSize[1] * 23 / 200,
                frameSize[0] * 27 / 60,
                frameSize[1] / 25
        );
        dateField.setFont(new Font("Arial", Font.PLAIN, 20));

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

        JLabel descriptionLabel = new JLabel("Description"); // Label for displaying description
        // Set bound based on frame size
        descriptionLabel.setBounds(
                frameSize[0] / 10,
                frameSize[1] * 3 / 10 ,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        descriptionLabel.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right

        // Set bound based on frame size
        descriptionField.setBounds(
                frameSize[0] * 7 / 20,
                frameSize[1] * 23 / 200 + frameSize[1] * 2 / 10,
                frameSize[0] * 27 / 60,
                frameSize[1] / 25
        );
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font

        JLabel amountLabel = new JLabel("Amount"); // Label for displaying amount
        // Set bound based on frame size
        amountLabel.setBounds(
                frameSize[0] / 10,
                frameSize[1] * 4 / 10 ,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        amountLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        amountLabel.setHorizontalAlignment(JLabel.RIGHT); // Set horizontal alignment to right

        // Set bound based on frame size
        amountField.setBounds(
                frameSize[0] * 7 / 20,
                frameSize[1] * 23 / 200 + frameSize[1] * 3 / 10,
                frameSize[0] * 27 / 60,
                frameSize[1] / 25
        );
        amountField.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font

        JButton saveButton = new JButton("Save"); // Store button for saving the record
        // Set bound based on frame size
        saveButton.setBounds(
                frameSize[0] * 39 / 60,
                frameSize[1] * 23 / 200 + frameSize[1] * 4 / 10,
                frameSize[0] * 9 / 60,
                frameSize[1] / 23
        );
        saveButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        saveButton.setBackground(themeColor);
        saveButton.setForeground(Color.white);
        saveButton.setBorder(BorderFactory.createLineBorder(Color.white));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Save spending records
                String errorMsg = "";
                int errorCnt = 0;
                try {
                    if(!isValidDate(dateField.getText())) { // Check if date is in correct format
                        throw new InvalidDateException(); // If not, throw exception
                    }
                } catch (InvalidDateException ex) {
                    errorCnt += 1; // Increment error count
                    errorMsg += (String.valueOf(errorCnt) + ". Date must be in mm/dd/yyyy format!\n");
                }

                try {
                    if(!isValidDigit(amountField.getText())) { // Check if amount is in correct format
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

                String date[] = dateField.getText().split("/"); // mm-dd-yyyy

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
                String record = String.valueOf(isSpending) + " " + descriptionField.getText() + " " + amountField.getText() + " " + categoryBox.getSelectedItem().toString() + "\n";

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
        add(dateLabel);
        add(dateField);
        add(categoryLabel);
        add(categoryBox);
        add(descriptionLabel);
        add(descriptionField);
        add(amountLabel);
        add(amountField);
        add(saveButton);
    }
}
