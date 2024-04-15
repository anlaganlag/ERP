package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityCorporateCaCertificateResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityCorporateCaCertificate;

 /**
 * 资源-公司实体银行设备法人CA证书;(Tb_Com_Entity_Corporate_CA_Certificate)--数据库访问层
 * @author : LSY
 * @date : 2023-7-28
 */
@Mapper
public interface TbComEntityCorporateCaCertificateMapper  extends BaseMapper<TbComEntityCorporateCaCertificate>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComEntityCorporateCaCertificateResult> selectByPage(IPage<TbComEntityCorporateCaCertificateResult> page , @Param(Constants.WRAPPER) Wrapper<TbComEntityCorporateCaCertificate> wrapper);
}