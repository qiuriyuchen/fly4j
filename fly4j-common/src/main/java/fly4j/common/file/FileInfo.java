package fly4j.common.file;


import fly4j.common.DateUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来前台展示文件信息
 *
 * @author guanpanpan
 */
public class FileInfo {
    //相对于下载网址的路径
    public final String fileRelativePath;
    public final boolean pic;
    //文件名称
    public final String fileName;
    //文件绝对路径
    public final String absolutePath;
    public final boolean isDirectory;

    //扩展属性
    public Map<String, Object> extMap = new HashMap<>();

    public FileInfo(File file) {
        //绝对路径
        this.absolutePath = file.getAbsolutePath();
        this.pic = FileUtil.isImg(file.getPath());
        //相对路径
        String absolutePathLinux = file.getAbsolutePath().replaceAll(
                "\\\\", "/");
        if (absolutePathLinux.split("/pic/").length > 1) {
            this.fileRelativePath = absolutePathLinux.split("/pic/")[1];
        } else {
            this.fileRelativePath = absolutePathLinux;
        }

        this.fileName = file.getName();
        isDirectory = file.isDirectory();
        if (!isDirectory) {
            extMap.put("size", file.length());
            extMap.put("displaySize", FileUtils.byteCountToDisplaySize(file.length()));

        }
        extMap.put("lmDate", new Date(file.lastModified()));

        String type = "File"; // This String will tell the extension of the file
        if (file.isDirectory()) type = "DIR"; // It's a DIR
        else {
            String tempName = file.getName().replace(' ', '_');
            if (tempName.lastIndexOf('.') != -1) type = tempName.substring(
                    tempName.lastIndexOf('.')).toLowerCase();
        }
        extMap.put("type", type);


    }

    @Override
    public String toString() {
        return fileRelativePath;
    }

    public String getFileRelativePathPic() {
        return "/pic/" + fileRelativePath;
    }

    public boolean isPacked() {
        return BroserUtil.isPacked(absolutePath, true);
    }


}
