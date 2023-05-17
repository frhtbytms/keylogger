import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    private JTextArea logArea;
    private JTextField emailField;
    private JTextField intervalField;
    private JTextField maxFileSizeField;
    private File logFile;
    private BufferedWriter writer;
    private long MAX_LOG_SIZE = 5 * 1024 * 1024; // 5 MB
    private long LOG_SEND_INTERVAL = 5 * 60 * 1000; // 5 minutes
    private boolean isRunning = false;

    public Main() {
        // Set up the main JFrame
        setTitle("KeyLogger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        // Create the logArea where the logged events will be displayed
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create the buttonPanel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create the "Clear Log" button
        JButton clearButton = new JButton("Clear Log");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        // Create the "Durdur" (Stop) button
        JButton stopButton = new JButton("Durdur");
        stopButton.addActionListener(this);
        buttonPanel.add(stopButton);

        // Create the "Başlat" (Start) button
        JButton startButton = new JButton("Başlat");
        startButton.addActionListener(this);
        buttonPanel.add(startButton);

        // Create the configPanel to hold the configuration fields
        JPanel configPanel = new JPanel(new GridLayout(4, 2));

        // Create labels and text fields for email, interval, and max file size
        JLabel emailLabel = new JLabel("E-posta Adresi:");
        emailField = new JTextField();
        JLabel intervalLabel = new JLabel("Gönderim Sıklığı (dakika):");
        intervalField = new JTextField();
        JLabel maxFileSizeLabel = new JLabel("Maksimum Dosya Boyutu (MB):");
        maxFileSizeField = new JTextField();

        // Add the labels and text fields to the configPanel
        configPanel.add(emailLabel);
        configPanel.add(emailField);
        configPanel.add(intervalLabel);
        configPanel.add(intervalField);
        configPanel.add(maxFileSizeLabel);
        configPanel.add(maxFileSizeField);

        // Add the buttonPanel and configPanel to the main JFrame
        add(buttonPanel, BorderLayout.SOUTH);
        add(configPanel, BorderLayout.NORTH);

        // Create a log file object
        logFile = new File("Log.txt");

        // Register the listeners for key events, mouse events, and mouse motion events
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        // Make the JFrame visible
        setVisible(true);
    }

    // ActionListener method to handle button clicks
    private boolean startButtonEnabled = true;
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Clear Log")) {
            clearLogFile();
        } else if (e.getActionCommand().equals("Durdur")) {
            isRunning = false;
            removeListeners();
        } else if (e.getActionCommand().equals("Başlat")) {
            // Get the values from the email, interval, and max file size fields
            String email = emailField.getText();
            String intervalText = intervalField.getText();
            String maxFileSizeText = maxFileSizeField.getText();

            // Check if any of the fields are empty
            if (email.isEmpty() || intervalText.isEmpty() || maxFileSizeText.isEmpty()) {
                // Display a warning message if any field is empty
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Parse the interval and max file size values
                int interval = Integer.parseInt(intervalText);
                int maxFileSize = Integer.parseInt(maxFileSizeText);

                // Start logging with the provided values
                startLogging(email, interval, maxFileSize);
            } catch (NumberFormatException ex) {
                // Display an error message if the interval or max file size is not a valid number
                JOptionPane.showMessageDialog(this, "Geçersiz sayı girişi.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to remove listeners from logArea
    private void removeListeners() {
        logArea.removeMouseListener(this);
        logArea.removeMouseMotionListener(this);
        logArea.removeKeyListener(this);
    }

// KeyListener methods

    // Called when a key is pressed
    public void keyPressed(KeyEvent e) {
        if (isRunning)
            logEvent("Key Pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
    }

    // Called when a key is released
    public void keyReleased(KeyEvent e) {
        if (isRunning)
            logEvent("Key Released: " + KeyEvent.getKeyText(e.getKeyCode()));
    }

    // Called when a key is typed (pressed and released)
    public void keyTyped(KeyEvent e) {
        if (isRunning)
            logEvent("Key Typed: " + e.getKeyChar());
    }

// MouseListener methods

    // Called when the mouse is clicked
    public void mouseClicked(MouseEvent e) {
        if (isRunning)
            logEvent("Mouse Clicked: x=" + e.getX() + ", y=" + e.getY());
    }

    // Called when a mouse button is pressed
    public void mousePressed(MouseEvent e) {
        if (isRunning)
            logEvent("Mouse Pressed: x=" + e.getX() + ", y=" + e.getY());
    }

    // Called when a mouse button is released
    public void mouseReleased(MouseEvent e) {
        if (isRunning)
            logEvent("Mouse Released: x=" + e.getX() + ", y=" + e.getY());
    }

    // Called when the mouse enters a component
    public void mouseEntered(MouseEvent e) {
        if (isRunning)
            logEvent("Mouse Entered: x=" + e.getX() + ", y=" + e.getY());
    }

    // Called when the mouse exits a component
    public void mouseExited(MouseEvent e) {
        if (isRunning)
            logEvent("Mouse Exited: x=" + e.getX() + ", y=" + e.getY());
    }

// MouseMotionListener methods

    // Called when the mouse is dragged (moved with a button pressed)
    public void mouseDragged(MouseEvent e) {
        if (isRunning)
            logEvent("Mouse Dragged: x=" + e.getX() + ", y=" + e.getY());
    }

    // Called when the mouse is moved (without any buttons pressed)
    public void mouseMoved(MouseEvent e) {
        if (isRunning)
            logEvent("Mouse Moved: x=" + e.getX() + ", y=" + e.getY());
    }

    // Method to log an event to the logArea
    public void logEvent(String event) {
        logArea.append(event + "\n");
        if (logFile.length() > MAX_LOG_SIZE) {
            sendLog();
            clearLogFile();
        }
    }

    // Method to send the log file
    public void sendLog() {
        // Log dosyasını belirtilen e-posta adresine göndermek için gerekli işlemleri yapın
        // ...
    }

    // Method to clear the log file and logArea
    public void clearLogFile() {
        logArea.setText("");
        try {
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to start the logging process
    public void startLogging(String email, int interval, int maxFileSize) {
        // Ayarlanan e-posta adresini ve değerleri kullanarak loglama işlemini başlatın
        // ...
        isRunning = true;
        emailField.setEditable(false);
        intervalField.setEditable(false);
        maxFileSizeField.setEditable(false);
        startButtonEnabled = false;

        LOG_SEND_INTERVAL = interval * 60 * 1000;
        MAX_LOG_SIZE = maxFileSize * 1024 * 1024;

        emailField.setText(email);
        intervalField.setText(String.valueOf(interval));
        maxFileSizeField.setText(String.valueOf(maxFileSize));

        emailField.setEnabled(false);
        intervalField.setEnabled(false);
        maxFileSizeField.setEnabled(false);
    }

    public static void main(String[] args) {
        // Create an instance of the Main class to start the application
        new Main();
    }
}


