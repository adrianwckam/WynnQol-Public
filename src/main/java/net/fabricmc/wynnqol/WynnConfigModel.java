package net.fabricmc.wynnqol;

import io.wispforest.owo.config.annotation.*;
@Modmenu(modId = "wynnqol")
@Config(name = "WynnQol-config", wrapperName = "WynnConfig")
public class WynnConfigModel {
    @SectionHeader("Tna")
    @Nest
    public TnaNest tna = new TnaNest();

    public static class TnaNest{
        public boolean testing = false;
        @SectionHeader("test 2")
        public int owo =1;
    }
    @SectionHeader("Chat")
    @Nest
    public ChatNest chat = new ChatNest();

    public static class ChatNest{
        public boolean copyMessage = false;
    }

    @SectionHeader("Preset")
    @Nest
    public Preset preset = new Preset();
    public static class Preset {
        public boolean presetButton = false;
        public String presetsJson = "{}";
        public boolean skillTreePreset = false;
        public String skillTreePresetJson = "{}";
    }

//    @SectionHeader("Spells")
//
//    public int SpellCasterdelay = 0;
//    public int SpellCasterFixdelay = 0;
//    public boolean MeteorIndicator = false;
}