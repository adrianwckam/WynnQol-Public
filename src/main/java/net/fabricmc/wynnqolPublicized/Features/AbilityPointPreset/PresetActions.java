package net.fabricmc.wynnqolPublicized.Features.AbilityPointPreset;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.wynnqolPublicized.Utils.ChatUtils;
import net.fabricmc.wynnqolPublicized.Utils.InventoryUtils;
import net.minecraft.screen.slot.SlotActionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.fabricmc.wynnqolPublicized.main.CONFIG;

public class PresetActions {
//    private static final JsonObject slotMap = JsonParser.parseString("{\"Strength\":11,\"Dexterity\":12,\"Intelligence\":13,\"Defence\":14,\"Agility\":15}").getAsJsonObject();
    public static List<InventoryUtils.ClickAction> actionList = new ArrayList<>();
    public static boolean scanning = false;
    public static boolean loading = false;
    private static List<String> scanResult = new ArrayList<>();
    public static List<String> loadingList = new ArrayList<>();
    private static String nameToSave = "";
    private static int downCount = 0;
    public static void savePreset(String name){
        JsonObject config = JsonParser.parseString(CONFIG.preset.skillTreePresetJson()).getAsJsonObject();
        if(name ==""){
            PresetMenu.status = "&cPlease name the preset";
            return;
        }
        if(scanning){
            PresetMenu.status = "&cAlready scanning and saving";
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
        nameToSave = name;
        scanning = true;
        for (int i = 0; i < 7; i++) {
            actionList.add(new InventoryUtils.ClickAction(59, 0, SlotActionType.PICKUP));
        }
        for (int i = 0; i < 7; i++) {
            actionList.add(new InventoryUtils.ClickAction(57, 0, SlotActionType.PICKUP));
        }

    }

    public static void loadPreset(String name){
        loading = true;
        downCount = 0;
        PresetMenu.status = "&cLoading preset &d"+name+"";
        for (int i = 0; i < 7; i++) {
            actionList.add(new InventoryUtils.ClickAction(57, 0, SlotActionType.PICKUP));
        }
        loadingList.clear();
        List<JsonElement> list = JsonParser.parseString(CONFIG.preset.skillTreePresetJson()).getAsJsonObject().get(name).getAsJsonArray().asList();
        for(JsonElement element : list){
            loadingList.add(element.getAsString());
        }
    }
    private static int tickCount = 10;

    public static void clickSlot(){
        if(loading && loadingList.size() == 0 && actionList.size() == 0){
            ChatUtils.chatWithPrefix("&aDone loading");
            PresetMenu.status = "&aPreset &d"+nameToSave+"&a saved!";
            downCount = 0;
            loading = false;
        }
        if(actionList.size() < 1) return;
        tickCount += 10;
        InventoryUtils.ClickAction action = actionList.get(0);
        InventoryUtils.ClickSlot(action);
        actionList.remove(0);
        if(scanning && actionList.size() == 0){
            scanning = false;
            PresetList.add(nameToSave, scanResult);
            PresetMenu.status = "&aDone Scanning!";
            ChatUtils.chatWithPrefix("&aDone Scanning!");
            scanResult.clear();
        }


    }

    public static void scanSlots(){
        if(scanning){
            scanResult = parseTree.getUnlocked(scanResult);
        }else{
            if(actionList.size() != 0) return;
            int slotToClick = parseTree.nextSlotToClick(loadingList);
            if(slotToClick == -1 && loadingList.size() != 0){
                //no more clicks for this page, swap to next page
                downCount++;
                System.out.println(loadingList);
                tickCount = 10;
                if(downCount >10){
                    actionList.add(new InventoryUtils.ClickAction(57, 0, SlotActionType.PICKUP));
                }else{
                    actionList.add(new InventoryUtils.ClickAction(59, 0, SlotActionType.PICKUP));
                }

            }else{
                if(slotToClick == -1){
//                    System.out.println("Start of log");
//                    System.out.println(loading && loadingList.size() == 0 && actionList.size() == 0);
//                    System.out.println(loading );
//                    System.out.println(loadingList.size() == 0);
//                    System.out.println(loadingList.size());
//                    System.out.println(actionList.size() == 0);
//                    System.out.println(actionList.size());
//                    System.out.println("end of log");
                    return;
                }
                actionList.add(new InventoryUtils.ClickAction(slotToClick, 0, SlotActionType.PICKUP));

            }


        }

    }
    public static void OnTick(){
        if(tickCount > 0){
            tickCount--;
            return;
        }
        if(scanning || loading){
            scanSlots();
        }
        clickSlot();
    }
}
