package fly4j.common.file;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by guan on 2020/3/19.
 */
public class FileUploadClient {
    public static int uploadFiles(String strURL, File[] allFile) {
        int status = 200;
        for (int i = 0; i < allFile.length; ++i) {
            status = uploadSingleFile(strURL, allFile[i]);
        }
        return status;
    }
    private static int uploadSingleFile(String strURL, File file) {
        int status = 200;
        String localFile = file.getAbsolutePath();
        if (!(file.exists())) {
            return status;
        }
        long startPos = 0L;
        HttpClient headclient = new DefaultHttpClient();
        HttpHead httphead = new HttpHead(strURL);
        try {
            httphead.addHeader("Content-Type", "application/octet-stream");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            headclient.getConnectionManager().shutdown();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(strURL).openConnection();
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(localFile), "r");
            if (startPos < randomAccessFile.length()) {
                conn.setRequestMethod("PUT");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/octet-stream");
                conn.setRequestProperty("File-Name", file.getName());
                OutputStream os = conn.getOutputStream();
                int rn = 0;
                byte[] buf = new byte[4096];
                while ((rn = randomAccessFile.read(buf, 0, 4096)) > 0) {
                    os.write(buf, 0, rn);
                }
                os.close();
                status = conn.getResponseCode();
            }
            randomAccessFile.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                conn.getResponseCode();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return status;
    }
}
