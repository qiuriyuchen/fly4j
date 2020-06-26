package fly4j.common.crypto;

import fly4j.common.test.TestData;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Path;

/**
 * 图片工具
 * Created by guanpanpan on 2016/10/31.
 */
public class XorUtil {


    public static void main(String[] args) throws Exception {
        encryptFile(Path.of(TestData.tSourceDir, "xorSource.txt"), Path.of(TestData.tTargetDir, "xorTarget.fbk"), 123);
        decryptFile(Path.of(TestData.tTargetDir, "xorTarget.fbk"), Path.of(TestData.tTargetDir, "xorResote.txt"), 123);
    }


    public static void encryptFile(Path sourceFileUrl, Path targetFileUrl, int pass) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(sourceFileUrl.toFile());
            out = new FileOutputStream(targetFileUrl.toFile());
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

    public static void decryptFile(Path sourceFileUrl, Path targetFileUrl, int pass) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(sourceFileUrl.toFile());
            out = new FileOutputStream(targetFileUrl.toFile());
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
