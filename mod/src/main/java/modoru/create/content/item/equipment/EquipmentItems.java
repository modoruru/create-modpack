package modoru.create.content.item.equipment;

import modoru.create.content.item.ItemCategory;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EquipmentItems extends ItemCategory {

    public final DeferredItem<AirTankItem> AIR_TANK = registerItem("air_tank", AirTankItem::new);
    public final DeferredItem<AirMaskItem> AIR_MASK = registerItem("air_mask", AirMaskItem::new);

    public EquipmentItems(DeferredRegister.Items register) {
        super(register, "equipment");
    }

}
