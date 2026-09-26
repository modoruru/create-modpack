package modoru.create.content.item;

import modoru.create.content.RegistrableCategory;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public abstract class ItemCategory extends RegistrableCategory<Item, DeferredRegister.Items> {

    public ItemCategory(DeferredRegister.Items register, String category) {
        super(register, category);
    }

    public <I extends Item> DeferredItem<I> registerItem(String name, Function<Item.Properties, ? extends I> func, Item.Properties props) {
        return register.register(category + "/" + name, () -> func.apply(props));
    }

    public <I extends Item> DeferredItem<I> registerItem(String name, Function<Item.Properties, ? extends I> func) {
        return this.registerItem(name, func, new Item.Properties());
    }

    public DeferredItem<Item> registerSimpleItem(String name, Item.Properties props) {
        return this.registerItem(name, Item::new, props);
    }

    public DeferredItem<Item> registerSimpleItem(String name) {
        return this.registerItem(name, Item::new, new Item.Properties());
    }

}
