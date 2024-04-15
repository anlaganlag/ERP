package com.tadpole.cloud.platformSettlement.modular.vat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseTaxRate;
import com.tadpole.cloud.platformSettlement.modular.vat.mapper.BaseTaxRateMapper;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseTaxRateParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseTaxRateResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseTaxRateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * <p>
 * 基础信息-税率表 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@Service
public class BaseTaxRateServiceImpl extends ServiceImpl<BaseTaxRateMapper, BaseTaxRate> implements IBaseTaxRateService {

    @Resource
    private BaseTaxRateMapper mapper;

    @Override
    @DataSource(name = "finance")
    public Page<BaseTaxRateResult> queryListPage(BaseTaxRateParam param) {
        Page pageContext = param.getPageContext();
        return this.baseMapper.queryListPage(pageContext, param);
    }

    /**
     * @param param
     * @return
     */
    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(BaseTaxRateParam param, String controllerName) {

        BigDecimal versionNumber = null;

        Date bizdate = param.getEffectiveDate();
        LocalDate bizLocalDate = bizdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //期间：当前生效时间的月份减1
        LocalDate activityPeriod = bizLocalDate.minusMonths(1);
        Date period = Date.from(activityPeriod.atStartOfDay(ZoneId.systemDefault()).toInstant());//LocalDate 转为 Date

        //上一个期间的失效日期：当前生效时间的月份减1，最后一天
        LocalDate expirationDate = bizLocalDate.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        Date exDate = Date.from(expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());//LocalDate 转为 Date

        //月份第一天
        LocalDate firstDay = bizLocalDate.with(TemporalAdjusters.firstDayOfMonth());
        Date fDay = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());//生效日期 LocalDate 转为 Date

        //当前同站点且生效数据  维度：站点、状态；
        LambdaQueryWrapper<BaseTaxRate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseTaxRate::getSite, param.getSite());
        wrapper.eq(BaseTaxRate::getIsDelete, 0);
        wrapper.eq(BaseTaxRate::getStatus, 1);
        BaseTaxRate oldModel = this.getOne(wrapper);

        //compareTo->  -1:小于; 0：等于; 1：大于;

        //新增行 生效日期 不能晚于 失效日期
        if (fDay.compareTo(param.getExpirationDate()) != -1) {
            return ResponseData.error(controllerName + "新增失败，原因：生效日期必须 早于 失效日期");
        }

        //新增行 税率大于 0
        if (param.getTaxRate().compareTo(new BigDecimal(0)) != 1) {
            return ResponseData.error(controllerName + "新增失败，原因：汇率必须大于0");
        }

        //验证查询 税金测算VAT明细 是否存在上一个期间未生成 数据
        if (this.baseMapper.queryNotGeneratedPeriod("to_char('" + period + "', 'yyyy-MM')",param.getSite()) > 0) {
            String strPeriod =  DateUtil.format(period, "yyyy-MM");
            return ResponseData.error(controllerName + "新增失败，原因：《税金测算VAT明细》,期间【"+strPeriod+"】--【"+param.getSite()+"】站点存在未生成核对表的数据。");
        }

        //查到了同站点 历史 数据
        if (ObjectUtil.isNotEmpty(oldModel)) {

            //上一个期间 生效日期 对比 本次修改后上一个期间的 失效日期 ；
            if (oldModel.getEffectiveDate().compareTo(exDate) != -1) {
                return ResponseData.error(controllerName + "新增失败，原因：上一个期间的【生效日期】不能 晚于 上一个期间的【失效日期】");
            }
            //上一个期间 生效日期 对比 新增行 生效日期；
            if (oldModel.getEffectiveDate().compareTo(fDay) != -1) {
                return ResponseData.error(controllerName + "新增失败，原因：上一个期间的【生效日期】 不能 晚于 新增行的【生效日期】");
            }
            //上一个期间 失效日期 对比 新增行 生效日期；
            if(exDate.compareTo(fDay) != -1 ){
                return ResponseData.error(controllerName + "新增失败，原因：上一个期间的【失效日期】 不能 晚于 新增行的【生效日期】");
            }

            versionNumber = oldModel.getVersionNumber().add(BigDecimal.valueOf(1));

            //修改记录 失效日期，状态，最后更新时间，最后更新人
            LambdaUpdateWrapper<BaseTaxRate> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(BaseTaxRate::getId, oldModel.getId());
            updateWrapper.set(BaseTaxRate::getExpirationDate, exDate);
            updateWrapper.set(BaseTaxRate::getStatus, 0);
            updateWrapper.set(BaseTaxRate::getUpdateTime, new Date());
            updateWrapper.set(BaseTaxRate::getUpdatePersonNo, LoginContext.me().getLoginUser().getAccount());
            updateWrapper.set(BaseTaxRate::getUpdatePersonName, LoginContext.me().getLoginUser().getAccount());
            this.update(updateWrapper);

        } else {
            versionNumber = BigDecimal.valueOf(1);
        }

        //新增记录
        BaseTaxRate entity = BeanUtil.copyProperties(param, BaseTaxRate.class);
        entity.setCreatePersonNo(LoginContext.me().getLoginUser().getAccount());
        entity.setCreatePersonName(LoginContext.me().getLoginUser().getName());
        entity.setVersionNumber(versionNumber);
        entity.setEffectiveDate(fDay);

        if (!this.save(entity)) {
            return ResponseData.error(controllerName + "新增失败，原因：数据存储异常！");
        }
        return ResponseData.success();
    }

    /**修改逻辑 提供用户修改字段：站点、税率、生效日期、生效日期
     * 保存验证：
     *      税率： 大于 0
     *      生效日期  比较 税金测算VAT明细-->站点，已生成，最大期间
     *          大于：允许；
     *      修改行：生效日期 小于 失效日期
     *      修改行：生效日期 大于 同站点最大失效日期。
     *      只能修改：状态为 【生效】 的数据(前端控制)。
     * @param param
     * @return
     */
    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData update(BaseTaxRateParam param, String controllerName) {

        Date bizdate = param.getEffectiveDate();
        LocalDate bizLocalDate = bizdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //月份第一天
        LocalDate firstDay = bizLocalDate.with(TemporalAdjusters.firstDayOfMonth());
        Date fDay = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());//生效日期 LocalDate 转为 Date

        //修改行 汇率大于 0
        if (param.getTaxRate().compareTo(new BigDecimal(0)) != 1) {
            return ResponseData.error(controllerName + "修改失败，原因：汇率必须 大于 0");
        }

        //修改行 生效日期 不能晚于 失效日期
        if (fDay.compareTo(param.getExpirationDate()) != -1) {
            return ResponseData.error(controllerName + "修改失败，原因：生效日期必须 早于 失效日期");
        }

        // 生效日期 大于 同站点最大失效日期。
        // 可以多加一个 VERSION_NUMBER 字段条件用来确保是 本站点的上一条生效数据
        // 已经做了排倒序所以没加字段验证
        BaseTaxRate oldModel  =  new LambdaQueryChainWrapper<>(mapper)
                .eq(BaseTaxRate::getSite, param.getSite())
                .eq(BaseTaxRate::getIsDelete,0)
                .eq(BaseTaxRate::getStatus,0)
                .orderByDesc(BaseTaxRate::getExpirationDate)
                .list().stream().findFirst().orElse(null);

        if(ObjectUtil.isNotEmpty(oldModel) && param.getEffectiveDate().compareTo(oldModel.getExpirationDate()) != 1){
            return ResponseData.error(controllerName + "修改失败，原因：生效日期必须 大于 同站点最大失效日期");
        }

        //生效日期  比较 税金测算VAT明细
        Date maxPeriodDate =  this.baseMapper.queryGeneratedMaxPeriod(param.getSite());
        if(ObjectUtil.isNotEmpty(maxPeriodDate) && param.getEffectiveDate().compareTo(maxPeriodDate) != 1){
            return ResponseData.error(controllerName + "修改失败，原因：检测到《税金测算VAT明细》【"+param.getSite()+"】站点，生效日期中存在已生成数据");
        }

        //修改历史失效状态：失效日期
        if(ObjectUtil.isNotEmpty(oldModel)){
            //上一个期间的失效日期：当前生效时间的月份减1，最后一天
            LocalDate expirationDate = bizLocalDate.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
            Date exDate = Date.from(expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());//LocalDate 转为 Date

            LambdaUpdateWrapper<BaseTaxRate> uWrapper = new LambdaUpdateWrapper<>();
            uWrapper.eq(BaseTaxRate::getId, oldModel.getId());
            uWrapper.set(BaseTaxRate::getExpirationDate, exDate);
            uWrapper.set(BaseTaxRate::getUpdateTime, new Date());
            uWrapper.set(BaseTaxRate::getUpdatePersonNo, LoginContext.me().getLoginUser().getAccount());
            uWrapper.set(BaseTaxRate::getUpdatePersonName, LoginContext.me().getLoginUser().getAccount());
            if(!this.update(uWrapper)) {
                return ResponseData.error(controllerName + "新增失败，原因：数据存储异常！");
            }
        }

        //修改现有启用状态数据
        LambdaUpdateWrapper<BaseTaxRate> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BaseTaxRate::getId, param.getId());
        updateWrapper.set(BaseTaxRate::getSite, param.getSite());
        updateWrapper.set(BaseTaxRate::getTaxRate,param.getTaxRate());
        updateWrapper.set(BaseTaxRate::getEffectiveDate, param.getEffectiveDate());
        updateWrapper.set(BaseTaxRate::getExpirationDate, param.getExpirationDate());

        updateWrapper.set(BaseTaxRate::getUpdateTime, new Date());
        updateWrapper.set(BaseTaxRate::getUpdatePersonNo, LoginContext.me().getLoginUser().getAccount());
        updateWrapper.set(BaseTaxRate::getUpdatePersonName, LoginContext.me().getLoginUser().getAccount());

        if (!this.update(updateWrapper)) {
            return ResponseData.error(controllerName + "新增失败，原因：数据存储异常！");
        }
        return ResponseData.success();
    }
}
