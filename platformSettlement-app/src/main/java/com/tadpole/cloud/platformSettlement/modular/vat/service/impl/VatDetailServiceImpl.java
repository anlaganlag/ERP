package com.tadpole.cloud.platformSettlement.modular.vat.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatDetail;
import com.tadpole.cloud.platformSettlement.modular.vat.enums.GenerateStatusEnum;
import com.tadpole.cloud.platformSettlement.modular.vat.mapper.VatDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatDetailParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatDetailResult;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatShopResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 税金测算VAT明细 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Service
public class VatDetailServiceImpl extends ServiceImpl<VatDetailMapper, VatDetail> implements IVatDetailService {

    @Autowired
    private VatDetailMapper mapper;

    @DataSource(name = "finance")
    @Override
    public ResponseData queryListPage(VatDetailParam param) {
        return ResponseData.success(mapper.queryListPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "finance")
    public VatDetailResult getQuantity(VatDetailParam param) {
        VatDetailResult detailToTal = mapper.listSum(param);
        return detailToTal;
    }

    @Override
    @DataSource(name = "finance")
    public List<VatDetailResult> exportVatDetail(VatDetailParam param) {
        List<VatDetailResult> records = mapper.vatDetailList(param);
        return records;
    }

    @Override
    @DataSource(name = "finance")
    public List<VatShopResult> exportVatShop(VatDetailParam param) {
        List<VatShopResult> records = mapper.vatShopList(param);
        return records;
    }

    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateVatCheck(VatDetailParam param) {
        if(StrUtil.isEmpty(param.getActivityPeriod())){
            return ResponseData.error("期间值不能为空！");
        }

        param.setCreatePerson(LoginContext.me().getLoginUser().getAccount());
        param.setCreatePersonName(LoginContext.me().getLoginUser().getName());
        param.setGenerateStatus(new BigDecimal(GenerateStatusEnum.DEFAULT.getCode()));

        try{

            //生成核对表
            this.baseMapper.generateVatCheck(param);

            //状态变更->已生成
            LambdaUpdateWrapper<VatDetail> updateWrapper=new LambdaUpdateWrapper<>();
            updateWrapper.eq(VatDetail::getActivityPeriod,param.getActivityPeriod())
                    .eq(VatDetail::getGenerateStatus, GenerateStatusEnum.DEFAULT.getCode())
                    .set(VatDetail::getGenerateStatus,GenerateStatusEnum.SUCCESS.getCode())
                    .set(VatDetail::getUpdatePerson,LoginContext.me().getLoginUser().getAccount())
                    .set(VatDetail::getUpdateTime,new Date());
            mapper.update(null,updateWrapper);

            return ResponseData.success("生成成功！");
        }catch(Exception e){
            log.error("生成核对表失败！ ",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("生成失败！");
        }
    }
}
