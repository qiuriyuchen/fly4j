package fly4j.common.track;

/**
 * 保存程序上下文信息
 * Created by guanpanpan on 2018-01-11.
 */
public class TrackContext {
    private static ThreadLocal<TrackContext> threadLocal=new ThreadLocal<TrackContext>();
    public static void init() {
        if(null==threadLocal.get()){
            threadLocal.set(new TrackContext());
        }

    }
    /**业务跟踪*/
    private StringBuilder trackInfoStrBuilder = new StringBuilder();

    public static String getTrackInfo() {
        init();
        return threadLocal.get().trackInfoStrBuilder.toString();
    }

    public static void appendTrackInfo(String iknowInfoAppend) {
        init();
        threadLocal.get().trackInfoStrBuilder.append(iknowInfoAppend).append(";   ");
    }
}
