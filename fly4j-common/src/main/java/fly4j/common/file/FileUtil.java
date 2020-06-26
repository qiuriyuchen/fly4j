package fly4j.common.file;

import fly4j.common.StringConst;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * 统一封装文件访问
 *
 * @author guanpanpan
 */
public class FileUtil {
    public static final String utf_8 = "utf-8";
    public static final String gbk = "gbk";


    public static boolean delFile4Safe(String fileStr) {
        File f = new File(fileStr);
        if ((f.isDirectory() && f.listFiles().length == 0) || !f.isDirectory()) {
            f.delete();
            return true;
        } else {

            return false;
        }
    }

    public static boolean delFile(String fileStr) {
        File file = new File(fileStr);

        if (file.isFile() && file.exists())
            return file.delete();
        else
            return false;
    }

    public static void delDir(String fileStr) {
        File file = new File(fileStr);

        if (file.isDirectory() && file.exists()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void safeDelete(File deleteFile) throws IOException {
        FileUtils.deleteDirectory(deleteFile);
        if (deleteFile.exists()) {
            throw new RuntimeException("safeDelete error");
        }
    }

    public static void createFile(String fileStr) {
        File file = new File(fileStr);
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("fileStr:" + fileStr + e.getMessage());
                e.printStackTrace();
            }

    }

    public static boolean isImg(String fileStr) {
        try {
            return isImg(new File(fileStr));
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isImg(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            return image != null;
        } catch (IOException ex) {
            return false;
        }
    }

    public static Collection<File> listDirFiles(String dirStr) {
        File file = new File(dirStr);
        if (!file.isDirectory()) {
            return new ArrayList<File>();
        }

        Collection<File> files = FileUtils.listFiles(file, null, false);
        return files;
    }

    /**
     * 根据文件名字后缀匹配文件列表
     */
    public static File[] listFilesWithEndStr(final String sourceFilePath,
                                             final String endStr) {
        File file = new File(sourceFilePath);
        return file.listFiles((dir, name) ->
                name.endsWith(endStr));
    }

    public static File[] listFilesWithEndStr(final Path sourceFilePath,
                                             final String endStr) {
        File file = sourceFilePath.toFile();
        return file.listFiles((dir, name) ->
                name.endsWith(endStr));
    }

    public static void forceMkdir(String dirStr) {
        try {
            FileUtils.forceMkdir(new File(dirStr));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean exists(String dirStr) {
        return new File(dirStr).exists();
    }

    public static String getFileStr(String filePath, String charSet, String br) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = FileUtil.getStrsListFromFile(filePath, charSet);
        for (String str : list) {
            stringBuilder.append(str).append(br);
        }
        return stringBuilder.toString();

    }

    /**
     * 读取文件内容，去除空行
     *
     * @param filePath
     * @return
     */
    public static List<String> getStrsListFromFile(String filePath, String charSet) {
        List<String> list = new ArrayList<String>();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), charSet);
            BufferedReader br = new BufferedReader(isr);

            String strLine = null;
            while ((strLine = br.readLine()) != null) {
                list.add(strLine.trim());
            }
            br.close();
            isr.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
     * 读取文件内容
     *
     * @param filePath
     * @return
     */
    public static Set<String> getStrsSetFromFile(String filePath) {
        Set<String> set = new HashSet<String>();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "utf-8");
            BufferedReader br = new BufferedReader(isr);

            String strLine = null;
            while ((strLine = br.readLine()) != null) {
                set.add(strLine);
            }
            br.close();
            isr.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return set;
    }

    public static void WriteStrToFile(String fileStr, List<String> list) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String str : list) {
            stringBuilder.append(str).append("\r\n");
        }
        WriteStrToFile(fileStr, stringBuilder.toString());
    }

    public static void WriteStrToFile(String fileStr, String str) {
        File file = new File(fileStr);
        if (str == null || str.trim().equals("")) {
            return;
        }

        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(str);
            bw.close();
            osw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void WriteStrToFileBlank(String fileStr) {
        File file = new File(fileStr);


        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("");
            bw.close();
            osw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getMd5ByFile(FileInputStream fis) throws Exception {
        String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        IOUtils.closeQuietly(fis);
        return md5;
    }

    public static String getRelativeStandardPath(String absolutePath, Path baseDir) {
        String key = FilenameUtils.normalize(absolutePath);
        key = FilenameUtils.separatorsToUnix(key);
        String baseDirTemp = FilenameUtils.normalize(baseDir.toString());
        baseDirTemp = FilenameUtils.separatorsToUnix(baseDirTemp);
        key = key.replaceAll(baseDirTemp, "");
        return key;
    }

    public static long convertK2M(long kLength) {

        return kLength / 1024 / 1024 / 1024;
    }

    public static long convertM2K(long kLength) {
        return kLength * 1024 * 1024 * 1024;
    }


    public static void writeFileToResponse(OutputStream servletOutputStream, File file) {

        /***
         * 把文件流写入客户端
         */
        try (InputStream inputStream = new FileInputStream(file)) {
            // 创建输入流，读取文件到内存
            // 创建输出流，输出内存到客户端
//            int readLength;
//            byte[] buf = new byte[4096];
//            while (((readLength = inputStream.read(buf)) != -1)) {
//                servletOutputStream.write(buf, 0, readLength);
//            }
            inputStream.transferTo(servletOutputStream);
//            IOUtils.copy(inputStream, servletOutputStream);
            servletOutputStream.flush();

        } catch (IOException e) {
            throw new RuntimeException("文件输出到客户端异常", e);
        } finally {
            if (null != servletOutputStream) {
                try {
                    servletOutputStream.close();
                } catch (IOException e1) {
//                    LogUtil.error(LogUtil.FILE_EXCEPTION, "servletOutputStream.close exception", e1);
                }
            }
        }
    }

    public static boolean rename(File file, String newNameNoWithEx) {
        if (!file.isDirectory()) {
            String prefix = FilenameUtils.getExtension(file.getName());
            String newAbsolutePath = FilenameUtils.getFullPath(file.getAbsolutePath()) + newNameNoWithEx + "." + prefix;
            return (file.canWrite() && file.renameTo(new File(newAbsolutePath)));
        }
        return false;
    }

    /**
     * ***************************下边测试部分演示容易出错部分用法***************************
     */
    public static void main(String[] args) throws Exception {

//        String path = "D:\\back\\a.zip";
//
//        String v = getMd5ByFile(new FileInputStream(path));
//        System.out.println("MD5:" + v.toUpperCase());
//        path = "D:\\back\\b.zip";
//        v = getMd5ByFile(new FileInputStream(path));
//        System.out.println("MD5:" + v.toUpperCase());

        //System.out.println("MD5:"+DigestUtils.md5Hex("WANGQIUYUN"));
//        System.out.println(convertK2M(18000000000L));
//        System.out.println(convertK2M(18000000000L));
//        System.out.println(convertK2M(1073741824));
//        System.out.println(convertM2K(19));
//        FileUtil.rename(new File("D:\\ssssss\\b.jpg"),"c");
//        testFilePath();

        testFileName();


    }

    private static void testFilePath() {
        //测试路径
        StringBuilder msg = new StringBuilder();
        msg.append("System.getProperty(\"user.dir\")").append(":").append(System.getProperty("user.dir")).append(StringUtils.LF);
        msg.append(" System.getProperty(\"java.class.path\")").append(":").append(System.getProperty("java.class.path")).append(StringUtils.LF);
        //不可以在jar包使用
        msg.append("this.getClass().getResource(\"/\").getPath()").append(":").append(FileUtil.class.getResource("/").getPath()).append(StringUtils.LF);
        System.out.println(msg);
    }

    private static void testFileName() {
        //fly\pin  warnig:会丢失 blogs,因为不知道结尾是文件，还是文件夹
        System.out.println(FilenameUtils.getPathNoEndSeparator("D:\\fly\\pin\\blogs"));
        //D:\fly\pin
        System.out.println(FilenameUtils.getFullPathNoEndSeparator("D:\\fly\\pin\\blogs"));
        //blogs
        System.out.println(FilenameUtils.getBaseName("D:\\fly\\pin\\blogs"));
        //空
        System.out.println(FilenameUtils.getBaseName("D:\\fly\\pin\\blogs\\"));
        //pin warnig:会丢失 blogs
        System.out.println(FilenameUtils.getBaseName(FilenameUtils.getPathNoEndSeparator("D:\\fly\\pin\\blogs")));
    }
}
