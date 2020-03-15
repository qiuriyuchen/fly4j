package fly4j.crypto;

import org.apache.commons.io.FilenameUtils;

import java.io.*;

/**
 * 图片工具
 * Created by guanpanpan on 2016/10/31.
 */
public class XorUtil {
    private static String tSourceDir = FilenameUtils.concat(System.getProperty("user.dir"), "testData/source");
    private static String tTargetDir = FilenameUtils.concat(System.getProperty("user.dir"), "testData/target");

    public static void main(String[] args) throws Exception {
        encryptFile(FilenameUtils.concat(tSourceDir, "xorSource.txt"), FilenameUtils.concat(tTargetDir, "xorTarget.fbk"), 123);
        decryptFile(FilenameUtils.concat(tTargetDir, "xorTarget.fbk"), FilenameUtils.concat(tTargetDir, "xorResote.txt"), 123);
    }


    public static void encryptFile(String sourceFileUrl, String targetFileUrl, int pass) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(sourceFileUrl);
            out = new FileOutputStream(targetFileUrl);
            int data = 0;
            while ((data = in.read()) != -1) {
                //将读取到的字节异或上一个数，加密输出
                out.write(data ^ pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //在finally中关闭开启的流
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void decryptFile(String sourceFileUrl, String targetFileUrl, int pass) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(sourceFileUrl);
            out = new FileOutputStream(targetFileUrl);
            int data = 0;
            while ((data = in.read()) != -1) {
                //将读取到的字节异或上一个数，加密输出
                out.write(data ^ pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //在finally中关闭开启的流
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
