package com.xsquare.modules.xsquare.common.aspect;

import com.xsquare.common.utils.DateUtils;
import com.xsquare.common.utils.R;
import com.xsquare.modules.sys.entity.SysUserEntity;
import com.xsquare.modules.xsquare.entity.LogMoneyEntity;
import com.xsquare.modules.xsquare.service.LogMoneyService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName
 * @Description 金钱日志，切面处理类
 * @Author YuSongYuan
 * @Create 2018-03-24 12:45
 * @Package_name com.xsquare.modules.xsquare.common.aspect
 * @Project_name renren-fast
 * @Since JDK 1.8
 **/
@Order(2)
@Aspect
@Component
public class MoneyAspect {

    @Autowired
    private LogMoneyService logMoneyService;

    @Pointcut("@annotation(com.xsquare.modules.xsquare.common.annotation.MoneyLog)")
    public void logPointCut() {

    }

    @AfterReturning(pointcut="logPointCut()", returning="result")
    public Object after(JoinPoint joinPoint, Object result) throws Throwable {
        R r= (R)result;
        //如果为0说明这次是成功的,日志记录
        if (Integer.valueOf(r.get("code").toString()) != 0) {
            return result;
        }
        //金钱日志对象
        LogMoneyEntity logMoney = new LogMoneyEntity();
        //操作人姓名
        String handlePerson = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getName();
        logMoney.setHandlePerson(handlePerson);
        logMoney.setHandleTime(DateUtils.getTime());
        //请求的参数
        JSONObject params = (JSONObject)joinPoint.getArgs()[0];
        //请求的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        JSONObject handleJson = params.getJSONObject("handle");
        JSONObject vipCardJson = params.getJSONObject("vipCard");
        JSONObject vipUserJson = params.getJSONObject("vipUser");
        JSONObject newVipCardJson = new JSONObject();
        if (params.containsKey("newVipCard")) {
            newVipCardJson = params.getJSONObject("newVipCard");
        }
        //获取操作类型
        int handleType = Integer.valueOf(handleJson.get("type").toString());
        switch (handleType) {
            //开卡
            case 6:
                //收入
                logMoney.setMoneyType(1);
                //金额，收入为正数
                logMoney.setMoneyNumber(Double.valueOf(vipCardJson.get("price").toString()));
                logMoney.setDescribe("开卡收入");
                break;
            //充值
            case 11:
                //收入
                logMoney.setMoneyType(1);
                //金额，收入为正数
                logMoney.setMoneyNumber(Double.valueOf(handleJson.get("getMoney").toString()));
                logMoney.setDescribe("充值收入");
                break;
            //换卡
            case 14:
                //收入
                logMoney.setMoneyType(1);
                //金额，收入为正数
                logMoney.setMoneyNumber(Double.valueOf(newVipCardJson.get("price").toString()));
                logMoney.setDescribe("换卡收入");
                break;
            //退款处理
            case 17:
                //支出
                logMoney.setMoneyType(0);
                //金额，支出为负数
                logMoney.setMoneyNumber(Double.valueOf("-"+handleJson.get("outMoney").toString()));
                logMoney.setDescribe("退款支出");
                break;
            default:
                return result;
        }
        //保存
        logMoneyService.save(logMoney);
        return result;
    }

}
