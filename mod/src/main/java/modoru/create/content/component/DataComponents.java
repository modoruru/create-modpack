package modoru.create.content.component;

import modoru.create.ModoruCreate;
import modoru.create.content.component.equipment.EquipmentDataComponents;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class DataComponents {

    private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ModoruCreate.MODID);

    public static final EquipmentDataComponents EQUIPMENT = new EquipmentDataComponents(DATA_COMPONENTS);

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }

}
