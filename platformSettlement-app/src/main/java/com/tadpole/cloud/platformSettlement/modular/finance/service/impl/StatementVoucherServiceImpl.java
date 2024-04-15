package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementVoucherDetail;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.StatementVoucherMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.VoucherDetailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStatementVoucherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* <p>
* 收入记录表凭证 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class StatementVoucherServiceImpl extends ServiceImpl<StatementVoucherMapper, StatementVoucher> implements IStatementVoucherService {

    @DataSource(name = "finance")
    @Override
    public StatementVoucher queryVoucher(StatementVoucher param) {

        QueryWrapper<StatementVoucher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("RECORD_ID",param.getRecordId());
        return this.baseMapper.selectOne(queryWrapper);
    }

    @DataSource(name = "finance")
    @Override
    public List<StatementVoucherDetail> queryVoucherDetail(StatementVoucher param) {
        return this.baseMapper.queryVoucherDetail(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<StatementVoucherDetail> voucherDetailTotal(StatementVoucher param) {
        return this.baseMapper.voucherDetailTotal(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<VoucherDetailResult> getSyncDetail(StatementVoucher param) {
        return this.baseMapper.getSyncDetail(param);
    }

    @DataSource(name = "finance")
    @Override
    public boolean updateById(StatementVoucher voucher) {
        return super.updateById(voucher);
    }


}
