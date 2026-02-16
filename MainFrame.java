import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private HomePanel homePanel;
    private OrderFormPanel orderFormPanel; // This will be re-initialized dynamically

    public MainFrame() {
        setTitle("QuickBite");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the window

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        homePanel = new HomePanel(this); // Pass the MainFrame instance to HomePanel
        add(homePanel, "home");

        // We will create and add orderFormPanel later dynamically when needed

        cardLayout.show(getContentPane(), "home"); // Show the HomePanel initially
    }

    // Dynamically creates and shows OrderFormPanel with products
    public void showOrderForm() {
        if (orderFormPanel != null) {
            remove(orderFormPanel); // Remove old version (if any)
        }

        orderFormPanel = new OrderFormPanel(homePanel.getProducts()); // Pass product list
        //orderFormPanel = new OrderFormPanel();

        add(orderFormPanel, "order");

        cardLayout.show(getContentPane(), "order");
        revalidate(); // Refresh layout
        repaint();
    }
    private ReceiptPanel receiptPanel;

public void showReceipt(String receiptText) {
    if (receiptPanel != null) {
        remove(receiptPanel); // Remove old one
    }

    receiptPanel = new ReceiptPanel(receiptText, () -> {
        cardLayout.show(getContentPane(), "home");
    });

    add(receiptPanel, "receipt");
    cardLayout.show(getContentPane(), "receipt");
    revalidate();
    repaint();
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
