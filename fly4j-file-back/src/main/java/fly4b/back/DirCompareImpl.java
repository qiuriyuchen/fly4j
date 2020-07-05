package fly4b.back;

import fly4j.common.DateUtil;
import fly4j.common.FlyResult;
import fly4j.common.JsonUtils;
import fly4j.common.StringConst;
import fly4j.common.file.FileAndDirFilter;
import fly4j.common.file.FileUtil;
import fly4j.common.track.TrackContext;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.*;

public class DirCompareImpl implements DirCompare {
    //md5 or size
    public static String genType = "size";
    @Setter
    private FileAndDirFilter noNeedCalMd5FileFilter;
    private boolean checkEmptyDir = true;

    @Override
    public DirCompareResult compareMulDir(List<File> compDirs) {
        DirCompareResult result = new DirCompareResult();
        TrackContext.reset();
        int same = 0;

        //得到文件夹的Md5Map
        Set<String> allKeys = new HashSet<>();
        List<Map<String, String>> allMap = new ArrayList<>();
        for (var compDir : compDirs) {
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
                    result.addDelFile(compDirs.get(dirIndex) + key);
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

    @Override
    public Map<String, String> getDirMd5Map(File checkDir) {
        Map<String, String> md5Map = new LinkedHashMap<>();
        getMd5FileStr(checkDir, md5Map, checkDir);
        return md5Map;
    }

    private void getMd5FileStr(File dirFile, Map<String, String> md5Map, File baseDir) {
        try {
            File[] files = dirFile.listFiles();
            var dirKey = FileUtil.getSubPathUnix(dirFile, baseDir);
            //如果不是空文件夹，把父亲文件夹加入
            if (checkEmptyDir) {
                md5Map.put(dirKey, "dir");
            } else {
                if (files.length > 0) {
                    md5Map.put(dirKey, "dir");
                }
            }

            for (File file : files) {

                if (null != noNeedCalMd5FileFilter && noNeedCalMd5FileFilter.accept(file)) {
                    continue;
                }
                if (file.isDirectory()) {
                    //递归
                    getMd5FileStr(file, md5Map, baseDir);
                } else {
                    //生成md5

                    String key = FileUtil.getSubPathUnix(file, baseDir);

                    if ("size".equals(genType)) {
                        md5Map.put(key, "" + file.length());
                    } else {
                        md5Map.put(key, FileUtil.getMD5(file));
                    }

                }

            }
        } catch (Exception e) {
            System.out.println("dirFile:" + dirFile);
            e.printStackTrace();
            throw new RuntimeException(e);
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


    @Override
    public FlyResult checkWithHistory(File checkDir) {
        final StringBuilder stringBuilder = new StringBuilder();
        StringConst.appendLine(stringBuilder, "check:" + checkDir);
        List<File> md5Files = getSortMd5Files(checkDir);
        if (null == md5Files || md5Files.size() == 0) {
            stringBuilder.append(" not have history file");
            return new FlyResult(true, stringBuilder.toString());
        }

        FlyResult flyResult = new FlyResult().success();
        File md5File = md5Files.get(0);
        try {
            StringConst.appendLine(stringBuilder, "....current file compare to history:" + DateUtil.getDateStr(new Date(md5File.lastModified())));
            //取得上次的md5
            String historyMd5Str = FileUtils.readFileToString(md5File, Charset.forName("utf-8"));
            HashMap<String, String> historyMd5MapRead = JsonUtils.readStringStringHashMap(historyMd5Str);
            //取得文件夹的Md5
            Map<String, String> currentMd5Map = this.getDirMd5Map(checkDir);
            historyMd5MapRead.forEach((oKey, oValue) -> {
                String md5New = currentMd5Map.get(oKey);
                if (null == md5New) {
                    flyResult.fail();
                    StringConst.appendLine(stringBuilder, "........delete:" + oKey);
                } else if (md5New.equals(oValue)) {

                } else {
                    flyResult.fail();
                    StringConst.appendLine(stringBuilder, "........diff:" + oKey);
                }
            });
            currentMd5Map.forEach((cKey, cValue) -> {
                String md5History = historyMd5MapRead.get(cKey);
                if (null == md5History) {
                    StringConst.appendLine(stringBuilder, "........add:" + cKey);
                }
            });
        } catch (Exception e) {
            StringConst.appendLine(stringBuilder, "Exception:" + e.getMessage());
        }
        return flyResult.append(stringBuilder.toString());

    }


    private List<File> getSortMd5Files(File checkDir) {
        var checkDirFile = Path.of(checkDir.getAbsolutePath(), ".flyMd5").toFile();
        File[] filesArray = checkDirFile.listFiles(((dir, name) -> name.endsWith(".md5")));
        if (null == filesArray) {
            return List.of();
        }
        List<File> md5Files = Arrays.asList(filesArray);
        Collections.sort(md5Files, (f1, f2) -> {
            long t1 = f1.lastModified();
            long t2 = f2.lastModified();
            Long c = t2 - t1;
            return c.intValue();
        });
        return md5Files;
    }

    @Override
    public void deleteMoreMd5Files(File beZipSourceDir, int maxCount) {
        //删除多余的Md5文件 deleteMore md5
        var md5Files = getSortMd5Files(beZipSourceDir);
        if (md5Files.size() > maxCount) {
            for (var i = maxCount - 1; i < md5Files.size(); i++) {
                md5Files.get(i).delete();
            }
        }
    }
}
