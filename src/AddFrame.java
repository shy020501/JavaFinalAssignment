import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFrame extends JFrame {
    private int[] frameSize = {0, 0}; // Stores size of the frame
    private String frameTitle; // Stores name of the frame
    String userID; // Stores ID of the user so that the folder could be accessed
    private Color themeColor = new Color(240, 135, 132);
    private int isSpending = 1; // 0: Saving, 1: Spending

    private JButton savingButton;
    private JButton spendingButton;
    private JPanel savingPanel;
    private JPanel spendingPanel;

    public AddFrame(int[] size, String title, String ID)
    {
        // Get frame's size, title, and userID as parameter and use them for setting the frame
        frameSize[0] = size[0];
        frameSize[1] = size[1];
        frameTitle = title;
        userID = ID;

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

                remove(savingPanel); // Remove saving panel
                add(spendingPanel); // Show spending panel
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

                remove(spendingPanel); // Remove spending panel
                add(savingPanel); // Show saving panel
            }
        });
        headerPanel.add(savingButton);

        spendingPanel = new JPanel(); // Store components for spending record
        spendingPanel.setLayout(null); // Layout set to null in order to place components based on coordinates
        spendingPanel.setBounds(
                0,
                frameSize[1] / 10,
                frameSize[0] * 193 / 200,
                frameSize[1] * 9 / 10
        );
        add(spendingPanel);

        savingPanel = new JPanel();

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
        new AddFrame(size, title, "ID");
    }
}
