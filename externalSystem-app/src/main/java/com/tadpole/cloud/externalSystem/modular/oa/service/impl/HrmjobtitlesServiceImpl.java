package com.tadpole.cloud.externalSystem.modular.oa.service.impl;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmjobtitlesResult;
import com.tadpole.cloud.externalSystem.config.OaConfig;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.oa.entity.HrmShiftCalendarNormal;
import com.tadpole.cloud.externalSystem.modular.oa.entity.Hrmjobtitles;
import com.tadpole.cloud.externalSystem.modular.oa.mapper.HrmjobtitlesMapper;
import com.tadpole.cloud.externalSystem.modular.oa.service.IHrmjobtitlesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-12
 */
@Service
public class HrmjobtitlesServiceImpl extends ServiceImpl<HrmjobtitlesMapper, Hrmjobtitles> implements IHrmjobtitlesService {

    private static String GET_TOKEN = "/ssologin/getToken";
    private static String CHECK_TOKEN = "/ssologin/checkToken";
    private static String LOGIN_TOKEN = "/wui/index.html";

    @Resource
    private HrmjobtitlesMapper mapper;

    @Autowired
    private OaConfig oaConfig;

    @Autowired
    RestTemplate restTemplate;

    @DataSource(name="OA")
    @Override
    public List<HrmjobtitlesResult> getJobTitle() {
        return this.baseMapper.getJobTitle();
    }

    @Override
    public String ssoLogin() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        //获取token的地址
        String url = oaConfig.getUrl()+GET_TOKEN+"?appid=" + oaConfig.getAppid() +"&loginid="+loginUser.getAccount();

        ResponseEntity<String> restTemplateResult = restTemplate.getForEntity(url,String.class);
        //token
        String code = restTemplateResult.getBody();
        //返回访问地址
        return oaConfig.getUrl()+LOGIN_TOKEN+"?ssoToken="+code;
    }

    @DataSource(name="OA")
    @Override
    public List<HrmShiftCalendarNormal> hrmCalendar(HrmShiftCalendarNormal param) {
        return this.baseMapper.hrmCalendar(param);
    }
}
