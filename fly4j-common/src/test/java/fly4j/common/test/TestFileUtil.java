package fly4j.common.test;

import fly4j.common.file.FileUtil;
import org.apache.commons.io.FilenameUtils;
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
        System.out.println(TestData.tTargetDir);
        FileUtil.delDir(FilenameUtils.concat(TestData.tTargetDir, "TestFileUtil"));
        Assert.assertTrue(!new File(FilenameUtils.concat(TestData.tTargetDir, "TestFileUtil")).exists());
        FileUtil.forceMkdir(FilenameUtils.concat(TestData.tTargetDir, "TestFileUtil/a/aa/aaa"));
        Assert.assertTrue(new File(FilenameUtils.concat(TestData.tTargetDir, "TestFileUtil/a/aa/aaa")).exists());
    }

    @After
    public void tearDown() throws Exception {
    }
}
