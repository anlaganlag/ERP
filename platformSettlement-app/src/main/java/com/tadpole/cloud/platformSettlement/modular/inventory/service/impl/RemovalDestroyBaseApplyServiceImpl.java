package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalDestroyBaseApply;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.RemovalDestroyBaseApplyMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ErpBankAccountParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyBaseApplyParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ApplyConfigResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ErpBankAccountResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemovalDestroyBaseApplyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
    *  服务实现类
    * </p>
*
* @author cyt
* @since 2022-05-25
*/
@Service
public class RemovalDestroyBaseApplyServiceImpl extends ServiceImpl<RemovalDestroyBaseApplyMapper, RemovalDestroyBaseApply> implements IRemovalDestroyBaseApplyService {

    @Autowired
    private RemovalDestroyBaseApplyMapper mapper;

    @DataSource(name = "erpcloud")
    @Override
    public PageResult<ErpBankAccountResult> bankAccountList(ErpBankAccountParam param) {
        Page pageContext = param.getPageContext();

        IPage<ErpBankAccountResult> page = this.baseMapper.bankAccountList(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "warehouse")
    @Override
    public ResponseData processCheck(RemovalDestroyBaseApplyParam param) {
        boolean isCheck=false;
        String account = LoginContext.me().getLoginUser().getAccount();
        if (StringUtils.isEmpty(account)) {
            return ResponseData.error("未获取到当前登录用户信息，请登录后再试！");
        }
        if(StrUtil.isNotEmpty(param.getApplyCode())){
            QueryWrapper<RemovalDestroyBaseApply> qw=new QueryWrapper<>();
            qw.eq("APPLY_CODE",param.getApplyCode());
            RemovalDestroyBaseApply entity=this.getOne(qw);
            if(this.count(qw)>0){
                String applyName=entity.getApplyName();
                String applyDepartment=entity.getApplyDepartment();
                ApplyConfigResult result=baseMapper.getApplyConfig(applyName,applyDepartment);
                //流程申请节点->申请人审批权限
                if(account.equals(entity.getApplyUserAccount()))
                {
                    switch (entity.getAuditStatus()) {
                        case "1":
                            isCheck=true;break;
                    }
                }
                else {
                    if (ObjectUtil.isNotEmpty(result)) {
                        //流程节点审批设置
                        switch (entity.getAuditStatus()) {
                            case "2":
                                //1-1.第一审批人审批,第一审批人抄送
                                if (ObjectUtil.contains(result.getFirstAuditAccount(), account)) {
                                    isCheck = true;
                                } else if (ObjectUtil.contains(result.getFirstSearchAccount(), account)) {
                                    isCheck = false;
                                }
                                break;
                            case "3":
                                //1-2.KCYC-库存移除第二审批人审批
                                if (ObjectUtil.contains(result.getSecondAuditAccount(), account)) { isCheck = true; }
                                break;
                            case "4":
                                //1-2-1.KCXH-库存销毁第二审批人审批
                                if (ObjectUtil.contains(result.getSecondAuditAccount(), account)) { isCheck = true; }
                                break;
                            case "5":
                                //1-3.归档抄送人员
                                if (ObjectUtil.contains(result.getSecondSearchAccount(), account)) { isCheck = false; }
                                break;
                        }

                        //2-1.经营分析人员
                        if (ObjectUtil.contains(result.getAnalysisAccount(), account)) {
                            isCheck = false;
                        }
                    }
                }
                return ResponseData.success(isCheck);
            }
        }
        return ResponseData.success();
    }
}
