package fly4j.common.encode;

/**
 * 
 * @author guanpanpan
 *
 */
public class HtmlEncode {

	public static String jiami(String str){
		return "";
	}
	public static String jiemi(String strMi){
		return "";
	}

	public static String htmEncode(String s) {
		StringBuffer stringbuffer = new StringBuffer();
		int j = s.length();
		for (int i = 0; i < j; i++) {
			char c = s.charAt(i);
			switch (c) {
			case 60:
				stringbuffer.append("&lt;");
				break;
			case 62:
				stringbuffer.append("&gt;");
				break;
			case 38:
				stringbuffer.append("&amp;");
				break;
//			case 34:
//				stringbuffer.append("&quot;");
//				break;
//			case 169:
//				stringbuffer.append("&copy;");
//				break;
//			case 174:
//				stringbuffer.append("&reg;");
//				break;
//			case 165:
//				stringbuffer.append("&yen;");
//				break;
//			case 8364:
//				stringbuffer.append("&euro;");
//				break;
//			case 8482:
//				stringbuffer.append("&#153;");
//				break;
//			case 13:
//				if (i < j - 1 && s.charAt(i + 1) == 10) {
//					stringbuffer.append("<br>");
//					i++;
//				}
//				break;
//			case 32:
//				if (i < j - 1 && s.charAt(i + 1) == ' ') {
//					stringbuffer.append("&nbsp;");
//					i++;
//					break;
//				}
			default:
				stringbuffer.append(c);
				break;
			}
		}
		return stringbuffer.toString();
	}

	public static void main(String[] args) {
		System.out.println(HtmlEncode.htmEncode("<a>sdf</a>fw"));
		char a=38;
		System.out.println(a);
	}
}
