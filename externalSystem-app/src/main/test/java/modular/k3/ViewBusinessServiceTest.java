package modular.k3;

import com.tadpole.cloud.externalSystem.ExternalSystemApplication;
import com.tadpole.cloud.externalSystem.modular.k3.service.ViewBusinessService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: Amte Ma
 * @version: 1.0
 * @date: 2023/10/20 <br>
 */
@Ignore
@SpringBootTest(classes = ExternalSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ViewBusinessServiceTest {
    @Autowired
    ViewBusinessService viewBusinessService;

    @Test
    public void  getAgencysTest(){
        System.out.println(viewBusinessService.getAgencys());
    }
}
