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

    public Map<String, String> encrypt(String srcDirUrl, String targetDirUrl, int pass) {
        String baseDir = new File(srcDirUrl).getAbsolutePath() + File.separator;
        Map<String, String> fileNameMap = new HashMap<>();
        innnerEncrypt(new File(srcDirUrl), targetDirUrl, baseDir, pass, fileNameMap);
        return fileNameMap;
    }


    private void innnerEncrypt(File srcDir, String targetDirUrl, String baseDir, int pass, Map<String, String> fileNameMap) {
        try {
            File[] files = srcDir.listFiles();

            for (File file : files) {
                if (file.isDirectory()) {
                    //递归
                    innnerEncrypt(file, targetDirUrl, baseDir, pass, fileNameMap);
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

    public void reStore(String srcDir, String targetDirUrl, int pass, Map<String, String> fileNameMap) {
        this.reStore(new File(srcDir), targetDirUrl, pass, fileNameMap);
    }

    public void reStore(File srcDir, String targetDirUrl, int pass, Map<String, String> fileNameMap) {
        try {
            File[] files = srcDir.listFiles();

            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }

                //解密存放目录
                String storeName = fileNameMap.get(file.getName());
                String storeFullPath = FilenameUtils.concat(targetDirUrl, storeName);
                //解密
                FileUtil.mkdirs(FilenameUtils.getFullPath(storeFullPath));
                XorUtil.decryptFile(file.getAbsolutePath(), storeFullPath, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        String targetDirEncrypt = FilenameUtils.concat(TestData.tTargetDir, "dirEncrypt");
        String targetDirEncryptResore = FilenameUtils.concat(targetDirEncrypt, "restore");
        FileUtil.mkdirs(targetDirEncrypt);
        FileUtil.mkdirs(targetDirEncryptResore);
        DirEncrypt dirEncrypt = new DirEncrypt();
        Map<String, String> fileNameMap = dirEncrypt.encrypt(FilenameUtils.concat(TestData.tSourceDir, "dirEncrypt"), targetDirEncrypt, 123);
        dirEncrypt.reStore(targetDirEncrypt, targetDirEncryptResore, 123, fileNameMap);
    }
}
