package fly4b.back;

import fly4j.common.os.OsUtil;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * eg: /export/mecode/* --> /export/back/**.zip
 */
public class ZipConfig {
    /**
     * eg /export/mecode/
     */
    private String beZipSourceDir;
    /**
     * eg  /export/back/
     */
    private String zipToDirPath;
    private String password;
    private boolean delZip = true;

    public ZipConfig() {
    }

    public ZipConfig(String beZipSourceDir, String zipToDirPath, String password) {
        this.beZipSourceDir = beZipSourceDir;
        this.zipToDirPath = zipToDirPath;
        this.password = password;
    }


    /**
     * eg mecode
     */
    @JsonIgnore
    private String getBackDirName() {
        return FilenameUtils.getBaseName(this.getBeZipSourceDir());
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
    public String getTargetBackDir() {
        return FilenameUtils.concat(getZipToDirPath(), getBackDirName()) + File.separator;
    }

    @JsonIgnore
    public String getDestZipFullPath() {
        DateFormat df = new SimpleDateFormat("yy-MM-dd_HH");
        String fileName = OsUtil.getSimpleOsName() + df.format(new Date()) + this.getBackDirName() + ".zip";
        return FilenameUtils.concat(this.getZipToDirPath(), fileName);
    }

    /**
     * ***********     seter and geter      ************
     */

    public String getBeZipSourceDir() {
        return beZipSourceDir;
    }

    public void setBeZipSourceDir(String beZipSourceDir) {
        this.beZipSourceDir = beZipSourceDir;
    }

    public String getZipToDirPath() {
        return zipToDirPath;
    }

    public void setZipToDirPath(String zipToDirPath) {
        this.zipToDirPath = zipToDirPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDelZip(boolean delZip) {
        this.delZip = delZip;
    }

    public boolean isDelZip() {
        return delZip;
    }


}
