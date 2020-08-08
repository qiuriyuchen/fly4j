package fly4j.back;

import fly4j.back.zip.Zip4jTool;
import fly4j.common.FlyResult;
import fly4j.common.JsonUtils;
import fly4j.common.pesistence.file.FileStrStore;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;

@Setter
@Getter
@Slf4j
/**
 * alter by qryc in 2020/07/04
 * 不再先拷贝，删除不需要备份文件，再压缩，直接压缩，通过提前规划好需要备份和不需要备份文件
 */
public class DirZip {
    private boolean afterTest = true;
    private DirCompare dirCompare;

    public FlyResult excuteBack(ZipConfig zipConfig) {
        FlyResult backResult = new FlyResult().success();
        try {
            //生成MD5摘要文件
            dirCompare.deleteMoreMd5Files(zipConfig.getBeZipSourceDir(), 3);
            var md5Map = dirCompare.getDirMd5Map(zipConfig.getBeZipSourceDir());
            var md5StoreJson = JsonUtils.writeValueAsString(md5Map);
            var md5StorePath = zipConfig.getSourceMd5File().toPath();
            FileStrStore.setValue(md5StorePath, md5StoreJson);

            //执行备份 backFile
            Zip4jTool.zipDir(zipConfig.getDestZipFile(), zipConfig.getBeZipSourceDir(), zipConfig.getPassword());
            backResult.append("executeBack success srcFile(" + zipConfig.getBeZipSourceDir()).append(") zipe to (")
                    .append(zipConfig.getDestZipFile().getAbsolutePath()).append(")")
                    .append(StringUtils.LF);

            //执行Test
            if (afterTest) {
                var checkResult = checkZip(zipConfig.getDestZipFile(), zipConfig.getBeZipSourceDir().getName(), zipConfig.getPassword());
                backResult.merge(checkResult);

            }
        } catch (Exception e) {
            log.error("Zip4jTool.zip  srcFile:" + zipConfig.getBeZipSourceDir(), e);
            backResult.append("Zip4jTool.zip  srcFile:" + zipConfig.getBeZipSourceDir()).append(" error ").append(e.getMessage()).append(StringUtils.LF);
        }
        return backResult;
    }

    private FlyResult checkZip(File zipFile, String inUnzipDirName, String pwd) throws Exception {
        var backResult = new FlyResult().success();
        var builder = new StringBuilder();
        var unzipDirName = "unzipT4"
                + zipFile.getName().replaceAll("\\.", "_");
        var unzipPath = Path.of(zipFile.getParent(), unzipDirName);
        Zip4jTool.unZip(zipFile, unzipPath.toFile(), pwd);
        builder.append("executeUnzip  (")
                .append(zipFile.getAbsolutePath())
                .append(")  to (")
                .append(unzipPath.toString())
                .append(")")
                .append(StringUtils.LF);
        var checkPath = Path.of(unzipPath.toString(), inUnzipDirName);
        FlyResult result = dirCompare.checkWithHistory(checkPath.toFile());
        builder.append("executeCheckVersion:" + checkPath.toFile().getAbsolutePath()).append(StringUtils.LF);
        if (result.isSuccess()) {
            builder.append("*******check ok").append(StringUtils.LF);
        } else {
            builder.append("******check fail!!!!!!!!!!!!").append(StringUtils.LF);
            backResult.fail();
        }
        builder.append(result.getMsg()).append(StringUtils.LF);

        return backResult.append(builder.toString());
    }


}
