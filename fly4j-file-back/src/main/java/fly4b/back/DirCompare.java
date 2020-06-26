package fly4b.back;

import java.util.List;
import java.util.Map;

public interface DirCompare {
    DirCompareResult compar(List<String> compDirs);

    Map<String, String> getDirMd5Map(String checkDir);

    String check(String checkDir, int checkMd5Count);

    void deleteMoreMd5Files(String beZipSourceDir, int maxCount);
}
