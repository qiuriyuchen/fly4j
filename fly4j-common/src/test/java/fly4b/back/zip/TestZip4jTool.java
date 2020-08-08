package fly4b.back.zip;


import fly4j.back.DirCompare;
import fly4j.back.DirCompareImpl;
import fly4j.back.DirZip;
import fly4j.back.ZipConfig;
import fly4j.back.zip.Zip4jTool;
import fly4j.common.FlyResult;
import fly4j.common.pesistence.file.FileStrStore;
import fly4j.common.test.TestData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author qryc
 */
@Slf4j
@RunWith(BlockJUnit4ClassRunner.class)
public class TestZip4jTool {
    //源文件
    private Path sourcePath = Path.of(TestData.testPath.toString(), "sourcePath");
    private Path backPath = Path.of(TestData.testPath.toString(), "back");

    @Before
    public void setup() throws Exception {
        if (Files.exists(TestData.testPath))
            FileUtils.forceDelete(TestData.testPath.toFile());
        Assert.assertFalse(Files.exists(backPath));
        Files.createDirectories(backPath);
        Assert.assertTrue(Files.exists(backPath));

        //创建测试文件
        Assert.assertFalse(Files.exists(sourcePath));
        FileStrStore.setValue(Path.of(sourcePath.toString(), "a.txt"), "a中国");
        FileStrStore.setValue(Path.of(sourcePath.toString(), "b.txt"), "b中国");
        FileStrStore.setValue(Path.of(sourcePath.toString(), "c.txt"), "c中国");
        FileStrStore.setValue(Path.of(sourcePath.toString(), "childDir/aa.txt"), "aa中国");
        FileStrStore.setValue(Path.of(sourcePath.toString(), "childDir/bb.txt"), "bb中国");
    }

    @Test
    public void testZip1() throws Exception {


        Zip4jTool.zipDir(Path.of(backPath.toString(), "test.zip").toFile(), sourcePath.toFile(), "123");

        Zip4jTool.unZip(Path.of(backPath.toString(), "test.zip").toFile(), backPath.toFile(), "123");
        Assert.assertEquals("a中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/a.txt")));
        Assert.assertEquals("b中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/b.txt")));
        Assert.assertEquals("c中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/c.txt")));
        Assert.assertEquals("aa中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/childDir/aa.txt")));
        Assert.assertEquals("bb中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/childDir/bb.txt")));
    }

    @Test
    public void testZip2() throws Exception {

        Zip4jTool.zipDir(Path.of(backPath.toString(), "test.zip").toFile(), new File(TestData.testPath.toString(), "sourcePath/"), "123");

        Zip4jTool.unZip(Path.of(backPath.toString(), "test.zip").toFile(), backPath.toFile(), "123");
        Assert.assertEquals("a中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/a.txt")));
        Assert.assertEquals("b中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/b.txt")));
        Assert.assertEquals("c中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/c.txt")));
        Assert.assertEquals("aa中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/childDir/aa.txt")));
        Assert.assertEquals("bb中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/childDir/bb.txt")));
    }

    @Test
    public void testZip3() throws Exception {
        DirCompare dirCompare = new DirCompareImpl();
        DirZip dirZip = new DirZip();
        dirZip.setDirCompare(dirCompare);
        ZipConfig zipConfig = new ZipConfig()
                .setBeZipSourceDir(sourcePath.toFile())
                .setDestZipFile(Path.of(backPath.toString(), "test.zip").toFile())
                .setPassword("ab123");
        FlyResult flyResult = dirZip.excuteBack(zipConfig);
        System.out.println(flyResult.getMsg());
        Assert.assertTrue(flyResult.isSuccess());
        Zip4jTool.unZip(Path.of(backPath.toString(), "test.zip").toFile(), backPath.toFile(), "ab123");
        Assert.assertEquals("a中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/a.txt")));
        Assert.assertEquals("b中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/b.txt")));
        Assert.assertEquals("c中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/c.txt")));
        Assert.assertEquals("aa中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/childDir/aa.txt")));
        Assert.assertEquals("bb中国", FileStrStore.getValue(Path.of(backPath.toString(), "sourcePath/childDir/bb.txt")));
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDeleteOnExit(TestData.testPath.toFile());
    }


}
