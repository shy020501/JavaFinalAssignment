import com.sun.istack.internal.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainFrame extends JFrame {

    private int[] frameSize = {0, 0}; // Stores size of the frame
    private String frameTitle; // Stores name of the frame
    String userID; // Stores ID of the user so that the folder could be accessed
    private Color[] amountColor = {Color.BLUE, Color.RED}; // Stores color of saving/spending label
    private Color themeColor = new Color(240, 135, 132);
    private int totalSpending = 0; // Stores total spending
    private int totalSaving = 0; // Stores total saving
    private int currentMonth = 11; // Stores the month the user is looking at
    private int currentYear = 2023; // Stores the year the user is looking at
    private String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private void deleteRecord(String dir, String fileName, String data)
    {
        File inputFile = new File(dir + fileName); // Get original file
        File tempFile = new File(dir + "tempFile.txt"); // Create temp file for storing data after deletion

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;
            while((currentLine = reader.readLine()) != null)
            {
                String trimmedLine = currentLine.trim(); // Get current line
                if(trimmedLine.equals(data)) continue; // If current line is line to be removed, don't run the code below
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
        } catch (FileNotFoundException ex) {
            System.err.println(dir + fileName + " file not found!"); // When input file is not found
        } catch (IOException ex) {
            ex.printStackTrace(); // When problem arises for writing file
        }

        // Write contents of temp file to input file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tempFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
            String currentLine;
            while((currentLine = reader.readLine()) != null)
            {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
        } catch (FileNotFoundException ex) {
            System.err.println(dir + fileName + " file not found!"); // When temp file is not found
        } catch (IOException ex) {
            ex.printStackTrace(); // When problem arises for writing file
        }

        tempFile.delete();
    }

    private JPanel createDatePanel(String date)
    {
        JPanel datePanel = new JPanel(); // Store panel for date
        datePanel.setLayout(null); // Set layout to null
        datePanel.setPreferredSize(new Dimension(frameSize[0] * 95 / 100, frameSize[1] / 15));
        datePanel.setBorder(BorderFactory.createLineBorder(Color.black)); // Set border to black line
        datePanel.setBackground(themeColor); // Set background to theme color

        String ordinalExpression = "th"; // Store ordinal expression
        if(!date.substring(0, 1).equals("1"))
        {
            // Setting
            if(date.substring(1).equals("1")) ordinalExpression = "st";
            else if (date.substring(1).equals("2")) ordinalExpression = "nd";
            else if (date.substring(1).equals("3")) ordinalExpression = "rd";
        }

        if(date.substring(0, 1).equals("0")) date = date.substring(1);

        JLabel dateLabel = new JLabel(month[currentMonth - 1] + " " + date + ordinalExpression); // Store label of current date
        // Set bounds based on frame size
        dateLabel.setBounds(
                0,
                0,
                frameSize[0] * 95 / 100,
                frameSize[1] / 15
        );
        dateLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        dateLabel.setBorder(BorderFactory.createEmptyBorder(0 , frameSize[0] / 60 , 0 , 0)); // Set margin
        dateLabel.setFont(new Font("Arial ", Font.BOLD, 18)); // Set font
        dateLabel.setForeground(Color.white); // Set text color to white
        datePanel.add(dateLabel);

        return datePanel;
    }
    private JPanel createRecordPanel(String fileName, String category, String description, int isSpending, int amount, String ID) // Create panel for user records
    {
        JPanel recordPanel = new JPanel(); // Store panel of each record
        recordPanel.setLayout(null); // Set layout to null
        recordPanel.setPreferredSize(new Dimension(frameSize[0] * 95 / 100, frameSize[1] / 15));
        recordPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        recordPanel.setBackground(Color.white); // Set background to while

        JButton editButton = new JButton(); // Button used to edit saved record
        // Set bounds based on frame size
        editButton.setBounds(
                0,
                0,
                frameSize[0] * 95 / 100,
                frameSize[1] / 15
        );
        // Set edit button transparent
        editButton.setOpaque(false);
        editButton.setContentAreaFilled(false);
        editButton.setBorderPainted(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dir = "src/userInfo/" + ID + "/" + currentYear + "/" + currentMonth + "/";
                String date = currentMonth + "/" + fileName.substring(0, 2) + "/" + currentYear;
                String removeData = String.valueOf(isSpending) + " " + description + " " + String.valueOf(amount) + " " + category; // Store line to be deleted

                deleteRecord(dir, fileName, removeData);
                AddFrame addFrame = new AddFrame(frameSize, frameTitle, ID);
                addFrame.setFieldText(date, description, String.valueOf(amount));
                dispose();
            }
        });
        recordPanel.add(editButton);

        JLabel categoryLabel = new JLabel(category); // Store label for displaying category
        // Set bounds based on frame size
        categoryLabel.setBounds(
                frameSize[0] / 60,
                0,
                frameSize[0] / 5,
                frameSize[1] / 15
        );
        categoryLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Set font
        recordPanel.add(categoryLabel);

        JLabel descriptionLabel = new JLabel(description); // Store label for displaying description
        // Set bounds based on frame size
        descriptionLabel.setBounds(
                frameSize[0] / 40 + frameSize[0] / 5,
                0,
                frameSize[0] * 2 / 5,
                frameSize[1] / 15
        );
        descriptionLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Set font
        recordPanel.add(descriptionLabel);

        JLabel amountLabel = new JLabel(NumberFormat.getInstance().format(amount)); // Store label for displaying amount
        // Set bounds based on frame size
        amountLabel.setBounds(
                frameSize[0] / 60 + frameSize[0] * 3 / 5 + (frameSize[0] * isSpending / 5),
                0,
                frameSize[0] * 1 / 5,
                frameSize[1] / 15
        );
        amountLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        amountLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Set font
        amountLabel.setForeground(amountColor[isSpending]);
        recordPanel.add(amountLabel);

        return recordPanel;
    }

    public MainFrame(int[] size, String title, String ID)
    {
        // Get frame's size, title, and userID as parameter and use them for setting the frame
        frameSize[0] = size[0];
        frameSize[1] = size[1];
        frameTitle = title;
        userID = ID;

        JPanel headerPanel = new JPanel(); // Stores header panel
        headerPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        headerPanel.setBounds(
                0,
                0,
                frameSize[0] * 193 / 200,
                frameSize[1] / 10
        );
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        headerPanel.setBackground(themeColor); // Change background color to theme color
        add(headerPanel);

        JLabel monthLabel = new JLabel("January"); // Stores label for displaying month
        // Set bound based on frame size
        monthLabel.setBounds(
                frameSize[1] * 27 / 340,
                frameSize[1] * 7 / 340,
                frameSize[0] / 3,
                frameSize[1] / 17
        );
        monthLabel.setHorizontalAlignment(JLabel.CENTER); // Set horizontal alignment to center
        monthLabel.setFont(new Font("Arial", Font.BOLD, 25)); // Change font
        monthLabel.setForeground(Color.white); // Set text color to white
        headerPanel.add(monthLabel);

        JButton prevMonthButton = new JButton("<"); // Stores button for moving on to the previous month
        // Set bound based on frame size
        prevMonthButton.setBounds(
                frameSize[1] * 7 / 340,
                frameSize[1] * 7 / 340,
                frameSize[1] / 17,
                frameSize[1] / 17
                );
        prevMonthButton.setFont(new Font("Arial", Font.BOLD, 20)); // Change font
        prevMonthButton.setBackground(themeColor); // Set background to white
        prevMonthButton.setForeground(Color.white); // Set text color to white
        prevMonthButton.setBorder(BorderFactory.createLineBorder(themeColor)); // Set border of the button to theme color
        headerPanel.add(prevMonthButton);

        JButton nextMonthButton = new JButton(">"); // Stores button for moving on to the next month
        // Set bound based on frame size
        nextMonthButton.setBounds(
                frameSize[1] * 27 / 340 + frameSize[0] / 3,
                frameSize[1] * 7 / 340,
                frameSize[1] / 17,
                frameSize[1] / 17
        );
        nextMonthButton.setFont(new Font("Arial", Font.BOLD, 20)); // Change font
        nextMonthButton.setBackground(themeColor); // Set background to white
        nextMonthButton.setForeground(Color.white); // Set text color to white
        nextMonthButton.setBorder(BorderFactory.createLineBorder(themeColor)); // Set border of the button to theme color
        headerPanel.add(nextMonthButton);

        JPanel summaryPanel = new JPanel(); // Stores summary panel that displays spendings, savings, and total spending
        summaryPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        // Set bound based on frame size
        summaryPanel.setBounds(
                0,
                frameSize[1] / 10,
                frameSize[0] * 193 / 200,
                frameSize[1] / 10
        );
        summaryPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // Create border (black line)
        add(summaryPanel);

        JPanel spendingPanel = new JPanel(); // Stores spending that displays spending
        spendingPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        // Set bound based on frame size
        spendingPanel.setBounds(
                0,
                0,
                frameSize[0] * 64 / 200,
                frameSize[1] / 10
        );
        spendingPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // Create border (black line)
        spendingPanel.setBackground(Color.white); // Set background to while
        summaryPanel.add(spendingPanel);

        JPanel savingPanel = new JPanel(); // Stores spending that displays savings
        savingPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        // Set bound based on frame size
        savingPanel.setBounds(
                frameSize[0] * 64 / 200,
                0,
                frameSize[0] * 64 / 200,
                frameSize[1] / 10
        );
        savingPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // Create border (black line)
        savingPanel.setBackground(Color.white); // Set background to while
        summaryPanel.add(savingPanel);

        JPanel totalPanel = new JPanel(); // Stores spending that displays total spending
        totalPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        // Set bound based on frame size
        totalPanel.setBounds(
                frameSize[0] * 128 / 200,
                0,
                frameSize[0] * 65 / 200,
                frameSize[1] / 10
        );
        totalPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // Create border (black line)
        totalPanel.setBackground(Color.white); // Set background to while
        summaryPanel.add(totalPanel);

        JPanel mainPanel = new JPanel(); // Stores main panel where all the spending details will be displayed
        mainPanel.setLayout(new GridBagLayout()); // Layout set to GridBagLayout in order to place components in a row
        GridBagConstraints constraints = new GridBagConstraints(); // Store constraints for GridBagLayout
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        constraints.fill = GridBagConstraints.HORIZONTAL; // The component of mainPanel fills the horizontal axis


        // Get directory of current year and month
        String currentDir = "src/userInfo/" + ID + "/" + String.valueOf(currentYear) + "/" + String.valueOf(currentMonth) + "/";
        File dir = new File(currentDir);
        int count = 0;
        if(dir.exists()) // If such directory exists
        {
            String[] records = dir.list(); // Get all the text files(day) in the directory
            Collections.reverse(Arrays.asList(records)); // Reverse the array so that the latest record would be shown on top

            for(String record : records) // Loop through each text file (record is name of each text file)
            {
                constraints.gridx = 0; // Set to 0 as there's one component in a column
                constraints.gridy = count; // Set to i as components would line up in a row
                constraints.gridwidth = 1;
                constraints.gridheight = 1;

                count += 1; // Increment count by 1

                JPanel tempPanel = createDatePanel(record.substring(0, 2));
                mainPanel.add(tempPanel, constraints);

                File dailyRecord = new File(currentDir + record); // Stores each daily text file
                try{
                    Scanner fileReader = new Scanner(dailyRecord); // Create scanner for reading text file
                    while(fileReader.hasNextLine()){
                        String rawData = fileReader.nextLine(); // Read next line from text file
                        String[] data = rawData.split(" "); // Split data inside text file based on space

                        String category = "Saving";
                        if(data[0].equals("1")) category = data[3]; // If data is spending record, fill in category

                        constraints.gridx = 0; // Set to 0 as there's one component in a column
                        constraints.gridy = count; // Set to i as components would line up in a row
                        constraints.gridwidth = 1;
                        constraints.gridheight = 1;

                        count += 1; // Increment count by 1
                        tempPanel = createRecordPanel(record, category, data[1], Integer.parseInt(data[0]), Integer.parseInt(data[2]), ID);
                        mainPanel.add(tempPanel, constraints);
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("File not found!");
                    ex.printStackTrace();
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Set bound based on frame size
        scrollPane.setBounds(
                0,
                frameSize[1] * 2 / 10,
                frameSize[0] * 193 / 200,
                frameSize[1] * 63 / 100
        );
        scrollPane.getVerticalScrollBar().setUnitIncrement(10); // Adjust scroll speed
        add(scrollPane);

        JPanel buttonPanel = new JPanel(); // Stores button panel that displays buttons for additional functions
        buttonPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        // Set bound based on frame size
        buttonPanel.setBounds(
                0,
                frameSize[1] * 83 / 100,
                frameSize[0] * 193 / 200,
                frameSize[1] / 10
        );
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        add(buttonPanel);

        JButton mainButton = new JButton("Main"); // Button that moves to main frame
        // Set bound based on frame size
        mainButton.setBounds(
                0,
                0,
                frameSize[0] * 48 / 200,
                frameSize[1] / 10
        );
        mainButton.setFont(new Font("Arial", Font.BOLD, 20));
        mainButton.setBackground(themeColor); // Set button's color to theme color
        mainButton.setForeground(Color.white); // Set text color to white;
        mainButton.setBorder(BorderFactory.createLineBorder(Color.white)); // Set border of the button to white
        buttonPanel.add(mainButton);

        JButton analysisButton = new JButton("Analysis"); // Button that moves to main frame
        // Set bound based on frame size
        analysisButton.setBounds(
                frameSize[0] * 48 / 200,
                0,
                frameSize[0] * 48 / 200,
                frameSize[1] / 10
        );
        analysisButton.setFont(new Font("Arial", Font.BOLD, 20));
        analysisButton.setBackground(themeColor); // Set button's color to theme color
        analysisButton.setForeground(Color.white); // Set text color to white
        analysisButton.setBorder(BorderFactory.createLineBorder(Color.white)); // Set border of the button to white
        buttonPanel.add(analysisButton);

        JButton assetButton = new JButton("Asset"); // Button that moves to main frame
        // Set bound based on frame size
        assetButton.setBounds(
                frameSize[0] * 96 / 200,
                0,
                frameSize[0] * 48 / 200,
                frameSize[1] / 10
        );
        assetButton.setFont(new Font("Arial", Font.BOLD, 20));
        assetButton.setBackground(themeColor); // Set button's color to theme color
        assetButton.setForeground(Color.white); // Set text color to white
        assetButton.setBorder(BorderFactory.createLineBorder(Color.white)); // Set border of the button to white
        buttonPanel.add(assetButton);

        JButton addButton = new JButton("Add"); // Button that moves to main frame
        // Set bound based on frame size
        addButton.setBounds(
                frameSize[0] * 144 / 200,
                0,
                frameSize[0] * 49 / 200,
                frameSize[1] / 10
        );
        addButton.setFont(new Font("Arial", Font.BOLD, 20));
        addButton.setBackground(themeColor); // Set button's color to theme color
        addButton.setForeground(Color.white); // Set text color to white
        addButton.setBorder(BorderFactory.createLineBorder(Color.white)); // Set border of the button to white
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFrame(frameSize, frameTitle, ID);
                dispose();
            }
        });
        buttonPanel.add(addButton);

        // Settings for frame
        setTitle(frameTitle);
        setSize(frameSize[0], frameSize[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        int[] size = {600, 800};
        String title = "Financial Ledger";
        new MainFrame(size, title, "shy020501");
    }
}
