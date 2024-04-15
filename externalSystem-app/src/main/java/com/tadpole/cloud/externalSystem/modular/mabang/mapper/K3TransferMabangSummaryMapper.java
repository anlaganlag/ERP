package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferMabangSummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* <p>
    * K3调拨单同步概要 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-15
*/

public interface K3TransferMabangSummaryMapper extends BaseMapper<K3TransferMabangSummary> {
    @Select("SELECT a.FNUMBER,b.FNAME FROM T_BD_Stock a,T_BD_STOCK_L b WHERE a.ISSPECIALWAREHOUSE= 1 AND a.FUSEORGID= 100269 AND a.FSTOCKID= b.FSTOCKID")
    List<Map<String, String>> k3MabangWarehouse();

}
