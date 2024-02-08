package net.fabricmc.wynnqol.Features.SkillPointPreset;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.wynnqol.Utils.ChatUtils;
import net.fabricmc.wynnqol.Utils.ItemUtils;
import net.minecraft.item.ItemStack;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static net.fabricmc.wynnqol.main.mc;

public class ParseGear {

    private static final List<String> Elements = Arrays.asList("Strength", "Dexterity", "Intelligence", "Defence", "Agility");
    public static String fixCrafted(String str){
        String[] arr = str.split("/");
        if (arr.length == 2) {
//            return arr[1];
            return arr[0];
        } else {
            return str.substring(1);
        }
    }

    public static JsonObject getGearSP(JsonObject base, ItemStack item){
        if(item == null){
            return base;
        }
        boolean encounteredSP = false;
        List<String> LoreList = ItemUtils.getLore(item);
        for (String string : LoreList){
            string = ChatUtils.removeFormatting(string);

            if(string.startsWith("✖") || (encounteredSP && !(string.startsWith("+") || string.startsWith("-")))){
                return base;
            }
            if(!(string.startsWith("+") || string.startsWith("-"))) continue;
            for(String element: Elements){// iterates through element
                if(string.endsWith(element)){
                    boolean minus = string.startsWith("-");
                    string = ChatUtils.removeText(fixCrafted(string)); //implement crafted fix
                    encounteredSP = true;
                    if(minus)base.addProperty(element, base.get(element).getAsInt() - Integer.parseInt(string));
                    else base.addProperty(element, base.get(element).getAsInt() + Integer.parseInt(string));

                    break;
                }
            }
        }
        System.out.println("GearSp: "+base.toString());
        return base;
    }
    public static JsonObject getGearReq(ItemStack item){
        JsonObject base = JsonParser.parseString("{\"Strength\":0,\"Dexterity\":0,\"Intelligence\":0,\"Defence\":0,\"Agility\":0}").getAsJsonObject();
        if(item == null){
            return base;
        }
        List<String> lores = ItemUtils.getLore(item);
        for(String lore : lores){
            if(!lore.contains("Min:") || lore.contains("Lv. Min:") ) continue;
            String[] lines = lore.split("Min:");
            String element = ChatUtils.keepText(lines[0]);
            int req = Integer.parseInt(ChatUtils.removeText(lines[1]));
            base.addProperty(element,req );
        }
        return base;
    }

    public static ItemStack isMatchReq(ItemStack itemStack, JsonObject menu){
        JsonObject req = getGearReq(itemStack);
        for(Map.Entry<String, JsonElement> entry : menu.entrySet()){
            if(entry.getValue().getAsInt() < req.get(entry.getKey()).getAsInt()) return null;
        }
        return itemStack;
    }

    public static JsonObject getTotalGearSp(){ // add crafted checks
        JsonObject base = JsonParser.parseString("{\"Strength\":0,\"Dexterity\":0,\"Intelligence\":0,\"Defence\":0,\"Agility\":0}").getAsJsonObject();
        base = getGearSP(base, ItemUtils.getHeldItem());// held item
        // accessories
        base = getGearSP(base, ItemUtils.getContainerSlot(27));
        base = getGearSP(base, ItemUtils.getContainerSlot(28));
        base = getGearSP(base, ItemUtils.getContainerSlot(29));
        base = getGearSP(base, ItemUtils.getContainerSlot(30));
        // armor
        base = getGearSP(base, mc.player.getInventory().getArmorStack(0));
        base = getGearSP(base, mc.player.getInventory().getArmorStack(1));
        base = getGearSP(base, mc.player.getInventory().getArmorStack(2));
        base = getGearSP(base, mc.player.getInventory().getArmorStack(3));


        System.out.println(" total GearSp: "+base.toString());
        return base;
    }
    public static JsonObject getTotalGearSp(JsonObject menu){ // add crafted checks
        JsonObject base = JsonParser.parseString("{\"Strength\":0,\"Dexterity\":0,\"Intelligence\":0,\"Defence\":0,\"Agility\":0}").getAsJsonObject();
        base = getGearSP(base, ItemUtils.getHeldItem());// held item
        // accessories
        base = getGearSP(base, ItemUtils.getContainerSlot(27));
        base = getGearSP(base, ItemUtils.getContainerSlot(28));
        base = getGearSP(base, ItemUtils.getContainerSlot(29));
        base = getGearSP(base, ItemUtils.getContainerSlot(30));
        // armor
        base = getGearSP(base, isMatchReq(mc.player.getInventory().getArmorStack(0) , menu));
        base = getGearSP(base, isMatchReq(mc.player.getInventory().getArmorStack(1) , menu));
        base = getGearSP(base, isMatchReq(mc.player.getInventory().getArmorStack(2) , menu));
        base = getGearSP(base, isMatchReq(mc.player.getInventory().getArmorStack(3) , menu));


        System.out.println(" total GearSp: "+base.toString());
        return base;
    }
    public static int getSpFromButton(ItemStack itemStack){
        try{
            List<String> lore = ItemUtils.getLore(itemStack);
            for(String l : lore){
                if(!l.endsWith("points")) continue;
                String[] line = l.split("points");

                return Integer.parseInt(ChatUtils.removeText(line[0], true));
            }
            return 0;
        }catch (Exception e){
            ChatUtils.sendErrorMessage("analzying skillpoint menu", e.toString());
            return 0;
        }

    }
    public static JsonObject parseSkillMenu(){
        // 11,12,13,14,15
        JsonObject base = JsonParser.parseString("{\"Strength\":0,\"Dexterity\":0,\"Intelligence\":0,\"Defence\":0,\"Agility\":0}").getAsJsonObject();
        base.addProperty("Strength", getSpFromButton(ItemUtils.getContainerSlot(11)));
        base.addProperty("Dexterity", getSpFromButton(ItemUtils.getContainerSlot(12)));
        base.addProperty("Intelligence", getSpFromButton(ItemUtils.getContainerSlot(13)));
        base.addProperty("Defence", getSpFromButton(ItemUtils.getContainerSlot(14)));
        base.addProperty("Agility", getSpFromButton(ItemUtils.getContainerSlot(15)));
        System.out.println("Menu SP: "+base.toString());
        return base;

    }
}
