package fly4j.http.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * 文件操作 Created by IntelliJ IDEA. UserInfo: guanpanpan Date: 14-2-4 Time: 上午12:43 To
 * change this template use File | Settings | File Templates.
 */
public class FileUpload {


    public static String uploadFile(HttpServletRequest request, Path currPath, long sizeMaxM) throws Exception {
        //如果不是文件上传，则返回
        var isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            return "not file upload";
        }


        // 图片地址 ，相对于webroot的
        var relativeWebRootPath = "";

        // 允许上传的文件格式的列表
        final var allpict = new String[]{".jpg", ".zip", ".jpeg", ".gif", ".jpeg", ".png", ".bmp", ".JPG", ".JPGE", ".GIF", ".JPEG", ".PNG",
                ".BMP"};

        var factory = new DiskFileItemFactory();
        // 设置临时目录：
        var tempPath = request.getSession().getServletContext().getRealPath("/temp");
        factory.setRepository(new File(tempPath));
        // 设置缓冲区大小，这里是40M
        factory.setSizeThreshold(41943040);

        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件4MB
        upload.setSizeMax(sizeMaxM * 1024 * 1024);
        // 得到所有的文件：
        List<FileItem> fileList = upload.parseRequest(request);


        for (var fileItem : fileList) {
            if (fileItem.isFormField()) {//如果是表单域，就不是文件上传
                continue;
            }

            String fileName = fileItem.getName();
            long size = fileItem.getSize();
            if ((fileName == null || fileName.equals("")) && size == 0) {
                continue;
            }

            var fileTypeName = getFileType(allpict, fileName);
            // 图片路径
            var picPath = "iknowData" + fileTypeName;

            // 返回文件名，以时间为名，路径：e:/temp
            var savePath = Path.of(currPath.toString(), fileName);
            var file = savePath.toFile();
            //返回相对路径
            relativeWebRootPath = "pic/" + picPath;
//            if (!file.getParentFile().isDirectory()) {
//                file.getParentFile().mkdirs();// 创建目录
//            }


            fileItem.write(file);


        }
        return relativeWebRootPath;
    }


    private static String getFileType(final String[] allpict, String fileName) {
        String fileTypeName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (fileName.endsWith(".tar.gz")) {
            fileTypeName = ".tar.gz";
        }
        boolean picge = false;
        for (String sin : allpict) {
            if (fileTypeName.trim().toLowerCase().equals(sin)) {
                picge = true;
            }
        }
        return fileTypeName;
    }
}
