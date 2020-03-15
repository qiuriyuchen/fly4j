package fly4b.back;

import fly4j.common.JsonUtils;
import fly4j.common.file.FileNameUtilHelper;
import fly4j.common.file.FileUtil;
import fly4j.common.test.TestData;
import fly4j.crypto.MD5Util;
import fly4j.crypto.XorUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by guan on 2020/3/15.
 */
public class DirEncrypt {

    public Map<String, String> encrypt(String srcDirUrl, String targetDirUrl, int pass) {
        String baseDir = new File(srcDirUrl).getAbsolutePath() + File.separator;
        Map<String, String> fileNameMap = new HashMap<>();
        innnerEncrypt(new File(srcDirUrl), targetDirUrl, baseDir, pass, fileNameMap, new AtomicInteger(0));
        try {
            FileUtils.writeStringToFile(new File(FilenameUtils.concat(targetDirUrl, "index.fbkindex")), JsonUtils.writeValueAsString(fileNameMap), Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileNameMap;
    }


    private void innnerEncrypt(File srcDir, String targetDirUrl, String baseDir, int pass, Map<String, String> fileNameMap, AtomicInteger index) {
        try {
            File[] files = srcDir.listFiles();

            for (File file : files) {
                if (file.isDirectory()) {
                    //递归
                    innnerEncrypt(file, targetDirUrl, baseDir, pass, fileNameMap, index);
                } else {
                    //生成md5
                    String key = FileUtil.getRelativeStandardPath(file, baseDir);
                    long md5 = MD5Util.ketamaHash(key);
                    String fbkFile = FilenameUtils.concat(targetDirUrl, "" + (index.get() / 100));
                    fbkFile = FilenameUtils.concat(fbkFile, md5 + ".fbk");
                    FileUtils.forceMkdirParent(new File(fbkFile));
                    XorUtil.encryptFile(file.getAbsolutePath(), fbkFile, pass);
                    fileNameMap.put(md5 + ".fbk", key);
                    index.incrementAndGet();
                }

            }
        } catch (Exception e) {
            System.out.println(srcDir.getAbsolutePath() + " exception");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void reStore(String srcDir, String targetDirUrl, int pass) {
        //取得上次的md5Map
        File md5File = new File(FilenameUtils.concat(srcDir, "index.fbkindex"));
        try {
            String md5Str = FileUtils.readFileToString(md5File, Charset.forName("utf-8"));
            HashMap<String, String> fileNameMap = JsonUtils.readStringStringHashMap(md5Str);
            this.reStore(new File(srcDir), targetDirUrl, pass, fileNameMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void reStore(File srcDir, String targetDirUrl, int pass, Map<String, String> fileNameMap) {
        try {
            File[] files = srcDir.listFiles();

            for (File file : files) {
                if (file.isDirectory()) {
                    reStore(file, targetDirUrl, pass, fileNameMap);
                } else {
                    if (FilenameUtils.getExtension(file.getName()).equals("fbk")) {
                        //解密存放目录
                        String storeName = fileNameMap.get(file.getName());
                        String storeFullPath = FilenameUtils.concat(targetDirUrl, storeName);
                        //解密
                        FileUtil.forceMkdir(FilenameUtils.getFullPath(storeFullPath));
                        XorUtil.decryptFile(file.getAbsolutePath(), storeFullPath, pass);
                    }

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws Exception {
//        test();
        Integer a = 0;
        Integer b = a;
        b = 100;
        System.out.println(a);
        System.out.println(b);
    }

    private static void test() throws IOException {
        FileUtils.forceDelete(new File(TestData.tTargetDir));
        String targetDirEncrypt = FilenameUtils.concat(TestData.tTargetDir, "dirEncrypt");
        String targetDirEncryptResore = FilenameUtils.concat(TestData.tTargetDir, "dirEncryptRestore");
        FileUtil.forceMkdir(targetDirEncrypt);
        FileUtil.forceMkdir(targetDirEncryptResore);
        DirEncrypt dirEncrypt = new DirEncrypt();
        dirEncrypt.encrypt(FilenameUtils.concat(TestData.tSourceDir, "dirEncrypt"), targetDirEncrypt, 123);
        dirEncrypt.reStore(targetDirEncrypt, targetDirEncryptResore, 123);
    }
}
