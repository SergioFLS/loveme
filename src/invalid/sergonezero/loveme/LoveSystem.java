package invalid.sergonezero.loveme;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import javax.microedition.lcdui.Display;

public class LoveSystem {
    static LoveMEMIDlet midlet = null;
    public static LuaTable init(LoveMEMIDlet m) {
        midlet = m;
        LuaTable system = LuaTable.tableOf();
        system.set("vibrate", new vibrate());
        return system;
    }

    static final class vibrate extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            int duration = 500;
            LuaValue arg1 = args.arg1();
            if (!arg1.isnil()) {
                duration = (int)(arg1.todouble() * 1000.0);
            }
            Display.getDisplay(midlet).vibrate(duration);
            return LuaValue.NIL;
        }
    }
}
