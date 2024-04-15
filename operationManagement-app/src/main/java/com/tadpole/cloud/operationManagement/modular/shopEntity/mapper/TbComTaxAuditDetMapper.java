package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxAuditDetResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxAuditDet;

 /**
 * 资源-税号查账记录明细;(Tb_Com_Tax_Audit_Det)--数据库访问层
 * @author : LSY
 * @date : 2023-7-28
 */
@Mapper
public interface TbComTaxAuditDetMapper  extends BaseMapper<TbComTaxAuditDet>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComTaxAuditDetResult> selectByPage(IPage<TbComTaxAuditDetResult> page , @Param(Constants.WRAPPER) Wrapper<TbComTaxAuditDet> wrapper);
}