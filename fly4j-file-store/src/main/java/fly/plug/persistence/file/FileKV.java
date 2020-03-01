package fly.plug.persistence.file;

import org.apache.commons.io.FilenameUtils;

import java.util.List;

/**
 * fileKv's floder
 * bseDir=/
 * db1
 * key1[json1]
 * key2[json2]
 * db2
 * db..n
 *
 * @author guanpanpan
 */
public class FileKV {
    private FileStrStore fileStrStore = new FileStrStore();


    public FileKV(String baseDir, String innerDir) {
        reset(baseDir, innerDir);
    }

    public void reset(String baseDir, String innerDir) {
        this.baseDir = baseDir;
        this.innerDir = innerDir;
    }

    private String baseDir;
    private String innerDir;


    public Long setValue(String pin, String id, String value) {

        String storePath = getStoreFilePath(pin, id);
        fileStrStore.setValue(storePath, value);

        return null;
    }

    public List<String> getValues(String pin, String keyPre) {
        return fileStrStore.getValues(getStoreDirPath(pin), keyPre);
    }

    public String getValue(String pin, String id) {
        String filePath = getStoreFilePath(pin, id);
        return fileStrStore.getValue(filePath);
    }

    public void delete(String pin, String id) {
        String filePath = getStoreFilePath(pin, id);
        fileStrStore.delete(filePath);
    }


    private String getStoreDirPath(String pin) {
        return FilenameUtils.concat(FilenameUtils.concat(baseDir, pin), innerDir);
    }

    public String getStoreFilePath(String pin, String id) {
        return FilenameUtils.concat(getStoreDirPath(pin), id);
    }


}