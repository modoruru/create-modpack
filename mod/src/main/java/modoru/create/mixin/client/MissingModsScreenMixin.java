package modoru.create.mixin.client;

import com.sajmonoriginal.ftbchecker.MissingModsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MissingModsScreen.class)
public final class MissingModsScreenMixin {

    @Final
    @Mutable
    @Shadow
    private Component titleText, subtitleText;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifyFinalFieldOnce(CallbackInfo ci) {
        this.titleText = Component.literal("We're unable to distribute FTB mods due to legal reasons,");
        this.subtitleText = Component.literal("so please download them yourself.");
    }

}
