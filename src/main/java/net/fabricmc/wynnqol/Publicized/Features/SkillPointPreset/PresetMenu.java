package net.fabricmc.wynnqol.Publicized.Features.SkillPointPreset;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.wynnqol.Publicized.Events.OnContainerScreenInitCallBack;
import net.fabricmc.wynnqol.Publicized.Events.OnGuiKeyPressedCallBack;
import net.fabricmc.wynnqol.Publicized.Events.PostScreenRenderCallBack;

import net.fabricmc.wynnqol.Publicized.Utils.ChatUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import java.util.Objects;

import static net.fabricmc.wynnqol.Publicized.main.mc;

public class PresetMenu {
    private static TextFieldWidget InputBox;
    public static String status = "";
    public static void init(){
        OnContainerScreenInitCallBack.EVENT.register((screen, title)->{
            if(!Objects.equals(screen.getTitle().getString(), "Character Info")) return;
            PresetActions.actionList.clear();
            status = "&dWynnQOL Skill point Preset";
            InputBox = new TextFieldWidget(mc.textRenderer, (screen.width*2/3)+5, screen.height/4,150,20,Text.literal("search bar"));
            InputBox.setPlaceholder(Text.literal(ChatUtils.colorCode("&7Search bar / Name a preset")));
            screen.addDrawableChild(InputBox);
            screen.addDrawableChild(ButtonWidget.builder(Text.literal("Save"), (button) -> {
                PresetActions.savePreset(InputBox.getText());
                InputBox.setText("");
            }).width(50).position((screen.width * 2 / 3 )+5+ InputBox.getWidth()+2, screen.height / 4).build());
            PresetList.onInit(screen,(screen.width*2/3) +4, screen.height/4 + 20 + 1);

        });

        OnGuiKeyPressedCallBack.EVENT.register((keyCode, scanCode, modifiers, cir) -> {
            if ( InputBox != null && keyCode==mc.options.inventoryKey.getDefaultKey().getCode() &&( PresetItems.PresetsWidget.AnyEditMode || InputBox.isFocused())) cir.setReturnValue(Boolean.TRUE);

        });

        ClientTickEvents.END_CLIENT_TICK.register((End)->{
            Screen screen = mc.currentScreen;
            if(screen == null || !Objects.equals(screen.getTitle().getString(), "Character Info")) return;
            PresetList.updateItems(screen,InputBox.getText());
            PresetActions.OnTick();
        });
        PostScreenRenderCallBack.EVENT.register((drawContext, mouseX, mouseY, delta, ci) -> {
            Screen screen = mc.currentScreen;
            if(screen == null || !Objects.equals(screen.getTitle().getString(), "Character Info")) return;

            drawContext.drawText(mc.textRenderer,ChatUtils.colorCode(status),(screen.width*2/3)+5, (screen.height/4)  -12,0, false);
        });
    }
}
