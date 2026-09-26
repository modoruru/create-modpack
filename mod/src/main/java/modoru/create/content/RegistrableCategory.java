package modoru.create.content;

import net.neoforged.neoforge.registries.DeferredRegister;

public abstract class RegistrableCategory<T, R extends DeferredRegister<T>> {

    protected final R register;
    protected final String category;

    public RegistrableCategory(R register, String category) {
        this.register = register;
        this.category = category;
    }

}
