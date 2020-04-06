package fly4b.back.zip;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.lang.StringUtils;

import java.io.File;

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
//        zipFile.setFileNameCharset("UTF-8");//在GBK系统中需要设置
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
//    public static void zip(String destZip, String srcFile, String passwd) throws ZipException {
//        //创建目标文件
//        ZipParameters parameters = new ZipParameters();
//        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
//        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
//        //set password
//        if (passwd != null) {
//            parameters.setEncryptFiles(true);
//            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
//            parameters.setPassword(passwd.toCharArray());
//        }
//
//        File parentF = new File(destZip).getParentFile();
//        if (!parentF.exists())
//            parentF.mkdir();
//
//        ZipFile zipfile = new ZipFile(destZip);
//        zipfile.setFileNameCharset("UTF-8");//在GBK系统中需要设置
//
//        File srcfile = new File(srcFile);
//
//        if (srcfile.isDirectory()) {
//            zipfile.addFolder(srcfile, parameters);
//        } else {
//            zipfile.addFile(srcfile, parameters);
//        }
//    }
//    public static void splitZip(String destZip, String srcFile, String passwd,int splitLengthM) throws ZipException {
//
//        //创建目标文件
//        ZipParameters parameters = new ZipParameters();
//        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
//        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
//
//        //set password
//        if (passwd != null) {
//            parameters.setEncryptFiles(true);
//            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
//            parameters.setPassword(passwd.toCharArray());
//        }
//
//        File parentF = new File(destZip).getParentFile();
//        if (!parentF.exists())
//            parentF.mkdir();
//
//        ZipFile zipfile = new ZipFile(destZip);
//        zipfile.setFileNameCharset("UTF-8");//在GBK系统中需要设置
//
//        File srcfile = new File(srcFile);
//
//        zipfile.createZipFileFromFolder(srcfile, parameters, true, 1024 * 1024 * splitLengthM);
//
//    }

//    /**
//     * 解压
//     *
//     * @param zipfile 压缩包文件
//     * @param dest    目标文件
//     * @param passwd  密码
//     * @throws ZipException 抛出异常
//     */
//    public static void unZip(String zipfile, String dest, String passwd) throws ZipException {
//        ZipFile zipFile = new ZipFile(zipfile);
//        zipFile.setFileNameCharset("UTF-8");//在GBK系统中需要设置
//        if (!zipFile.isValidZipFile()) {
//            throw new ZipException("压缩文件不合法，可能已经损坏！");
//        }
//
//        File file = new File(dest);
//        if (file.isDirectory() && !file.exists()) {
//            file.mkdirs();
//        }
//
//        if (zipFile.isEncrypted()) {
//            zipFile.setPassword(passwd.toCharArray());
//        }
//        zipFile.extractAll(dest);
//    }

    public static String buildDestFileName(File srcfile, String dest) {
        if (dest == null) {
            if (srcfile.isDirectory()) {
                dest = srcfile.getParent() + File.separator + srcfile.getName() + ".zip";
            } else {
                String filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                dest = srcfile.getParent() + File.separator + filename + ".zip";
            }
        } else {
            createDestPath(dest);//路径的创建
            if (dest.endsWith(File.separator)) {
                String filename = "";
                if (srcfile.isDirectory()) {
                    filename = srcfile.getName();
                } else {
                    filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                }
                dest += filename + ".zip";
            }
        }
        return dest;
    }

    private static void createDestPath(String dest) {
        File destDir = null;
        if (dest.endsWith(File.separator)) {
            destDir = new File(dest);//给出的是路径时
        } else {
            destDir = new File(dest.substring(0, dest.lastIndexOf(File.separator)));
        }

        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

}
