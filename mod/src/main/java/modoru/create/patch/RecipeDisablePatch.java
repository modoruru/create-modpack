package modoru.create.patch;

import com.google.gson.Gson;
import modoru.create.ModoruCreate;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public final class RecipeDisablePatch {

    private static final Gson GSON = new Gson();
    private final Set<ResourceLocation> DISABLED_RECIPES;

    public RecipeDisablePatch() {
        DISABLED_RECIPES = new HashSet<>();

        try (InputStream inputStream = ModoruCreate.class.getResourceAsStream("/patch/recipe_disable.json")) {
            if(inputStream == null) throw new NullPointerException();

            String[] array = GSON.fromJson(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), String[].class);
            for (String string : array) {
                DISABLED_RECIPES.add(ResourceLocation.parse(string));
            }
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public boolean disabled(ResourceLocation resourceLocation) {
        return DISABLED_RECIPES.contains(resourceLocation);
    }

}
