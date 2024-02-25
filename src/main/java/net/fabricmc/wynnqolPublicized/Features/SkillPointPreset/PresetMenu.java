package net.fabricmc.wynnqolPublicized.Features.SkillPointPreset;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.wynnqolPublicized.Events.OnContainerScreenInitCallBack;
import net.fabricmc.wynnqolPublicized.Events.OnGuiKeyPressedCallBack;
import net.fabricmc.wynnqolPublicized.Events.PostScreenRenderCallBack;

import net.fabricmc.wynnqolPublicized.Utils.ChatUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.awt.event.InputEvent;
import java.util.Objects;

import static net.fabricmc.wynnqolPublicized.main.CONFIG;
import static net.fabricmc.wynnqolPublicized.main.mc;

public class PresetMenu {
    private static TextFieldWidget InputBox;
    private static ButtonWidget Button;
    public static String status = "";
    public static void init(){
        OnContainerScreenInitCallBack.EVENT.register((screen, title)->{

            if(!CONFIG.preset.presetButton()) return;
            if(!Objects.equals(screen.getTitle().getString(), "Character Info")) return;
            PresetActions.actionList.clear();
            status = "&dWynnQOL Skill point Preset";
            InputBox = new TextFieldWidget(mc.textRenderer, (screen.width*2/3)+5, screen.height/4,150,20,Text.literal("search bar"));
            InputBox.setPlaceholder(Text.literal(ChatUtils.colorCode("&7Search bar / Name a preset")));
            screen.addDrawableChild(InputBox);
            Button = ButtonWidget.builder(Text.literal("Save"), (button) -> {
                PresetActions.savePreset(InputBox.getText());
                InputBox.setText("");
            }).width(50).position((screen.width * 2 / 3 )+5+ InputBox.getWidth()+2, screen.height / 4).build();
            screen.addDrawableChild(Button);
            PresetList.onInit(screen,(screen.width*2/3) +4, screen.height/4 + 20 + 1);

        });

        OnGuiKeyPressedCallBack.EVENT.register((keyCode, scanCode, modifiers, cir) -> {
            if(!CONFIG.preset.presetButton()) return;
            if ( InputBox != null && keyCode==mc.options.inventoryKey.getDefaultKey().getCode() &&( PresetItems.PresetsWidget.AnyEditMode || InputBox.isFocused())) cir.setReturnValue(Boolean.TRUE);
        });

        ClientTickEvents.END_CLIENT_TICK.register((End)->{
            if(!CONFIG.preset.presetButton()) return;
            Screen screen = mc.currentScreen;
            if(InputBox == null ||screen == null || !Objects.equals(screen.getTitle().getString(), "Character Info")) return;
            PresetList.updateItems(screen,InputBox.getText());
            PresetActions.OnTick();
        });
        PostScreenRenderCallBack.EVENT.register((drawContext, mouseX, mouseY, delta, ci) -> {
            if(!CONFIG.preset.presetButton()) return;
            Screen screen = mc.currentScreen;
            if(screen == null || !Objects.equals(screen.getTitle().getString(), "Character Info")) return;
            InputBox.setPosition((screen.width*2/3)+5, screen.height/4);
            Button.setPosition((screen.width * 2 / 3 )+5+ InputBox.getWidth()+2, screen.height / 4);
            drawContext.drawText(mc.textRenderer,ChatUtils.colorCode(status),(screen.width*2/3)+5, (screen.height/4)  -12,0, false);
        });
    }
}
