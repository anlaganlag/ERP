package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.entity.VerifInfoRecord;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.VerifInfoRecordMapper;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPurchaseOrdersService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IVerifInfoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* <p>
    * 审核记录信息 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
@Service
public class VerifInfoRecordServiceImpl extends ServiceImpl<VerifInfoRecordMapper, VerifInfoRecord> implements IVerifInfoRecordService {

    @Resource
    private VerifInfoRecordMapper mapper;


    @Autowired
    protected IPurchaseOrdersService purchaseOrdersService;


    @DataSource(name = "stocking")
    public Boolean saveOrUpdateBatch(List<VerifInfoRecord> list) {
        for (VerifInfoRecord model : list){
            LambdaQueryWrapper<VerifInfoRecord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(VerifInfoRecord::getPurchaseOrderId, model.getPurchaseOrderId())
                    .eq(VerifInfoRecord::getVerifBizType, StockConstant.VERIF_BIZ_TYPE_PMC) ;

            List<VerifInfoRecord> extsisList = this.baseMapper.selectList((wrapper));
            if (extsisList != null) {
                //TODO：写法【集合对象属性是否包含某一个值】
                if (extsisList.stream().map(VerifInfoRecord::getPurchaseOrderId).collect(Collectors.toList()).contains(model.getPurchaseOrderId())) {
                    //TODO：写法【集合中获取符合条件的对象】
                    //model.setId(extsisList.stream().filter(f -> f.getPurchaseOrderId() == model.getPurchaseOrderId()).findAny().orElse(null).getId());
                    VerifInfoRecord uModel = extsisList.stream().filter(f -> f.getPurchaseOrderId() == model.getPurchaseOrderId()).findAny().orElse(null);
                    if(uModel!=null)
                        model.setId(uModel.getId());

                    model.setUpdateTime(new Date());
                } else model.setCreateTime(new Date());
            }
            model.setVerifBizType(StockConstant.VERIF_BIZ_TYPE_PMC);
            model.setVerifType("自动");
            model.setVerifDate(new Date());
            model.setVerifPersonNo(this.getUserAccount());
            model.setVerifPersonName(this.getUserName());

        }
        return this.saveOrUpdateBatch(list, 1000);
    }

    @Override
    @DataSource(name = "stocking")
    public ResponseData batchPass(List<String> orderIdList) {
        return null;
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
