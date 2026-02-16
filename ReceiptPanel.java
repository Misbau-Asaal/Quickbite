import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;

public class ReceiptPanel extends JPanel {

    private Color backgroundColor = new Color(245, 245, 220);
    private Color accentColor = new Color(139, 69, 19);
    private Font primaryFont = new Font("Arial", Font.PLAIN, 16);

    private JTextArea receiptArea;
    private JButton backButton;
    private JButton printButton;

    public ReceiptPanel(String receiptText, Runnable backAction) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);

        JLabel titleLabel = new JLabel("Receipt", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(accentColor);
        add(titleLabel, BorderLayout.NORTH);

        receiptArea = new JTextArea(receiptText);
        receiptArea.setFont(primaryFont);
        receiptArea.setEditable(false);
        receiptArea.setBackground(backgroundColor);
        receiptArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(backgroundColor);

        // 🔁 Print Button
        printButton = new JButton("Print Receipt");
        printButton.setFont(primaryFont);
        printButton.setBackground(accentColor);
        printButton.setForeground(Color.WHITE);
        printButton.setFocusPainted(false);
        printButton.addActionListener(e -> printReceipt());

        // 🔙 Back Button
        backButton = new JButton("Back to Home");
        backButton.setFont(primaryFont);
        backButton.setBackground(accentColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> backAction.run());

        buttonPanel.add(printButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // ✅ Print method
    private void printReceipt() {
        try {
            boolean done = receiptArea.print();
            if (done) {
                JOptionPane.showMessageDialog(this, "Receipt sent to printer!", "Print Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Printing cancelled.", "Print Cancelled", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Printing failed: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
