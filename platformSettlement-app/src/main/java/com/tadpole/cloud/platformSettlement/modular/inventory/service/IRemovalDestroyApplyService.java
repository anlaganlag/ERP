package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalDestroyApply;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyApplyParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyBaseApplyParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalDestroyApplyResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyt
 * @since 2022-05-24
 */
public interface IRemovalDestroyApplyService extends IService<RemovalDestroyApply> {

    /**
     * 库存销毁申请列表接口
     */
    PageResult<RemovalDestroyApplyResult> findPageBySpec(RemovalDestroyApplyParam param);

    /**
     * 库存销毁申请列表合计接口
     */
    RemovalDestroyApplyResult getQuantity(RemovalDestroyApplyParam param);

    /**
     * 库存移除申请导入接口
     */
    ResponseData importRemovalApply(RemovalDestroyBaseApplyParam param, MultipartFile file);

    /**
     * 库存批量移除导入Excel
     */
    ResponseData uploadBatchRemoval(RemovalDestroyBaseApplyParam param, MultipartFile file);

    /**
     * 库存销毁申请导入接口
     */
    ResponseData importDestroyApply(RemovalDestroyBaseApplyParam param,MultipartFile file);

    /**
     * 库存销毁申请导出接口
     */
    List<RemovalDestroyApplyResult> export(RemovalDestroyApplyParam param);

    /**
     * 库存销毁申请编辑接口
     */
    ResponseData edit(RemovalDestroyApplyParam param);

    /**
     * 库存销毁基础信息修改接口
     */
    ResponseData baseSelect(RemovalDestroyBaseApplyParam param);

    /**
     * 库存销毁申请提交接口
     */
    ResponseData submit(RemovalDestroyBaseApplyParam param);

    /**
     * 库存销毁申请删除接口
     */
    ResponseData delete(RemovalDestroyApplyParam param);

    /**
     * 库存移除销毁驳回接口
     */
    ResponseData reject(RemovalDestroyBaseApplyParam param);

    /**
     * 库存销毁申请刷listing接口
     */
    void updateDetailList() throws IOException;

    void updateFileDetailList() throws IOException;

    /**
     * 库存销毁申请明细接口
     */
    List<RemovalDestroyApplyResult> getApplyDetail(RemovalDestroyApplyParam param);

    /**
     * 事业部下拉接口
     */
    List<Map<String, Object>> getDepartmentSelect();

}
