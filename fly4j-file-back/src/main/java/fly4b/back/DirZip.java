package fly4b.back;

import fly4b.back.zip.Zip4jTool;
import fly4j.common.FlyResult;
import fly4j.common.JsonUtils;
import fly4j.common.StringConst;
import fly4j.common.pesistence.file.FileStrStore;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

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
        var builder = new StringBuilder();
        try {
            //生成MD5摘要文件
            dirCompare.deleteMoreMd5Files(zipConfig.getBeZipSourceDir(), 3);
            var md5Map = dirCompare.getDirMd5Map(zipConfig.getBeZipSourceDir());
            var md5StoreJson = JsonUtils.writeValueAsString(md5Map);
            var md5StorePath = zipConfig.getSourceMd5File().toPath();
            FileStrStore.setValue(md5StorePath, md5StoreJson);

            //执行备份 backFile
            Zip4jTool.zipDir(zipConfig.getDestZipFile(), zipConfig.getBeZipSourceDir(), zipConfig.getPassword());
            builder.append("Zip4jTool.zip  srcFile:" + zipConfig.getBeZipSourceDir()).append(" ziped").append(StringUtils.LF);

            //执行Test
            if (afterTest) {
                var unzipName = "unzipT4" + zipConfig.getDestZipFile().getName().replaceAll("\\.", "_");
                var testPathStr = Path.of(zipConfig.getDestZipFile().getParent(), unzipName);
                FileUtils.forceMkdir(testPathStr.toFile());
                Zip4jTool.unZip(zipConfig.getDestZipFile().toString(), testPathStr.toString(), "123");
                var checkPath = Path.of(testPathStr.toString(), zipConfig.getBeZipSourceDir().getName());
                log.error("checkPath:" + checkPath.toFile().getAbsolutePath());
                FlyResult result = dirCompare.checkWithHistory(checkPath.toFile());
                if (result.isSuccess()) {
                    builder.append("check ok").append(StringUtils.LF);
                    log.info(result.getMsg());
                } else {
                    builder.append("check fail!!!!!!!!!!!!").append(StringUtils.LF);
                    log.error(result.getMsg());
                    backResult.fail();
                }

            }
            StringConst.appendLine(builder, "back end");
        } catch (Exception e) {
            log.error("Zip4jTool.zip  srcFile:" + zipConfig.getBeZipSourceDir(), e);
            builder.append("Zip4jTool.zip  srcFile:" + zipConfig.getBeZipSourceDir()).append(" error ").append(e.getMessage()).append(StringUtils.LF);
        }
        return backResult.setMsg(builder.toString());
    }


}
