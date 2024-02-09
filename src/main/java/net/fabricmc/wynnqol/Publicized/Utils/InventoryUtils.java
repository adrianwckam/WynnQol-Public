package net.fabricmc.wynnqol.Publicized.Utils;

import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import static net.fabricmc.wynnqol.Publicized.main.mc;
public class InventoryUtils {
    public static boolean IsInGui(){
        return mc.currentScreen !=null;
    }
    public static class ClickAction{
        public int slotid;
        public int MouseButton;
        public SlotActionType actionType;
        public ClickAction(int slotid, int MouseButton, SlotActionType actionType){
            this.slotid = slotid;
            this.MouseButton = MouseButton;
            this.actionType = actionType;
        }
    }
    public static void ClickSlot(ClickAction clickAction){
        ClickSlot(clickAction.slotid, clickAction.MouseButton, clickAction.actionType);
    }
    public static void ClickSlot(int slotid, int MouseButton, SlotActionType actionType){
        if(mc.currentScreen==null || !mc.player.currentScreenHandler.isValid(slotid)) return;
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slotid, MouseButton, actionType, mc.player);
    }

    public static Slot getSlot(int slot){
        return mc.player.currentScreenHandler.getSlot(slot);
    }
    public static int  SearchItem(String ItemName,int range,boolean definite){
        if(definite){
            for(int i=0; range>= i; i++){
                if(mc.player.currentScreenHandler.isValid(i) && mc.player.currentScreenHandler.getSlot(i).getStack() !=null && mc.player.currentScreenHandler.getSlot(i) !=null &&  mc.player.currentScreenHandler.getSlot(i).getStack().getName().getString()==ItemName){
                    return i;
                }
            }
        }else{
            for(int i=0; range>= i; i++){
                if(mc.player.currentScreenHandler.isValid(i) && mc.player.currentScreenHandler.getSlot(i).getStack() !=null && mc.player.currentScreenHandler.getSlot(i) !=null &&  mc.player.currentScreenHandler.getSlot(i).getStack().getName().getString().contains(ItemName)){
                    return i;
                }
            }
        }

        return -1;
    }

}
