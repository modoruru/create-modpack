package modoru.create.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ItemCategory {

    protected final DeferredRegister.Items items;
    protected final String category;

    public ItemCategory(DeferredRegister.Items items, String category) {
        this.items = items;
        this.category = category;
    }

    public <I extends Item> DeferredItem<I> registerItem(String name, Function<Item.Properties, ? extends I> func, Item.Properties props) {
        return items.register(category + "/" + name, (Supplier) () -> (Item) func.apply(props));
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
