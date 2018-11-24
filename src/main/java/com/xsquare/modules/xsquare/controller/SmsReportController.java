package com.xsquare.modules.xsquare.controller;

import com.xsquare.modules.xsquare.entity.DictEntity;
import com.xsquare.modules.xsquare.entity.LogSignEntity;
import com.xsquare.modules.xsquare.service.DictService;
import com.xsquare.modules.xsquare.service.LogSignService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  接收东信短信回调
 *
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
@RestController
@RequestMapping("/caihsms")
public class SmsReportController {


    private static Logger LOG = LoggerFactory.getLogger(SmsReportController.class);

    @Autowired
    private DictService dictService;
    @Autowired
    private LogSignService logSignService;

    @PostMapping("/report")
    public JSONObject report(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject.toString());
        LOG.info("----接收东信短信回调，回调内容为：{}", jsonObject);
        JSONObject reportHeader = null;
        JSONObject reportBody = null;
        JSONObject reportResult = new JSONObject();
        reportResult.put("code", 0);
        reportResult.put("msg", "success");
        if (jsonObject.containsKey("header") && jsonObject.containsKey("body")) {
            reportHeader = jsonObject.getJSONObject("header");
            reportBody = jsonObject.getJSONObject("body");
        } else {
            reportResult.put("code", 1);
            reportResult.put("msg", "header and body chick fail !");
            LOG.info("----header and body chick fail !");
            return reportResult;
        }
        String appKey = null;
        String appId = null;
        String msgId = null;
        String phone = null;
        String status = null;
        //将pType为18的全部查出来
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pType", 18);
        List<DictEntity> list = dictService.queryList(map);
        for (DictEntity dictEntity : list) {
            if (20 == dictEntity.getId()) {
                appKey = dictEntity.getDescribe();
            } else if (21 == dictEntity.getId()) {
                appId = dictEntity.getDescribe();
            }
        }
        //校验回调的appKey和appId
        if (appKey.equals(reportHeader.getString("appKey")) && appId.equals(reportHeader.getString("appId"))) {
            msgId = reportBody.getString("msgId");
            phone = reportBody.getString("phone");
            status = reportBody.getString("status");
        } else {
            reportResult.put("code", 1);
            reportResult.put("msg", "appKey and appId chick fail !");
            LOG.info("----appKey and appId chick fail !");
            return reportResult;
        }
        if (msgId != null && phone != null && status != null) {
            Map queryMap = new HashMap();
            queryMap.put("msgId", msgId);
            queryMap.put("phone", phone);
            try {
                List<LogSignEntity> signEntityList = logSignService.queryList(queryMap);
                if (signEntityList != null && signEntityList.size() == 1){
                    LogSignEntity logSign = signEntityList.get(0);
                    //0：发送成功，1：发送失败
                    if ("0".equals(status)) {
                        logSign.setSmsStatus(1);
                    } else {
                        logSign.setSmsStatus(2);
                    }
                    logSignService.update(logSign);
                    LOG.info("----更新签到表成功，更新内容为：{}", logSign);
                }
            } catch (Exception e) {
                LOG.error("----查询签到列表或更新签到表出现错误，手机号：{}，msgId:{},具体错误为：", phone, msgId, e);
            }
        }
        return reportResult;
    }

}
