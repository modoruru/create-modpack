package modoru.create.patch;

import modoru.create.ModoruCreate;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public final class DimensionDisablePatch {

    private final Set<ResourceLocation> DISABLED_DIMENSIONS;

    public DimensionDisablePatch() {
        DISABLED_DIMENSIONS = new HashSet<>();

        try (InputStream inputStream = ModoruCreate.class.getResourceAsStream("/patch/dimension_disable.json")) {
            if(inputStream == null) throw new NullPointerException();

            String[] array = Patches.GSON.fromJson(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), String[].class);
            for (String string : array) {
                DISABLED_DIMENSIONS.add(ResourceLocation.parse(string));
            }
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public boolean disabled(ResourceLocation resourceLocation) {
        return DISABLED_DIMENSIONS.contains(resourceLocation);
    }

}
