package fly4j.common.file;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

public class FileAndDirFilter implements FileFilter {
    //过滤的路径
    private Set<String> filterDirNames = new HashSet<>();
    //过滤文件名
    private Set<String> filterSuffixNames = new HashSet<>();
    private Set<String> alwaysNotAcceptNames = new HashSet<>();
    private boolean includeFile = true;
    private boolean isIncludeDir = true;

    @Override
    public boolean accept(File file) {
        if (null != alwaysNotAcceptNames) {
            for (String filterDir : alwaysNotAcceptNames) {
                if (file.getAbsolutePath().contains(filterDir)) {
                    return false;
                }
            }
        }
        if (null != filterDirNames) {
            for (String filterDir : this.getFilterDirNames()) {
                if (file.getAbsolutePath().contains(filterDir)) {
                    return true;
                }
            }
        }
        if (this.getFilterSuffixNames() != null) {

            String suffix = FilenameUtils.getExtension(file.getName());
            return this.getFilterSuffixNames().contains(suffix);
        }
        return false;
    }

    public Set<String> getFilterDirNames() {
        return filterDirNames;
    }

    public void setFilterDirNames(Set<String> filterDirNames) {
        this.filterDirNames = filterDirNames;
    }

    public Set<String> getFilterSuffixNames() {
        return filterSuffixNames;
    }

    public void setFilterSuffixNames(Set<String> filterSuffixNames) {
        this.filterSuffixNames = filterSuffixNames;
    }

    public void setIsIncludeDir(boolean isIncludeDir) {
        this.isIncludeDir = isIncludeDir;
    }

    public void setIncludeFile(boolean includeFile) {
        this.includeFile = includeFile;
    }

    public void setAlwaysNotAcceptNames(Set<String> alwaysNotAcceptNames) {
        this.alwaysNotAcceptNames = alwaysNotAcceptNames;
    }
}
