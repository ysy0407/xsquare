package com.xsquare.common.utils;

import com.xsquare.common.httpClient.HttpClientUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName
 * @Description 发送短信工具类
 * @Author YuSongYuan
 * @Create 2018-09-01 15:58
 * @Package_name com.xsquare.common.utils
 * @Project_name xsquare
 * @Since JDK 1.8
 **/

public class SendSmsUtils {

    private static Logger LOG = LoggerFactory.getLogger(SendSmsUtils.class);

    /**
     * @功能描述
     *  发送短信，并返回信息
     * @执行流程
     *
     * @注意事项
     *  result为null便是提交失败或完全出现错误，success为1，便是提交成功，msgid为流水号
     * @author YuSongYuan
     * @date 2018/9/1
     * @param appkey
    * @param appId
    * @param templateId
    * @param templateArgs
    * @param phone
    * @param authToken
    * @param url
     * @return org.json.JSONObject
     * @since JDK 1.8
     */
    public static JSONObject sendSms(String appkey, String appId, String templateId, String[] templateArgs, String phone, String authToken, String url) {
        JSONObject result = null;
        try {
            JSONObject jsonRequest = new JSONObject();
            JSONObject requestHeader = new JSONObject();
            JSONObject requestBody = new JSONObject();
            JSONArray args = new JSONArray();
            args.put(templateArgs[0]);
            requestHeader.put("appkey", appkey);
            requestHeader.put("appId", appId);
            requestHeader.put("startTime", DateUtils.getTime());
            jsonRequest.put("header", requestHeader);
            requestBody.put("templateId", templateId);
            requestBody.put("templateArgs",args);
            requestBody.put("phones", phone);
            requestBody.put("sendtime", "");
            requestBody.put("subcode", "");
            jsonRequest.put("body", requestBody);
            String sign = MD5Util.md5(authToken.concat(jsonRequest.toString()).concat(authToken)).toUpperCase();
            Map<String, String> headerMap = new HashMap<String, String>();
            headerMap.put("sign", sign);
            LOG.info("----发送短信url：{}，请求内容：{}，请求sign：{}", url, jsonRequest.toString(), sign);
            String response = HttpClientUtil.doPost(url, jsonRequest.toString(), headerMap);
//            String response = "{\"header\":{\"desc\":\"success\",\"succ\":1},\"body\":{\"msgid\":\"11111111\"}}";
            LOG.info("----发送短信响应为：{}", response);
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject responseHeader = jsonResponse.getJSONObject("header");
            JSONArray responseBody = null;
            if ("success".equals(responseHeader.get("desc"))) {
                //响应内容中的body字段为数组，只获取第一个即可
                responseBody = jsonResponse.getJSONArray("body");
                JSONObject body = responseBody.getJSONObject(0);
                result = new JSONObject();
                result.put("success", responseHeader.get("succ"));
                result.put("msgid", body.getString("msgid"));
            } else {
                LOG.info("发送失败！！！");
            }
        } catch (Exception e) {
            LOG.error("----发送短信出现错误,手机号为：{}，具体错误为：{}", phone, e);
        }
        return result;
    }

}
