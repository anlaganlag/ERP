package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityCertificateSealResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityCertificateSeal;

 /**
 * 资源-公司实体证件印章;(Tb_Com_Entity_Certificate_Seal)--数据库访问层
 * @author : LSY
 * @date : 2023-7-28
 */
@Mapper
public interface TbComEntityCertificateSealMapper  extends MPJBaseMapper<TbComEntityCertificateSeal> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComEntityCertificateSealResult> selectByPage(IPage<TbComEntityCertificateSealResult> page , @Param(Constants.WRAPPER) Wrapper<TbComEntityCertificateSeal> wrapper);
}