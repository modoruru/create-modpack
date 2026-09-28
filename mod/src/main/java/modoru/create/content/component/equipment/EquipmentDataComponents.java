package modoru.create.content.component.equipment;

import modoru.create.content.component.DataComponentCategory;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EquipmentDataComponents extends DataComponentCategory {

    public final DeferredHolder<DataComponentType<?>, DataComponentType<AirState>> TANK_AIR_STATE = registerComponentType(
            "tank_air_state", builder -> builder.persistent(AirState.CODEC).networkSynchronized(AirState.STREAM_CODEC)
    );

    public EquipmentDataComponents(DeferredRegister.DataComponents register) {
        super(register, "equipment");
    }

}
