package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ApplyConfig;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.ApplyConfigMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ApplyConfigParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ApplyConfigResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IApplyConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
* <p>
*  服务实现类
* </p>
*
* @author cyt
* @since 2022-05-24
*/
@Service
public class ApplyConfigServiceImpl extends ServiceImpl<ApplyConfigMapper, ApplyConfig> implements IApplyConfigService {

    @Autowired
    private ApplyConfigMapper mapper;
    @Autowired
    private IApplyConfigService service;

    @DataSource(name = "warehouse")
    @Override
    public PageResult<ApplyConfigResult> queryListPage(ApplyConfigParam param) {
        Page pageContext = getPageContext();
        IPage<ApplyConfigResult> page = this.baseMapper.list(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "warehouse")
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData add(ApplyConfigParam param) {
        if(StrUtil.isEmpty(param.getApplyName()) || StrUtil.isEmpty(param.getDepartment())){
            return ResponseData.error("流程名称和部门不为空");
        }
        QueryWrapper<ApplyConfig> qw  = new QueryWrapper<>();
        qw.eq("APPLY_NAME",param.getApplyName()).eq("DEPARTMENT",param.getDepartment());
        if ( service.count(qw) >0){
            return ResponseData.error("流程名称和部门与已有数据重复!");
        }

        ApplyConfig applyConfig=new ApplyConfig();
        BeanUtils.copyProperties(param, applyConfig);
        applyConfig.setCreateTime(new Date());
        applyConfig.setCreateBy(LoginContext.me().getLoginUser().getName());
        applyConfig.setAnalysisName(joinList( param.getAnalysisName()));
        applyConfig.setAnalysisAccount(joinList(param.getAnalysisAccount()));
        applyConfig.setFirstSearchName(joinList(param.getFirstSearchName()));
        applyConfig.setFirstSearchAccount(joinList(param.getFirstSearchAccount()));
        applyConfig.setSecondSearchName(joinList(param.getSecondSearchName()));
        applyConfig.setSecondSearchAccount(joinList(param.getSecondSearchAccount()));

        this.save(applyConfig);
        return ResponseData.success();
    }

    @DataSource(name = "warehouse")
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData edit(ApplyConfigParam param) {
        if(param.getId()==null || "0".equals(param.getId().toString())){
            return ResponseData.error("ID不能为空");
        }
        QueryWrapper<ApplyConfig> qw  = new QueryWrapper<>();

        //非当前id的流程名称和部门存在一条,则保存的参数无效,无法修改!
        qw.eq("APPLY_NAME",param.getApplyName()).eq("DEPARTMENT",param.getDepartment()).ne("ID",param.getId());;
        if ( service.count(qw) > 0 ){
            return ResponseData.error("该流程名称和部门已存在!");
        }

        ApplyConfig applyConfig=new ApplyConfig();
        BeanUtils.copyProperties(param, applyConfig);
        applyConfig.setId(param.getId());
        applyConfig.setUpdateTime(new Date());
        applyConfig.setUpdateBy(LoginContext.me().getLoginUser().getName());
        applyConfig.setAnalysisName(joinList( param.getAnalysisName()));
        applyConfig.setAnalysisAccount(joinList(param.getAnalysisAccount()));
        applyConfig.setFirstSearchName(joinList(param.getFirstSearchName()));
        applyConfig.setFirstSearchAccount(joinList(param.getFirstSearchAccount()));
        applyConfig.setSecondSearchName(joinList(param.getSecondSearchName()));
        applyConfig.setSecondSearchAccount(joinList(param.getSecondSearchAccount()));
        this.updateById(applyConfig);
        return ResponseData.success();
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData delete(ApplyConfigParam param) {
        if(param.getId()==null || "0".equals(param.getId().toString())){
            return ResponseData.error("ID不能为空");
        }
        mapper.deleteById(param.getId());
        return ResponseData.success();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private  String joinList(List<String> list){
        if (CollUtil.isNotEmpty(list) ){
            return String.join( "、", list);
        }
        return "";
    }
}
