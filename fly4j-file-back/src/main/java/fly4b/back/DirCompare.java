package fly4b.back;

import java.util.List;
import java.util.Map;

public interface DirCompare {
    DirCompareResult compar(List<String> compDirs) throws Exception;

    Map<String, String> getDirMd5Map(String checkDir) throws Exception;

    String check(String checkDir, int checkMd5Count) throws Exception;

    void deleteMoreMd5Files(String beZipSourceDir);
}
