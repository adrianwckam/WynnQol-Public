package net.fabricmc.wynnqol.Features.AbilityPointPreset;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.wynnqol.Utils.ChatUtils;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.fabricmc.wynnqol.main.CONFIG;
import static net.fabricmc.wynnqol.main.mc;

public class PresetList {
    public static void delete(String name){
        JsonObject preset = JsonParser.parseString(CONFIG.preset.skillTreePresetJson()).getAsJsonObject();
        preset.remove(name);

        PresetItems.PresetsWidget widgetToDelete = null;
        for(PresetItems.PresetsWidget widget : widgetList){
            if(widget.name == name){
                widgetToDelete = widget;
            }
        }
        if(widgetToDelete != null){
            widgetList.remove(widgetToDelete);
            widgetToDelete.destroy();
        }else{
            ChatUtils.chat("An unknown exception occured when deleting preset");
        }
        CONFIG.preset.skillTreePresetJson(preset.getAsJsonObject().toString());
    }

    public static void add(String name, JsonElement item){
        JsonObject preset = JsonParser.parseString(CONFIG.preset.skillTreePresetJson()).getAsJsonObject();
        preset.add(name, item);
        PresetItems.PresetsWidget newWidget = new PresetItems.PresetsWidget(mc.textRenderer, name);
        newWidget.build(mc.currentScreen,0,0);
        newWidget.allInvisible();
        widgetList.add(newWidget);

        CONFIG.preset.skillTreePresetJson(preset.getAsJsonObject().toString());
    }
    public static void add(String name, List<String> strings){
        Gson gson = new Gson();
        add(name, JsonParser.parseString(gson.toJson(strings)));
    }
    public static void rename(String oldName,String newName){
        JsonObject preset = JsonParser.parseString(CONFIG.preset.skillTreePresetJson()).getAsJsonObject();
        JsonElement data = preset.get(oldName);
        delete(oldName);
        add(newName, data);
    }


    private static List<PresetItems.PresetsWidget> widgetList = new ArrayList<>();
    private static int Initialx = 0;
    private static int initalHeight = 0;
    public static void onInit(Screen screen, int x, int y){

        widgetList.clear();
        Initialx = x;
        initalHeight = y;
        try{
            JsonObject preset = JsonParser.parseString(CONFIG.preset.skillTreePresetJson()).getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : preset.entrySet()) {
                PresetItems.PresetsWidget widget = new PresetItems.PresetsWidget(mc.textRenderer, entry.getKey());
                widgetList.add(widget);
            }
            int heightCounter = initalHeight;
            for(PresetItems.PresetsWidget widget : widgetList){
                widget.build(screen,Initialx, heightCounter);
                heightCounter += 20;
            }
        }catch (Exception e){
            ChatUtils.sendErrorMessage("loading presets", e.toString());
        }

    }
    public static void updateItems(Screen screen, String prompt){
        int heightCounter = initalHeight;
        PresetItems.PresetsWidget.AnyEditMode = false;
        for(PresetItems.PresetsWidget widget : widgetList){
            widget.visible = widget.name.contains(prompt);

            widget.updateVisibility();
            if(widget.visible){
                widget.update(Initialx, heightCounter);
                heightCounter += 20;
            }
        }
    }
}
