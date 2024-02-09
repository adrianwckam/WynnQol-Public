package net.fabricmc.wynnqol.Publicized.Features.SkillPointPreset;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.wynnqol.Publicized.Utils.ChatUtils;
import net.fabricmc.wynnqol.Publicized.Utils.InventoryUtils;
import net.fabricmc.wynnqol.Publicized.Utils.ItemUtils;
import net.minecraft.screen.slot.SlotActionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.fabricmc.wynnqol.Publicized.main.CONFIG;

public class PresetActions {
    private static final JsonObject slotMap = JsonParser.parseString("{\"Strength\":11,\"Dexterity\":12,\"Intelligence\":13,\"Defence\":14,\"Agility\":15}").getAsJsonObject();
    public static List<InventoryUtils.ClickAction> actionList = new ArrayList<>();
    public static void savePreset(String name){
        JsonObject config = JsonParser.parseString(CONFIG.preset.presetsJson()).getAsJsonObject();
        if(name ==""){
            PresetMenu.status = "&cPlease name the preset";
            return;
        }
        String lowerName = name.toLowerCase();
        for (Map.Entry<String, JsonElement> entry : config.entrySet()) {
            String exist = entry.getKey().toLowerCase();

            if(exist.equals(lowerName)){
                PresetMenu.status = "&cPreset &d"+name+"&c had already been used!";
                return;
            }
        }
        JsonObject TotalGearSp = ParseGear.getTotalGearSp();
        JsonObject MenuSp = ParseGear.parseSkillMenu();
        JsonObject base = JsonParser.parseString("{\"Strength\":0,\"Dexterity\":0,\"Intelligence\":0,\"Defence\":0,\"Agility\":0}").getAsJsonObject();
        int spTotal = 0;
        for (Map.Entry<String, JsonElement> entry : MenuSp.entrySet()) {
            int sp = entry.getValue().getAsInt() - TotalGearSp.get(entry.getKey()).getAsInt();
            spTotal += sp;
            base.addProperty(entry.getKey(), sp);
        }
        System.out.println("Total sp:" + spTotal);
        if((spTotal - 200) > 15){
            PresetMenu.status = "&cPlease make your armor visible";
            return;
        }

        PresetList.add(name, base);
        System.out.println("Saved preset: "+base.toString());
        PresetMenu.status = "&aPreset &d"+name+"&a saved!";
    }

    public static void loadPreset(String name){
        if(actionList.size() != 0){
            PresetMenu.status = "&cA preset is already being load";
            return;
        }
        List<String> lores = ItemUtils.getLore(ItemUtils.getContainerSlot(4));
        int skillPointLeft = -1;
        for(String lore : lores){
            if(ChatUtils.removeFormatting(lore).startsWith("You have")){
                skillPointLeft = Integer.parseInt(ChatUtils.removeText(ChatUtils.removeFormatting(lore)));
                break;
            }
        }
        if(skillPointLeft == -1){
            PresetMenu.status = "&cFailed to get current SkillPoint";
            return;
        }else if(skillPointLeft != 200){
            PresetMenu.status = "&cReset your SkillPoint before loading";
            return;
        }
        JsonObject preset = (JsonObject) JsonParser.parseString(CONFIG.preset.presetsJson()).getAsJsonObject().get(name);
        JsonObject menuSp = ParseGear.parseSkillMenu();
        JsonObject gearSp = ParseGear.getTotalGearSp(menuSp);

        for (Map.Entry<String, JsonElement> entry : menuSp.entrySet()) {
            int sp = entry.getValue().getAsInt() - gearSp.get(entry.getKey()).getAsInt();
            menuSp.addProperty(entry.getKey(), sp);
        }

        int spTotal = 0;
        for (Map.Entry<String, JsonElement> entry : preset.entrySet()) {
            int sp = entry.getValue().getAsInt() - menuSp.get(entry.getKey()).getAsInt();
            spTotal += sp;
            preset.addProperty(entry.getKey(), sp);

        }
        ChatUtils.chat(String.valueOf(spTotal));
        ChatUtils.chat(preset.toString());
        if(spTotal > 200){
            PresetMenu.status = "&cYour preset requires +" +String.valueOf(spTotal-200) + " SkillPoint";
            return;
        }
        ChatUtils.chat(String.valueOf(actionList.size()));
        for (Map.Entry<String, JsonElement> entry : preset.entrySet()) {
            int sp = entry.getValue().getAsInt();
            int slot = slotMap.get(entry.getKey()).getAsInt();
            int sp_click = sp%5;
            int sp_shift = (int) Math.floor(sp/5f);

            for (int i = 0; i < sp_click; i++) {
                actionList.add(new InventoryUtils.ClickAction(slot, 0, SlotActionType.PICKUP));
            }
            for (int i = 0; i < sp_shift; i++) {
                actionList.add(new InventoryUtils.ClickAction(slot, 0, SlotActionType.QUICK_MOVE));
            }
        }

    }
    private static int tickCount = 0;

    public static void OnTick(){
        if(actionList.size() < 1) return;
        if(tickCount > 0){
            tickCount--;
            return;
        }
        tickCount = 1;
        InventoryUtils.ClickAction action = actionList.get(0);
        InventoryUtils.ClickSlot(action);
        actionList.remove(0);
    }
}
