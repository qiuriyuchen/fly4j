package fly4j.crypto;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片工具
 * Created by guanpanpan on 2016/10/31.
 */
public class PicUtil {
    public static void main(String[] args) throws Exception {
//        jiami("D:\\data\\pic\\a.jpg","D:\\data\\pic\\aa.jpg");
//        jiami("D:\\data\\pic\\aa.jpg", "D:\\data\\pic\\aaa.jpg");
        System.out.println(FileUtils.checksumCRC32(new File("D:\\data\\pic\\a.jpg")));
        System.out.println(FileUtils.checksumCRC32(new File("D:\\data\\pic\\aa.jpg")));
    }

    private static void jiami(String src, String desc) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        List<Integer> list = new ArrayList<Integer>();//定义集合<字节>用来存储数据
        int len;              //定义变量，用来存储数据
        while ((len = bis.read()) != -1) {//循环读取，直到读取到末尾为止
            list.add(len ^ 123);//从文件中逐个字节读取数据，异或密码，存入集合
        }

        bis.close();         //关闭输入流
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desc));
        for (Integer i : list)//遍历集合，将所有数据写回文件
            bos.write(i);
        bos.close();
    }
}
