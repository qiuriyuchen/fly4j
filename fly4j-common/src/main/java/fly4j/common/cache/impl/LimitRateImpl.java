package fly4j.common.cache.impl;

import fly4j.common.cache.FlyCache;
import fly4j.common.cache.LimitRate;

import java.util.concurrent.atomic.AtomicInteger;

public class LimitRateImpl implements LimitRate {
    private FlyCache flyCache;
    private boolean isLimit = true;
    private int time = 20;
    private int limitNum = 2;

    public LimitRateImpl(int time, int limitNum) {
        this.time = time;
        this.limitNum = limitNum;
    }

    public LimitRateImpl(FlyCache flyCache, int time, int limitNum) {
        this.flyCache = flyCache;
        this.time = time;
        this.limitNum = limitNum;
    }

    //限流
    @Override
    public boolean isHotLimit(String id) {
        try {
            String key = "limit" + String.valueOf(id);
            AtomicInteger count = (AtomicInteger) flyCache.get(key);
            if (null == count) {
                flyCache.put(key, new AtomicInteger(1), time);
                return false;
            }
            int countNum = count.incrementAndGet();
            if (countNum <= 5) {
//                         + "_" + count);
            } else if (countNum <= 10) {
//                         + "_countMore6");
            } else if (countNum <= 15) {
//                         + "_countMore11");
            } else if (countNum <= 20) {
//                         + "_countMore16");
            } else {
//                         + "_countMore21");
            }
            if (isLimit && countNum > limitNum) {
//                     + "_hit");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
