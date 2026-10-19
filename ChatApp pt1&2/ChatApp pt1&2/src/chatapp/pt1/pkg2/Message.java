package chatapp.pt1.pkg2;   // change to your package name

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Message class for QuickChat application.
 * Concepts used: loops, String manipulation, Random numbers
 * (Farrell, 2019, Chapters 4 & 5).
 */
public class Message {

    private String messageID;
    private final int messageNumber;          // auto-incremented
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int totalMessages = 0;
    private static final ArrayList<String> sentMessages = new ArrayList<>();

    // Constructor
    public Message() {
        this.messageNumber = totalMessages;
    }

    // ========== 1. checkMessageID() ==========
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // ========== 2. checkRecipientCell() ==========
    // Returns 1 if valid, 0 if invalid (as required by method signature)
    public int checkRecipientCell(String cell) {
        if (cell == null) return 0;
        // Must start with + and be reasonable length (international code)
        if (cell.startsWith("+") && cell.length() <= 13) {
            return 1;
        }
        return 0;
    }

    // ========== 3. createMessageHash() ==========
    public String createMessageHash() {
        if (messageID == null || messageText == null) {
            return "";
        }

        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        // Remove any non-letter characters from first/last word
        firstWord = firstWord.replaceAll("[^a-zA-Z]", "");
        lastWord = lastWord.replaceAll("[^a-zA-Z]", "");

        messageHash = (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        return messageHash;
    }

    // ========== 4. SentMessage() ==========
    public String SentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "What would you like to do with this message?",
                "Send Message",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0: // Send
                totalMessages++;
                sentMessages.add(getMessageDetails());
                return "Message successfully sent.";
            case 1: // Disregard
                return "Press 0 to delete message.";
            case 2: // Store
                storeMessage();
                return "Message successfully stored.";
            default:
                return "No action selected.";
        }
    }

    // ========== 5. printMessages() ==========
    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (String msg : sentMessages) {
            sb.append(msg).append("\n\n");
        }
        return sb.toString();
    }

    // ========== 6. returnTotalMessages() ==========
    public int returnTotalMessages() {
        return totalMessages;
    }

    // ========== 7. storeMessage() – stores in JSON format ==========
    // Method created with assistance from ChatGPT (OpenAI)
    // Reference: OpenAI. (2023). ChatGPT [Large language model]. https://chat.openai.com
    public void storeMessage() {
        try (FileWriter file = new FileWriter("storedMessages.json", true)) {
            String json = String.format(
                "{\"MessageID\":\"%s\",\"MessageHash\":\"%s\",\"Recipient\":\"%s\",\"Message\":\"%s\"}%n",
                messageID, messageHash, recipient, messageText
            );
            file.write(json);
            file.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error storing message: " + e.getMessage());
        }
    }

    // ========== Helper methods ==========
    public void generateMessageID() {
        Random rnd = new Random();                     // Farrell, 2019 – Random class
        long number = 1000000000L + (long)(rnd.nextDouble() * 9000000000L);
        this.messageID = String.valueOf(number);
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageDetails() {
        return "MessageID: " + messageID +
               "\nMessage Hash: " + messageHash +
               "\nRecipient: " + recipient +
               "\nMessage: " + messageText;
    }

    // Getters needed for tests
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}
