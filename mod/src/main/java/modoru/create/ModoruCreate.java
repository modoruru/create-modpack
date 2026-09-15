package modoru.create;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ModoruCreate.MODID)
public final class ModoruCreate {

    public static final String MODID = "modoru_create";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ModoruCreate(IEventBus modEventBus, ModContainer modContainer) {

    }

}
