package fly4j.common.test;

import fly4j.common.file.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.File;

/**
 * @author guanpanpan
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestFileUtil {

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void testFile() throws Exception {
//        System.out.println(TestData.tTargetDir);
//        FileUtil.delDir(FilenameUtils.concat(TestData.tTargetDir, "TestFileUtil"));
//        Assert.assertTrue(!new File(FilenameUtils.concat(TestData.tTargetDir, "TestFileUtil")).exists());
//        FileUtil.forceMkdir(FilenameUtils.concat(TestData.tTargetDir, "TestFileUtil/a/aa/aaa"));
//        Assert.assertTrue(new File(FilenameUtils.concat(TestData.tTargetDir, "TestFileUtil/a/aa/aaa")).exists());
    }
    /**
     * ***************************下边测试部分演示容易出错部分用法***************************
     */
    public static void main(String[] args) throws Exception {

//        String path = "D:\\back\\a.zip";
//
//        String v = getMd5ByFile(new FileInputStream(path));
//        System.out.println("MD5:" + v.toUpperCase());
//        path = "D:\\back\\b.zip";
//        v = getMd5ByFile(new FileInputStream(path));
//        System.out.println("MD5:" + v.toUpperCase());

        //System.out.println("MD5:"+DigestUtils.md5Hex("WANGQIUYUN"));
//        System.out.println(convertK2M(18000000000L));
//        System.out.println(convertK2M(18000000000L));
//        System.out.println(convertK2M(1073741824));
//        System.out.println(convertM2K(19));
//        FileUtil.rename(new File("D:\\ssssss\\b.jpg"),"c");
//        testFilePath();

        testFileName();


    }

    private static void testFilePath() {
        //测试路径
        StringBuilder msg = new StringBuilder();
        msg.append("System.getProperty(\"user.dir\")").append(":").append(System.getProperty("user.dir")).append(StringUtils.LF);
        msg.append(" System.getProperty(\"java.class.path\")").append(":").append(System.getProperty("java.class.path")).append(StringUtils.LF);
        //不可以在jar包使用
        msg.append("this.getClass().getResource(\"/\").getPath()").append(":").append(FileUtil.class.getResource("/").getPath()).append(StringUtils.LF);
        System.out.println(msg);
    }

    private static void testFileName() {
        //fly\pin  warnig:会丢失 blogs,因为不知道结尾是文件，还是文件夹
        System.out.println(FilenameUtils.getPathNoEndSeparator("D:\\fly\\pin\\blogs"));
        //D:\fly\pin
        System.out.println(FilenameUtils.getFullPathNoEndSeparator("D:\\fly\\pin\\blogs"));
        //blogs
        System.out.println(FilenameUtils.getBaseName("D:\\fly\\pin\\blogs"));
        //空
        System.out.println(FilenameUtils.getBaseName("D:\\fly\\pin\\blogs\\"));
        //pin warnig:会丢失 blogs
        System.out.println(FilenameUtils.getBaseName(FilenameUtils.getPathNoEndSeparator("D:\\fly\\pin\\blogs")));
    }

    @After
    public void tearDown() throws Exception {
    }
}
