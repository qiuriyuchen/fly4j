package fly.plug.persistence.file;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * dataDir 数据存储的目录,比如路径为/export/data；下边可以存放不同数据，通过pin和innerDir来区分，可以理解为不通数据库
 * pin   可以理解为不同的数据库
 * secondDbSpace 细分数据,可以为空,比如存放文章和用户，分别为article和user，则对应数据目录分别为/export/data/article和/export/data/user
 * <p>
 * bseDir
 * ------/pin
 * ------/pin/article/
 * ------/pin/user/
 * ------/pin2
 * ------/pin/article/
 * 如果pin和innerDir为空，则退化为全局数据Map
 * bseDir 数据文件
 * key :作为文件名存储
 * value：作为文件内容
 *
 * @author guanpanpan
 * @date created:forget;created is before:2020/03/07
 */
public class FileKV {
    private FileStrStore fileStrStore = new FileStrStore();

    private String dataDir;
    private String secondDbSpace;

    public FileKV(String dataDir) {
        reset(dataDir, null);
    }

    public FileKV(String dataDir, String secondDbSpace) {
        reset(dataDir, secondDbSpace);
    }

    public void reset(String baseDir, String secondDbSpace) {
        this.dataDir = baseDir;
        this.secondDbSpace = secondDbSpace;
    }

    /**
     * @param
     * @param id    主键，可以理解为Map的key
     * @param value
     * @return
     */
    public Long setValue(String firstDbSpace, String id, String value) {
        String storePath = getStoreFilePath(firstDbSpace, id);
        fileStrStore.setValue(storePath, value);
        return null;
    }

    public List<String> getValues(String firstDbSpace, String keyPre) {
        return fileStrStore.getValues(getStoreDirPath(firstDbSpace), keyPre);
    }

    public String getValue(String firstDbSpace, String id) {
        String filePath = getStoreFilePath(firstDbSpace, id);
        return fileStrStore.getValue(filePath);
    }

    public void delete(String firstDbSpace, String id) {
        String filePath = getStoreFilePath(firstDbSpace, id);
        fileStrStore.delete(filePath);
    }


    private String getStoreDirPath(String firstDbSpace) {
        if (StringUtils.isBlank(firstDbSpace)) {
            return dataDir;
        }
        if (StringUtils.isBlank(secondDbSpace)) {
            return FilenameUtils.concat(dataDir, firstDbSpace);
        }
        return FilenameUtils.concat(FilenameUtils.concat(dataDir, firstDbSpace), secondDbSpace);
    }

    public String getStoreFilePath(String pin, String id) {
        return FilenameUtils.concat(getStoreDirPath(pin), id);
    }


}