package fly4j.common.test;

import fly4j.common.os.OsUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by guan on 2020/3/15.
 */
public class TestData {
    public static Path testPath;

    static {
        if (OsUtil.isWindows()) {
            testPath = Path.of("D:/flyTestData");
        } else {
            testPath = Path.of("/export/flyTestData");
        }
    }


    public static String tSourceDir = FilenameUtils.concat(System.getProperty("user.dir"), "testData/source");
    public static String tTargetDir = FilenameUtils.concat(System.getProperty("user.dir"), "testData/target");
}
