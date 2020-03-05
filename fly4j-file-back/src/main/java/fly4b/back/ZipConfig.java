package fly4b.back;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

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
    private String destZipFileName;


    public ZipConfig(String beZipSourceDir, String zipToDirPath, String password) {
        this.beZipSourceDir = beZipSourceDir;
        this.zipToDirPath = zipToDirPath;
        this.password = password;
    }

    /**
     * eg mecode
     */
    public String getBackDirName() {
        return FilenameUtils.getBaseName(FilenameUtils.getPathNoEndSeparator(this.getBeZipSourceDir()));
    }
    public String getMd5DirName() {
        return FilenameUtils.concat(getBeZipSourceDir(), ".md5") + File.separator;
    }
    /**
     * eg  /export/back/mecode/
     */
    public String getTargetBackDir() {
        return FilenameUtils.concat(getZipToDirPath(), getBackDirName()) + File.separator;
    }

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

    public void setDestZipFileName(String destZipFileName) {
        this.destZipFileName = destZipFileName;
    }

    public String getDestZipFileName() {
        return destZipFileName;
    }


}
