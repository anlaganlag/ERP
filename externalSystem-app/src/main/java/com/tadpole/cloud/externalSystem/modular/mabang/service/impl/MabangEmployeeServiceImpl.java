package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import com.tadpole.cloud.externalSystem.config.MabangConfig;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangEmployee;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangEmployeeMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.BindEmployeeParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.MabangHeadParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.MabangEmployeeService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.utils.HMACSHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* <p>
    * 马帮员工信息 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Service
@Slf4j
public class MabangEmployeeServiceImpl extends ServiceImpl<MabangEmployeeMapper, MabangEmployee> implements MabangEmployeeService {
    @Resource
    private MabangEmployeeMapper mapper;

    @Resource
    IMabangRequstService mabangRequstServic;


    @Autowired
    MabangConfig mabangConfig;

    @Autowired
    RestTemplate restTemplate;


    @DataSource(name = "external")
    @Override
    public ResponseData sync(Integer status) {

        MabangHeadParm mabangHeadParm = new MabangHeadParm("sys-get-employee-list",status);
        MabangResult mabangResult = null;
        try {
            mabangResult = mabangRequstServic.sysGetEmployeeList(mabangHeadParm);

            if (ObjectUtil.isNotNull(mabangResult) && "200".equals(mabangResult.getCode()) && ObjectUtil.isNotNull(mabangResult.getData())) {
                Map<String,Object> dataMap = (Map<String,Object>) mabangResult.getData();
                String employeeListJson = JSON.toJSONString(dataMap.get("data"));
                List<MabangEmployee> employeeList = JSON.parseArray(employeeListJson, MabangEmployee.class);
                this.saveOrUpdateBatch(employeeList);
                return ResponseData.success();
            }

            return ResponseData.error(JSON.toJSONString(mabangResult));

        } catch (Exception e) {
            log.info("同步马帮员工信息异常:"+e.getMessage());
            return ResponseData.error("同步马帮员工信息异常:" + e.getMessage());
        }
    }

    @DataSource(name = "external")
    @Override
    public MabangResult ssoLogin() {

        MabangResult result = new MabangResult();

        LoginUser loginUser = LoginContext.me().getLoginUser();

        //无手机号
        if(loginUser.getPhone()==null || loginUser.getPhone().equals("")){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("loginurl", mabangConfig.getLoginUrl());
            result.setCode("200");
            result.setData(jsonObject);
            return result;
        }

        QueryWrapper<MabangEmployee>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MOBILE",loginUser.getPhone());
        MabangEmployee  employee = this.getOne(queryWrapper);

        //手机号为关联到马帮用户
        if(employee==null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("loginurl", mabangConfig.getLoginUrl());
            result.setCode("200");
            result.setData(jsonObject);
            return result;
        }

        BindEmployeeParam bindEmployeeParam =new BindEmployeeParam();
        bindEmployeeParam.setMbEmployeeId(employee.getEmployeeId());
        bindEmployeeParam.setOuterId(loginUser.getAccount());

        MabangHeadParm mabangHeadParm = new MabangHeadParm("sys-bind-employee-outer-id", bindEmployeeParam);
        if(employee.getJdpin()==null){
            mabangRequstServic.postRequset(mabangHeadParm);
        }
        mabangHeadParm.setApi("sys-do-employee-login");
        result = mabangRequstServic.postRequset(mabangHeadParm);

        return result;
    }

}
