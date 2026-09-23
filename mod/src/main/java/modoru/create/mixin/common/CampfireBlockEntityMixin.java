package modoru.create.mixin.common;

import com.simibubi.create.AllItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CampfireBlockEntity.class)
public final class CampfireBlockEntityMixin {

    @Redirect(
            method = "cookTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/Containers;dropItemStack(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"
            )
    )
    private static void cookTickInjection(Level level, double x, double y, double z, ItemStack stack) {
        if(stack.is(AllItems.ANDESITE_ALLOY)) {
            level.playSound(
                    null,
                    x, y, z,
                    SoundEvents.DECORATED_POT_BREAK,
                    SoundSource.BLOCKS,
                    1.0f,
                    1.0f
            );
        }

        Containers.dropItemStack(level, x, y, z, stack);
    }

}
