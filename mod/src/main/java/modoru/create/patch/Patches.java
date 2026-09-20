package modoru.create.patch;

import com.google.gson.Gson;
import net.neoforged.bus.api.IEventBus;

public final class Patches {

    public static final Gson GSON = new Gson();

    public static RecipeDisablePatch RECIPE_DISABLE_PATCH;
    public static DimensionDisablePatch DIMENSION_DISABLE_PATCH;

    private Patches() {}

    public static void bootstrap(IEventBus modEventBus) {
        RECIPE_DISABLE_PATCH = new RecipeDisablePatch();
        DIMENSION_DISABLE_PATCH = new DimensionDisablePatch();
    }

}
