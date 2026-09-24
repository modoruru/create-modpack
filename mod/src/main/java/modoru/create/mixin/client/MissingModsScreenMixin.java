package modoru.create.mixin.client;

import com.sajmonoriginal.ftbchecker.MissingModsScreen;
import modoru.create.mixin.accessor.ScreenAccessor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(MissingModsScreen.class)
public abstract class MissingModsScreenMixin {

    @Final
    @Mutable
    @Shadow
    private Component titleText, subtitleText;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifyFinalFieldOnce(CallbackInfo ci) {
        this.titleText = Component.literal("We're unable to distribute FTB mods due to legal reasons,");
        this.subtitleText = Component.literal("so please download them yourself.");
    }

    @Shadow protected abstract Map<String, String> getMods();
    @Shadow protected abstract void addButtonForMod(String modName, String downloadLink, int x, int y);
    @Shadow protected abstract void openDownloadPages();

    /**
     * @author just_lofe
     * @reason add exit game button
     */
    @Overwrite
    protected void init() {
        MissingModsScreen asOriginalObject = (MissingModsScreen) (Object) this;

        int centerX = asOriginalObject.width / 2;
        int topTextY = 10;
        int buttonWidth = 200;
        int buttonHeight = 20;
        int modButtonY = topTextY + 45;
        int modButtonSpacing = 25;
        int buttons = 0;

        for(Map.Entry<String, String> entry : getMods().entrySet()) {
            addButtonForMod(entry.getKey(), entry.getValue(), centerX - buttonWidth / 2, modButtonY + modButtonSpacing * buttons);
            ++buttons;
        }

        int downloadAllButtonY = modButtonY + modButtonSpacing * buttons + 10;
        ScreenAccessor screenAccessor = (ScreenAccessor) this;
        screenAccessor.invokeAddRenderableWidget(
                Button.builder(Component.literal("Download All"), (button) -> openDownloadPages())
                        .bounds(centerX - buttonWidth / 2, downloadAllButtonY, buttonWidth, buttonHeight)
                        .build()
        );

        int exitGameButton = downloadAllButtonY + modButtonSpacing;
        screenAccessor.invokeAddRenderableWidget(
                Button.builder(Component.translatable("menu.quit"), (ignored) -> asOriginalObject.getMinecraft().stop())
                        .bounds(centerX - buttonWidth / 2, exitGameButton, buttonWidth, buttonHeight)
                        .build()
        );
    }

}
