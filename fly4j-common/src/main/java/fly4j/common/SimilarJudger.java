package fly4j.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 字符串相信度判断
 * Created by guanpanpan on 2015/8/20.
 */
public class SimilarJudger {
    private static final Log log = LogFactory.getLog(SimilarJudger.class);
    /**
     * 判断两个字符串的包含度，用于不可大规模对原文做出修改的判断
     */
    public static int containDegree(String oldStr, String newStr) {
        if(StringUtils.isBlank(oldStr)){
            return 100;
        }
        Set<Character> charactersOld = getUsedChars(oldStr);
        Set<Character> charactersNew = getUsedChars(newStr);
        int fenmu=charactersOld.size();
        //找出新串中包含老串中的字符，做分子
        int feizi=0;
        for(Character cOld:charactersOld){
            if(charactersNew.contains(cOld)){
                feizi++;
            }
        }
        log.debug("charactersOld"+charactersOld);
        log.debug("charactersNew"+charactersNew);
        log.debug("oldStr"+oldStr);
        log.debug("newStr"+newStr);
        log.debug("feizi:"+feizi);
        log.debug("fenmu:"+fenmu);
       return feizi*100/fenmu;
    }

    private static Set<Character> getUsedChars(String str) {
        Set<Character> characters = new HashSet<Character>();
        for (char item : str.toCharArray())

            if (isUsedChar(item)) {

                characters.add(item);

            }

        return characters;

    }

    /**
     * 是否是有意义的字符
     *
     * @param charValue
     * @return
     */
    private static boolean isUsedChar(char charValue) {

        return (charValue >= 0x4E00 && charValue <= 0X9FA5)

                || (charValue >= 'a' && charValue <= 'z')

                || (charValue >= 'A' && charValue <= 'Z')

                || (charValue >= '0' && charValue <= '9');

    }
}
