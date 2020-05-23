package fly4j.common.crypto;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by guanpanpan on 2016/4/28.
 */
public class MD5Util {

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }
    public static long ketamaHash(String k) {
        long rv = 0L;
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var3) {
            throw new RuntimeException("MD5 not supported", var3);
        }

        md5.reset();
        md5.update(getKeyBytes(k));
        byte[] bKey = md5.digest();
        rv = ((long) (bKey[3] & 0xFF) << 24)
                | ((long) (bKey[2] & 0xFF) << 16)
                | ((long) (bKey[1] & 0xFF) << 8)
                | (bKey[0] & 0xFF);
        return  rv & 0xffffffffL; /* Truncate to 32-bits */
    }
    public static byte[] getKeyBytes(String k) {
        try {
            return k.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException(var2);
        }
    }
    // 测试主函数
    public static void main(String args[]) throws Exception{
        String s = "abc";
        System.out.println("原始：" + s);
        System.out.println("MD5后："+string2MD5(s) + string2MD5(s).length());
        System.out.println("MD5后："+ketamaHash(s) );
    }
}
