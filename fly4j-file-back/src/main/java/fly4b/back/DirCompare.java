package fly4b.back;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface DirCompare {
    DirCompareResult compar(List<Path> compDirs);

    Map<String, String> getDirMd5Map(Path checkDir);

    String check(Path checkDir, int checkMd5Count);

    void deleteMoreMd5Files(Path beZipSourceDir, int maxCount);
}
