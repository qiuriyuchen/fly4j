package fly4j.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Date;

/**
 * 用于代码备份的时候，清除目录下的target/classes
 *使用的时候先拷贝一个文件夹，再备份
 * Created by guanpanpan on 2016/5/4.
 */
public class ProjectClean {
    public static void main(String[] args) throws Exception {
        System.out.println("start:"+ DateUtil.getCurrDateStr());
        String backPath = "D:\\MOST-CURRENT\\CODES-back-"+DateUtil.getDateStrForName(new Date());
        String srcPath = "D:\\MOST-CURRENT\\CODES";
        FileUtils.deleteDirectory(new File(backPath));
        FileUtils.copyDirectory(new File(srcPath), new File(backPath));

        cleanFile(new File(backPath), "target", true);
        cleanFile(new File(backPath), ".git", true);
        cleanFile(new File(backPath), ".svn", true);
        cleanFile(new File(backPath), "IP.Dat", true);
//        FileToZip.zip("D:\\MOST-CURRENT\\code"+DateUtil.getDateStrForName(new Date())+".zip",backPath);


        System.out.println("end"+ DateUtil.getCurrDateStr());
    }

    public static void cleanFile(File dirFile, String name, boolean del) throws Exception {
        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (name.equals(file.getName())) {
                    System.out.println(file.getAbsolutePath() + " " + del);
                    if (del)
                        FileUtils.deleteDirectory(file);
                } else {
                    cleanFile(file, name, del);
                }

            }else {
                if (name.equals(file.getName())) {
                    System.out.println(file.getAbsolutePath() + " " + del);
                    if (del)
                        FileUtils.forceDeleteOnExit(file);
                }
            }

        }
    }

}
