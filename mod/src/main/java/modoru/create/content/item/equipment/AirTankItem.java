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

    private static final int
            RED_COLOR = 0xff5555,
            BLUE_COLOR = 0x5555ff,
            WHITE_COLOR = 0xffffff,
            MAX_TEMPERATURE = 100,
            MIN_TEMPERATURE = -100;

    public static final AirState DEFAULT_AIR_STATE = new AirState(0, 1000, 0);

    public AirTankItem(Properties properties) {
        super(properties.component(DataComponents.EQUIPMENT.TANK_AIR_STATE, DEFAULT_AIR_STATE));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        AirState airState = stack.get(DataComponents.EQUIPMENT.TANK_AIR_STATE);
        if(airState == null) return;

        float temperature = airState.temperature();

        int color;
        if(temperature > 0) color = gradient(WHITE_COLOR, RED_COLOR, temperature / MAX_TEMPERATURE);
        else if(temperature == 0) color = WHITE_COLOR;
        else color = gradient(WHITE_COLOR, BLUE_COLOR, temperature / MIN_TEMPERATURE);

        tooltipComponents.add(Component.translatable(
                "air_tank.info",
                airState.value(),
                airState.maxValue(),
                Component.literal(String.valueOf(temperature))
                        .withColor(color)
        ));
    }

    private static int unpack(int value, int shift) {
        return (value >> shift) & 255;
    }

    private static int pack(int value, int shift) {
        return (value & 255) << shift;
    }

    private static int gradientForSingleValue(int valueA, int valueB, float progress) {
        int maximum = Math.max(valueA, valueB);
        return maximum - Math.round((float) (maximum - Math.min(valueA, valueB)) * progress);
    }

    private static int gradient(int base, int end, float progress) {
        int redB = unpack(base, 16), greenB = unpack(base, 8), blueB = unpack(base, 0);
        int redE = unpack(end, 16), greenE = unpack(end, 8), blueE = unpack(end, 0);

        return pack(gradientForSingleValue(redB, redE, progress), 16) | pack(gradientForSingleValue(greenB, greenE, progress), 8) | pack(gradientForSingleValue(blueB, blueE, progress), 0);
    }

}
