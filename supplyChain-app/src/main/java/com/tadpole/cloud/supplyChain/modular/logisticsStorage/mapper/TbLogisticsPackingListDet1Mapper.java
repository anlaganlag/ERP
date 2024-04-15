package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet1;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackingListDet1Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 出货清单明细1-箱子长宽高重-金蝶+海外仓;(tb_logistics_packing_list_det1)表数据库访问层
 * @author : LSY
 * @date : 2023-12-29
 */
@Mapper
public interface TbLogisticsPackingListDet1Mapper  extends MPJBaseMapper<TbLogisticsPackingListDet1> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbLogisticsPackingListDet1Result> selectByPage(IPage<TbLogisticsPackingListDet1Result> page , @Param(Constants.WRAPPER) Wrapper<TbLogisticsPackingListDet1> wrapper);

    /**
     * 获取通关所需箱件信息
     * @param lhrOddNumb
     * @param packCodeList
     * @return
     */
    List<TbLogisticsPackingListDet1Result> getClearanceBoxInfoData(@Param("lhrOddNumb") String lhrOddNumb, @Param("packCodeList")List<String> packCodeList);

    @Update("UPDATE Tb_logistics_packing_list_det1 " +
            "SET pack_det_box_log_state = '未发货', " +
            "    pack_det_box_plan_state = '未申请', " +
            "    log_tra_mode2 = NULL, " +
            "    pack_det_box_end_log_no = '', " +
            "    pack_det_box_fir_log_no = '' " +
            "WHERE pack_code = (SELECT pack_code FROM Tb_logistics_list_to_head_route_det WHERE lhr_code = #{lhrCode} AND lhr_odd_numb = #{lhrOddNumb}) " +
            "AND pack_det_box_code = (SELECT pack_det_box_code FROM Tb_logistics_list_to_head_route_det WHERE lhr_code = '{lhrCode}' AND lhr_odd_numb = #{lhrOddNumb}) " +
            "AND EXISTS ( " +
            "    SELECT 1  " +
            "    FROM Tb_logistics_list_to_head_route_det  " +
            "    WHERE lhr_code = #{lhrCode}  " +
            "    AND lhr_odd_numb = #{lhrOddNumb} " +
            "    AND Tb_logistics_packing_list_det1.pack_code = Tb_logistics_list_to_head_route_det.pack_code " +
            "    AND Tb_logistics_packing_list_det1.pack_det_box_code = Tb_logistics_list_to_head_route_det.pack_det_box_code " +
            ") ")
    int resetData(@Param("lhrCode")String lhrCode, @Param("lhrOddNumb")String lhrOddNumb);

    @Update("MERGE INTO " +
            "    Tb_Logistics_Packing_List_Det1 T1 " +
            "USING Tb_Logistics_List_To_Head_Route_Det T2 " +
            "ON ( " +
            "        T1.pack_code = T2.pack_code " +
            "    AND T1.pack_det_box_num = T2.pack_det_box_num " +
            "    AND T2.lhr_code = #{lhrCode}                    " +
            ") " +
            "WHEN MATCHED THEN " +
            "UPDATE SET " +
            "    T1.pack_det_box_fir_log_no = #{lhrOddNumb}, " +
            "    T1.pack_det_box_end_log_no = #{lhrOddNumb}, " +
//            "    T1.log_tra_mode1 = #{logTraMode1}, " +
            "    T1.log_tra_mode2 = #{logTraMode2}  ")
    int updateLogNoAndTraMode2(@Param("lhrCode")String lhrCode, @Param("lhrOddNumb")String lhrOddNumb, @Param("logTraMode2")String logTraMode2, @Param("logTraMode1")String logTraMode1);

}