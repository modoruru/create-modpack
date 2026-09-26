package modoru.create.content.block;

import modoru.create.ModoruCreate;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class Blocks {

    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ModoruCreate.MODID);

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }

}
