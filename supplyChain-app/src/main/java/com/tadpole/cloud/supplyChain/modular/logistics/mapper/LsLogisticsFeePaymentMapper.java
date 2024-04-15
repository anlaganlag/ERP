package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsFeePayment;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsCompanyBankParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsFeePaymentParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.K3CurrencyResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 物流费付款 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
public interface LsLogisticsFeePaymentMapper extends BaseMapper<LsLogisticsFeePayment> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<LsLogisticsFeePaymentResult> queryPage(@Param("page") Page page, @Param("param") LsLogisticsFeePaymentParam param);

    /**
     * 获取最新的物流付费编号
     * @param orderPre
     * @return
     */
    String getNowLogisticsFeeNo(@Param("orderPre") String orderPre);

    /**
     * 获取物流费付款最新的K3单据编号
     * @param orderPre
     * @return
     */
    String getNowLogisticsPaymentNo(@Param("orderPre") String orderPre);

    /**
     * 组织下拉
     * @return
     */
    List<BaseSelectResult> orgSelect();

    /**
     * 付款类型下拉
     * @return
     */
    List<BaseSelectResult> payTypeSelect();

    /**
     * 结算方式下拉
     * @return
     */
    List<BaseSelectResult> settlementTypeSelect();

    /**
     * 付款用途下拉
     * @return
     */
    List<BaseSelectResult> useTypeSelect();

    /**
     * 物流收款单位下拉
     * @return
     */
    List<BaseSelectResult> lsCompanySelect();

    /**
     * 内部供应商下拉
     * @return
     */
    List<BaseSelectResult> supplierNameSelect();

    /**
     * 对方银行信息下拉
     * @return
     */
    List<LsCompanyBankParam> lsCompanyBankSelect(@Param("param") LsCompanyBankParam param);

    /**
     * K3币别下拉
     * @return
     */
    List<K3CurrencyResult> k3CurrencySelect();

}
