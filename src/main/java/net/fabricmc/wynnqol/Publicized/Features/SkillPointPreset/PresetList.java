package net.fabricmc.wynnqol.Publicized.Features.SkillPointPreset;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.wynnqol.Publicized.Features.SkillPointPreset.PresetItems.PresetsWidget;
import net.fabricmc.wynnqol.Publicized.Utils.ChatUtils;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.fabricmc.wynnqol.Publicized.main.CONFIG;
import static net.fabricmc.wynnqol.Publicized.main.mc;

public class PresetList {

    private static String currentPreset = "{\"name\":{\"Strength\":0,\"Dexterity\":0,\"Intelligence\":0,\"Defence\":0,\"Agility\":0},\"ewe\":{\"Strength\":0,\"Dexterity\":0,\"Intelligence\":0,\"Defence\":0,\"Agility\":0}}";

    public static void delete(String name){
        JsonObject preset = JsonParser.parseString(CONFIG.preset.presetsJson()).getAsJsonObject();
        preset.remove(name);
        PresetsWidget widgetToDelete = null;
        for(PresetsWidget widget : widgetList){
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
        CONFIG.preset.presetsJson(preset.getAsJsonObject().toString());
    }

    public static void add(String name, JsonElement item){
        JsonObject preset = JsonParser.parseString(CONFIG.preset.presetsJson()).getAsJsonObject();
        preset.add(name, item);
        PresetsWidget newWidget = new PresetsWidget(mc.textRenderer, name);
        newWidget.build(mc.currentScreen,0,0);
        newWidget.allInvisible();
        widgetList.add(newWidget);

        CONFIG.preset.presetsJson(preset.getAsJsonObject().toString());
    }
    public static void rename(String oldName,String newName){
        JsonObject preset = JsonParser.parseString(CONFIG.preset.presetsJson()).getAsJsonObject();
        JsonElement data = preset.get(oldName);
        delete(oldName);
        add(newName, data);
    }


    private static List<PresetsWidget> widgetList = new ArrayList<>();
    private static int Initialx = 0;
    private static int initalHeight = 0;
    public static void onInit(Screen screen,int x, int y){

        widgetList.clear();
        Initialx = x;
        initalHeight = y;
        try{
            JsonObject preset = JsonParser.parseString(CONFIG.preset.presetsJson()).getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : preset.entrySet()) {
                PresetsWidget widget = new PresetsWidget(mc.textRenderer, entry.getKey());
                widgetList.add(widget);
            }
            int heightCounter = initalHeight;
            for(PresetsWidget widget : widgetList){
                widget.build(screen,Initialx, heightCounter);
                heightCounter += 20;
            }
        }catch (Exception e){
            ChatUtils.sendErrorMessage("loading presets", e.toString());
        }

    }
//    public static void render(){
//        if(mc.currentScreen== null || !Objects.equals(mc.currentScreen.getTitle().getString(), "Character Info")) return;
//        Screen currentScreen = mc.currentScreen;
//    }

    public static void updateItems(Screen screen, String prompt){
        int heightCounter = initalHeight;
        PresetsWidget.AnyEditMode = false;
        for(PresetsWidget widget : widgetList){
            widget.visible = widget.name.contains(prompt);

            widget.updateVisibility();
            if(widget.visible){
                widget.update(Initialx, heightCounter);
                heightCounter += 20;
            }
        }
    }
}
