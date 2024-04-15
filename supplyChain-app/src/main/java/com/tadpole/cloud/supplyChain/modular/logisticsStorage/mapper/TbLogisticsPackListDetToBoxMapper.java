package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListDetToBox;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListDetToBoxResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 亚马逊货件-明细-多少箱每箱装多少;(tb_logistics_pack_list_det_to_box)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsPackListDetToBoxMapper  extends BaseMapper<TbLogisticsPackListDetToBox>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsPackListDetToBoxResult> selectByPage(IPage<TbLogisticsPackListDetToBoxResult> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsPackListDetToBox> wrapper);

    @Update(" DELETE FROM tb_logistics_pack_list_det_to_box " +
            " WHERE ship_det_id IN ( " +
            "     SELECT T.ship_det_id " +
            "     FROM tb_logistics_pack_list_det  tlpld, " +
            "          tb_logistics_pack_list_det_to_box  T " +
            "     WHERE T.ship_det_id = tlpld.ship_det_id " +
            "     AND tlpld.pack_code = #{packCode} )")
    int deleteByPackCode(String packCode);
}