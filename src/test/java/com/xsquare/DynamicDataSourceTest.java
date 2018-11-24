package com.xsquare;


import com.xsquare.datasources.DataSourceTestService;
import com.xsquare.modules.app.entity.UserEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDataSourceTest {
    @Autowired
    private DataSourceTestService dataSourceTestService;



    @Test
    public void test(){
        Byte b1 = new Byte((byte)1);
        if (b1 == (byte)1) {
            System.out.println(1);
        }
        if (b1 != (byte)1) {
            System.out.println(2);
        }
        //数据源1
//        UserEntity user = dataSourceTestService.queryObject(1L);
//        System.out.println(ToStringBuilder.reflectionToString(user));
//
//        //数据源2
//        UserEntity user2 = dataSourceTestService.queryObject2(1L);
//        System.out.println(ToStringBuilder.reflectionToString(user2));
//
//        //数据源1
//        UserEntity user3 = dataSourceTestService.queryObject(1L);
//        System.out.println(ToStringBuilder.reflectionToString(user3));
    }

}
