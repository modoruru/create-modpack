package modoru.create.content.item.intermediate;

import modoru.create.content.item.ItemCategory;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class IntermediateItems extends ItemCategory {

    public final DeferredItem<UnfiredAndesiteAlloyItem> UNFIRED_ANDESITE_ALLOY = registerItem("unfired_andesite_alloy", UnfiredAndesiteAlloyItem::new);

    public IntermediateItems(DeferredRegister.Items items) {
        super(items, "intermediate");
    }

}
