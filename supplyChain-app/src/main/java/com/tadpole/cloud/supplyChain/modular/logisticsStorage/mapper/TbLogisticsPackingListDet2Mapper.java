package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet2;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsShipmentDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet2Result;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbShipemtListClearancModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 出货清单明细2-装箱内容-金蝶+海外仓;(tb_logistics_packing_list_det2)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsPackingListDet2Mapper  extends MPJBaseMapper<TbLogisticsPackingListDet2> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsPackingListDet2Result> selectByPage(IPage<TbLogisticsPackingListDet2Result> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsPackingListDet2> wrapper);

    Integer getMaxBoxNoByPackCode(@Param("packCode") String packCode);

    List<TbLogisticsShipmentDet> getSkuCountQtyByPackCode(@Param("packCode")String packCode);

    /**
     * 更具物流单号以及出货清单号 查询清关数据
     * @param lhrOddNumb
     * @param packCodeList
     * @return
     */
    List<TbShipemtListClearancModel> getClearanceData(@Param("lhrOddNumb") String lhrOddNumb, @Param("packCodeList")List<String> packCodeList);

    @Select("SELECT DISTINCT T1.* " +
            "FROM tb_logistics_packing_list_det2  T1 " +
            "JOIN tb_logistics_list_to_head_route_det  T2 " +
            "    ON T1.pack_code = T2.pack_code " +
            "    AND T1.pack_det_box_code = T2.pack_det_box_code " +
            "    AND T1.pack_det_box_num = T2.pack_det_box_num " +
            "WHERE T2.lhr_code = #{lhrCode}")
    List<TbLogisticsPackingListDet2> queryByLhrCode(@Param("lhrCode")String lhrCode);
}