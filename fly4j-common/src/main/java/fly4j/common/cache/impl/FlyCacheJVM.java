package fly4j.common.cache.impl;

import fly4j.common.cache.FlyCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FlyCacheJVM implements FlyCache {

    private static final Log log = LogFactory.getLog(FlyCacheJVM.class);
    private Map<String, CacheInfo> cacheInfoMap = new ConcurrentHashMap<String, CacheInfo>();
    private ConcurrentLinkedQueue<String> keyQueue = new ConcurrentLinkedQueue<String>();
    private int QUEUESIZE = 1000;

    public FlyCacheJVM() {
    }

    public FlyCacheJVM(int QUEUESIZE) {
        this.QUEUESIZE = QUEUESIZE;
    }

    @Override
    public Object get(final String key) {
        try {
            if (cacheInfoMap.containsKey(key)) {
                CacheInfo cache = cacheInfoMap.get(key);
                // 保证三项数都有才是正常数据，队列，超时时间，存储数据
                if (cache != null && keyQueue.contains(key)
                        && cache.isLive()) {
                    return cache.value;
                }
            }
        } catch (Exception e) {
            log.error("get获取本地缓存用户信息错误", e);
        }

        return null;
    }

    @Override
    public long ttl(String key) {
        try {
            if (cacheInfoMap.containsKey(key)) {
                CacheInfo cache = cacheInfoMap.get(key);
                // 保证三项数都有才是正常数据，队列，超时时间，存储数据
                if (cache != null && keyQueue.contains(key)) {
                    return cache.ttl();
                }
            }
        } catch (Exception e) {
            log.error("get获取本地缓存用户信息错误", e);
        }

        return -1;
    }

    @Override
    public void invalidateAll() {
        cacheInfoMap = new ConcurrentHashMap<String, CacheInfo>();
        keyQueue = new ConcurrentLinkedQueue<String>();
    }

    @Override
    public void invalidate(String key) {
        cacheInfoMap.remove(key);
        keyQueue.remove(key);
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        cacheInfoMap.forEach((key, value) -> {
            if (get(key) != null) {
                map.put(key, get(key));
            }
        });
        return map;
    }

    @Override
    public void put(final String key, final Object obj, long cacheLife) {
        try {
            CacheInfo cacheInfo = cacheInfoMap.get(key);
            if (cacheInfo != null && keyQueue.contains(key)
                    && cacheInfo.isLive()) {
                cacheInfoMap.put(key, new CacheInfo(obj, cacheLife));
            } else {
                int remainNum = QUEUESIZE - keyQueue.size();
                while (remainNum <= 0) {
                    String removeKey = keyQueue.poll();
                    cacheInfoMap.remove(removeKey);
                    remainNum++;
                }
                cacheInfoMap.put(
                        key,
                        new CacheInfo(obj, cacheLife));
            }
            // 保证队列中值唯一
            synchronized (keyQueue) {
                keyQueue.remove(key);
                keyQueue.add(key);
            }
        } catch (Exception e) {
            log.error("put缓存本地缓存用户信息错误", e);
        } finally {
        }
    }

    private static class CacheInfo {
        public CacheInfo(Object value, long life) {
            this.value = value;
            this.createTime = System.currentTimeMillis();
            this.life = life;
        }

        public boolean isLive() {
            long now = System.currentTimeMillis();
            return now < getDeadTime();

        }

        private long getDeadTime() {
            return this.createTime + life;
        }

        private long ttl() {
            return getDeadTime() - System.currentTimeMillis();
        }


        private Object value;
        private long createTime;
        private long life;
    }
}
