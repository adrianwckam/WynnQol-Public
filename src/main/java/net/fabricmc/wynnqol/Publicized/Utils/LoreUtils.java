package net.fabricmc.wynnqol.Publicized.Utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class LoreUtils {
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
            lore.add(TextUtils.getCoded(result));
        }

        return lore;
    }

    public static boolean loreContains(ItemStack item, String string,boolean removeFormattings){ // shitty attempt of getting lore but it works so shut up
        NbtList loreTag = getLoreTag(item);

        if (loreTag == null) return false;
        for (int i = 0; i < loreTag.size(); ++i) {
            MutableText result = Text.Serializer.fromJson(loreTag.getString(i));
            if (result == null) continue;
            if (removeFormattings){
                if (ChatUtils.removeFormatting(TextUtils.getCoded(result)).contains(string)) return true;
            }else{
                if (TextUtils.getCoded(result).contains(string)) return true;
            }

        }

        return false;
    }


    public static void replaceLore(ItemStack item,int index, String text){
        NbtList lore = getLoreTag(item);
        if(lore == null) return;
        if(lore.size() <= index) return;
        lore.remove(index);
        lore.add(index, NbtString.of("{\"text\":\"" + ChatUtils.toMcFormatted(text) + "\",\"italic\":false}"));
        NbtCompound display = item.getOrCreateSubNbt(ItemStack.DISPLAY_KEY);
        display.put(ItemStack.LORE_KEY, lore);
    }

    public static void appendLore(ItemStack item, String text){

        NbtCompound display = item.getOrCreateSubNbt(ItemStack.DISPLAY_KEY);
        NbtList lore = getLoreTag(item);
        if(lore == null){
            lore = new NbtList();
        }
        lore.add(NbtString.of("{\"text\":\"" + ChatUtils.toMcFormatted(text) + "\",\"italic\":false}"));
        display.put(ItemStack.LORE_KEY, lore);
    }


}
