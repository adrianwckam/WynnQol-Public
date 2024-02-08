package net.fabricmc.wynnqol.Utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;

import static net.fabricmc.wynnqol.main.mc;

public class ItemUtils {
    public static ItemStack getHeldItem(){
        assert mc.player != null;
        return mc.player.getStackInHand(Hand.MAIN_HAND);
    }
//
    private static NbtList getLoreTag(ItemStack item) {// yoinked from wynntils
        if (item.isEmpty()) return null;
        NbtCompound display = item.getSubNbt("display");

        if (display == null || display.getNbtType() != NbtCompound.TYPE || !display.contains("Lore")) return null;
        NbtElement loreBase = display.get("Lore");

        if (loreBase.getNbtType() != NbtList.TYPE) return null;
        return (NbtList) loreBase;
    }

    public static List<String> getLore(ItemStack item){ // shitty attempt of getting lore but it works so shut up
        NbtList loreTag = getLoreTag(item);

        List<String> lore = new ArrayList<String>();

        if (loreTag == null) return lore;
        for (int i = 0; i < loreTag.size(); ++i) {
            MutableText result = Text.Serializer.fromJson(loreTag.getString(i));
            if(result == null){
                lore.add("");
                continue;
            }
            lore.add(result.getString());
        }

        return lore;
    }

    public static boolean doLoreContain(ItemStack item, String str){

        List<String> lore = getLore(item);
        for(String e : lore){
            if(e.contains(str)) return true;
        }
        return false;
    }
    public static ItemStack getContainerSlot(int slot){
        return mc.player.currentScreenHandler.getSlot(slot).getStack();
    }

    public static class Wynn {
        public static boolean isWeapon(ItemStack item){
            return doLoreContain(item , "Class Req:");
        }

        public static boolean isArcher(ItemStack item){
            return doLoreContain(item, "Class Req: Archer");
        }
        public static boolean isMage(ItemStack item){
            return doLoreContain(item, "Class Req: Mage");
        }
    }
}
