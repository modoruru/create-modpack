package modoru.create.screen;

import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;

@OnlyIn(Dist.CLIENT)
public final class NotInModpackScreen extends Screen {

    private static final String GITHUB_URL = "https://github.com/modoruru/create-modpack";
    private static final String MODRINTH_URL = "https://modrinth.com/modpack/modoru-create";

    private final Component errorText =
            Component.literal("Mod is started outside of the \"modoru: create\" modpack.");
    private final Component errorSecondLine =
            Component.literal("This may cause undefined behavior and is not supported.");

    public NotInModpackScreen() {
        super(Component.literal("Not in modpack"));
    }

    @Override
    protected void init() {
        int buttonWidth = 200;
        int buttonHeight = 20;
        int buttonGap = 5;

        int centerX = this.width / 2;

        // Total height:
        // 3 buttons + 2 gaps
        int totalButtonHeight = buttonHeight * 3 + buttonGap * 2;

        // Center the entire button group vertically.
        int firstButtonY = this.height / 2 - totalButtonHeight / 2;

        this.addRenderableWidget(
                Button.builder(Component.literal("Modpack on GitHub"), button -> openWebPage(GITHUB_URL))
                        .bounds(centerX - buttonWidth / 2, firstButtonY, buttonWidth, buttonHeight)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(Component.literal("Modpack on Modrinth"), button -> openWebPage(MODRINTH_URL))
                        .bounds(centerX - buttonWidth / 2, firstButtonY + buttonHeight + buttonGap, buttonWidth, buttonHeight)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(Component.translatable("menu.quit"), button -> minecraft.stop())
                        .bounds(centerX - buttonWidth / 2, firstButtonY + (buttonHeight + buttonGap) * 2, buttonWidth, buttonHeight)
                        .build()
        );
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        int centerX = this.width / 2;

        int titleY = this.height / 2 - 80;
        int subtitleY = titleY + 15;

        graphics.drawCenteredString(
                this.font,
                this.errorText,
                centerX,
                titleY,
                0xFFFFFF
        );

        graphics.drawCenteredString(
                this.font,
                this.errorSecondLine,
                centerX,
                subtitleY,
                0xFFFFFF
        );
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    private void openWebPage(String url) {
        try {
            Util.getPlatform().openUri(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}