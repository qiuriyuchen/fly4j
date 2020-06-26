package fly4b.back;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fly4j.common.os.OsUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class ZipConfig {
    /**
     * eg /export/mecode/
     */
    private String beZipSourceDir;
    /**
     * eg  /export/back/
     */
    private String zipToDir;
    private String password;
    private boolean delZip = true;

    public ZipConfig(String beZipSourceDir, String zipToDir, String password) {
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
        String dirName = FilenameUtils.getBaseName(this.getBeZipSourceDir());
        return StringUtils.isNotBlank(dirName) ? dirName :
                new File(this.getBeZipSourceDir()).getName();
    }

    /**
     * 版本文件存放路径
     */
    @JsonIgnore
    public String getMd5DirName() {
        return FilenameUtils.concat(getBeZipSourceDir(), ".flyMd5") + File.separator;
    }


    /**
     * eg  /export/back/mecode/
     */
    @JsonIgnore
    public Path getTargetBackDirPath() {
        return Path.of(getZipToDir(), getBackDirName());
    }

    @JsonIgnore
    public String getTargetMiDir() {
        return FilenameUtils.concat(getZipToDir(), getBackDirName() + "mi") + File.separator;
    }

    @JsonIgnore
    public Path genDestZipFullPath() {
        DateFormat df = new SimpleDateFormat("yy-MM-dd_HH");
        String fileName = OsUtil.getSimpleOsName() + df.format(new Date()) + this.getBackDirName().replace('.', '_') + ".zip";
        return Path.of(this.getZipToDir(), fileName);
    }

    @JsonIgnore
    public Path genDestZipFullPath(String aliasDirName) {
        DateFormat df = new SimpleDateFormat("yy-MM-dd_HH");
        String fileName = OsUtil.getSimpleOsName() + df.format(new Date()) + aliasDirName.replace('.', '_') + ".zip";
        return Path.of(this.getZipToDir(), fileName);
    }

    @JsonIgnore
    public int getPasswordInt() {
        return Integer.parseInt(password);
    }


}
