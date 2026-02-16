import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderFormPanel extends JPanel {

    private Color backgroundColor = new Color(245, 245, 220); // Light Beige
    private Color primaryColor = new Color(210, 180, 140);    // Tan
    private Color accentColor = new Color(139, 69, 19);       // Dark Brown (SaddleBrown)
    private Font primaryFont = new Font("Arial", Font.PLAIN, 16);
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("ms", "MY"));

    private ArrayList<Product> products;
    private ArrayList<JSpinner> quantitySpinners = new ArrayList<>();
    private ArrayList<JLabel> subtotalLabels = new ArrayList<>();
    private JLabel totalLabel;
    private JTextField dateField;
    public OrderFormPanel(ArrayList<Product> products) {
        this.products = products;

        setLayout(new BorderLayout());
        setBackground(backgroundColor);

        // Title
        JLabel titleLabel = new JLabel("Order Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(accentColor);
        add(titleLabel, BorderLayout.NORTH);

        // 🗓️ Date Panel
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        datePanel.setBackground(backgroundColor);
        JLabel dateLabel = new JLabel("Order Date: ");
        dateLabel.setFont(primaryFont);
        dateField = new JTextField(10);
        dateField.setFont(primaryFont);
        dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        dateField.setEditable(false);
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        add(datePanel, BorderLayout.BEFORE_FIRST_LINE);

        // 📦 Items Panel
        JPanel itemsPanel = new JPanel(new GridLayout(products.size(), 1, 5, 5));
        itemsPanel.setBackground(backgroundColor);

        for (Product product : products) {
            JPanel itemRow = new JPanel(new BorderLayout(10, 10));
            itemRow.setBackground(backgroundColor);
            itemRow.setBorder(BorderFactory.createLineBorder(accentColor));

            // 📸 Image
            JLabel imageLabel;
            try {
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/" + product.getImagePath()));
                Image scaled = imageIcon.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(scaled));
            } catch (Exception e) {
                imageLabel = new JLabel("No image");
            }

            // ℹ️ Info Panel
            JPanel infoPanel = new JPanel(new GridLayout(2, 2));
            infoPanel.setBackground(backgroundColor);
            JLabel nameLabel = new JLabel(product.getName());
            nameLabel.setFont(primaryFont);
            JLabel priceLabel = new JLabel("Price: " + currencyFormat.format(product.getPrice()));
            priceLabel.setFont(primaryFont);
            JLabel qtyLabel = new JLabel("Qty:");
            qtyLabel.setFont(primaryFont);
            JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            quantitySpinner.setFont(primaryFont);
            quantitySpinners.add(quantitySpinner);

            infoPanel.add(nameLabel);
            infoPanel.add(priceLabel);
            infoPanel.add(qtyLabel);
            infoPanel.add(quantitySpinner);

            // 💰 Subtotal
            JLabel subtotalLabel = new JLabel("Subtotal: " + currencyFormat.format(0));
            subtotalLabel.setFont(primaryFont);
            subtotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            subtotalLabels.add(subtotalLabel);

            // 🔄 Update subtotal on qty change
            quantitySpinner.addChangeListener(e -> updateTotals());

            itemRow.add(imageLabel, BorderLayout.WEST);
            itemRow.add(infoPanel, BorderLayout.CENTER);
            itemRow.add(subtotalLabel, BorderLayout.SOUTH);

            itemsPanel.add(itemRow);
        }

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setBackground(backgroundColor);
        add(scrollPane, BorderLayout.CENTER);

        // 🧾 Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(backgroundColor);

        totalLabel = new JLabel("Total: " + currencyFormat.format(0), SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(accentColor);
        bottomPanel.add(totalLabel, BorderLayout.NORTH);

        JButton submitButton = new JButton("Submit Order");
        submitButton.setBackground(primaryColor);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(primaryFont);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> handleSubmit());
        bottomPanel.add(submitButton, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateTotals() {
        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            int quantity = (int) quantitySpinners.get(i).getValue();
            double price = products.get(i).getPrice();
            double subtotal = quantity * price;
            subtotalLabels.get(i).setText("Subtotal: " + currencyFormat.format(subtotal));
            total += subtotal;
        }
        totalLabel.setText("Total: " + currencyFormat.format(total));
    }

 private void handleSubmit() {
    StringBuilder receipt = new StringBuilder("Order Summary:\n\n");
    double total = 0;
    boolean hasItems = false; // ✅ flag to track order contents

    for (int i = 0; i < products.size(); i++) {
        int qty = (int) quantitySpinners.get(i).getValue();
        if (qty > 0) {
            hasItems = true; // ✅ item found
            Product p = products.get(i);
            double subtotal = qty * p.getPrice();
            receipt.append(p.getName())
                    .append(" x").append(qty)
                    .append(" = ").append(currencyFormat.format(subtotal)).append("\n");
            total += subtotal;
        }
    }

    // ✅ IF EMPTY — show warning and stop here
    if (!hasItems) {
        JOptionPane.showMessageDialog(this,
                "You haven’t selected any items.\nPlease choose at least one to place an order.",
                "No Items Selected",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    // ✅ Continue normally
    String date = dateField.getText();
    receipt.append("\nTotal: ").append(currencyFormat.format(total));
   receipt.append("\nDate: ").append(date);
   
   // ✅ Add branding block here
   receipt.append("\n\nThank you for ordering!\n");
   receipt.append("QuickBite\n");
   receipt.append("Sebrang Ramai, Jaya Fasa 03, 02000, Kuala Perlis, Malaysia\n");
   receipt.append("Tel: +60193452030 | Email: quickbite@gmail.com\n");
   receipt.append("Facebook | Instagram | Twitter: @QuickBite\n");


    String receiptText = receipt.toString();
    saveReceiptToFile(receiptText);
    ((MainFrame) SwingUtilities.getWindowAncestor(this)).showReceipt(receiptText);
    
}

private void saveReceiptToFile(String content) {
    try {
        String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new java.util.Date());
        java.io.File folder = new java.io.File("receipts");
        if (!folder.exists()) {
            folder.mkdir(); // create receipts folder if it doesn't exist
        }

        java.io.File file = new java.io.File(folder, "receipt_" + timestamp + ".txt");
        java.io.FileWriter writer = new java.io.FileWriter(file);
        writer.write(content);
        writer.close();

        System.out.println("Receipt saved to: " + file.getAbsolutePath());
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}
