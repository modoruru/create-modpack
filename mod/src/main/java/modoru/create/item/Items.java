package modoru.create.item;

import modoru.create.ModoruCreate;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class Items {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModoruCreate.MODID);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

}
