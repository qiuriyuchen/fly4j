package fly.plug.persistence.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
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
public class FileStrStore {
    private Charset fileCharset = Charset.forName("utf-8");


    public Long setValue(String storePath, String value) {

        try {
            FileUtils.writeStringToFile(new File(storePath), value, Charset.forName("utf-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<String> getValues(String storePath, String keyPre) {
        List<String> values = new ArrayList<>();
        Collection<File> files = listDirFiles(storePath);
        for (File file : files) {
            if (file.getName().startsWith(".")) {
                continue;
            }
            if (file.getName().startsWith(keyPre)) {

                String json = readFileToStr(file);
                values.add(json);
            }
        }
        return values;
    }

    public List<String> getChildFileNames(String storePath, String keyPre) {
        List<String> values = new ArrayList<>();
        Collection<File> files = listDirFiles(storePath);
        for (File file : files) {
            if (file.getName().startsWith(".")) {
                continue;
            }
            if (file.getName().startsWith(keyPre)) {
                values.add(file.getName());
            }
        }
        return values;
    }

    public String getValue(String filePath) {
        return readFileToStr(filePath);
    }

    public void delete(String filePath) {
        File file = new File(filePath);

        if (file.isFile() && file.exists())
            file.delete();

    }

    private static Collection<File> listDirFiles(String dirStr) {
        File file = new File(dirStr);
        if (!file.isDirectory()) {
            return new ArrayList<File>();
        }

        Collection<File> files = FileUtils.listFiles(file, null, false);
        return files;
    }

    protected String readFileToStr(File file) {
        if (!file.exists()) {
            return null;
        }
        try {
            return FileUtils.readFileToString(file, fileCharset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected String readFileToStr(String filePath) {
        return readFileToStr(new File(filePath));
    }
}
//    protected String encrypt(String str, String pin) {
//        return AESUtil.encryptStr2HexStr(str, mima(pin));
//    }
//
//    protected String decrypt(String hexEncryptStr, String pin) {
//        return AESUtil.decryptHexStrToStr(hexEncryptStr, mima(pin));
//    }