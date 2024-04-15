package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityPortCardLegalResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityPortCardLegal;

 /**
 * 资源-公司实体银行设备口岸卡法人;(Tb_Com_Entity_Port_Card_Legal)--数据库访问层
 * @author : LSY
 * @date : 2023-7-28
 */
@Mapper
public interface TbComEntityPortCardLegalMapper  extends BaseMapper<TbComEntityPortCardLegal>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComEntityPortCardLegalResult> selectByPage(IPage<TbComEntityPortCardLegalResult> page , @Param(Constants.WRAPPER) Wrapper<TbComEntityPortCardLegal> wrapper);
}