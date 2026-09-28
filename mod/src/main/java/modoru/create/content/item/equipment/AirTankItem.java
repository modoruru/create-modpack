package modoru.create.content.item.equipment;

import modoru.create.content.component.DataComponents;
import modoru.create.content.component.equipment.AirState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class AirTankItem extends Item {

    public AirTankItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        AirState airState = stack.get(DataComponents.EQUIPMENT.TANK_AIR_STATE);
        if(airState == null) return;

        tooltipComponents.add(Component.translatable("air_tank.amount", airState.value(), airState.maxValue()));
        tooltipComponents.add(Component.translatable("air_tank.temperature", airState.temperature()));
    }
}
