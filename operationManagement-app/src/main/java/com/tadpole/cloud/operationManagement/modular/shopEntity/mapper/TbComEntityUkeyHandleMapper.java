package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityUkeyHandleResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityUkeyHandle;

 /**
 * 资源-公司实体银行设备银行UKEY经办信息;(Tb_Com_Entity_Ukey_Handle)--数据库访问层
 * @author : LSY
 * @date : 2023-7-28
 */
@Mapper
public interface TbComEntityUkeyHandleMapper  extends BaseMapper<TbComEntityUkeyHandle>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComEntityUkeyHandleResult> selectByPage(IPage<TbComEntityUkeyHandleResult> page , @Param(Constants.WRAPPER) Wrapper<TbComEntityUkeyHandle> wrapper);
}