package fly4b.back;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guan on 2020/2/23.
 */
public class DirCompareResult {
    private List<String> delFiles = new ArrayList<>();

    public List<String> getDelFiles() {
        return delFiles;
    }

    public void addDelFile(String delFile) {
        this.delFiles.add(delFile);
    }
}
