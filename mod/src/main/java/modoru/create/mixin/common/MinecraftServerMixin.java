package modoru.create.mixin.common;

import com.google.common.collect.ImmutableList;
import modoru.create.patch.Patches;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.CatSpawner;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Shadow @Final protected WorldData worldData;
    @Shadow @Final private LayeredRegistryAccess<RegistryLayer> registries;
    @Shadow @Final private Executor executor;
    @Shadow @Final protected LevelStorageSource.LevelStorageAccess storageSource;
    @Shadow @Final private Map<ResourceKey<Level>, ServerLevel> levels;

    @Shadow private @Nullable CommandStorage commandStorage;

    @Shadow
    protected abstract void readScoreboard(DimensionDataStorage dataStorage);

    @Shadow
    private static void setInitialSpawn(ServerLevel level, ServerLevelData levelData, boolean generateBonusChest, boolean debug) {}

    @Shadow
    protected abstract void setupDebugLevel(WorldData worldData);

    /**
     * @author just_lofe
     * @reason provide ability to disable dimensions on the server startup
     */
    @Overwrite
    protected void createLevels(ChunkProgressListener listener) {
        ServerLevelData serverleveldata = worldData.overworldData();
        boolean flag = worldData.isDebugWorld();
        Registry<LevelStem> registry = registries.compositeAccess().registryOrThrow(Registries.LEVEL_STEM);
        WorldOptions worldoptions = worldData.worldGenOptions();
        long i = worldoptions.seed();
        long j = BiomeManager.obfuscateSeed(i);
        List<CustomSpawner> list = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(serverleveldata));
        LevelStem levelstem = registry.get(LevelStem.OVERWORLD);
        ServerLevel serverlevel = new ServerLevel((MinecraftServer) (Object) this, executor, storageSource, serverleveldata, Level.OVERWORLD, levelstem, listener, flag, j, list, true, null);
        levels.put(Level.OVERWORLD, serverlevel);
        DimensionDataStorage dimensiondatastorage = serverlevel.getDataStorage();
        readScoreboard(dimensiondatastorage);
        commandStorage = new CommandStorage(dimensiondatastorage);
        WorldBorder worldborder = serverlevel.getWorldBorder();
        NeoForge.EVENT_BUS.post(new LevelEvent.Load(levels.get(Level.OVERWORLD)));
        if (!serverleveldata.isInitialized()) {
            try {
                setInitialSpawn(serverlevel, serverleveldata, worldoptions.generateBonusChest(), flag);
                serverleveldata.setInitialized(true);
                if (flag) {
                    setupDebugLevel(worldData);
                }
            }
            catch (Throwable throwable1) {
                CrashReport crashreport = CrashReport.forThrowable(throwable1, "Exception initializing level");

                try {
                    serverlevel.fillReportDetails(crashreport);
                } catch (Throwable var22) {
                }

                throw new ReportedException(crashreport);
            }

            serverleveldata.setInitialized(true);
        }

        MinecraftServer asMinecraftServer = (MinecraftServer) (Object) this;
        asMinecraftServer.getPlayerList().addWorldborderListener(serverlevel);
        if (this.worldData.getCustomBossEvents() != null) {
            asMinecraftServer.getCustomBossEvents().load(worldData.getCustomBossEvents(), asMinecraftServer.registryAccess());
        }

        RandomSequences randomsequences = serverlevel.getRandomSequences();

        for(Map.Entry<ResourceKey<LevelStem>, LevelStem> entry : registry.entrySet()) {
            ResourceKey<LevelStem> resourcekey = entry.getKey();
            // The actual patch begins here: we need to cancel initialization and registration if world is disabled.
            if (resourcekey != LevelStem.OVERWORLD && !Patches.DIMENSION_DISABLE_PATCH.disabled(resourcekey.location())) {
                ResourceKey<Level> resourcekey1 = ResourceKey.create(Registries.DIMENSION, resourcekey.location());
                DerivedLevelData derivedleveldata = new DerivedLevelData(this.worldData, serverleveldata);
                ServerLevel serverlevel1 = new ServerLevel(asMinecraftServer, this.executor, this.storageSource, derivedleveldata, resourcekey1, (LevelStem)entry.getValue(), listener, flag, j, ImmutableList.of(), false, randomsequences);
                worldborder.addListener(new BorderChangeListener.DelegateBorderChangeListener(serverlevel1.getWorldBorder()));
                this.levels.put(resourcekey1, serverlevel1);
                NeoForge.EVENT_BUS.post(new LevelEvent.Load(this.levels.get(resourcekey)));
            }
        }

        worldborder.applySettings(serverleveldata.getWorldBorder());
    }

}
