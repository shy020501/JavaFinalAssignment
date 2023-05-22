import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private int[] frameSize = {0, 0}; // Stores size of the frame
    private String frameTitle; // Stores name of the frame
    String userID;
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
        summaryPanel.add(totalPanel);


        // 해결책) https://woongbin96.tistory.com/105

//        JPanel outerPanel = new JPanel();

        JPanel mainPanel = new JPanel(); // Stores main panel where all the spending details will be displayed
        mainPanel.setLayout(new GridBagLayout()); // Layout set to null in order to place components based on coordinates
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        constraints.fill = GridBagConstraints.BOTH;

        for(int i = 0; i < 100; i++)
        {
            constraints.gridx = 0;
            constraints.gridy = i;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            JPanel tempPanel = new JPanel();
            tempPanel.setPreferredSize(new Dimension(frameSize[0] * 95 / 100, frameSize[1] * 2 / 30));
            tempPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            JLabel tempLabel = new JLabel(String.valueOf(i));
            tempPanel.add(tempLabel);
            mainPanel.add(tempPanel, constraints);
        }

//        outerPanel.add(innerPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Set bound based on frame size
        scrollPane.setBounds(
                0,
                frameSize[1] * 2 / 10,
                frameSize[0] * 193 / 200,
                frameSize[1] * 63 / 100
        );
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
        new MainFrame(size, "Hi", "ID");
    }
}
