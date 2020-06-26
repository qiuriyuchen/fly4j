package fly4b.back;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * dir:以/结尾的文件夹
 * path：文件路径
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ZipConfig {
    /**
     * eg /export/mecode/
     */
    private Path beZipSourceDir;
    /**
     * eg  /export/back/
     */
    private Path zipToDir;
    private String password;
    private boolean delZip = true;

    public ZipConfig(Path beZipSourceDir, Path zipToDir, String password) {
        this.beZipSourceDir = beZipSourceDir;
        this.zipToDir = zipToDir;
        this.password = password;
    }


    /**
     * eg mecode
     */
    @JsonIgnore
    private String getBackDirName() {
//            不可识别.开头文件
        String dirName = FilenameUtils.getBaseName(this.getBeZipSourceDir().toString());
        return StringUtils.isNotBlank(dirName) ? dirName :
                this.getBeZipSourceDir().toFile().getName();
    }

    /**
     * 版本文件存放路径
     */
    @JsonIgnore
    public Path getMd5DirName() {
        return Path.of(getBeZipSourceDir().toString(), ".flyMd5");
    }


    /**
     * eg  /export/back/mecode/
     */
    @JsonIgnore
    public Path getTargetBackDirPath() {
        return Path.of(getZipToDir().toString(), getBackDirName());
    }

    @JsonIgnore
    public Path getTargetMiDir() {
        return Path.of(getZipToDir().toString(), getBackDirName() + "mi");
    }

    @JsonIgnore
    public Path genDestZipFullPath() {
        DateFormat df = new SimpleDateFormat("yy-MM-dd_HH");
        String fileName = OsUtil.getSimpleOsName() + df.format(new Date()) + this.getBackDirName().replace('.', '_') + ".zip";
        return Path.of(this.getZipToDir().toString(), fileName);
    }

    @JsonIgnore
    public Path genDestZipFullPath(String aliasDirName) {
        DateFormat df = new SimpleDateFormat("yy-MM-dd_HH");
        String fileName = OsUtil.getSimpleOsName() + df.format(new Date()) + aliasDirName.replace('.', '_') + ".zip";
        return Path.of(this.getZipToDir().toString(), fileName);
    }

    @JsonIgnore
    public int getPasswordInt() {
        return Integer.parseInt(password);
    }


}
