package modoru.create.mixin.common;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import modoru.create.patch.Patches;
import modoru.create.patch.RecipeDisablePatch;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.conditions.WithConditions;
import net.neoforged.neoforge.resource.ContextAwareReloadListener;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Optional;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin extends ContextAwareReloadListener {

    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    private Multimap<RecipeType<?>, RecipeHolder<?>> byType;
    @Shadow
    private Map<ResourceLocation, RecipeHolder<?>> byName;

    @Shadow
    private boolean hasErrors;

    /**
     * @author just_lofe
     * @reason provide ability to disable certain recipes on each reload
     */
    @Overwrite
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        hasErrors = false;
        ImmutableMultimap.Builder<RecipeType<?>, RecipeHolder<?>> builder = ImmutableMultimap.builder();
        ImmutableMap.Builder<ResourceLocation, RecipeHolder<?>> builder1 = ImmutableMap.builder();
        RegistryOps<JsonElement> registryops = super.makeConditionalOps();

        RecipeDisablePatch recipeDisablePatch = Patches.RECIPE_DISABLE_PATCH;

        for(Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            if (resourcelocation.getPath().startsWith("_") || recipeDisablePatch.disabled(resourcelocation)) continue;

            try {
                Optional<WithConditions<Recipe<?>>> decoded = Recipe.CONDITIONAL_CODEC.parse(registryops, entry.getValue()).getOrThrow(JsonParseException::new);
                decoded.ifPresentOrElse((r) -> {
                    Recipe<?> recipe = r.carrier();
                    RecipeHolder<?> recipeholder = new RecipeHolder<>(resourcelocation, recipe);
                    builder.put(recipe.getType(), recipeholder);
                    builder1.put(resourcelocation, recipeholder);
                }, () -> LOGGER.debug("Skipping loading recipe {} as its conditions were not met", resourcelocation));
            }
            catch (JsonParseException | IllegalArgumentException jsonparseexception) {
                LOGGER.error("Parsing error loading recipe {}", resourcelocation, jsonparseexception);
            }
        }

        byType = builder.build();
        byName = builder1.build();
        LOGGER.info("Loaded {} recipes", this.byType.size());
    }

}
