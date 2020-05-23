package fly4j.common.crypto;

import java.util.regex.Pattern;

/**
 * 字节工具类
 * Created by guanpanpan on 2015/8/19.
 */
final public class ByteUtil {
    private ByteUtil() {

    }

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(255));
        System.out.println(Integer.toHexString(1 & 0xFF));
        ;
        System.out.println(Integer.toHexString(255 & 0xFF));
        ;
    }

    /**
     * 将二进制转换成16进制
     * 返回的长度*2
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            //0xFF为十进制255，二进制 11111111，字节本来就8位，其实& 0xFF没有起到作用，只是习惯写法
            //如果是int，只想补码相同，需要把前面的都置为0
            /**
             * 1.byte的大小为8bits而int的大小为32bits
             * 2.java的二进制采用的是补码形式
             */
            String hex = Integer.toHexString(buf[i] & 0xFF);
            //高位补0
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        //结果大小应该是除以2，因为转换的时候一个字节转了二位
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            //计算的时候，要分别算出低位和高位，乘以进制，再相加
            int beginIndex = i * 2;
            int high = Integer.parseInt(hexStr.substring(beginIndex, beginIndex + 1), 16);
            int low = Integer.parseInt(hexStr.substring(beginIndex + 1, beginIndex + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static boolean isHexStr(String str) {
        String regString = "^[a-f0-9A-F]+$";
        return Pattern.matches(regString, str);

    }
}
