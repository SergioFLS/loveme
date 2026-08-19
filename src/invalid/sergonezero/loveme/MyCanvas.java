package invalid.sergonezero.loveme;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import java.io.IOException;

public final class MyCanvas extends GameCanvas {
    public boolean[] keyPresses = {false, false, false, false, false, false, false, false, false};
    public static int[] backgroundColor = {0, 0, 0};
    public static int[] color = {255, 255, 255};

    LuaValue keypressed;
    LuaValue keyreleased;

    protected MyCanvas() {
        super(false);
        setFullScreenMode(true);
        System.err.println("width: " + getWidth() + " height: " + getHeight());
    }

    public void loadCallbacks(LuaTable l) {
        keypressed = l.get("keypressed");
        keyreleased = l.get("keyreleased");
    }

    public Graphics getMyGraphics() {
        return this.getGraphics();
    }

    public static void setColor(Graphics g) {
        g.setColor(color[0],  color[1], color[2]);
    }

    public static void setBackgroundColor(Graphics g) {
        g.setColor(backgroundColor[0],  backgroundColor[1],  backgroundColor[2]);
    }

    protected void keyPressed(int k) {
        String out = gameActionToLoveString(k);
        if (out == null) {
            return;
        }

        pressKey(k);
        if (keypressed.isfunction()) {
            keypressed.call(LuaValue.valueOf(out));
        }
    }

    protected void keyReleased(int k) {
        String out = gameActionToLoveString(k);
        if (out == null) {
            return;
        }

        releaseKey(k);
        if (keyreleased.isfunction()) {
            keyreleased.call(LuaValue.valueOf(out));
        }
    }

    private String gameActionToLoveString(int k) {
        switch (getGameAction(k)) {
            case GAME_C: return "kp1";
            case DOWN: return "kp2";
            case GAME_D: return "kp3";
            case LEFT: return "kp4";
            case FIRE: return "kp5";
            case RIGHT: return "kp6";
            case GAME_A: return "kp7";
            case UP: return "kp8";
            case GAME_B: return "kp9";
            default: return null;
        }
    }

    private void pressKey(int k) {
        switch (getGameAction(k)) {
            case GAME_C: keyPresses[0] = true; break;
            case DOWN: keyPresses[1] = true; break;
            case GAME_D: keyPresses[2] = true; break;
            case LEFT: keyPresses[3] = true; break;
            case FIRE: keyPresses[4] = true; break;
            case RIGHT: keyPresses[5] = true; break;
            case GAME_A: keyPresses[6] = true; break;
            case UP: keyPresses[7] = true; break;
            case GAME_B: keyPresses[8] = true; break;
        }
    }

    private void releaseKey(int k) {
        switch (getGameAction(k)) {
            case GAME_C: keyPresses[0] = false; break;
            case DOWN: keyPresses[1] = false; break;
            case GAME_D: keyPresses[2] = false; break;
            case LEFT: keyPresses[3] = false; break;
            case FIRE: keyPresses[4] = false; break;
            case RIGHT: keyPresses[5] = false; break;
            case GAME_A: keyPresses[6] = false; break;
            case UP: keyPresses[7] = false; break;
            case GAME_B: keyPresses[8] = false; break;
        }
    }
}
