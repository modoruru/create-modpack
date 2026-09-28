package modoru.create.content.component;

import modoru.create.content.RegistrableCategory;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public abstract class DataComponentCategory extends RegistrableCategory<DataComponentType<?>, DeferredRegister.DataComponents> {

    public DataComponentCategory(DeferredRegister.DataComponents register, String category) {
        super(register, category);
    }

    public <D> DeferredHolder<DataComponentType<?>, DataComponentType<D>> registerComponentType(String name, UnaryOperator<DataComponentType.Builder<D>> builder) {
        return register.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }

}
