import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main application class for demonstrating CardLayout navigation.
 * This class sets up the JFrame, initializes the CardLayout, and
 * manages navigation between different card views.
 */
public class CardLayoutExample extends JFrame {

    // CardLayout instance to manage switching between cards
    private CardLayout cardLayout;

    // Main panel that holds all the cards
    private JPanel cardPanel;

    // Constants for card names (used by CardLayout)
    private static final String CARD_ONE = "Card One";
    private static final String CARD_TWO = "Card Two";
    private static final String CARD_THREE = "Card Three";

    /**
     * Constructor: Initializes the main frame and sets up the UI.
     */
    public CardLayoutExample() {
        setTitle("CardLayout Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        // Initialize CardLayout and main card panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add the three cards to the card panel
        cardPanel.add(createCard(CARD_ONE, Color.PINK), CARD_ONE);
        cardPanel.add(createCard(CARD_TWO, Color.CYAN), CARD_TWO);
        cardPanel.add(createCard(CARD_THREE, Color.LIGHT_GRAY), CARD_THREE);

        // Add navigation buttons at the bottom
        JPanel buttonPanel = createNavigationPanel();

        // Add components to the frame
        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates a JPanel representing a single card view.
     *
     * @param cardName The name to display on the card.
     * @param bgColor  The background color of the card.
     * @return A JPanel configured as a card.
     */
    private JPanel createCard(String cardName, Color bgColor) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new GridBagLayout()); // Center the label

        JLabel label = new JLabel(cardName);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label);

        return panel;
    }

    /**
     * Creates the navigation panel with buttons to switch between cards.
     *
     * @return A JPanel containing navigation buttons.
     */
    private JPanel createNavigationPanel() {
        JPanel panel = new JPanel();

        // Create navigation buttons
        JButton btnCardOne = new JButton("Show Card One");
        JButton btnCardTwo = new JButton("Show Card Two");
        JButton btnCardThree = new JButton("Show Card Three");

        // Add action listeners to handle card switching
        btnCardOne.addActionListener(new CardButtonListener(CARD_ONE));
        btnCardTwo.addActionListener(new CardButtonListener(CARD_TWO));
        btnCardThree.addActionListener(new CardButtonListener(CARD_THREE));

        // Add buttons to the panel
        panel.add(btnCardOne);
        panel.add(btnCardTwo);
        panel.add(btnCardThree);

        return panel;
    }

    /**
     * Inner class to handle navigation button clicks.
     * Uses the provided card name to switch the view.
     */
    private class CardButtonListener implements ActionListener {
        private String cardName;

        /**
         * Constructor: Stores the card name for navigation.
         *
         * @param cardName The name of the card to display.
         */
        public CardButtonListener(String cardName) {
            this.cardName = cardName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Switch to the selected card
            cardLayout.show(cardPanel, cardName);
        }
    } // <-- closes inner class

    /**
     * Main method: Entry point of the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Ensure GUI creation runs on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            CardLayoutExample frame = new CardLayoutExample();
            frame.setVisible(true);
        });
    }
} //
