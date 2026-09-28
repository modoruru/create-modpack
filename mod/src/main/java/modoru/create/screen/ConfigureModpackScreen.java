package modoru.create.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

// todo
public final class ConfigureModpackScreen extends Screen {

    public ConfigureModpackScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void renderPanorama(@NotNull GuiGraphics guiGraphics, float partialTick) {}

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

}
