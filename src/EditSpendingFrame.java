import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditSpendingFrame extends AddSpendingFrame{ // Extends AddSpendingFrame
    public EditSpendingFrame(int[] size, String title, String ID, String date, String description, String amount)
    {
        super(size, title, ID);
        super.setFieldText(date, description, amount);

        headerPanel.remove(savingButton);
        headerPanel.remove(spendingButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(
                frameSize[0] * 12 / 60,
                frameSize[1] * 23 / 200 + frameSize[1] * 5 / 10,
                frameSize[0] * 9 / 60,
                frameSize[1] / 23
        );
        deleteButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        deleteButton.setBackground(themeColor);
        deleteButton.setForeground(Color.white);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.white));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame(frameSize, frameTitle, ID);
                timerThread.interrupt();
                dispose();
            }
        });
        add(deleteButton);
    }
}
