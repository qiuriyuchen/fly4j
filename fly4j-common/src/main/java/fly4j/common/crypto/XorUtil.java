package fly4j.common.crypto;

import fly4j.common.test.TestData;
import org.apache.commons.io.FilenameUtils;

import java.io.*;

/**
 * 图片工具
 * Created by guanpanpan on 2016/10/31.
 */
public class XorUtil {


    public static void main(String[] args) throws Exception {
        encryptFile(FilenameUtils.concat(TestData.tSourceDir, "xorSource.txt"), FilenameUtils.concat(TestData.tTargetDir, "xorTarget.fbk"), 123);
        decryptFile(FilenameUtils.concat(TestData.tTargetDir, "xorTarget.fbk"), FilenameUtils.concat(TestData.tTargetDir, "xorResote.txt"), 123);
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
