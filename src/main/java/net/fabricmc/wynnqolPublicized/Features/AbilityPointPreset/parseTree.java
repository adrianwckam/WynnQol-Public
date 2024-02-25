package net.fabricmc.wynnqolPublicized.Features.AbilityPointPreset;

import net.fabricmc.wynnqolPublicized.Utils.ChatUtils;
import net.fabricmc.wynnqolPublicized.Utils.ItemUtils;
import net.minecraft.item.ItemStack;

import java.util.List;

public class parseTree {
    public static List<String> getUnlocked(List<String> strings){
        for(int i = 0; i <= 53; i++){
            ItemStack itemStack = ItemUtils.getContainerSlot(i);
            if(itemStack == null) continue;

            List<String> lores = ItemUtils.getLore(itemStack);
            if(lores.size() < 2) continue;
            if(!ChatUtils.removeFormatting(lores.get(lores.size() - 1)).equals("You already unlocked this ability") && !ChatUtils.removeFormatting(lores.get(lores.size() - 1)).equals("Right Click to undo") ) continue;
            String name = ChatUtils.removeFormatting(itemStack.getName().getString());
            if(!strings.contains(name+String.valueOf(i))){
                strings.add(name+String.valueOf(i));
            }
        }
        return strings;
    }
    public static boolean isClickable(ItemStack itemStack){
        List<String> lores = ItemUtils.getLore(itemStack);
        if(lores.size() < 2) return false;
        for(String lore : lores){
            if(ChatUtils.removeFormatting(lore).startsWith("Click to unlock this") || ChatUtils.removeFormatting(lore).startsWith("You already unlocked")) return true;
        }
        return false;
    }

    public static int nextSlotToClick(List<String> abilities){
        for(int i = 0; i <= 53; i++) {
            ItemStack itemStack = ItemUtils.getContainerSlot(i);
            if (itemStack == null) continue;
            String name = ChatUtils.removeFormatting(itemStack.getName().getString()).replace("Unlock ","").replace(" ability","")+String.valueOf(i);
            if(abilities.contains(name) && isClickable(itemStack)){
                abilities.remove(name);
                ChatUtils.chat(name);
                return i;
            }
        }
        return -1;
    }
}
