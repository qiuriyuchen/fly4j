package fly4b.back;

import java.util.List;
import java.util.Set;

public interface DirZipConfig {
    List<ZipConfig> getZipConfigList();


    void reSetZipConfigs(List<ZipConfig> zipConfigs);

    boolean isdeleteDir(String path);

}
