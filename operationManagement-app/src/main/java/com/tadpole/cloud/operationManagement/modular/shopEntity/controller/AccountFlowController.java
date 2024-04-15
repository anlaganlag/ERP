package com.tadpole.cloud.operationManagement.modular.shopEntity.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.constants.AccountMgtEnum;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountFlow;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountFlowParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.AccountFlowResult;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.AccountFlowService;
import io.swagger.annotations.Api;
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
 * 账户流水;(ACCOUNT_FLOW)表控制层
 *
 * @author : LSY
 * @date : 2023-11-10
 */
@Api(tags = "账户流水")
@RestController
@ApiResource(name = "账户流水", path = "/accountFlow")
public class AccountFlowController {
    public final String baseName = "账户流水";
    public final String queryByIdFunName = baseName+"--通过ID查询账户流水";
    public final String paginQueryFunName = baseName+"--分页查询账户流水";
    public final String addFunName = baseName+"--新增账户流水";
    public final String editFunName = baseName+"--更新账户流水";
    public final String exportFunName = baseName+"--按查询条件导出账户流水";
    public final String deleteByIdFunName = baseName+"--通过主键删除账户流水据";
    public final String deleteBatchIdsFunName = baseName+"--通过主键批量删除账户流水";
    public final String depositAmountFunName = baseName + "--预存金额";
    public final String addInFlowFunName = baseName + "--账户充值";
    public final String addOutFlowFunName = baseName + "--账户支出";
    @Resource
    private AccountFlowService accountFlowService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = queryByIdFunName, response = AccountFlow.class)
    @GetResource(name = queryByIdFunName, path = "/queryByid" )
    public ResponseData queryById(String id) {
        return ResponseData.success(accountFlowService.queryById(id));
    }

    /**
     * 分页查询
     *
     * @param accountFlowParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = paginQueryFunName, response = AccountFlow.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName, opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody AccountFlowParam accountFlowParm) {
        //1.分页参数
        Page page = accountFlowParm.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<AccountFlowResult> pageResult = accountFlowService.paginQuery(accountFlowParm, current, size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }

    /**
     * 账户充值
     *
     * @param accountFlow 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = addInFlowFunName, response = AccountFlow.class)
    @PostResource(name = addInFlowFunName, path = "/addInFlow")
    @BusinessLog(title = addInFlowFunName, opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData addInFlow(@RequestBody AccountFlowParam accountFlow) {
        accountFlow.setInOrOut(AccountMgtEnum.FLOW_IN.getCode());
        return accountFlowService.addFlow(accountFlow);
    }

    /**
     * 账户支出
     *
     * @param accountFlow 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = addOutFlowFunName, response = AccountFlow.class)
    @PostResource(name = addOutFlowFunName, path = "/addOutFlow")
    @BusinessLog(title = addOutFlowFunName, opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData addOutFlow(@RequestBody AccountFlowParam accountFlow) {
        accountFlow.setInOrOut(AccountMgtEnum.FLOW_OUT.getCode());
        return accountFlowService.addFlow(accountFlow);
    }


    /**
     * 更新数据
     *
     * @param accountFlow 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = editFunName, response = AccountFlow.class)
    @PostResource(name = editFunName, path = "/update")
    @BusinessLog(title = editFunName, opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody AccountFlowParam accountFlow) {
        AccountFlow result = accountFlowService.update(accountFlow);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }


    /**
     * 导出
     *
     * @param accountFlowParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export")
    @ApiOperation(value = exportFunName, response = AccountFlowResult.class)
    @BusinessLog(title = exportFunName, opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody AccountFlowParam accountFlowParm, HttpServletResponse response) throws IOException {
        //1.分页参数
        long current = 1L;
        long size = Integer.MAX_VALUE;
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<AccountFlowResult> pageResult = accountFlowService.paginQuery(accountFlowParm, current, size);
        List<AccountFlowResult> records = pageResult.getRecords();
        if (Objects.isNull(records) || records.size() == 0) {
            return ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("账户流水.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), AccountFlowResult.class).sheet("账户流水").doWrite(records);
        return ResponseData.success();
    }


    /**
     * 预存金额
     *
     * @param accountFlowParam
     * @return 是否成功
     */
    @ApiOperation(value = depositAmountFunName)
    @PostResource(name = depositAmountFunName, path = "/depositAmount")
    @BusinessLog(title = depositAmountFunName, opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData depositAmount(@RequestBody AccountFlowParam accountFlowParam) {
        accountFlowParam.setBizFlowType(AccountMgtEnum.BIZ_FLOW_TYPE_DEPOSIT_AMOUNT.getName());
        accountFlowParam.setBizDataSources(AccountMgtEnum.BIZ_DATA_SOURCES_operationManagement.getName());
        accountFlowParam.setCurrency("CNY");
        accountFlowParam.setRate(BigDecimal.ONE);
        return accountFlowService.addFlow(accountFlowParam);
    }

}