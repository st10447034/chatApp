package chatapp.pt1.pkg2;

import java.util.Scanner;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        // ===== PART 1: Registration & Login =====
        try (Scanner sc = new Scanner(System.in)) {
            // ===== PART 1: Registration & Login =====
            Registration registration = new Registration();
            Login login = new Login(registration);
            
            System.out.println("===== REGISTRATION =====");
            System.out.print("Enter first name: ");
            registration.setFirstName(sc.nextLine());
            
            System.out.print("Enter last name: ");
            registration.setLastName(sc.nextLine());
            
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            
            System.out.print("Enter cell number (e.g. +27838968976): ");
            String cell = sc.nextLine();
            
            String regMsg = registration.registerUser(username, password, cell);
            System.out.println(regMsg);
            
            if (!regMsg.contains("successfully")) {
                System.out.println("Registration failed. Exiting.");
                return;
            }
            
            System.out.println("\n===== LOGIN =====");
            System.out.print("Enter username: ");
            String loginUser = sc.nextLine();
            System.out.print("Enter password: ");
            String loginPass = sc.nextLine();
            
            String loginStatus = login.returnLoginStatus(loginUser, loginPass);
            System.out.println(loginStatus);
            
            if (!loginStatus.startsWith("Welcome")) {
                System.out.println("Login failed. You cannot send messages.");
                return;
            }
            
            // ===== PART 2: QuickChat =====
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
            
            boolean running = true;
            while (running) {                                      // Farrell, 2019 – while loop
                String menu = """
                              Please choose an option:
                              1) Send Messages
                              2) Show recently sent messages
                              3) Quit""";
                
                String choice = JOptionPane.showInputDialog(menu);
                
                if (choice == null) break;
                
                switch (choice) {
                    case "1" -> sendMessages();
                    case "2" -> JOptionPane.showMessageDialog(null, "Coming Soon.");
                    case "3" -> {
                        running = false;
                        JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!");
                    }
                    default -> JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
                }
            }
        }
    }

    // Method that handles sending a batch of messages
    private static void sendMessages() {
        String numStr = JOptionPane.showInputDialog("How many messages do you wish to send?");
        if (numStr == null) return;

        int numMessages;
        try {
            numMessages = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            return;
        }

        Message messageHandler = new Message();

        for (int i = 0; i < numMessages; i++) {               // Farrell, 2019 – for loop
            Message msg = new Message();
            msg.generateMessageID();

            // Recipient
            String recipient = JOptionPane.showInputDialog("Enter recipient cell number (with international code):");
            if (msg.checkRecipientCell(recipient) == 0) {
                JOptionPane.showMessageDialog(null,
                    "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                i--; // allow user to try again
                continue;
            }
            msg.setRecipient(recipient);

            // Message text
            String text = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
            if (text == null) continue;

            if (text.length() > 250) {
                int excess = text.length() - 250;
                JOptionPane.showMessageDialog(null,
                    "Message exceeds 250 characters by " + excess + ", please reduce size.");
                i--;
                continue;
            }

            msg.setMessageText(text);
            msg.createMessageHash();

            // Ask Send / Disregard / Store
            String result = msg.SentMessage();
            JOptionPane.showMessageDialog(null, result);

            // If sent, show full details
            if (result.contains("successfully sent")) {
                JOptionPane.showMessageDialog(null, msg.getMessageDetails());
            }
        }

        // Show total after all messages
        JOptionPane.showMessageDialog(null,
            "Total messages sent: " + messageHandler.returnTotalMessages());
    }
}