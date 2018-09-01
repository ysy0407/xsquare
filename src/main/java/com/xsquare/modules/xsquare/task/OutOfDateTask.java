package com.xsquare.modules.xsquare.task;

import com.xsquare.common.utils.DateUtils;
import com.xsquare.modules.xsquare.entity.TeacherEntity;
import com.xsquare.modules.xsquare.entity.VipCardEntity;
import com.xsquare.modules.xsquare.service.TeacherService;
import com.xsquare.modules.xsquare.service.VipCardService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName
 * @Description 定时器任务，每天0点对过期的会员卡和老师进行修改状态
 * @Author YuSongYuan
 * @Create 2018-03-25 15:38
 * @Package_name com.xsquare.modules.xsquare.task
 * @Project_name renren-fast
 * @Since JDK 1.8
 **/
@Component
public class OutOfDateTask {

    private final Logger logger = LoggerFactory.getLogger(OutOfDateTask.class);

    @Autowired
    private VipCardService vipCardService;
    @Autowired
    private TeacherService teacherService;

//    @Scheduled(cron="*/50 * * * * ?")
//    @Scheduled(cron="0 0 1 * * ?") //每天,1点0分执行 测试
    @Scheduled(cron="0 0 1 * * ?") //每天,1点0分执行 生产
    public void outOfDateTaskContent() {
        logger.info("---------outOfDateTaskContent");
        //查询状态正常的会员卡信息
        Map<String, Object> vipCardParam = new HashMap<>();
        vipCardParam.put("vipCardStatus", 1);
        List<VipCardEntity> vipCardList = vipCardService.queryList(vipCardParam);
        for (VipCardEntity vipCard : vipCardList) {
            System.out.println(vipCard.toString());
            //确定是否过期
            if (isOutOfDate(vipCard.getEffectiveDate())) {
                vipCard.setStatus("3");
                try {
                    vipCardService.update(vipCard);
                } catch (Exception e) {
                    logger.error("定时器修改会员卡过期状态 失败，会员卡号："+vipCard.getVipCardNum()+",会员姓名："+vipCard.getVipUser().getName());
                    e.printStackTrace();
                }
                logger.info("定时器修改会员卡过期状态 成功，会员卡号："+vipCard.getVipCardNum()+",会员姓名："+vipCard.getVipUser().getName());
            }
        }
        //查询状态正常且有离职时间的老师
        Map<String, Object> teacherParam = new HashMap<>();
        teacherParam.put("status", 1);
        List<TeacherEntity> teacherList = teacherService.queryList(teacherParam);
        for (TeacherEntity teacher : teacherList) {
            System.out.println(teacher.toString());
            //当老师有离职时间
            if (teacher.getLeaveDate() != null && "".equals(teacher.getLeaveDate())) {
                if (isOutOfDate(teacher.getLeaveDate())) {
                    teacher.setStatus(0);
                    try {
                        teacherService.update(teacher);
                    } catch (Exception e) {
                        logger.error("定时器修改老师离职状态 失败,老师姓名:"+teacher.getName());
                        e.printStackTrace();
                    }
                    logger.info("定时器修改老师离职状态 成功,老师姓名:"+teacher.getName());
                }
            }
        }

    }

    //根据当前时间和指定的过期时间确定是否过期
    private boolean isOutOfDate(String outOfDate){
        boolean isOutOfDate = false;
        //将指定的时间-现在时间
        long mill = DateUtils.getNowDatePoorMill(outOfDate+" 23:59:59", new Date());
        //当毫秒值小于0时，说明已经过期
        if (mill < 0) {
            isOutOfDate = true;
        }
        return isOutOfDate;
    }

}
