package fly4j.back;

import fly4j.common.FlyResult;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface DirCompare {
    DirCompareResult compareMulDir(List<File> compDirs);

    Map<String, String> getDirMd5Map(File checkDir);

    FlyResult checkWithHistory(File checkDir);

    void deleteMoreMd5Files(File beZipSourceDir, int maxCount);
}
