package fly4j.common.cache;

public interface LimitRate {
    //限流
    boolean isHotLimit(String id);
}
