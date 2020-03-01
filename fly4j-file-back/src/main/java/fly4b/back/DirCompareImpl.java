package fly4b.back;

import fly4j.common.DateUtil;
import fly4j.common.JsonUtils;
import fly4j.common.StringConst;
import fly4j.common.file.FileAndDirFilter;
import fly4j.common.file.FileUtil;
import fly4j.common.track.TrackContext;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.*;

public class DirCompareImpl implements DirCompare {
    public static String genType = "md5";
    private FileAndDirFilter noNeedCalMd5FileFilter;
    @Override
    public DirCompareResult compar(List<String> compDirs, boolean masterSlave) throws Exception {
        DirCompareResult result = new DirCompareResult();
        TrackContext.reset();
        int same = 0;

        Set<String> allKeys = new HashSet<>();
        List<Map<String, String>> allMap = new ArrayList<>();
        for (String compDir : compDirs) {
            TrackContext.appendTrackInfo("comp dir:" + compDir);
            Map<String, String> md5Map = this.getDirMd5Map(compDir);
            allMap.add(md5Map);
            allKeys.addAll(md5Map.keySet());
        }
        //如果是主从对比
//        allKeys.addAll(fileMd5Cal.getDirMd5Map(compDirs.get(0)).keySet());

        for (String key : allKeys) {
            //每个文件夹对比
            Map<Integer, String> diffDirMd5 = new HashMap<>();
            Set<String> values = new HashSet<>();
            //遍历要对比的文件夹
            for (int dirIndex = 0; dirIndex < compDirs.size(); dirIndex++) {
                //取得每个文件夹的文件md5
                String md5Value = allMap.get(dirIndex).get(key);
                if (md5Value == null) {
                    TrackContext.appendTrackInfo(key + " is Delete from " + compDirs.get(dirIndex));
                } else {
                    diffDirMd5.put(dirIndex, md5Value);
                    values.add(md5Value);
                }
            }

            //对比
            if (values.size() == 1) {
                same++;
            } else {
                TrackContext.appendTrackInfo(key + " is Diff:" + values.size());
            }

        }
        TrackContext.appendTrackInfo("total files:" + allKeys.size() + " same: " + same);
        return result;
    }

    public Map<String, String> getDirMd5Map(String checkDir) throws Exception {
        Map<String, String> md5Map = new LinkedHashMap<>();
        getMd5FileStr(new File(checkDir), md5Map, checkDir);
        return md5Map;
    }

    public void getMd5FileStr(File dirFile, Map<String, String> md5Map, String baseDir) throws Exception {
        try {
            File[] files = dirFile.listFiles();
            for (File file : files) {
                if (noNeedCalMd5FileFilter.accept(file)) {
                    continue;
                }
                if (file.isDirectory()) {
                    //递归
                    getMd5FileStr(file, md5Map, baseDir);
                } else {
                    //生成md5

                    String key = file.getAbsolutePath();
                    baseDir = baseDir.replaceAll("\\\\", "/");
                    key = key.replaceAll("\\\\", "/");
                    key = key.replaceAll(baseDir, "");

                    if ("size".equals(genType)) {
                        md5Map.put(key, "" + file.length());
                    } else {
                        md5Map.put(key, getMD5(file));
                    }

                }

            }
        } catch (Exception e) {
            System.out.println("dirFile:" + dirFile);
            e.printStackTrace();
        }

    }

    /**
     * 获取一个文件的md5值(可处理大文件)
     *
     * @return md5 value
     */
    private String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 求一个字符串的md5值
     *
     * @param target 字符串
     * @return md5 value
     */
    private String MD5(String target) {
        return DigestUtils.md5Hex(target);
    }

    public void setNoNeedCalMd5FileFilter(FileAndDirFilter noNeedCalMd5FileFilter) {
        this.noNeedCalMd5FileFilter = noNeedCalMd5FileFilter;
    }

    @Override
    public String check(String checkDir, int checkMd5Count) throws Exception {
        final StringBuilder stringBuilder = new StringBuilder();
        StringConst.appendLine(stringBuilder, "check:" + checkDir);
        List<File> md5Files = getSortMd5Files(checkDir);
        for (int index = 0; index < checkMd5Count; index++) {
            File md5File = md5Files.get(index);
            try {
                long md5Time = Long.valueOf(md5File.getName().replaceAll("md5", "").replaceAll("\\.", ""));
                StringConst.appendLine(stringBuilder, "....current file compare to history:" + DateUtil.getDateStr(new Date(md5Time)));

                checkSingleFile(checkDir, md5File, stringBuilder);
            } catch (Exception e) {
                StringConst.appendLine(stringBuilder, "Exception:" + e.getMessage());
            }
        }
        return stringBuilder.toString();

    }

    private void checkSingleFile(String checkDir, File md5File, StringBuilder stringBuilder) throws Exception {
        //取得上次的md5
        String md5Str = FileUtils.readFileToString(md5File, Charset.forName("utf-8"));
        HashMap<String, String> md5MapRead = JsonUtils.readStringStringHashMap(md5Str);


        //取得文件夹的Md5
        Map<String, String> md5Map = this.getDirMd5Map(checkDir);
//        md5Map.forEach((key, value) -> System.out.println(key));
        for (Map.Entry readEntry : md5MapRead.entrySet()) {
            String md5New = md5Map.get(readEntry.getKey());
            if (null == md5New) {
                StringConst.appendLine(stringBuilder, "........delete:" + readEntry.getKey());
            } else if (md5New.equals(readEntry.getValue())) {

            } else {
                StringConst.appendLine(stringBuilder, "........diff:" + readEntry.getKey());
            }

        }
    }

    public  List<File> getSortMd5Files(String checkDir) {
        List<File> md5Files = Arrays.asList(FileUtil.listFilesWithEndStr(checkDir, ".md5"));
        Collections.sort(md5Files, (f1, f2) -> {
            long t1 = Long.valueOf(f1.getName().replaceAll("md5", "").replaceAll("\\.", ""));
            long t2 = Long.valueOf(f2.getName().replaceAll("md5", "").replaceAll("\\.", ""));
            Long c = t2 - t1;
            return c.intValue();
        });
        return md5Files;
    }
    @Override
    public void deleteMoreMd5Files(String beZipSourceDir) {
        //删除多余的Md5文件 deleteMore md5
        List<File> md5Files = getSortMd5Files(beZipSourceDir);
        if (md5Files.size() > 3) {
            for (int i = 2; i < md5Files.size(); i++) {
                md5Files.get(i).delete();
            }
        }
    }
}
