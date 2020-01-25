package fly4j.cache;

public interface LimitRate {
    //限流
    boolean isHotLimit(String id);
}
