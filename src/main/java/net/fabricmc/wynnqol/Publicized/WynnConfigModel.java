package net.fabricmc.wynnqol.Publicized;

import io.wispforest.owo.config.annotation.*;
@Modmenu(modId = "wynnqol-public")
@Config(name = "WynnQolPublic-config", wrapperName = "WynnQolPublicConfig")
public class WynnConfigModel {
    @SectionHeader("Raids")
    @Nest
    public TnaNest tna = new TnaNest();

    public static class TnaNest{
        public boolean highlightLightHolder = false;
        @Nest
        public WynnConfigModel.TnaNest.TnaBoss tnaBoss = new WynnConfigModel.TnaNest.TnaBoss();
        public static class TnaBoss {
            public boolean gregOutline = false;
            public boolean gregBiggerHealth = false;
        }

    }
    @SectionHeader("Chat")
    @Nest
    public ChatNest chat = new ChatNest();

    public static class ChatNest{
        public boolean copyMessage = false;
        public boolean chatTab = false;
        public boolean wynnicTranslator = false;
    }

    @SectionHeader("Misc")
    @Nest
    public Preset preset = new Preset();
    public static class Preset {
        public boolean presetButton = false;
        public String presetsJson = "{}";
        public boolean skillTreePreset = false;
        public String skillTreePresetJson = "{}";
    }
}