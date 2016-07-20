package com.whunf.putaomovieday1.common.parser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 解析任务工具类
 */
public class ParserTaskUtils
{

    /**
     * 取消解析任务
     * 
     * @param task
     */
    public static void cancelParserTask(ParserTask task)
    {
        if (task != null)
        {
            task.cancel();
        }
    }

    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded
     * encoded string.
     */
    private static String encodeParameters(Map<String, String> params, String paramsEncoding)
    {
        StringBuilder encodedParams = new StringBuilder();
        try
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                if (entry.getKey() != null && entry.getValue() != null)
                {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                    encodedParams.append('&');
                }
            }
            String pstr = encodedParams.toString();
            if (pstr.length() > 0)
            {
                pstr = pstr.substring(0, pstr.length() - 1);
            }
            return pstr;
        }
        catch (UnsupportedEncodingException uee)
        {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    /**
     * 将参数拼装路径
     * @param map
     * @param reqPath
     * @return
     */
    public static String buildPath(Map<String, String> map, String reqPath)
    {
        StringBuffer pathSb = new StringBuffer(reqPath);
        String enparam = encodeParameters(map, "UTF-8");
        if (enparam.length() > 0)//
        {
            pathSb.append("?");
            pathSb.append(enparam);
        }
        return pathSb.toString();
    }

}
