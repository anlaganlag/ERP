package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SeasonParameter;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SeasonParameterMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SeasonParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SeasonParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISeasonParameterService;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SeasonParameterVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* <p>
    * 季节系数参数表 服务实现类
    * </p>
*
* @author cyt
* @since 2022-07-20
*/
@Service
public class SeasonParameterServiceImpl extends ServiceImpl<SeasonParameterMapper, SeasonParameter> implements ISeasonParameterService {

    @Resource
    private SeasonParameterMapper mapper;

    @Resource
    private ISeasonParameterService service;



    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(SeasonParameterParam seasonParameterParam) {
        SeasonParameter seasonParameter = BeanUtil.copyProperties(seasonParameterParam, SeasonParameter.class);
        seasonParameter.setId(BigDecimal.valueOf(IdWorker.getId()));
        int priorty = this.calcPriority(seasonParameter);
        seasonParameter.setPriority(priorty);

        if (mapper.insert(seasonParameter)>0) {
            return ResponseData.success();
        }
        return ResponseData.error("新增失败") ;
    }



    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public PageResult<SeasonParameterResult> queryList(SeasonParameterVO param) {
        Page pageContext = param.getPageContext();
        Page<SeasonParameterResult> page = this.mapper.queryList(pageContext, param);
        return new PageResult<>(page);
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchDeleteById(List<BigDecimal> idList) {
        if (mapper.deleteBatchIds(idList) == idList.size()) {
            return ResponseData.success();
        }
        return ResponseData.error("删除出现异常");
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData update(SeasonParameter seasonParameter) {
        if (mapper.updateById(seasonParameter)>0) {
            return ResponseData.success();
        }
        return ResponseData.error("更新失败！");
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SeasonParameterResult> exportExcel(SeasonParameterVO param) {
        List<SeasonParameterResult> page = this.baseMapper.exportExcel(param);
        return page;

    }






    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyData (List<SeasonParameter> list, List<String> platList, List<String> teamList, List<String> ptList, List<String> deptList, List<String> brandList,List<String> areaList ) {
        List<SeasonParameter> filteredList = list.stream().filter(i->i.getPlatformName() !=null && i.getArea() != null  && i.getDepartment() != null && i.getTeam() != null && i.getProductType() != null
        && platList.contains(i.getPlatformName() )&& teamList.contains(i.getTeam()) && ptList.contains(i.getProductType() ) && deptList.contains(i.getDepartment()) &&
                areaList.contains(i.getArea())
        ).collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            return;
        }
        filteredList.forEach(i->{i.setId(BigDecimal.valueOf(IdWorker.getId()));i.setCreateBy(this.getUserName());});
        LambdaQueryWrapper<SeasonParameter> wp = new LambdaQueryWrapper<>() ;
        List<SeasonParameter> updateList = new ArrayList<>();
        List<SeasonParameter> insertList = new ArrayList<>();
        for (SeasonParameter seasonParameter : filteredList) {
            wp.eq(SeasonParameter::getPlatformName,seasonParameter.getPlatformName())
                    .eq(SeasonParameter::getDepartment,seasonParameter.getDepartment())
                    .eq(SeasonParameter::getTeam,seasonParameter.getTeam())
                    .eq(SeasonParameter::getArea,seasonParameter.getArea())
                    .eq(SeasonParameter::getProductType,seasonParameter.getProductType());
                   if (ObjectUtil.isEmpty(seasonParameter.getBrand())){
                       wp.isNull(SeasonParameter::getBrand);
                   }else {
                       wp.eq(SeasonParameter::getBrand,seasonParameter.getBrand());
                   }

                    if (ObjectUtil.isEmpty(seasonParameter.getModel())){
                        wp.isNull(SeasonParameter::getModel);
                    }else {
                        wp.eq(SeasonParameter::getModel,seasonParameter.getModel());
                    }

                    if (ObjectUtil.isEmpty(seasonParameter.getFestivalLabel())){
                        wp.isNull(SeasonParameter::getFestivalLabel);
                    }else {
                        wp.eq(SeasonParameter::getFestivalLabel,seasonParameter.getFestivalLabel());
                    }

                    if (ObjectUtil.isEmpty(seasonParameter.getSeason())){
                        wp.isNull(SeasonParameter::getSeason);
                    }else {
                        wp.eq(SeasonParameter::getSeason,seasonParameter.getSeason());
                    }

                    if (ObjectUtil.isEmpty(seasonParameter.getProductName())){
                        wp.isNull(SeasonParameter::getProductName);
                    }else {
                        wp.eq(SeasonParameter::getProductName,seasonParameter.getProductName());
                    }

                  /*  if (ObjectUtil.isEmpty(seasonParameter.getPriority())){
                        wp.isNull(SeasonParameter::getPriority);
                    }else {
                        wp.eq(SeasonParameter::getPriority,seasonParameter.getPriority());
                    }*/

            List exist = service.list(wp);
            wp.clear();
            int priorty = this.calcPriority(seasonParameter);
            seasonParameter.setPriority(priorty);
            if (exist.size()>0) {
                SeasonParameter ent = (SeasonParameter)exist.get(0);
                seasonParameter.setId(ent.getId());
                seasonParameter.setUpdateTime(new Date());

                updateList.add(seasonParameter);
            } else {
                insertList.add(seasonParameter);
            }
        }
        service.updateBatchById(updateList);
        service.saveBatch(insertList);
    }



    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private int calcPriority(SeasonParameter parm) {
        int priorty=1;
        if (StringUtils.isNotEmpty(parm.getPlatformName())) {
            priorty = 1;
        }
        if (StringUtils.isNotEmpty(parm.getDepartment())) {
            priorty = 2;
        }
        if (StringUtils.isNotEmpty(parm.getTeam())) {
            priorty = 3;
        }
        if (StringUtils.isNotEmpty(parm.getArea())) {
            priorty = 4;
        }
        if (StringUtils.isNotEmpty(parm.getProductType())) {
            priorty = 5;
        }
        if (StringUtils.isNotEmpty(parm.getBrand())) {
            priorty =6;
        }
        if (StringUtils.isNotEmpty(parm.getModel())) {
            priorty = 7;
        }
        if (StringUtils.isNotEmpty(parm.getFestivalLabel())) {
            priorty = 8;
        }
        if (StringUtils.isNotEmpty(parm.getSeason()) ) {
            if (!"无".equals(parm.getSeason())) {
                priorty = 9;
            }else {
                parm.setSeason(null);
            }
        }
        if (StringUtils.isNotEmpty(parm.getProductName()) ) {
                priorty = 10;
        }

        return priorty;

    }

    private String getUserName() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getName();
    }

    private String getUserAccount() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getAccount();
    }
}
