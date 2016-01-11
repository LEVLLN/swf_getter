import com.sun.org.apache.xerces.internal.util.URI;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.Cookie;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.List;

public class Runner {
    static URL url;
    Cookie cookie = new Cookie("kazdevelop@gmail.com", "dmsic1");
    private static CookieStore cookieStore = new BasicCookieStore();

    public static void main(String[] args) throws MalformedURLException {

        URLConnection conn;
        String strPath = "D:/Package.swf" ;
        String strURL = "https://elearning.robocamp.eu/get.php?exercise=wedo-kolejka-en&file=kolejka-wstep/kolejka-wstep-001.swf" ;
        url = new URL("http://cs7001.vk.me/c540105/v540105766/161/ycbsieoMr80.jpg");
        downloadFiles(strURL, strPath, 1024);

    }


    public static void downloadFiles(String strURL, String strPath, int buffSize) {
        try {
            URL connection = new URL(strURL);
            authorize();
            HttpURLConnection urlconn;
            urlconn = (HttpURLConnection) connection.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.connect();
            InputStream in = null;
            in = urlconn.getInputStream();
            OutputStream writer = new FileOutputStream(strPath);
            byte buffer[] = new byte[buffSize];
            int c = in.read(buffer);
            while (c > 0) {
                writer.write(buffer, 0, c);
                c = in.read(buffer);
            }
            writer.flush();
            writer.close();
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void authorize() throws IOException, URISyntaxException {
        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        try {
            HttpGet httpget = new HttpGet("http://mysite.com/login/");
            CloseableHttpResponse response1 = httpclient.execute(httpget);
            try {
                HttpEntity entity = response1.getEntity();

                System.out.println("Login form get: " + response1.getStatusLine());
                EntityUtils.consume(entity);

                System.out.println("Initial set of cookies:");
                List<org.apache.http.cookie.Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (org.apache.http.cookie.Cookie cooky : cookies)
                        System.out.println("- " + cooky.toString() + " авадакедабра " + cooky.getName());
                }

            } finally {
                response1.close();
            }

            HttpUriRequest login = RequestBuilder.post()
                    .setUri(String.valueOf(new URI("https://elearning.robocamp.eu/")))
                    .addParameter("email", "kazdevelop@gmail.com")
                    .addParameter("password", "dmsic1")
                    .build();
            CloseableHttpResponse response2 = httpclient.execute(login);
            System.out.println(response2.getFirstHeader("dcsd"));
            try {
                HttpEntity entity = response2.getEntity();

                System.out.println("Login form get: " + response2.getStatusLine());
                EntityUtils.consume(entity);

                System.out.println("Post logon cookies:");
                List<org.apache.http.cookie.Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        System.out.println("- " + cookies.get(i).toString());

                    }
                }


            } finally {
                response2.close();
            }
            HttpGet httpget1 = new HttpGet("https://elearning.robocamp.eu/");
            CloseableHttpResponse response3 = httpclient.execute(httpget1);
            try {
                HttpEntity entity = response3.getEntity();
                System.out.println("Login form get: " + response3.getStatusLine());
                System.out.println(entity.getContentLength());
            } finally {
                response3.close();
            }
        } finally {
            httpclient.close();
        }

    }
}

