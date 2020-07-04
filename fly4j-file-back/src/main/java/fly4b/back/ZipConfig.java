package fly4b.back;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fly4j.common.DateUtil;
import fly4j.common.os.OsUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * eg: /export/mecode/* --> /export/back/**.zip
 * dir:文件夹
 * path：文件路径
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ZipConfig {
    private Path beZipSourceDir;
    private Path destZipFilePath;
    private String password;
    private boolean delZip = true;

    public ZipConfig(Path beZipSourceDir, Path destZipFilePath, String password) {
        this.beZipSourceDir = beZipSourceDir;
        this.destZipFilePath = destZipFilePath;
        this.password = password;
    }

    /**
     * 版本文件存放路径
     */
    public Path getMd5DirName() {
        return Path.of(getBeZipSourceDir().toString(), ".flyMd5");
    }

    public Path getMd5FilePath() {
        File dirFile = this.getMd5DirName().toFile();
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        return Path.of(this.getMd5DirName().toString(), DateUtil.getHourStr4Name(new Date()) + ".md5");
    }

}
