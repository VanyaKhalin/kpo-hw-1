package di;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DIContainer {
    private final Map<Class<?>, Supplier<?>> factories = new HashMap<>();
    private final Map<Class<?>, Object> objects = new HashMap<>();

    public <T> void register(Class<T> type, Supplier<T> factory) {
        factories.put(type, factory);
    }

    public <T> T get(Class<T> type) {
        if (!objects.containsKey(type)) {
            Supplier<?> factory = factories.get(type);

            if (factory == null) {
                throw new IllegalArgumentException(
                        "Не зарегистрирован: " + type.getSimpleName()
                );
            }

            objects.put(type, factory.get());
        }

        return type.cast(objects.get(type));
    }
}
