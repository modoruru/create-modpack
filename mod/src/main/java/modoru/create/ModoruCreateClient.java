package modoru.create;

import modoru.create.screen.NotInModpackScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ScreenEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Mod(value = ModoruCreate.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ModoruCreate.MODID, value = {Dist.CLIENT})
public final class ModoruCreateClient {

    public ModoruCreateClient(IEventBus modEventBus) {
    }

    @SubscribeEvent
    public static void onInitScreenEventPost(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof TitleScreen && !inModpack(Minecraft.getInstance().gameDirectory)) {
            Minecraft.getInstance().setScreen(new NotInModpackScreen());
        }
    }

    private static boolean inModpack(File gameDirectory) {
        File file = new File(gameDirectory, "modoru_modpack.properties");
        if(!file.exists()) return false;

        try (FileInputStream fis = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(fis);

            return properties.getProperty("modoru_modpack", "false").equals("true");
        }
        catch (IOException exception) {
            return false;
        }
    }

}
