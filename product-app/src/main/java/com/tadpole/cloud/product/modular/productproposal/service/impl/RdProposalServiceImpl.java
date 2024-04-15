package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.product.api.productproposal.entity.RdProposal;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.productplan.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdProposalMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdProposalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 提案-提案管理 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
@Service
public class RdProposalServiceImpl extends ServiceImpl<RdProposalMapper, RdProposal> implements IRdProposalService {

    @Resource
    private RdProposalMapper mapper;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData addOrEdit(RdProposalParam param){

        try {
            RdProposal model = BeanUtil.copyProperties(param,RdProposal.class);

            Date oprDate = new Date();

            if (param.getSysPageOpr().equals(RdProposalEnum.TA_PAGE_NEW.getName())){
                model.setSysTaCode(generatorNoUtil.getTaBillNoExtents("000", "TA", 3));
            }else {
                model.setSysLDate(oprDate);
            }
            if (this.saveOrUpdate(model)){
                return ResponseData.success("提案创建/编辑成功");
            }else {
                return ResponseData.error("提案创建/编辑失败");
            }
        }catch (Exception ex){
            return ResponseData.error("系统异常");
        }

    }
}
