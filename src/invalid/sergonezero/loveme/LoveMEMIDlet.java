package invalid.sergonezero.loveme;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

public class LoveMEMIDlet extends MIDlet implements Runnable {
    private static final long maxFrameRate = (1000L / 60L);
    public MyCanvas myCanvas = null;
    LuaTable love = null;
    boolean started = false;

    protected void startApp() {
        if (started) {
            return;
        }
        started = true;
        myCanvas = new MyCanvas();

        Globals lGlobals = LovemePlatform.standardGlobals(this);
        lGlobals.get("require").call(LuaValue.valueOf("game/main"));
        lGlobals.set("thing", "lol");

        love = (LuaTable)lGlobals.get("love");
        myCanvas.loadCallbacks(love);
        LuaValue load = love.get("load");
        if (!load.isnil() && load.isfunction()) {
            load.call();
        }

        Display.getDisplay(this).setCurrent(myCanvas);
        new Thread(this, "LoveME run").start();
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) {
    }

    public void run() {
        Graphics g = myCanvas.getMyGraphics();
        LuaValue update = love.get("update");
        LuaValue draw = love.get("draw");
        long lastDelta = 0;
        while (true) {
            long delta = System.currentTimeMillis();
            if (!update.isnil())
                update.call(LuaValue.valueOf((double)lastDelta / 1000.0));
            MyCanvas.setBackgroundColor(g);
            g.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
            if (!draw.isnil())
                draw.call();
            myCanvas.flushGraphics();

            delta = System.currentTimeMillis() - delta;
            lastDelta = delta;
            long sleep = maxFrameRate - delta;

            if (sleep > 0) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(sleep);
                } catch (InterruptedException ignored) {
                }
            }

            if (lastDelta < maxFrameRate) {
                lastDelta = maxFrameRate;
            }
        }
    }
}
