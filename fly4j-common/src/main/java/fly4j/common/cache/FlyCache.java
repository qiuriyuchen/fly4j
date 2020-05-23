package fly4j.common.cache;

import java.util.Map;

public interface FlyCache {
    Object get(final String key);

    void put(final String key, final Object obj, long cacheLife);

    long ttl(final String key);

    void invalidateAll();

    void invalidate(String key);

    Map<String, Object> asMap();
}
