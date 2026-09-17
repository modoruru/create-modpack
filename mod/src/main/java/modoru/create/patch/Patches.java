package modoru.create.patch;

import net.neoforged.bus.api.IEventBus;

public final class Patches {

    public static RecipeDisablePatch RECIPE_DISABLE_PATCH;

    private Patches() {}

    public static void bootstrap(IEventBus modEventBus) {
        RECIPE_DISABLE_PATCH = new RecipeDisablePatch();
    }

}
