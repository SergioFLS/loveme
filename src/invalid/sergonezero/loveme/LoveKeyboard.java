package invalid.sergonezero.loveme;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class LoveKeyboard {
    static MyCanvas myCanvas;
    public static LuaTable init(LoveMEMIDlet m) {
        myCanvas = m.myCanvas;
        LuaTable keyboard = LuaTable.tableOf();
        keyboard.set("isDown", new isDown());
        return keyboard;
    }

    static final class isDown extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String key = args.arg1().tojstring();
            boolean isDown = false;

            if (key.equals("kp1")) isDown = myCanvas.keyPresses[0];
            else if (key.equals("kp2")) isDown = myCanvas.keyPresses[1];
            else if (key.equals("kp3")) isDown = myCanvas.keyPresses[2];
            else if (key.equals("kp4")) isDown = myCanvas.keyPresses[3];
            else if (key.equals("kp5")) isDown = myCanvas.keyPresses[4];
            else if (key.equals("kp6")) isDown = myCanvas.keyPresses[5];
            else if (key.equals("kp7")) isDown = myCanvas.keyPresses[6];
            else if (key.equals("kp8")) isDown = myCanvas.keyPresses[7];
            else if (key.equals("kp9")) isDown = myCanvas.keyPresses[8];

            return LuaValue.valueOf(isDown);
        }
    }
}
