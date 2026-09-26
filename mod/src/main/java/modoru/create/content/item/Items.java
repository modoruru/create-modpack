package modoru.create.content.item;

import modoru.create.ModoruCreate;
import modoru.create.content.item.intermediate.IntermediateItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class Items {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModoruCreate.MODID);

    public static final IntermediateItems INTERMEDIATE = new IntermediateItems(ITEMS);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

}
