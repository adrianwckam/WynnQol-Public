package net.fabricmc.wynnqol.Publicized.Events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.text.Text;

public interface OnContainerScreenInitCallBack {
    Event<OnContainerScreenInitCallBack> EVENT = EventFactory.createArrayBacked(OnContainerScreenInitCallBack.class,
            (listeners) -> ( screen, title) -> {
                for (OnContainerScreenInitCallBack listener : listeners) {
                    listener.interact(screen, title);

                }
            });


    void interact(GenericContainerScreen screen, Text title);
}
