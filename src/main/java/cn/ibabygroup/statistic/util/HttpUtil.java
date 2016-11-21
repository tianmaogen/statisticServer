package cn.ibabygroup.statistic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public String urlRequest(String type, String urlPath, String token, String postParam) throws Exception {
        URL url;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            url = new URL(urlPath);
            URLConnection connection = url.openConnection();
            if (!StringUtils.isEmpty(token)) {
                connection.setRequestProperty("token", token);
            }
            if (type.equalsIgnoreCase("post")) {
                connection.setDoInput(true);
                connection.setDoOutput(true);
                out = new PrintWriter(connection.getOutputStream());
                // 发送请求参数
                out.print(postParam);
                // flush输出流的缓冲
                out.flush();
                in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                return result;
            }
            connection.connect();
            return StreamUtils.copyToString(connection.getInputStream(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.error(String.format("http 请求失败 type:%s urlPath:%s token:%s ", type, urlPath, token), e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        throw new Exception(String.format("http 请求失败 type:%s urlPath:%s token:%s ", type, urlPath, token));
    }


}
