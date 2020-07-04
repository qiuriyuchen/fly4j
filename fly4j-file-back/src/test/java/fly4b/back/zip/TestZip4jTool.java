package fly4b.back.zip;


import fly4b.back.DirCompare;
import fly4b.back.DirCompareImpl;
import fly4b.back.DirZip;
import fly4b.back.ZipConfig;
import fly4j.common.FlyResult;
import fly4j.common.file.FileUtil;
import fly4j.common.pesistence.file.FileStrStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.File;
import java.nio.file.Path;

/**
 * @author qryc
 */
@Slf4j
@RunWith(BlockJUnit4ClassRunner.class)
public class TestZip4jTool {
    private static final String testPathStr = "/testFly/zip";

    @Before
    public void setup() throws Exception {
        File testDir = Path.of("/testFly/zip/").toFile();

        if (testDir.exists()) {
            FileUtils.forceDelete(testDir);
//            dir.delete();
        }
        //解决问题思路不止是直接解决，比如不放心是否删除了，可以加判断，并没有直接研究为什么 dir.delete();没有删除；当然有时间要看下，其实是因为cleanDirectory
        Assert.assertFalse(testDir.exists());
        FileUtils.forceMkdir(testDir);
        System.out.println(testDir.getAbsoluteFile());
        Assert.assertTrue(testDir.exists());
    }

    @Test
    public void testZip1() throws Exception {


        Zip4jTool.zipDir(Path.of(testPathStr, "test.zip").toFile(), FileUtil.getClassPathFile("/test-files/testDir"), "123");

        Zip4jTool.unZip(Path.of(testPathStr, "test.zip").toString(), testPathStr, "123");
        Assert.assertEquals("a中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/a.txt")));
        Assert.assertEquals("b中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/b.txt")));
        Assert.assertEquals("c中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/c.txt")));
        Assert.assertEquals("aa中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/childDir/aa.txt")));
        Assert.assertEquals("bb中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/childDir/bb.txt")));
        System.out.println("end");
    }

    @Test
    public void testZip2() throws Exception {

        Zip4jTool.zipDir(Path.of(testPathStr, "test.zip").toFile(), FileUtil.getClassPathFile("/test-files/testDir"), "123");

        Zip4jTool.unZip(Path.of(testPathStr, "test.zip").toString(), testPathStr + "/", "123");
        Assert.assertEquals("a中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/a.txt")));
        Assert.assertEquals("b中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/b.txt")));
        Assert.assertEquals("c中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/c.txt")));
        Assert.assertEquals("aa中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/childDir/aa.txt")));
        Assert.assertEquals("bb中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/childDir/bb.txt")));
        System.out.println("end");
    }

    @Test
    public void testZip3() throws Exception {
        DirCompare dirCompare = new DirCompareImpl();
        DirZip dirZip = new DirZip();
        dirZip.setDirCompare(dirCompare);
        ZipConfig zipConfig = new ZipConfig()
                .setBeZipSourceDir(FileUtil.getClassPathFile("/test-files/testDir").toPath())
                .setDestZipFilePath(Path.of(testPathStr, "test.zip"))
                .setPassword("123");
        FlyResult flyResult = dirZip.excuteBack(zipConfig);
        Assert.assertTrue(flyResult.isSuccess());
        Zip4jTool.unZip(Path.of(testPathStr, "test.zip").toString(), testPathStr + "/", "123");
        Assert.assertEquals("a中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/a.txt")));
        Assert.assertEquals("b中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/b.txt")));
        Assert.assertEquals("c中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/c.txt")));
        Assert.assertEquals("aa中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/childDir/aa.txt")));
        Assert.assertEquals("bb中国", FileStrStore.getValue(Path.of(testPathStr, "testDir/childDir/bb.txt")));
        System.out.println("end");
    }

    @After
    public void tearDown() throws Exception {
    }


}
