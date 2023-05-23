import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class MainFrame extends JFrame {

    private int[] frameSize = {0, 0}; // Stores size of the frame
    private String frameTitle; // Stores name of the frame
    String userID; // Stores ID of the user so that the folder could be accessed
    private Color[] amountColor = {Color.BLUE, Color.RED}; // Stores color of saving/spending label

    private JPanel createRecordPanel(String category, String description, int isSpending, int amount)
    {
        JPanel recordPanel = new JPanel(); // Store panel of each record
        recordPanel.setLayout(null); // Set layout to null
        recordPanel.setPreferredSize(new Dimension(frameSize[0] * 95 / 100, frameSize[1] / 15));
        recordPanel.setBorder(BorderFactory.createLineBorder(Color.black));

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
                frameSize[0] / 60 + frameSize[0] / 5,
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

        JPanel mainPanel = new JPanel(); // Stores main panel where all the spending details will be displayed
        mainPanel.setLayout(new GridBagLayout()); // Layout set to GridBagLayout in order to place components in a row
        GridBagConstraints constraints = new GridBagConstraints(); // Store constraints for GridBagLayout
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        constraints.fill = GridBagConstraints.BOTH; // The component of mainPanel fills the whole panel

        for(int i = 0; i < 100; i++)
        {
            constraints.gridx = 0; // Set to 0 as there's one component in a column
            constraints.gridy = i; // Set to i as components would line up in a row
            constraints.gridwidth = 1;
            constraints.gridheight = 1;

            int isSpending = (i % 2 == 0 ? 0 : 1); // 0: saving, 1: spending

            JPanel tempPanel = createRecordPanel("Entertainment", "Baseball game held in Olympic Stadium", 1, 100000);

            mainPanel.add(tempPanel, constraints);
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
        String title = "Financial Ledger";
        new MainFrame(size, title, "ID");
    }
}
