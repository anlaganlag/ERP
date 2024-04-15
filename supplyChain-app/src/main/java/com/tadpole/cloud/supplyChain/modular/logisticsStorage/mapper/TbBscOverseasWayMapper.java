package com.tadpole.cloud.supplyChain.modular.logisticsStorage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbBscOverseasWay;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbBscOverseasWayResult;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 发货汇总表;(Tb_Bsc_Overseas_Way)表数据库访问层
 * @author : LSY
 * @date : 2024-1-10
 */
@Mapper
public interface TbBscOverseasWayMapper  extends MPJBaseMapper<TbBscOverseasWay> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbBscOverseasWayResult> selectByPage(IPage<TbBscOverseasWayResult> page , @Param(Constants.WRAPPER) Wrapper<TbBscOverseasWay> wrapper);

    /**
     * 查询并生产发货记录
     * @param packCode
     * @return
     */
     List<TbBscOverseasWay> getBscOverseasWayMapper(String packCode);


     @Update(" MERGE INTO tb_bsc_overseas_way t1 " +
             " USING tb_logistics_list_to_head_route_det t2 " +
             " ON (t1.pack_code = t2.pack_code " +
             " AND t1.pack_det_box_num = t2.pack_det_box_num " +
             " AND t2.lhr_code = #{lhrCode} " +
             " AND t2.lhr_odd_numb = #{lhrOddNumb} ) " +
             "WHEN MATCHED THEN " +
             "UPDATE SET  " +
             " t1.deliver_type = NULL ")
    int updateDeliverTypeIsNull(@Param("lhrCode")String lhrCode,@Param("lhrOddNumb") String lhrOddNumb);

    @Update(" MERGE INTO tb_bsc_overseas_way t1 " +
            " USING tb_logistics_list_to_head_route_det t2 " +
            " ON (t1.pack_code = t2.pack_code " +
            " AND t1.pack_det_box_num = t2.pack_det_box_num " +
            " AND t2.lhr_code = #{lhrCode} " +
            " AND t2.lhr_odd_numb = #{lhrOddNumb} ) " +
            "WHEN MATCHED THEN " +
            "UPDATE SET  " +
            " t1.deliver_type = #{deliverStatus} ")
    int updateDeliverType(@Param("lhrCode")String lhrCode,@Param("lhrOddNumb") String lhrOddNumb,@Param("deliverStatus") String deliverStatus);

    @Update(" MERGE INTO tb_bsc_overseas_way t1 " +
            " USING (" +
            "     SELECT " +
            "         lhr_code, " +
            "         pack_code, " +
            "         lhr_odd_numb " +
            "     FROM " +
            "         tb_logistics_list_to_head_route_det " +
            "     GROUP BY " +
            "         lhr_code, " +
            "         pack_code, " +
            "         lhr_odd_numb) t2 " +
            " ON(t1.pack_code = t2.pack_code " +
            "    AND t2.lhr_code = #{lhrCode} " +
            "    AND t2.lhr_odd_numb = #{lhrOddNumb}) " +
            "WHEN MATCHED THEN " +
            "UPDATE SET " +
            "    t1.deliver_status = #{deliverStatus}")
    int updateDeliverStatus(@Param("lhrCode")String lhrCode,@Param("lhrOddNumb") String lhrOddNumb,@Param("deliverStatus") String deliverStatus);

    @Update(" MERGE INTO tb_bsc_overseas_way t1 " +
            " USING tb_logistics_list_to_head_route_det t2 " +
            " ON (t1.pack_code = t2.pack_code " +
            " AND t1.pack_det_box_num = t2.pack_det_box_num " +
            " AND t2.lhr_code = #{lhrCode} ) " +
            "WHEN MATCHED THEN " +
            "UPDATE SET  " +
            " t1.deliver_type = #{deliverStatus} ")
    int updateDeliverType(@Param("lhrCode")String lhrCode,@Param("deliverStatus") String deliverStatus);

    void updateShipmentRealStatus();

    void updateBscOverseasWayStatus();
}