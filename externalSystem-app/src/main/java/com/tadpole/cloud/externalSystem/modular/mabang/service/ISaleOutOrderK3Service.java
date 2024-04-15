package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrderK3;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.SaleOutOrderK3QueryResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3AvailableResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 根据K3仓库可用数量自动产生-销售出库单 服务类
 * </p>
 *
 * @author lsy
 * @since 2023-04-07
 */
public interface ISaleOutOrderK3Service extends IService<SaleOutOrderK3> {
    /**
     * 生成k3的销售出库单，调用k3销售出库全部扣减掉，扣减成功后保存在MCMS
     * @param availableResultList
     */
    void createK3SaleOutOrder(List<K3AvailableResult> availableResultList);

    /**
     *  生成k3的销售出库单异常情况处理（调用K3接口出现异常）
     */
    void saleOutOrderExceptionHandle(String billNo);

    /**
     * 查询k3系统物料可用数量大于0的
     * @param warehouseList
     * @return
     */
     List<K3AvailableResult> queryK3AvailableQty(List<String> warehouseList);

    /**
     * 更新马帮订单信息
     * @param orderNo
     * @return
     */
    ResponseData orderUpdate(String orderNo);

    /**
     * K3库存成本价
     * @param matCode
     * @return
     */
    ResponseData k3stockPrice(String jsonData);

    ResponseData k3stockPrice2(String jsonData);


    PageResult<SaleOutOrderK3QueryResult> findPageBySpec(SaleOutOrderK3QueryResult param);




}
