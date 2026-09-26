package modoru.create.content.item.equipment;

import modoru.create.content.item.ItemCategory;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EquipmentItems extends ItemCategory {

    public final DeferredItem<OxygenTankItem> OXYGEN_TANK = registerItem("oxygen_tank", OxygenTankItem::new);
    public final DeferredItem<OxygenMaskItem> OXYGEN_MASK = registerItem("oxygen_mask", OxygenMaskItem::new);

    public EquipmentItems(DeferredRegister.Items register) {
        super(register, "equipment");
    }

}
