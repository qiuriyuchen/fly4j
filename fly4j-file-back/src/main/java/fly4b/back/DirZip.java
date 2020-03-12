package fly4b.back;

import fly4b.back.zip.Zip4jTool;
import fly4j.common.JsonUtils;
import fly4j.common.StringConst;
import fly4j.common.log.LogUtil;
import fly4j.common.os.OsUtil;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DirZip {
    public static final Log log = LogFactory.getLog(DirZip.class);
    protected FileFilter noNeedBackFileFilter;
    protected List<ZipConfig> zipConfigs = new ArrayList<>();
    private DirCompare dirCompare;
    private long afterCopySleppTime = 0;

    public String executeCheck() throws Exception {
        StringBuilder builder = new StringBuilder();
        for (ZipConfig zipConfig : getZipConfigList()) {
            builder.append(dirCompare.check(zipConfig.getBeZipSourceDir(), 1)).append(StringConst.N_N);
        }
        return builder.toString();
    }

    public String excuteBack() throws IOException, ZipException {
        StringBuilder builder = new StringBuilder();


//        // 删除过期zip文件
//        File[] zipFiles = FileUtil.listFilesWithEndStr(ArticleConfig.getDownDir(pin), ".zip");
//        if (null != zipFiles) {
//            for (File zFile : zipFiles) {
//                String fName = zFile.getName();
//                if (DateUtil.getBetweenDayForNow(getDayFromZipFileName(fName)) > maxBackZipFileDayNum) {
//                    boolean result = zFile.delete();
//                    System.out.println(">>>>>> 删除过期文件成功. <<<<<< to " + fName
//                            + result);
//                }
//            }
//        }


        //在要备份的文件夹生成Md5文件 gen md5File for back dirs
        getZipConfigList().forEach(zipConfig -> {
            try {
                Map<String, String> md5Map = dirCompare.getDirMd5Map(zipConfig.getBeZipSourceDir());
                String md5FilePath = getMd5FilePath(zipConfig);
                FileUtils.writeStringToFile(new File(md5FilePath), JsonUtils.writeValueAsString(md5Map), Charset.forName("utf-8"));
                dirCompare.deleteMoreMd5Files(zipConfig.getBeZipSourceDir());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //删除上次备份文件 delete last backFile
        for (ZipConfig zipConfig : getZipConfigList()) {
            File[] files = new File(zipConfig.getZipToDirPath()).listFiles();
            for (File file : files) {

                if (file.isDirectory()) {
                    //上次拷贝过来的文件
                    FileUtils.deleteDirectory(file);
                    System.out.println("deleteBefore" + file.getAbsolutePath());
                } else {
                    if ("zip".equals(FilenameUtils.getExtension(file.getAbsolutePath()))) {
                        if (zipConfig.isDelZip()) {
                            FileUtils.forceDelete(file);
                            System.out.println("deleteBefore" + file.getAbsolutePath());
                        }

                    } else {
                        FileUtils.forceDelete(file);
                        System.out.println("deleteBefore" + file.getAbsolutePath());
                    }
                }

            }

        }


        //删除上次拷贝过去的备份文件，并拷贝到临时目录
        for (ZipConfig zipConfig : getZipConfigList()) {
            FileUtils.forceMkdir(new File(zipConfig.getTargetBackDir()));
            FileUtils.copyDirectory(new File(zipConfig.getBeZipSourceDir()), new File(zipConfig.getTargetBackDir()));
            System.out.println("copy " + zipConfig.getBeZipSourceDir() + " ---> " + zipConfig.getTargetBackDir());
        }


        //删除不需要备份的文件 delete no need back files,eg:target .git etc.
        getZipConfigList().forEach(zipConfig -> {
            this.deleteNoNeedBackFloder(new File(zipConfig.getTargetBackDir()));
        });
        System.out.println("TargetFileDel end");
        //休息等临时文件消失
        if (afterCopySleppTime > 0) {
            try {
                Thread.sleep(afterCopySleppTime * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //执行备份 backFile
        for (ZipConfig zipConfig : getZipConfigList()) {
            DateFormat df = new SimpleDateFormat("yy-MM-dd_HH");
            try {
                Zip4jTool.zip(zipConfig.genDestZipFullPath(), zipConfig.getTargetBackDir(), zipConfig.getPassword());
            } catch (ZipException e) {
                e.printStackTrace();
                log.error("Zip4jTool.zip destZip:" + zipConfig.getLastDestZipFullPath() + " srcFile:" + zipConfig.getBeZipSourceDir(), e);
                builder.append(zipConfig.getLastDestZipFullPath()).append(" error ").append(e.getMessage()).append(StringConst.N_N);
            }
            builder.append(zipConfig.getLastDestZipFullPath()).append(" ziped").append(StringConst.N_N);
        }

        //

//        if (DomainEdgeService.isBackCodeEmail()) {
////            FlyMailUtil.forceSendMail("mecode", "mecode", getZipName("mecode.zip"), "panpan_002@126.com");
//        }

        StringConst.appendLine(builder, "back end");
        return builder.toString();
    }

    private String getMd5FilePath(ZipConfig zipConfig) {
        File dirFile = new File(zipConfig.getMd5DirName());
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        return zipConfig.getMd5DirName() + "/md5" + System.currentTimeMillis() + ".md5";
    }


    private void deleteNoNeedBackFloder(File dirFile) {
        //for debug
        boolean doDel = true;

        if (!dirFile.exists())
            return;
        File[] files = dirFile.listFiles();
        for (File file : files) {

            if (file.isDirectory()) {
                if (noNeedBackFileFilter.accept(file)) {
                    System.out.println(file.getAbsolutePath() + " delete");
                    if (doDel) {
                        try {
                            FileUtils.deleteDirectory(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                }

                //递归
                deleteNoNeedBackFloder(file);
            }

        }

    }


//    private void cleanFloder(File dirFile) throws Exception {
//
//
//        File[] files = dirFile.listFiles();
//        for (File file : files) {
//            if (noNeedCalMd5FileFilter.accept(file)) {
//                System.out.println(file.getAbsolutePath());
//                FileUtils.forceDelete(file);
//                continue;
//            }
//            if (file.isDirectory()) {
//                //查找大文件夹
//                //递归
//                cleanFloder(file);
//            }
//
//        }
//
//    }

    /**
     * **************************************************** seter and geter*************************************
     */
    public void setDirCompare(DirCompare dirCompare) {
        this.dirCompare = dirCompare;
    }

    public List<ZipConfig> getZipConfigList() {
        return zipConfigs;
    }

    public void reSetZipConfigs(List<ZipConfig> zipConfigs) {
        this.zipConfigs = zipConfigs;
    }

    public void setZipConfigs(List<ZipConfig> zipConfigs) {
        this.zipConfigs = zipConfigs;
    }

    public void setNoNeedBackFileFilter(FileFilter noNeedBackFileFilter) {
        this.noNeedBackFileFilter = noNeedBackFileFilter;
    }

    public FileFilter getNoNeedBackFileFilter() {
        return noNeedBackFileFilter;
    }

    public void setAfterCopySleppTime(long afterCopySleppTime) {
        this.afterCopySleppTime = afterCopySleppTime;
    }
}
