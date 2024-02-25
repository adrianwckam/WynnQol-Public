package net.fabricmc.wynnqolPublicized.Features.SkillPointPreset;

import net.fabricmc.wynnqolPublicized.Utils.ChatUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class PresetItems {
    static class PresetsWidget{
//        public int x;
//        public int y;
//        public int width;
//        public int heigth;
        public String name;
        public boolean visible = true;
        private TextRenderer textRenderer;
        private ButtonWidget deleteButton;
        private ButtonWidget renameButton;
        private ButtonWidget mainButton;
        private boolean editMode = false;
        private TextFieldWidget inputBox;
        public static boolean AnyEditMode = false;

        public PresetsWidget(TextRenderer textRenderer,String name){
            this.name = name;
            this.textRenderer = textRenderer;
            inputBox = new TextFieldWidget(textRenderer, 0,0,120,20,Text.literal("input box"));
            inputBox.visible = false;
        }


        public void build(Screen screen,int x, int y){
            screen.addDrawableChild(inputBox);
            deleteButton = screen.addDrawableChild(ButtonWidget.builder(Text.literal(ChatUtils.colorCode("&c✖")), (button) -> {
                if(editMode){
                    //cancel
                    editMode = false;
                }else{
                    //delete
                    PresetMenu.status = "&aDeleted preset&d "+this.name;
                    PresetList.delete(this.name);

                }
            }).dimensions(x,y,20,20).build());

            renameButton = screen.addDrawableChild(ButtonWidget.builder(Text.literal(ChatUtils.colorCode("&b✎")), (button) -> {
                if(editMode){
                    // confirm, save preset
                    PresetList.rename(this.name, inputBox.getText());
                    PresetMenu.status = "&aRenamed &d"+this.name+"&a to &d"+inputBox.getText();
                    editMode = false;
                }else{
                    //edit the name
                    inputBox.setText("");
                    inputBox.setPlaceholder(Text.of(ChatUtils.colorCode("&7"+this.name)));
                    editMode = true;
                }
            }).dimensions(x+20,y,20,20).build());

            mainButton = screen.addDrawableChild(ButtonWidget.builder(Text.literal(this.name), (button) -> {
                // load the preset

                PresetActions.loadPreset(this.name);
            }).dimensions(x+40,y,120,20).build());
            inputBox.setPosition(x+40,y);

        }

        public void update(int x,int y){
            deleteButton.setPosition(x,y);
            renameButton.setPosition(x+20,y);
            mainButton.setPosition(x+40,y);
            inputBox.setPosition(x+40,y);
        }

        public void updateVisibility(){
            if(editMode){
                AnyEditMode = true;
            }
            deleteButton.visible = visible;
            renameButton.visible = visible;
            if(visible){
                if(editMode){
                    // in edit mode
                    renameButton.setMessage(Text.literal(ChatUtils.colorCode("&a✓")));
                    deleteButton.setMessage(Text.literal(ChatUtils.colorCode("&c✗")));
                    inputBox.visible = true;
                    mainButton.visible = false;
                }else{

                    renameButton.setMessage(Text.literal(ChatUtils.colorCode("&b✎")));
                    deleteButton.setMessage(Text.literal(ChatUtils.colorCode("&c✖")));
                    inputBox.visible = false;
                    mainButton.visible = true;
                }
            }else{
                inputBox.visible = false;
                mainButton.visible = false;
            }

        }

        public void destroy(){
            visible = false;
            deleteButton.visible = false;
            inputBox.visible = false;
            mainButton.visible = false;
            renameButton.visible = false;
        }

        public void allInvisible(){
            deleteButton.visible = false;
            inputBox.visible = false;
            mainButton.visible = false;
            renameButton.visible = false;
        }

    }
}
