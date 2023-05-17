import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Logger implements KeyListener {
    private Main main;

    public Logger(Main main) {
        this.main = main;
    }

    public void keyTyped(KeyEvent e) {
        main.logEvent("Key Typed: " + e.getKeyChar());
    }

    public void keyPressed(KeyEvent e) {
        main.logEvent("Key Pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
    }

    public void keyReleased(KeyEvent e) {
        main.logEvent("Key Released: " + KeyEvent.getKeyText(e.getKeyCode()));
    }
}
