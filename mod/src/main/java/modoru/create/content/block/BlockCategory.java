package modoru.create.content.block;

import modoru.create.content.RegistrableCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BlockCategory extends RegistrableCategory<Block, DeferredRegister.Blocks> {

    public BlockCategory(DeferredRegister.Blocks register, String category) {
        super(register, category);
    }

    public <B extends Block> DeferredBlock<B> register(String name, Function<ResourceLocation, ? extends B> func) {
        return register.register(category + "/" + name, func);
    }

    public <B extends Block> DeferredBlock<B> register(String name, Supplier<? extends B> sup) {
        return register(name, (key) -> sup.get());
    }

    public <B extends Block> DeferredBlock<B> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends B> func, BlockBehaviour.Properties props) {
        return register(name, () -> func.apply(props));
    }

    public <B extends Block> DeferredBlock<B> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends B> func) {
        return registerBlock(name, func, BlockBehaviour.Properties.of());
    }

}
