package fly4j.cache;

import fly4j.common.cache.FlyCache;
import fly4j.common.cache.impl.FlyCacheJVM;
import fly4j.common.pesistence.file.FileStrStore;
import org.junit.Assert;
import org.junit.Test;

public class TestFileStrStore {


    @Test
    public void testCache() throws Exception {
        FileStrStore.setValue("d:/aa/aa.txt","abc");
        Assert.assertEquals("abc", FileStrStore.getValue("d:/aa/aa.txt"));
    }


}
