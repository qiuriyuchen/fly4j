package fly4b.back;

import fly4j.common.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.File;
import java.nio.file.Path;
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
    private File beZipSourceDir;
    private File destZipFile;
    private String password;
    private boolean delZip = true;

    public ZipConfig(File beZipSourceDir, File destZipFile, String password) {
        this.beZipSourceDir = beZipSourceDir;
        this.destZipFile = destZipFile;
        this.password = password;
    }

    /**
     * 版本文件存放路径
     */
    public Path getSourceMd5DirName() {
        return Path.of(getBeZipSourceDir().toString(), ".flyMd5");
    }

    public File getSourceMd5File() {
        File dirFile = this.getSourceMd5DirName().toFile();
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        return Path.of(this.getSourceMd5DirName().toString(), DateUtil.getHourStr4Name(new Date()) + ".md5").toFile();
    }

}
