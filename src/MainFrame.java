import sun.awt.windows.ThemeReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.*;

public class MainFrame extends JFrame {

    private int[] frameSize = {0, 0}; // Stores size of the frame
    private String frameTitle; // Stores name of the frame
    String userID; // Stores ID of the user so that the folder could be accessed
    private Color[] amountColor = {Color.BLUE, Color.RED}; // Stores color of saving/spending label
    private Color themeColor = new Color(240, 135, 132);
    private int totalSpending = 0; // Stores total spending
    private int totalSaving = 0; // Stores total saving
    private int currentYear = Integer.parseInt(java.time.LocalDate.now().toString().split("-")[0]);
    private int  currentMonth = Integer.parseInt(java.time.LocalDate.now().toString().split("-")[1]);
    private JScrollPane mainScrollPane = new JScrollPane();
    private JScrollPane analysisScrollPane = new JScrollPane();
    JLabel categoryLabel = new JLabel();
    private String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String[] categories = {"Food", "Transport", "Entertainment", "Clothes", "Healthcare", "Education", "Housing", "Condolences", "Etc"};
    private boolean done = false; // Stores weather creation of the page is done
    private void writeCurrentDate(int month, int year) // Keep record of month & year the user is looking at
    {
        File dateFile = new File("src/userInfo/" + userID + "/dateFile.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dateFile));
            String StringMonth = String.valueOf(month);
            if(StringMonth.length() == 1) StringMonth = "0" + StringMonth; // If month is 1~9, change it to 01~09
            writer.write(StringMonth + " " + String.valueOf(year)); // Store month and date
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String readCurrentDate() // Get month and year that the user is looking at
    {
        String date = "";
        String path = "src/userInfo/" + userID + "/dateFile.txt";
        File dateFile = new File(path);
        if(!dateFile.exists())
        {
            try {
                Files.createFile(Paths.get(path)); // Create file
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            currentMonth = Integer.parseInt(java.time.LocalDate.now().toString().split("-")[1]);
            currentYear = Integer.parseInt(java.time.LocalDate.now().toString().split("-")[0]);
            writeCurrentDate(currentMonth, currentYear); // Write month/year on text file if text file is empty
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(dateFile));
            date = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return date;
    }
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
                if(trimmedLine.equals(data)) {
                    continue; // If current line is line to be removed, don't run the code below
                }
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
                String month = String.valueOf(currentMonth);
                if(month.length() == 1) month = "0" + month;
                String dir = "src/userInfo/" + ID + "/" + currentYear + "/" + month + "/";
                String date = month + "/" + fileName.substring(0, 2) + "/" + currentYear;
                String removeData = String.valueOf(isSpending) + " " + category  + " " + String.valueOf(amount)+ " " + description; // Store line to be deleted

                deleteRecord(dir, fileName, removeData);
                if(isSpending == 1) new EditSpendingFrame(frameSize, frameTitle, ID, date, description, String.valueOf(amount)); // If the panel stores spending record, open edit window for spending record
                else if(isSpending == 0) new EditSavingFrame(frameSize, frameTitle, ID, date, description, String.valueOf(amount)); // If the panel stores spending record, open edit window for saving record
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
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Set font
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
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Set font
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
        amountLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Set font
        amountLabel.setForeground(amountColor[isSpending]);
        recordPanel.add(amountLabel);

        return recordPanel;
    }

    private JPanel createEmptyPanel()
    {
        JPanel emptyPanel = new JPanel(); // Store panel of each record
        emptyPanel.setLayout(null); // Set layout to null
        emptyPanel.setPreferredSize(new Dimension(frameSize[0] * 95 / 100, frameSize[1] / 15));

        return emptyPanel;
    }

    public MainFrame(int[] size, String title, String ID)
    {
        // Get frame's size, title, and userID as parameter and use them for setting the frame
        frameSize[0] = size[0];
        frameSize[1] = size[1];
        frameTitle = title;
        userID = ID;

        String date = readCurrentDate();
        currentYear = Integer.parseInt(date.split(" ")[1]);
        currentMonth = Integer.parseInt(date.split(" ")[0]);

        JPanel headerPanel = new JPanel(); // Stores header panel
        headerPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        // Set bound based on frame size
        headerPanel.setBounds(
                0,
                0,
                frameSize[0] * 193 / 200,
                frameSize[1] / 10
        );
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        headerPanel.setBackground(themeColor); // Change background color to theme color

        JLabel monthLabel = new JLabel(month[currentMonth - 1] + " " + currentYear); // Stores label for displaying month
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
        prevMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMonth -= 1; // Decrease month
                if(currentMonth == 0) // If month becomes 0, move on to previous year
                {
                    currentYear -= 1;
                    currentMonth = 12;
                }
                writeCurrentDate(currentMonth, currentYear);
                new MainFrame(frameSize, frameTitle, ID);
                dispose();
            }
        });
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
        nextMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMonth += 1; // Increase month
                if(currentMonth == 13) // If month becomes 13, move on to next year
                {
                    currentYear += 1;
                    currentMonth = 1;
                }
                writeCurrentDate(currentMonth, currentYear);
                new MainFrame(frameSize, frameTitle, ID);
                dispose();
            }
        });
        headerPanel.add(nextMonthButton);

        ImageIcon mainIcon = new ImageIcon("src/Icon/icon.jpg"); // Get icon of the program

        // Resize image
        Image mainImg = mainIcon.getImage();
        mainImg = mainImg.getScaledInstance(
                frameSize[0] / 8,
                frameSize[0] / 8,
                Image.SCALE_SMOOTH
        );
        mainIcon = new ImageIcon(mainImg);

        JLabel iconLabel = new JLabel(mainIcon); // Create label for displaying icon
        // Set bounds based on frame size
        iconLabel.setBounds(
                frameSize[0] * 4 / 5,
                3,
                frameSize[0] / 8,
                frameSize[0] / 8
        );
        headerPanel.add(iconLabel);

        add(headerPanel);

        JPanel mainPanel = new JPanel(); // Stores main panel where all the spending details will be displayed
        mainPanel.setLayout(new GridBagLayout()); // Layout set to GridBagLayout in order to place components in a row
        GridBagConstraints constraints = new GridBagConstraints(); // Store constraints for GridBagLayout
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        constraints.fill = GridBagConstraints.HORIZONTAL; // The component of mainPanel fills the horizontal axis

        // Convert month to string
        String month = String.valueOf(currentMonth);
        if(month.length() == 1) month = "0" + month;

        // Get directory of current year and month
        String currentDir = "src/userInfo/" + ID + "/" + String.valueOf(currentYear) + "/" + month + "/";
        File dir = new File(currentDir);

        int count = 0;
        if(dir.exists()) // If such directory exists
        {
            String[] records = dir.list(); // Get all the text files(day) in the directory
            Collections.reverse(Arrays.asList(records)); // Reverse the array so that the latest record would be shown on top

            for(String record : records) // Loop through each text file (record is name of each text file)
            {
                if(record.equals("tempFile.txt")) continue; // Don't display temp file

                File dailyRecord = new File(currentDir + record); // Stores each daily text file

                if(dailyRecord.length() != 0) // Ignore empty files
                {
                    constraints.gridx = 0; // Set to 0 as there's one component in a column
                    constraints.gridy = count; // Set to count as components would line up in a row
                    constraints.gridwidth = 1;
                    constraints.gridheight = 1;

                    count += 1; // Increment count by 1

                    JPanel recordPanel = createDatePanel(record.substring(0, 2));
                    mainPanel.add(recordPanel, constraints);

                    try{
                        Scanner fileReader = new Scanner(dailyRecord); // Create scanner for reading text file
                        while(fileReader.hasNextLine()){
                            String rawData = fileReader.nextLine(); // Read next line from text file
                            String[] data = rawData.split(" "); // Split data inside text file based on space
                            // data => isSpending category amount description

                            String category = data[1]; // Fill in category
                            if(data[0].equals("1")) totalSpending += Integer.parseInt(data[2]); // If data is spending record, add to spending
                            else totalSaving += Integer.parseInt(data[2]); // If data is saving record, add to saving

                            String description = "";
                            for(int i = 3; i < data.length; i++)
                            {
                                description += data[i]; // Get description from data
                                if(i != data.length - 1) description += " ";
                            }

                            constraints.gridx = 0; // Set to 0 as there's one component in a column
                            constraints.gridy = count; // Set to count as components would line up in a row
                            constraints.gridwidth = 1;
                            constraints.gridheight = 1;

                            count += 1; // Increment count by 1
                            // (String fileName, String category, String description, int isSpending, int amount, String ID)
                            recordPanel = createRecordPanel(record, category, description, Integer.parseInt(data[0]), Integer.parseInt(data[2]), ID);
                            mainPanel.add(recordPanel, constraints);
                        }
                    } catch (FileNotFoundException ex) {
                        System.out.println("File not found!");
                        ex.printStackTrace();
                    }
                }
            }

            if(count < 10)
            {
                for(int i = 0; i < 10 - count; i++) // Create empty panel (if not layout becomes weird for record panels)
                {
                    constraints.gridx = 0; // Set to 0 as there's one component in a column
                    constraints.gridy = count + 1 + i; // Set empty spaces
                    constraints.gridwidth = 1;
                    constraints.gridheight = 1;

                    JPanel emptyPanel = createEmptyPanel();
                    mainPanel.add(emptyPanel, constraints);
                }
            }
        }

        mainScrollPane = new JScrollPane(mainPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Set bound based on frame size
        mainScrollPane.setBounds(
                0,
                frameSize[1] * 2 / 10,
                frameSize[0] * 193 / 200,
                frameSize[1] * 63 / 100
        );
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(10); // Adjust scroll speed
        add(mainScrollPane);


        JPanel analysisPanel = new JPanel(); // Stores main panel where analysis of spending will be displayed
        analysisPanel.setLayout(new GridBagLayout()); // Layout set to GridBagLayout in order to place components in a row

        constraints.fill = GridBagConstraints.HORIZONTAL; // The component of mainPanel fills the horizontal axis

        // Convert month to string
        month = String.valueOf(currentMonth);
        if(month.length() == 1) month = "0" + month; // If month is 1~9, convert it into 01~09

        // Get directory of current year and month
        currentDir = "src/userInfo/" + ID + "/" + String.valueOf(currentYear) + "/" + month + "/";
        dir = new File(currentDir);
        int[] categorySpending = new int[categories.length];
        for(int i = 0; i < categories.length; i++) { categorySpending[i] = 0; } // Initialise category spending to 0

        if(dir.exists()) // If such directory exists
        {
            String[] records = dir.list(); // Get all the text files(day) in the directory
            Collections.reverse(Arrays.asList(records)); // Reverse the array so that the latest record would be shown on top

            for(String record : records) // Loop through each text file (record is name of each text file)
            {
                if(record.equals("tempFile.txt")) continue; // Don't display temp file
                File dailyRecord = new File(currentDir + record); // Stores each daily text file

                if(dailyRecord.length() != 0) // Ignore empty files
                {
                    try{
                        Scanner fileReader = new Scanner(dailyRecord); // Create scanner for reading text file
                        while(fileReader.hasNextLine()){
                            String rawData = fileReader.nextLine(); // Read next line from text file
                            String[] data = rawData.split(" "); // Split data inside text file based on space
                            // data => isSpending category amount description

                            String category = data[1]; // Fill in category
                            if(!data[0].equals("0")) // Exclude saving category
                            {
                                // Get index of category and add amount
                                categorySpending[Arrays.asList(categories).indexOf(category)] += Integer.parseInt(data[2]);
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        System.out.println("File not found!");
                        ex.printStackTrace();
                    }
                }
            }

            for(int i = 0; i < categories.length; i++)
            {
                constraints.gridx = 0; // Set to 0 as there's one component in a column
                constraints.gridy = i; // Set to i as components would line up in a row
                constraints.gridwidth = 1;
                constraints.gridheight = 1;

                JPanel categoryPanel = new JPanel();
                categoryPanel.setLayout(null); // Set layout to null
                categoryPanel.setPreferredSize(new Dimension(frameSize[0] * 95 / 100, frameSize[1] / 15));
                categoryPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // Set border to black line
                categoryPanel.setBackground(Color.white); // Set background to white

                categoryLabel = new JLabel(categories[i]); // Store label for each category
                // Set bounds based on frame size
                categoryLabel.setBounds(
                        frameSize[0] / 60,
                        0,
                        frameSize[0] / 4,
                        frameSize[1] / 15
                );
                categoryLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
                categoryLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Set font
                categoryPanel.add(categoryLabel);

                JLabel amountLabel = new JLabel(String.valueOf(categorySpending[i])); // Store label for each category spending
                amountLabel.setBounds(
                        (frameSize[0] * 95 / 100) - (frameSize[0] * 2 / 5),
                        0,
                        frameSize[0] / 4,
                        frameSize[1] / 15
                );
                amountLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
                amountLabel.setHorizontalAlignment(JLabel.RIGHT); // Set vertical alignment to center
                amountLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Set font
                categoryPanel.add(amountLabel);

                analysisPanel.add(categoryPanel, constraints);
            }
        }

        if(categories.length < 10)
        {
            for(int i = 0; i < 10 - categories.length; i++) // Create empty panel (if not layout becomes weird for record panels)
            {
                constraints.gridx = 0; // Set to 0 as there's one component in a column
                constraints.gridy = categories.length + 1 + i; // Set empty spaces
                constraints.gridwidth = 1;
                constraints.gridheight = 1;

                JPanel emptyPanel = createEmptyPanel();
                analysisPanel.add(emptyPanel, constraints);
            }
        }

        analysisScrollPane = new JScrollPane(analysisPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Set bound based on frame size
        analysisScrollPane.setBounds(
                0,
                frameSize[1] * 2 / 10,
                frameSize[0] * 193 / 200,
                frameSize[1] * 63 / 100
        );
        analysisScrollPane.getVerticalScrollBar().setUnitIncrement(10); // Adjust scroll speed

        add(analysisScrollPane);

        JPanel summaryPanel = new JPanel(); // Stores summary panel that displays spending, saving, and total spending
        summaryPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        // Set bound based on frame size
        summaryPanel.setBounds(
                0,
                frameSize[1] / 10,
                frameSize[0] * 193 / 200,
                frameSize[1] / 10
        );
        summaryPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // Create border (black line)

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

        JLabel spendingLabel = new JLabel("Total Spending"); // Store label for total spending
        // Set bound based on frame size
        spendingLabel.setBounds(
                0,
                frameSize[1] / 75,
                frameSize[0] * 64 / 200,
                frameSize[1] / 25
        );
        spendingLabel.setFont(new Font("Arial", Font.BOLD, 17)); // Set font
        spendingLabel.setHorizontalAlignment(JLabel.CENTER); // Set horizontal alignment to center
        spendingLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        spendingLabel.setForeground(amountColor[1]); // Set color to red
        spendingPanel.add(spendingLabel);

        JLabel spendingAmountLabel = new JLabel(NumberFormat.getInstance().format(totalSpending)); // Store label for total spending
        // Set bound based on frame size
        spendingAmountLabel.setBounds(
                0,
                frameSize[1] / 20,
                frameSize[0] * 64 / 200,
                frameSize[1] / 25
        );
        spendingAmountLabel.setFont(new Font("Arial", Font.BOLD, 17)); // Set font
        spendingAmountLabel.setHorizontalAlignment(JLabel.CENTER); // Set horizontal alignment to center
        spendingAmountLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        spendingAmountLabel.setForeground(amountColor[1]); // Set color to red
        spendingPanel.add(spendingAmountLabel);

        summaryPanel.add(spendingPanel);

        JPanel savingPanel = new JPanel(); // Stores spending that displays savings
        savingPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        savingPanel.setBounds(
                frameSize[0] * 64 / 200,
                0,
                frameSize[0] * 64 / 200,
                frameSize[1] / 10
        );
        savingPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // Create border (black line)
        savingPanel.setBackground(Color.white); // Set background to while

        JLabel savingLabel = new JLabel("Total Saving"); // Store label for total saving
        // Set bound based on frame size
        savingLabel.setBounds(
                0,
                frameSize[1] / 75,
                frameSize[0] * 64 / 200,
                frameSize[1] / 25
        );
        savingLabel.setFont(new Font("Arial", Font.BOLD, 17)); // Set font
        savingLabel.setHorizontalAlignment(JLabel.CENTER); // Set horizontal alignment to center
        savingLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        savingLabel.setForeground(amountColor[0]); // Set color to red
        savingPanel.add(savingLabel);

        JLabel savingAmountLabel = new JLabel(NumberFormat.getInstance().format(totalSaving)); // Store label for total saving
        // Set bound based on frame size
        savingAmountLabel.setBounds(
                0,
                frameSize[1] / 20,
                frameSize[0] * 64 / 200,
                frameSize[1] / 25
        );
        savingAmountLabel.setFont(new Font("Arial", Font.BOLD, 17)); // Set font
        savingAmountLabel.setHorizontalAlignment(JLabel.CENTER); // Set horizontal alignment to center
        savingAmountLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        savingAmountLabel.setForeground(amountColor[0]); // Set color to red
        savingPanel.add(savingAmountLabel);

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

        JLabel totalLabel = new JLabel("Total"); // Store label for total
        // Set bound based on frame size
        totalLabel.setBounds(
                0,
                frameSize[1] / 75,
                frameSize[0] * 64 / 200,
                frameSize[1] / 25
        );
        totalLabel.setFont(new Font("Arial", Font.BOLD, 17)); // Set font
        totalLabel.setHorizontalAlignment(JLabel.CENTER); // Set horizontal alignment to center
        totalLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        totalLabel.setForeground(Color.black); // Set color to red
        totalPanel.add(totalLabel);

        JLabel totalAmountLabel = new JLabel(NumberFormat.getInstance().format(totalSaving - totalSpending)); // Store label for total
        // Set bound based on frame size
        totalAmountLabel.setBounds(
                0,
                frameSize[1] / 20,
                frameSize[0] * 64 / 200,
                frameSize[1] / 25
        );
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 17)); // Set font
        totalAmountLabel.setHorizontalAlignment(JLabel.CENTER); // Set horizontal alignment to center
        totalAmountLabel.setVerticalAlignment(JLabel.CENTER); // Set vertical alignment to center
        totalAmountLabel.setForeground(Color.black); // Set color to red
        totalPanel.add(totalAmountLabel);

        summaryPanel.add(totalPanel);

        add(summaryPanel);

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

        JButton mainButton = new JButton("Main"); // Button that moves to main frame
        // Set bound based on frame size
        mainButton.setBounds(
                0,
                0,
                frameSize[0] * 64 / 200,
                frameSize[1] / 10
        );
        mainButton.setFont(new Font("Arial", Font.BOLD, 20));
        mainButton.setBackground(themeColor); // Set button's color to theme color
        mainButton.setForeground(Color.white); // Set text color to white;
        mainButton.setBorder(BorderFactory.createLineBorder(Color.white)); // Set border of the button to white
        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(analysisScrollPane);
                add(mainScrollPane);
                repaint();
            }
        });
        buttonPanel.add(mainButton);

        JButton analysisButton = new JButton("Analysis"); // Button that moves to main frame
        // Set bound based on frame size
        analysisButton.setBounds(
                frameSize[0] * 64 / 200,
                0,
                frameSize[0] * 64 / 200,
                frameSize[1] / 10
        );
        analysisButton.setFont(new Font("Arial", Font.BOLD, 20));
        analysisButton.setBackground(themeColor); // Set button's color to theme color
        analysisButton.setForeground(Color.white); // Set text color to white
        analysisButton.setBorder(BorderFactory.createLineBorder(Color.white)); // Set border of the button to white
        analysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(mainScrollPane);
                add(analysisScrollPane);
                repaint();
            }
        });
        buttonPanel.add(analysisButton);

        JButton addButton = new JButton("Add"); // Button that moves to main frame
        // Set bound based on frame size
        addButton.setBounds(
                frameSize[0] * 128 / 200,
                0,
                frameSize[0] * 64 / 200,
                frameSize[1] / 10
        );
        addButton.setFont(new Font("Arial", Font.BOLD, 20));
        addButton.setBackground(themeColor); // Set button's color to theme color
        addButton.setForeground(Color.white); // Set text color to white
        addButton.setBorder(BorderFactory.createLineBorder(Color.white)); // Set border of the button to white
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddSpendingFrame(frameSize, frameTitle, ID);
                dispose();
            }
        });
        buttonPanel.add(addButton);

        add(buttonPanel);
        done = true;


        // Settings for frame
        setTitle(frameTitle);
        setSize(frameSize[0], frameSize[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
    }
}
