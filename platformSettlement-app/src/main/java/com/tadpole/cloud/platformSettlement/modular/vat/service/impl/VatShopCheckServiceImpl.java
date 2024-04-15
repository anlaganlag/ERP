package com.tadpole.cloud.platformSettlement.modular.vat.service.impl;

import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbComShop;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatShopCheck;
import com.tadpole.cloud.platformSettlement.modular.vat.mapper.VatShopCheckMapper;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatShopCheckParam;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatShopCheckService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 账号检查表 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Service
public class VatShopCheckServiceImpl extends ServiceImpl<VatShopCheckMapper, VatShopCheck> implements IVatShopCheckService {

    @DataSource(name = "finance")
    @Override
    public ResponseData queryListPage(VatShopCheckParam param) {
        Page pageContext = param.getPageContext();
        return ResponseData.success(this.baseMapper.queryListPage(pageContext,param));
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData updateRmark(VatShopCheckParam param) {

        UpdateWrapper<VatShopCheck> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId()).set("REMARK",param.getRemark());

        this.baseMapper.update(null,updateWrapper);

        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData shopCheck(List<TbComShop> list) {


        //上个月会计期间
        String lastMonthPeriod = getLastMonth();

        for (TbComShop tbComShop : list) {
            //插入ebms店铺
            tbComShop.setShopColAccBank(lastMonthPeriod);
            this.baseMapper.shopCheckInsert(tbComShop);
        }

        this.baseMapper.shopCheckStatus(lastMonthPeriod);

        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData euShop() {
        return ResponseData.success(this.baseMapper.euShop());
    }

    public static final String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        // 设置为当前时间
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        // 设置为上一个月
        //calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        date = calendar.getTime();
        return format.format(date);
    }
}
