package invalid.sergonezero.loveme;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jme.JmePlatform;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;

public class LovemePlatform {
    static MyCanvas myCanvas;
    public static Globals standardGlobals(LoveMEMIDlet m) {
        myCanvas = m.myCanvas;
        Globals globals = JmePlatform.standardGlobals();
        LuaTable love = LuaTable.tableOf();

        love.set("graphics", LoveGraphics.init(m));
        love.set("system", LoveSystem.init(m));
        love.set("keyboard", LoveKeyboard.init(m));
        globals.set("love", love);

        return globals;
    }
}