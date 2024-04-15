package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalDestroyApply;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyApplyParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalDestroyApplyResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
*  Mapper 接口
* </p>
*
* @author cyt
* @since 2022-05-24
*/
public interface RemovalDestroyApplyMapper extends BaseMapper<RemovalDestroyApply> {

    /**
     * 库存销毁申请列表接口
     * @param page
     * @param paramCondition
     * @return
     */
    Page<RemovalDestroyApplyResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") RemovalDestroyApplyParam paramCondition);

    RemovalDestroyApplyResult getQuantity(@Param("paramCondition") RemovalDestroyApplyParam paramCondition);

    /**
     * 库存销毁申请导出接口
     * @param paramCondition
     * @return
     */
    List<RemovalDestroyApplyResult> getApplyDetail(@Param("paramCondition") RemovalDestroyApplyParam paramCondition);

    void updateDetailList();

    void updateFileDetailList();

    /**
     * 导入删除库存明细记录
     */
    @Delete("Delete  FROM  REMOVAL_DESTROY_APPLY  WHERE APPLY_CODE = #{applyCode}")
    void deleteByDestroyApply(String applyCode);

    /**
     * 库存驳回原节点状态
     */
    @Select("SELECT REJECT_SUBMIT_NODE  FROM  REMOVAL_DESTROY_BASE_APPLY  WHERE APPLY_CODE = #{applyCode}")
    String selectByRejectStatus(String applyCode);

    /**
     * 库存申请流程编码序号
     */
    @Select("SELECT MAX(SUBSTR(APPLY_CODE,instr(APPLY_CODE,'-',6,1)+1)) FROM  REMOVAL_DESTROY_BASE_APPLY WHERE APPLY_CODE like '%'||#{applyType}||'%'")
    String selectByApplyNo(String applyType);

}
