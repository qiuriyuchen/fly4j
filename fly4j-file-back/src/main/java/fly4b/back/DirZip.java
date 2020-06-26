package fly4b.back;

import fly4b.back.zip.Zip4jTool;
import fly4j.common.JsonUtils;
import fly4j.common.StringConst;
import fly4j.common.pesistence.file.FileStrStore;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;

@Setter
@Getter
@Slf4j
public class DirZip {
    protected FileFilter noNeedBackFileFilter;
    private DirCompare dirCompare;
    private long afterCopySleppTime = 0;

    public String excuteBack(ZipConfig zipConfig) {
        var builder = new StringBuilder();
        try {
            //生成MD5摘要文件
            var md5Map = dirCompare.getDirMd5Map(zipConfig.getBeZipSourceDir());
            var md5StoreJson = JsonUtils.writeValueAsString(md5Map);
            var md5StorePath = getMd5FilePath(zipConfig);
            FileStrStore.setValue(md5StorePath, md5StoreJson);
            dirCompare.deleteMoreMd5Files(zipConfig.getBeZipSourceDir(), 3);

            //拷贝文件
            var targetBackDirFile = zipConfig.getTargetBackDirPath().toFile();
            FileUtils.deleteDirectory(targetBackDirFile);
            System.out.println("deleteBefore" + targetBackDirFile);
            if (targetBackDirFile.exists()) {
                throw new RuntimeException(targetBackDirFile + "is not delete success");
            }
            FileUtils.forceMkdir(targetBackDirFile);
            FileUtils.copyDirectory(zipConfig.getBeZipSourceDir().toFile(), targetBackDirFile);
            System.out.println("copy " + zipConfig.getBeZipSourceDir() + " ---> " + zipConfig.getTargetBackDirPath());
            //删除不需要备份的文件 delete no need back files,eg:target .git etc.
            this.deleteNoNeedBackFloder(targetBackDirFile);
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
            Zip4jTool.zip(zipConfig.genDestZipFullPath().toFile(), zipConfig.getTargetBackDirPath().toFile(), zipConfig.getPassword());
            builder.append("Zip4jTool.zip  srcFile:" + zipConfig.getBeZipSourceDir()).append(" ziped").append(StringUtils.LF);
            StringConst.appendLine(builder, "back end");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Zip4jTool.zip  srcFile:" + zipConfig.getBeZipSourceDir(), e);
            builder.append("Zip4jTool.zip  srcFile:" + zipConfig.getBeZipSourceDir()).append(" error ").append(e.getMessage()).append(StringUtils.LF);
        }
        return builder.toString();
    }

    private Path getMd5FilePath(ZipConfig zipConfig) {
        File dirFile = zipConfig.getMd5DirName().toFile();
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        return Path.of(zipConfig.getMd5DirName() + "/md5" + System.currentTimeMillis() + ".md5");
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
                    continue;
                }
                //递归
                deleteNoNeedBackFloder(file);
            }

        }

    }


}
