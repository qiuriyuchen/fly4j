package fly4j.common.file;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileInfoUtil {
    public static List<FileInfo> getFileInfos(Collection<File> files) {
        return getFileInfos(files, null);
    }

    public static List<FileInfo> getFileInfos(Collection<File> files, Boolean isPic) {
        List<FileInfo> fileInfos = new ArrayList<FileInfo>();
        if (files == null || fileInfos.size() == 0) {
            return fileInfos;
        }
        for (File file : files) {
            if (null == isPic) {
                FileInfo fileInfo = new FileInfo(file);
                fileInfos.add(fileInfo);
                continue;
            }
            if (FileUtil.isImg(file)) {
                if (isPic) {
                    FileInfo fileInfo = new FileInfo(file);
                    fileInfos.add(fileInfo);
                }

            } else {
                if (!isPic) {
                    FileInfo fileInfo = new FileInfo(file);
                    fileInfos.add(fileInfo);
                }
            }

        }

        return fileInfos;
    }

    public static List<FileInfo> getFileInfos(File[] files) {
        List<FileInfo> fileInfos = new ArrayList<FileInfo>();
        if (null == files) {
            return fileInfos;
        }
        if (0 == files.length) {
            return fileInfos;
        }
        for (File file : files) {
            FileInfo fileInfo = new FileInfo(file);
            fileInfos.add(fileInfo);
        }

        return fileInfos;
    }
}
