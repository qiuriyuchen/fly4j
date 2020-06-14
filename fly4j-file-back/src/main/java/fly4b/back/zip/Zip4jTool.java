package fly4b.back.zip;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 使用zip4j
 * http://www.lingala.net/zip4j.html
 */
public class Zip4jTool {

    /**
     * 压缩
     *
     * @param srcFile 源目录
     * @param destZip 要压缩的目录
     * @param passwd  密码 不是必填
     * @throws ZipException 异常
     */
    public static void zip(String destZip, String srcFile, String passwd) throws ZipException {
        if (StringUtils.isBlank(passwd)) {
            throw new RuntimeException("password is empty");
        }
        //创建目标文件
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setCompressionLevel(CompressionLevel.NORMAL);
        //set password
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);

        File parentF = new File(destZip).getParentFile();
        if (!parentF.exists())
            parentF.mkdir();

        ZipFile zipfile = new ZipFile(destZip, passwd.toCharArray());
//        zipfile.setFileNameCharset("UTF-8");//在GBK系统中需要设置

        File srcfile = new File(srcFile);

        if (srcfile.isDirectory()) {
            zipfile.addFolder(srcfile, parameters);
        } else {
            zipfile.addFile(srcfile, parameters);
        }
    }

    /**
     * 解压
     *
     * @param zipfile 压缩包文件
     * @param dest    目标文件
     * @param passwd  密码
     * @throws ZipException 抛出异常
     */
    public static void unZip(String zipfile, String dest, String passwd) throws ZipException {
        if (StringUtils.isBlank(passwd)) {
            throw new RuntimeException("password is empty");
        }

        ZipFile zipFile = new ZipFile(zipfile, passwd.toCharArray());
        zipFile.setCharset(Charset.forName("UTF-8"));//在GBK系统中需要设置
        if (!zipFile.isValidZipFile()) {
            throw new ZipException("压缩文件不合法，可能已经损坏！");
        }

        File file = new File(dest);
        if (!file.exists()) {
            file.mkdirs();
        }
        zipFile.extractAll(dest);
    }

    public static void unZip(String zipfile, String dest) throws ZipException {

        ZipFile zipFile = new ZipFile(zipfile);
//        zipFile.setFileNameCharset("UTF-8");//在GBK系统中需要设置
        if (!zipFile.isValidZipFile()) {
            throw new ZipException("压缩文件不合法，可能已经损坏！");
        }

        File file = new File(dest);
        if (file.exists()) {
            file.mkdirs();
        }
        zipFile.extractAll(dest);
    }

}
