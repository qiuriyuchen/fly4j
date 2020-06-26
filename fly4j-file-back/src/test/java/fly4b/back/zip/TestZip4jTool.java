package fly4b.back.zip;


import fly4j.common.file.FileUtil;
import fly4j.common.pesistence.file.FileStrStore;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * @author guanpanpan
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestZip4jTool {

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void testZip() throws Exception {
        Path path = Path.of("/export/testflyzip/");
        File dir = new File("/export/testflyzip/");

        if (dir.exists()) {
            FileUtils.forceDelete(path.toFile());
//            dir.delete();
        }
        //解决问题思路不止是直接解决，比如不放心是否删除了，可以加判断，并没有直接研究为什么 dir.delete();没有删除；当然有时间要看下，其实是因为cleanDirectory
        Assert.assertFalse(dir.exists());
        dir.mkdir();
        System.out.println(dir.getAbsoluteFile());
        Assert.assertTrue(dir.exists());

        Zip4jTool.zip("/export/testflyzip/test.zip", getTestFileFromResources("testDir").getAbsolutePath(), "123");

        Zip4jTool.unZip("/export/testflyzip/test.zip", "/export/testflyzip/", "123");
        Assert.assertEquals("a中国", FileStrStore.getValue(Path.of(path + "testDir/a.txt")));
        Assert.assertEquals("b中国", FileStrStore.getValue(Path.of(path + "testDir/b.txt")));
        Assert.assertEquals("aa中国", FileStrStore.getValue(Path.of(path + "testDir/childDir/aa.txt")));
        Assert.assertEquals("bb中国", FileStrStore.getValue(Path.of(path + "testDir/childDir/bb.txt")));
        System.out.println("end");
    }


    @After
    public void tearDown() throws Exception {
    }


    private static final String TEST_FILES_FOLDER_NAME = "test-files";
    private static final String TEST_ARCHIVES_FOLDER_NAME = "test-archives";

    public static File getTestFileFromResources(String fileName) {
        return getFileFromResources(TEST_FILES_FOLDER_NAME, fileName);
    }

    public static File getTestArchiveFromResources(String fileName) {
        return getFileFromResources(TEST_ARCHIVES_FOLDER_NAME, fileName);
    }

    private static File getFileFromResources(String parentFolder, String fileName) {
        try {
            String path = "/" + parentFolder + "/" + fileName;
            String utfDecodedFilePath = URLDecoder.decode(TestZip4jTool.class.getResource(path).getFile(),
                    StandardCharsets.UTF_8.toString());
            return new File(utfDecodedFilePath);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
