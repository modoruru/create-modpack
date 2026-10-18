package modoru.create.content.item.equipment;

import modoru.create.content.component.DataComponents;
import modoru.create.content.component.equipment.AirState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class AirTankItem extends Item {

    private static final int TANK_PRESSURE = 1; // 1 p

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

    public static float celsiusToKelvin(float celsius) {
        return celsius + 273.15f;
    }

    public static float kelvinToCelsius(float kelvin) {
        return kelvin - 273.15f;
    }

    public static float mixRatio(float highestTemperatureC, float targetTemperatureC, float lowestTemperatureC) {
        if(!(highestTemperatureC >= targetTemperatureC && targetTemperatureC >= lowestTemperatureC)) throw new IllegalArgumentException("highest >= target >= lowest");
        if(highestTemperatureC == targetTemperatureC) return Float.POSITIVE_INFINITY;
        if(lowestTemperatureC == targetTemperatureC) return 0;

        float htk = celsiusToKelvin(highestTemperatureC), ttk = celsiusToKelvin(targetTemperatureC), ltk = celsiusToKelvin(lowestTemperatureC);
        return (htk * (ttk - ltk)) / (ltk * (htk - ttk));
    }

    public static float volumeOfMix(AirState highest, float targetTemperature, AirState lowest) {
        float ratio = mixRatio(highest.temperature(), targetTemperature, lowest.temperature());
        if(ratio == Float.POSITIVE_INFINITY) return highest.value();
        if(ratio == 1) return highest.value() + lowest.value();
        if(ratio == 0) return lowest.value();

        float htk = celsiusToKelvin(highest.temperature()), ttk = celsiusToKelvin(targetTemperature), ltk = celsiusToKelvin(lowest.temperature());

        float lowUsed = Math.min(lowest.value(), highest.value() / ratio);
        float highUsed = ratio * lowUsed;

        return ttk * (highUsed / htk + lowUsed / ltk);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    public static float getFillLevel(ItemStack stack) {
        AirState airState = stack.getOrDefault(DataComponents.EQUIPMENT.TANK_AIR_STATE, DEFAULT_AIR_STATE);
        return airState.value() / (float) airState.maxValue();
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return Math.round(13.0F * getFillLevel(stack));
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return getTemperatureColor(stack.getOrDefault(DataComponents.EQUIPMENT.TANK_AIR_STATE, DEFAULT_AIR_STATE).temperature());
    }

    public static int getTemperatureColor(float temperature) {
        int color;
        if(temperature > 0) color = gradient(WHITE_COLOR, RED_COLOR, temperature / MAX_TEMPERATURE);
        else if(temperature == 0) color = WHITE_COLOR;
        else color = gradient(WHITE_COLOR, BLUE_COLOR, temperature / MIN_TEMPERATURE);

        return color;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        AirState airState = stack.get(DataComponents.EQUIPMENT.TANK_AIR_STATE);
        if(airState == null) return;

        float temperature = airState.temperature();
        tooltipComponents.add(Component.translatable(
                "air_tank.info",
                airState.value(),
                airState.maxValue(),
                Component.literal(String.valueOf(temperature))
                        .withColor(getTemperatureColor(temperature))
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
