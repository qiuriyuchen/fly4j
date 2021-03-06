package fly4j.back.zip;

import fly4j.common.file.FileUtil;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ExcludeFileFilter;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * 使用zip4j
 * http://www.lingala.net/zip4j.html
 */
public class Zip4jTool {


    public static void zipFile(File destZipFile, File srcFile, String pwd) throws Exception {
        if (StringUtils.isBlank(pwd)) {
            throw new RuntimeException("password is empty");
        }
        if (srcFile.isDirectory()) {
            throw new RuntimeException("is not Directory");
        }
        FileUtils.forceMkdirParent(destZipFile);
        new ZipFile(destZipFile, pwd.toCharArray())
                .addFile(srcFile, getZipParameters());

    }

    public static void zipDir(File destZipFile, File srcDir, String pwd) throws Exception {
        if (StringUtils.isBlank(pwd)) {
            throw new RuntimeException("password is empty");
        }
        if (!srcDir.isDirectory()) {
            throw new RuntimeException("is not Directory");
        }

        FileUtils.forceMkdirParent(destZipFile);

        new ZipFile(destZipFile, pwd.toCharArray())
                .addFolder(srcDir, getZipParameters());

    }

    private static ZipParameters getZipParameters() {
        ZipParameters parameters = new ZipParameters();
        //DEFLATE是同时使用了LZ77算法与哈夫曼编码（Huffman Coding）的一个无损数据压缩算法
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setCompressionLevel(CompressionLevel.NORMAL);
        //set password
        parameters.setEncryptFiles(true);
        // byte temp_val = (byte) (val ^ zipCryptoEngine.decryptByte() & 0xff);异或加密
        parameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
        return parameters;
    }

    /**
     * 解压
     *
     * @param unZipFile 压缩包文件
     * @param destFile      目标文件
     * @param pwd       密码
     * @throws ZipException 抛出异常
     */
    public static void unZip(File unZipFile, File destFile, String pwd) throws Exception {
        if (StringUtils.isBlank(pwd)) {
            throw new RuntimeException("password is empty");
        }

        ZipFile zipFile = new ZipFile(unZipFile.getAbsoluteFile(), pwd.toCharArray());
        zipFile.setCharset(StandardCharsets.UTF_8);//在GBK系统中需要设置
        if (!zipFile.isValidZipFile()) {
            throw new ZipException("压缩文件不合法，可能已经损坏！");
        }

        FileUtils.forceMkdir(destFile);
        zipFile.extractAll(destFile.getAbsolutePath());
    }

    public static void unZip(File zipfile, File destFile) throws Exception {

        ZipFile zipFile = new ZipFile(zipfile);
        if (!zipFile.isValidZipFile()) {
            throw new ZipException("压缩文件不合法，可能已经损坏！");
        }

        FileUtils.forceMkdir(destFile);
        zipFile.extractAll(destFile.getAbsolutePath());
    }

}
