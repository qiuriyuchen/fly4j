package fly4b.back;

import fly4j.common.file.FileNameUtilHelper;
import fly4j.common.file.FileUtil;
import fly4j.common.test.TestData;
import fly4j.crypto.MD5Util;
import fly4j.crypto.XorUtil;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guan on 2020/3/15.
 */
public class DirEncrypt {
    private Map<String, String> fileNameMap = new HashMap<>();

    public void encrypt(String srcDirUrl, String targetDirUrl, int pass) {
        String baseDir = new File(srcDirUrl).getAbsolutePath() + File.separator;
        innnerEncrypt(new File(srcDirUrl), targetDirUrl, baseDir, pass);
    }

    private void innnerEncrypt(File srcDir, String targetDirUrl, String baseDir, int pass) {
        try {
            File[] files = srcDir.listFiles();

            for (File file : files) {
                if (file.isDirectory()) {
                    //递归
                    innnerEncrypt(file, targetDirUrl, baseDir, pass);
                } else {
                    //生成md5
                    String key = FileUtil.getRelativeStandardPath(file, baseDir);
                    long md5 = MD5Util.ketamaHash(key);
                    XorUtil.encryptFile(file.getAbsolutePath(), FilenameUtils.concat(targetDirUrl, md5 + ".fbk"), pass);
                    fileNameMap.put(md5 + ".fbk", key);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private void innnerDecrypt(File srcDir, String targetDirUrl, String baseDir, int pass) {
        try {
            File[] files = srcDir.listFiles();

            for (File file : files) {
                if (file.isDirectory()) {
                    //递归
                    innnerDecrypt(file, targetDirUrl, baseDir, pass);
                } else {
                    //解密存放目录
                    String storeName = fileNameMap.get(file.getName());
                    //解密
                    XorUtil.decryptFile(file.getAbsolutePath(), FilenameUtils.concat(targetDirUrl, storeName), pass);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        DirEncrypt dirEncrypt = new DirEncrypt();
        dirEncrypt.encrypt(FilenameUtils.concat(TestData.tSourceDir, "dirEncrypt"), FilenameUtils.concat(TestData.tTargetDir, "dirEncrypt"), 123);
    }
}
