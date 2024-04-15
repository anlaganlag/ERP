package com.tadpole.cloud.product.modular.productplan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.product.api.productplan.entity.RdPreProposal;
import com.tadpole.cloud.product.api.productplan.entity.RdPreProposalUp;
import com.tadpole.cloud.product.api.productplan.model.params.RdPreProposalParam;
import com.tadpole.cloud.product.api.productplan.model.params.RdPreProposalUpParam;
import com.tadpole.cloud.product.api.productplan.model.result.RdPreProposalUpResult;
import com.tadpole.cloud.product.modular.productplan.enums.RdPreProposalEnum;
import com.tadpole.cloud.product.modular.productplan.mapper.RdPreProposalUpMapper;
import com.tadpole.cloud.product.modular.productplan.service.IRdPreProposalUpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 预提案-改良 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-11
 */
@Service
public class RdPreProposalUpServiceImpl extends ServiceImpl<RdPreProposalUpMapper, RdPreProposalUp> implements IRdPreProposalUpService {

    @Resource
    private RdPreProposalUpMapper mapper;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int add(List<RdPreProposalUpParam> params){

        int result = 0;

        for (RdPreProposalUpParam l:params) {
            try {
                RdPreProposalUp up = BeanUtil.copyProperties(l,RdPreProposalUp.class);
                result = result + this.mapper.insert(up);

            }catch (Exception e){
                result = result - 1;
            }
        }

        if(result != params.size()){
            return 0;
        }else {
            return result;
        }

        //return this.mapper.savaBatch(params);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int delete(RdPreProposalUpParam param){
        QueryWrapper<RdPreProposalUp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_YA_CODE",param.getSysYaCode());
        return this.mapper.delete(queryWrapper);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdPreProposalUpResult> detail(RdPreProposalUpParam param){
        QueryWrapper<RdPreProposalUp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_YA_CODE",param.getSysYaCode());

        List<RdPreProposalUp> list = this.baseMapper.selectList(queryWrapper);
        List<RdPreProposalUpResult> results = new ArrayList<>();

        for (RdPreProposalUp up:list) {
            RdPreProposalUpResult result = BeanUtil.copyProperties(up,RdPreProposalUpResult.class);
            results.add(result);
        }
        return results;
    }

}
