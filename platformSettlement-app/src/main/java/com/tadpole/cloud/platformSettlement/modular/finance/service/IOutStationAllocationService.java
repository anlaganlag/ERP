package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.OutStationAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OutStationAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OutStationAllocationResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 站外分摊汇总表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface IOutStationAllocationService extends IService<OutStationAllocation> {

    /**
     * 站外分摊汇总列表
     * @param param
     * @return
     */
    PageResult<OutStationAllocationResult> findPageBySpec(OutStationAllocationParam param);

    /**
     * 导出
     * @param param
     * @return
     */
    List<OutStationAllocationResult> queryList(OutStationAllocationParam param);

    /**
     * 站外分摊汇总导入Excel
     * @param file
     * @param departmentList
     * @param teamList
     * @return
     */
    ResponseData importExcel(MultipartFile file,List<String> departmentList,List<String> teamList);

    /**
     * 修改
     * @param param
     * @return
     */
    ResponseData edit(OutStationAllocationParam param);

    /**
     * 确认
     * @param param
     * @return
     */
    ResponseData confirm(OutStationAllocationParam param);

    /**
     * 删除
     * @param param
     */
    void delete(OutStationAllocationParam param);

    /**
     * 批量确认
     * @param param
     * @return
     */
    ResponseData confirmBatch(OutStationAllocationParam param);

    /**
     * 批量删除
     * @param param
     */
    void deleteBatch(OutStationAllocationParam param);

    /**
     * 获取汇总数量
     * @param param
     * @return
     */
    OutStationAllocationResult getQuantity(OutStationAllocationParam param);
}
