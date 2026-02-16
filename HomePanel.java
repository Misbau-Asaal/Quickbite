import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HomePanel extends JPanel {

    private ArrayList<Product> products;
    private JButton orderButton;
    private NumberFormat currencyFormat;
    private MainFrame mainFrame; // Reference to the MainFrame

    //Color Palette
    private Color backgroundColor = new Color(245, 245, 220); // Light Beige
    private Color primaryColor = new Color(210, 180, 140);    // Tan
    private Color accentColor = new Color(139, 69, 19);       // Dark Brown (SaddleBrown)
    private Font primaryFont = new Font("Arial", Font.PLAIN, 16);
    private Font sloganFont = new Font("Arial", Font.ITALIC, 20);

    public HomePanel(MainFrame mainFrame) { // Constructor now takes a MainFrame object
        this.mainFrame = mainFrame; //Store the reference to the MainFrame

        //Initialize the product list (replace with your actual data)
        products = new ArrayList<>();
        products.add(new Product("Cheeseburger", "Classic cheeseburger with lettuce, tomato, and onion", 5.99, "images/cheeseburger.png"));
        products.add(new Product("Pizza", "Large cheese pizza", 12.99, "images/pizza.png"));
        products.add(new Product("Coke", "12 oz can of Coke", 1.50, "images/coke.png"));
        products.add(new Product("Fries", "Large fries", 3.00, "images/fries.png"));
        products.add(new Product("Chicken Burger", "Grilled chicken burger with mayo and lettuce", 6.50, "images/chicken_burger.png"));
        products.add(new Product("Noodles", "Stir-fried noodles with vegetables", 8.00, "images/noodles.png"));
        products.add(new Product("Ice Cream", "Vanilla ice cream with chocolate sauce", 4.00, "images/ice_cream.png"));
        products.add(new Product("Salad", "Fresh garden salad with vinaigrette", 7.50, "images/salad.png"));

        //Currency formatting for Malaysian Ringgit
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("ms", "MY")); // Malaysian locale

        setLayout(new BorderLayout());
        setBackground(backgroundColor);

        //Slogan Panel
        JPanel sloganPanel = new JPanel();
        sloganPanel.setBackground(backgroundColor);
        JLabel sloganLabel = new JLabel("QuickBite: Taste the Difference!");
        sloganLabel.setFont(sloganFont);
        sloganLabel.setForeground(accentColor);
        sloganPanel.add(sloganLabel);
        add(sloganPanel, BorderLayout.NORTH);

        //Product Display Panel
        JPanel productPanel = new JPanel(new GridLayout(2, 4, 10, 10)); // 2 rows, 4 columns, 10px gaps
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        productPanel.setBackground(backgroundColor);
        Border border = BorderFactory.createLineBorder(accentColor, 2);
        productPanel.setBorder(BorderFactory.createTitledBorder(border, "Menu", 0, 0, primaryFont, accentColor));

      for (Product product : products) {
       JPanel itemPanel = new JPanel();
       itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
       itemPanel.setBackground(backgroundColor);
       itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Optional: reduce outer spacing
   
       // Load image
       ImageIcon imageIcon = new ImageIcon(getClass().getResource("/" + product.getImagePath()));
       Image image = imageIcon.getImage().getScaledInstance(120, 90, Image.SCALE_SMOOTH); // Adjusted size
       JLabel imageLabel = new JLabel(new ImageIcon(image));
       imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
   
       // Product name
       JLabel nameLabel = new JLabel(product.getName());
       nameLabel.setFont(primaryFont);
       nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
   
       // Product price
       JLabel priceLabel = new JLabel(currencyFormat.format(product.getPrice()));
       priceLabel.setFont(primaryFont);
       priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
   
       // Add to panel with tighter vertical spacing
       itemPanel.add(imageLabel);
       itemPanel.add(Box.createVerticalStrut(5)); // small gap
       itemPanel.add(nameLabel);
       itemPanel.add(Box.createVerticalStrut(3));
       itemPanel.add(priceLabel);
   
       productPanel.add(itemPanel);
   }

        add(productPanel, BorderLayout.CENTER);

        // Order Button
        orderButton = new JButton("Place Order");
        orderButton.setBackground(primaryColor);
        orderButton.setForeground(Color.WHITE);
        orderButton.setFont(primaryFont);
        orderButton.setFocusPainted(false); //Remove the focus border
        add(orderButton, BorderLayout.SOUTH);

        // Action Listener for Order Button
        orderButton.addActionListener(e -> {
            mainFrame.showOrderForm(); //Call the showOrderForm method in MainFrame
        });
    }

    public JButton getOrderButton() {
        return orderButton;
    }
    public ArrayList<Product> getProducts() {
       return products;
    }
}