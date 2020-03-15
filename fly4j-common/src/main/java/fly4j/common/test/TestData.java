package fly4j.common.test;

import org.apache.commons.io.FilenameUtils;

/**
 * Created by guan on 2020/3/15.
 */
public class TestData {
    public static String tSourceDir = FilenameUtils.concat(System.getProperty("user.dir"), "testData/source");
    public static String tTargetDir = FilenameUtils.concat(System.getProperty("user.dir"), "testData/target");
}
