package fly4j.common.pesistence.file;

import fly4j.common.pesistence.file.FileStrStore;

/**
 * Created by guan on 2020/5/3.
 */
public class TestFileStrStore {
    public void demo() {
        FileStrStore.setObject("d:\\flyConfig.json", new Object());
    }
}
