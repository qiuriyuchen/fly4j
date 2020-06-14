package fly4j.common.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by qryc on 2018/11/24.
 */
public class CompareFile {
    public static CompareResult compareResult = new CompareResult();

    public static void main(String[] args) throws Exception {
        List<String> dirs = new ArrayList<>();
        dirs.add("D:\\a");
        dirs.add("G:\\a");
        compareDirs(dirs, false, "sync");
    }

    public static void compareDirs(List<String> dirs, boolean fix, String asyncOrSync) throws IOException {

        if (compareResult.run) {
            return;
        }
        if ("sync".equals(asyncOrSync)) {
            compareResult.run = true;
            doCompareDirs(dirs, fix);
            compareResult.run = false;
            return;
        } else {
            new Thread() {
                @Override
                public void run() {
                    compareResult.run = true;
                    try {
                        doCompareDirs(dirs, fix);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    compareResult.run = false;
                }
            }.start();
        }


    }

    private static void doCompareDirs(List<String> dirs, boolean fix) throws IOException {
        compareResult.reSet();
        String dir1 = dirs.get(0).trim();
        Map<String, Long> name_size1 = getRelativePaths_fileSize(dir1);
        compareResult.oneDirCount = name_size1.size();
        for (int i = 1; i < dirs.size(); i++) {
            String dir2 = dirs.get(i).trim();
            if (StringUtils.isBlank(dir2)) {
                continue;
            }
            CompareResult oneCp = compareTwoDir(dir1, dir2, fix, name_size1);
            compareResult.merge(oneCp, dir2);
        }
    }

    private static CompareResult compareTwoDir(String dir1, String dir2, boolean fix, Map<String, Long> relativePaths_size1) throws IOException {

        CompareResult oneCp = new CompareResult();
        oneCp.totalCount = relativePaths_size1.size();

        Map<String, Long> relativePaths_fileSize2 = getRelativePaths_fileSize(dir2);
        for (String relativePath1 : relativePaths_size1.keySet()) {
            if (relativePaths_fileSize2.containsKey(relativePath1)) {
                if (relativePaths_size1.get(relativePath1).equals(relativePaths_fileSize2.get(relativePath1))) {
                    oneCp.sameCount++;
                } else {
                    oneCp.msgResult.add("大小不一样:" + dir2 + relativePath1);
                    oneCp.diffCount++;
                    if (fix) {
                        File src = new File(dir1 + relativePath1);
                        File desc = new File(dir2 + relativePath1);
                        FileUtils.copyFile(src, desc, true);
                        oneCp.msgResult.add("修复文件:" + dir2 + relativePath1);
                    }
                }
            } else {
                oneCp.msgResult.add("lost file:" + dir2 + relativePath1);
                oneCp.diffCount++;
                if (fix) {
                    File src = new File(dir1 + relativePath1);
                    File desc = new File(dir2 + relativePath1);
                    FileUtils.copyFile(src, desc, true);
                    oneCp.msgResult.add("拷贝文件:" + dir2 + relativePath1);
                }
            }
        }
        return oneCp;
    }

    private static Map<String, Long> getRelativePaths_fileSize(String dirPath) {
        Map<String, Long> relativePath_fileSize = new HashMap<>();
        File dirFile = new File(dirPath);
        Collection<File> childrenFiles = FileUtils.listFiles(dirFile, null, true);
        for (File file : childrenFiles) {
            String relativePath = file.getAbsolutePath().substring(dirPath.length());
            relativePath_fileSize.put(relativePath, file.length());
        }
        return relativePath_fileSize;
    }


}
