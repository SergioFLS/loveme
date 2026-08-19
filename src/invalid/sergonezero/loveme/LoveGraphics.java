package invalid.sergonezero.loveme;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.io.IOException;

public class LoveGraphics {
    static MyCanvas myCanvas = null;
    public static LuaTable init(LoveMEMIDlet m) {
        myCanvas = m.myCanvas;
        LuaTable graphics = LuaTable.tableOf();
        graphics.set("rectangle", new rectangle());
        graphics.set("getWidth", new getWidth());
        graphics.set("getHeight", new getHeight());
        graphics.set("print", new print());
        graphics.set("newImage", new newImage());
        graphics.set("draw", new draw());
        graphics.set("setColor", new setColor());
        graphics.set("setBackgroundColor", new setBackgroundColor());

        return graphics;
    }

    static final class print extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String s = args.arg1().toString();
            int x = args.arg(2).toint();
            int y = args.arg(3).toint();

            Graphics g = myCanvas.getMyGraphics();
            MyCanvas.setColor(g);
            g.drawString(s, x, y, 0);

            return LuaValue.NIL;
        }
    }
    static final class rectangle extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String mode = args.arg1().tojstring();
            int x = args.arg(2).toint();
            int y = args.arg(3).toint();
            int width = args.arg(4).toint();
            int height = args.arg(5).toint();

            Graphics g = myCanvas.getMyGraphics();
            MyCanvas.setColor(g);
            if (mode.equals("fill")) {
                g.fillRect(x, y, width, height);
            } else {
                g.drawRect(x, y, width, height);
            }
            return LuaValue.NIL;
        }
    }

    static final class getWidth extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            return LuaValue.valueOf(myCanvas.getWidth());
        }
    }

    static final class getHeight extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            return LuaValue.valueOf(myCanvas.getHeight());
        }
    }

    static final class newImage extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String path = args.arg1().tojstring();

            Image i;
            try {
                i = Image.createImage("/game/" + path);
            } catch (IOException e) {
                e.printStackTrace();
                return LuaValue.NIL;
            }

            return LuaValue.userdataOf(i);
        }
    }
    static final class draw extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            Image i = (Image)args.arg1().touserdata();
            int x = args.arg(2).toint();
            int y = args.arg(3).toint();

            Graphics g = myCanvas.getMyGraphics();
            g.drawImage(i, x, y, 0);

            return LuaValue.NIL;
        }
    }

    static final class setColor extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            int red = (int)(args.arg1().todouble() * 255.0);
            int green = (int)(args.arg(2).todouble() * 255.0);
            int blue = (int)(args.arg(3).todouble() * 255.0);

            myCanvas.color[0] = red;
            myCanvas.color[1] = green;
            myCanvas.color[2] = blue;

            return LuaValue.NIL;
        }
    }

    static final class setBackgroundColor extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            int red = (int)(args.arg1().todouble() * 255.0);
            int green = (int)(args.arg(2).todouble() * 255.0);
            int blue = (int)(args.arg(3).todouble() * 255.0);

            myCanvas.backgroundColor[0] = red;
            myCanvas.backgroundColor[1] = green;
            myCanvas.backgroundColor[2] = blue;

            return LuaValue.NIL;
        }
    }
}
