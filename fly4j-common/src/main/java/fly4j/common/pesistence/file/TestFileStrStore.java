package fly4j.common.pesistence.file;

import fly4j.common.pesistence.file.FileStrStore;

import java.nio.file.Path;

/**
 * Created by guan on 2020/5/3.
 */
public class TestFileStrStore {
    public void demo() {
        FileStrStore.setObject(Path.of("d:\\flyConfig.json"), new Object());
    }
}
