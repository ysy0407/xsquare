package com.xsquare.modules.xsquare.common.aspect;

import com.google.gson.Gson;
import com.xsquare.common.utils.DateUtils;
import com.xsquare.common.utils.R;
import com.xsquare.modules.sys.entity.SysUserEntity;
import com.xsquare.modules.xsquare.common.annotation.VipHandleLog;
import com.xsquare.modules.xsquare.entity.LogVipHandleEntity;
import com.xsquare.modules.xsquare.service.LogVipHandleService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 会员操作日志，切面处理类
 *
 * @author yusongyuan
 * @create 2018-02-26 14:20
 **/
@Order(3)
@Aspect
@Component
public class VipHandleAspect {

    @Autowired
    private LogVipHandleService logVipHandleService;

    @Pointcut("@annotation(com.xsquare.modules.xsquare.common.annotation.VipHandleLog)")
    public void logPointCut() {

    }

    @AfterReturning(pointcut="logPointCut()", returning="result")
    public Object after(JoinPoint joinPoint, Object result) throws Throwable {
        R r= (R)result;
        //如果不为0说明这次是失败的,退出日志的记录
        if (Integer.valueOf(r.get("code").toString()) != 0) {
            return result;
        }
        System.out.println(r.toString());
        StringBuffer describe = new StringBuffer();
        String handlePerson = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogVipHandleEntity logVipHandle = new LogVipHandleEntity();
        //请求的参数
        JSONObject params = (JSONObject)joinPoint.getArgs()[0];
        JSONObject handleJson = params.getJSONObject("handle");
        JSONObject newVipCardJson = new JSONObject();
        if (params.containsKey("newVipCard")) {
            newVipCardJson = params.getJSONObject("newVipCard");
        }
        JSONObject vipCardJson = params.getJSONObject("vipCard");
        JSONObject vipUserJson = params.getJSONObject("vipUser");
        //获取操作类型
        int handleType = Integer.valueOf(handleJson.get("type").toString());
        //扣款方式
        String deductionType = vipCardJson.get("deductionType").toString();
        //根据操作类型，添加操作详细描述
        describe.append("会员"+vipUserJson.get("name").toString()+"，");
        switch (handleType) {
            //开卡
            case 6:
                describe.append("开卡缴纳"+vipCardJson.get("price").toString()+"元，");
                break;
            //删卡
            case 7:
                describe.append("删卡时，");
                break;
            //签到扣费
            case 8:
                describe.append("签到扣费，");
                break;
            //手动签到扣费
            case 9:
                describe.append("手动签到扣费时，");
                break;
            //手动扣费
            case 10:
                describe.append("手动扣费时，<br>");
                if ("2".equalsIgnoreCase(deductionType)) {
                    describe.append("扣除金额为"+handleJson.get("deductMoney").toString()+"元，");
                } else if ("3".equalsIgnoreCase(deductionType)) {
                    describe.append("扣除次数为"+handleJson.get("deductNumber").toString()+"次，");
                }
                describe.append("有效期至"+handleJson.get("deductDate").toString()+"，");
                break;
            //充值
            case 11:
                describe.append("充值缴纳"+handleJson.get("getMoney")+"元，<br>");
                describe.append("赠送积分为"+handleJson.get("rechargeIntegral").toString()+"分，");
                if ("2".equalsIgnoreCase(deductionType)) {
                    describe.append("充值金额为"+handleJson.get("rechargeMoney").toString()+"元，");
                } else if ("3".equalsIgnoreCase(deductionType)) {
                    describe.append("充值次数为"+handleJson.get("rechargeNumber").toString()+"次，");
                }
                describe.append("有效期至"+handleJson.get("rechargeDate").toString()+"，");
                break;
            //冻结
            case 12:
                describe.append("会员卡被冻结，");
                break;
            //解冻
            case 13:
                describe.append("解冻时，");
                break;
            //换卡
            case 14:
                describe.append("换卡缴纳金额为"+newVipCardJson.get("price").toString()+"，<br>新卡");
                describe.append("积分为"+newVipCardJson.get("integral").toString()+"分，");
                if ("2".equalsIgnoreCase(newVipCardJson.get("deductionType").toString())) {
                    describe.append("初始金额为"+newVipCardJson.get("balanceMoney").toString()+"元，");
                } else if ("3".equalsIgnoreCase(deductionType)) {
                    describe.append("初始次数为"+newVipCardJson.get("balanceNumber").toString()+"次，");
                }
                describe.append("生效日期为"+newVipCardJson.get("takeEffectDate").toString()+"，");
                describe.append("有效期至"+newVipCardJson.get("effectiveDate").toString()+"，");
                break;
            //修改会员信息
            case 15:
                describe.append("修改会员信息时，");
                break;
            //积分操作
            case 16:
                describe.append("积分操作增加"+handleJson.get("integral").toString()+"分，");
                break;
            //退款处理
            case 17:
                describe.append("退款操作，<br>退款金额为"+handleJson.get("outMoney").toString()+"元，");
                if ("2".equalsIgnoreCase(deductionType)) {
                    describe.append("扣除金额为"+handleJson.get("deductMoney").toString()+"元，");
                } else if ("3".equalsIgnoreCase(deductionType)) {
                    describe.append("扣除次数为"+handleJson.get("deductNumber").toString()+"次，");
                }
                describe.append("有效期至"+handleJson.get("deductDate").toString()+"，");
                break;
        }
        describe.append("<br>操作时积分为"+vipCardJson.get("integral").toString()+"分，");
        if ("2".equalsIgnoreCase(deductionType)) {
            describe.append("操作时余额为"+vipCardJson.get("balanceMoney").toString()+"元，");
        } else if ("3".equalsIgnoreCase(deductionType)) {
            describe.append("操作时次数为"+vipCardJson.get("balanceNumber").toString()+"次，");
        }
        describe.append("操作时有效期至"+vipCardJson.get("effectiveDate").toString());
        //操作类型
        logVipHandle.setHandleType(handleType);
        //会员卡号
        logVipHandle.setVipCardNum(vipCardJson.get("vipCardNum").toString());
        //备注
        if (handleJson.containsKey("note")) {
            logVipHandle.setNote(handleJson.get("note").toString());
        }
        logVipHandle.setHandlePerson(handlePerson);
        logVipHandle.setHandleTime(DateUtils.getTime());
        logVipHandle.setDescribe(describe.toString());
        logVipHandleService.save(logVipHandle);
        return result;
    }

}
