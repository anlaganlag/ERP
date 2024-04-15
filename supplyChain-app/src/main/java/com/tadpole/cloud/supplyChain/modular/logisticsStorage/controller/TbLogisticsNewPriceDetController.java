package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsNewPriceDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsNewPriceDetParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsNewPriceDetResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsNewPriceDetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 物流商的价格信息-明细;(tb_logistics_new_price_det)表控制层
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@Api(tags = "物流商的价格信息-明细")
@RestController
@ApiResource(name = "物流商的价格信息-明细", path = "/tbLogisticsNewPriceDet")
public class TbLogisticsNewPriceDetController {
    public final String baseName = "物流商的价格信息-明细";
    public final String queryByIdFunName = baseName + "--通过ID查询物流商的价格信息-明细";
    public final String updateDetUseStatus = baseName + "--通过ID+传参状态——更新价格明细的使用状态";
    public final String paginQueryFunName = baseName + "--分页查询物流商的价格信息-明细";
    public final String addFunName = baseName + "--新增物流商的价格信息-明细";
    public final String editFunName = baseName + "--更新物流商的价格信息-明细";
    public final String exportFunName = baseName + "--按查询条件导出物流商的价格信息-明细";
    public final String deleteByIdFunName = baseName + "--通过主键删除物流商的价格信息-明细据";
    public final String deleteBatchIdsFunName = baseName + "--通过主键批量删除物流商的价格信息-明细";
    public final String queryLogisticsNewPriceDetHistory = baseName + "--物流价格明细历史记录";
    @Resource
    private TbLogisticsNewPriceDetService tbLogisticsNewPriceDetService;

    /**
     * 通过ID查询单条数据
     *
     * @param pkLogpDetId 主键
     * @return 实例对象
     */
    @ApiOperation(value = queryByIdFunName, response = TbLogisticsNewPriceDet.class)
    @GetResource(name = queryByIdFunName, path = "/queryBypkLogpDetId" )
    public ResponseData queryById(BigDecimal pkLogpDetId) {
        return ResponseData.success(tbLogisticsNewPriceDetService.queryById(pkLogpDetId));
    }

    /**
     * 分页查询
     *
     * @param tbLogisticsNewPriceDetParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = paginQueryFunName, response = TbLogisticsNewPriceDet.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName, opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsNewPriceDetParam tbLogisticsNewPriceDetParm) {
        //1.分页参数
        Page page = tbLogisticsNewPriceDetParm.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsNewPriceDetResult> pageResult = tbLogisticsNewPriceDetService.paginQuery(tbLogisticsNewPriceDetParm, current, size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }

    /**
     * 新增数据
     *
     * @param tbLogisticsNewPriceDet 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = addFunName, response = TbLogisticsNewPriceDet.class)
    @PostResource(name = addFunName, path = "/add" )
    @BusinessLog(title = addFunName, opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TbLogisticsNewPriceDet tbLogisticsNewPriceDet) {
        return ResponseData.success(tbLogisticsNewPriceDetService.insert(tbLogisticsNewPriceDet));
    }

    /**
     * 更新数据
     *
     * @param tbLogisticsNewPriceDet 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = editFunName, response = TbLogisticsNewPriceDet.class)
    @PostResource(name = editFunName, path = "/update" )
    @BusinessLog(title = editFunName, opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbLogisticsNewPriceDetParam tbLogisticsNewPriceDet) {
        return tbLogisticsNewPriceDetService.update(tbLogisticsNewPriceDet);
    }

    /**
     * 通过主键删除数据
     *
     * @param pkLogpDetId 主键
     * @return 是否成功
     */
    @ApiOperation(value = deleteByIdFunName)
    @GetResource(name = deleteByIdFunName, path = "/deleteById" )
    @BusinessLog(title = deleteByIdFunName, opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(BigDecimal pkLogpDetId) {
        if (tbLogisticsNewPriceDetService.deleteById(pkLogpDetId)) {
            return ResponseData.success();
        }
        return ResponseData.error("通过主键删除数据失败");
    }

    /**
     * 批量删除数据
     *
     * @param pkLogpDetIdList 主键List集合
     * @return 是否成功
     */
    @ApiOperation(value = deleteBatchIdsFunName)
    @GetResource(name = deleteBatchIdsFunName, path = "/deleteBatchIds" )
    @BusinessLog(title = deleteBatchIdsFunName, opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatchIds(@RequestBody List<BigDecimal> pkLogpDetIdList) {
        if (Objects.isNull(pkLogpDetIdList) || pkLogpDetIdList.isEmpty()) {
            return ResponseData.error("主键List不能为空");
        }
        return ResponseData.success(tbLogisticsNewPriceDetService.deleteBatchIds(pkLogpDetIdList));
    }

    /**
     * 导出
     *
     * @param tbLogisticsNewPriceDetParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export" )
    @ApiOperation(value = exportFunName, response = TbLogisticsNewPriceDetResult.class)
    @BusinessLog(title = exportFunName, opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsNewPriceDetParam tbLogisticsNewPriceDetParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsNewPriceDetResult> pageResult = tbLogisticsNewPriceDetService.paginQuery(tbLogisticsNewPriceDetParm, current, size);
        List<TbLogisticsNewPriceDetResult> records = pageResult.getRecords();
        if (Objects.isNull(records) || records.size() == 0) {
            return ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流商的价格信息-明细.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsNewPriceDetResult.class).sheet("物流商的价格信息-明细").doWrite(records);
        return ResponseData.success();
    }


    /**
     * 通过ID+状态  更新价格明细的使用状态
     *
     * @param pkLogpDetId 主键
     * @param busLogpDetUseStatus 状态：已启用,已禁用
     * @return 实例对象
     */
    @ApiOperation(value = updateDetUseStatus, response = TbLogisticsNewPriceDet.class)
    @GetResource(name = updateDetUseStatus, path = "/updateDetUseStatus" )
    public ResponseData updateDetUseStatus(BigDecimal pkLogpId,BigDecimal pkLogpDetId,String busLogpDetUseStatus) {
        return tbLogisticsNewPriceDetService.updateDetUseStatus(pkLogpId,pkLogpDetId,busLogpDetUseStatus);
    }

}