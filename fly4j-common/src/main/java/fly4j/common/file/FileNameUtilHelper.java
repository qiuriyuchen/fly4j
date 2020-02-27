package fly4j.common.file;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * Created by guan on 2020/2/27.
 */
public class FileNameUtilHelper {
    public static final String EXTENSION_SEPARATOR_STR = ".";

    public static String normalizeEndSeparator(final String filename) {
        if (filename.contains(EXTENSION_SEPARATOR_STR)) {
            return FilenameUtils.normalizeNoEndSeparator(filename);
        } else {
            return FilenameUtils.normalizeNoEndSeparator(filename) + File.separator;
        }

    }

    public static void main(String[] args) {
        System.out.println(FileNameUtilHelper.normalizeEndSeparator("c:\\a\\b\\c.txt"));
        System.out.println(FileNameUtilHelper.normalizeEndSeparator("c:\\a\\b\\e"));
        System.out.println(FileNameUtilHelper.normalizeEndSeparator("c:\\a\\b\\e\\"));
    }
}
