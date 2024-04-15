package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockAreaBlacklist;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
    * 区域黑名单 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-12-19
*/
public interface StockAreaBlacklistMapper extends BaseMapper<StockAreaBlacklist> {
    @Select(
            "<script>"+
                "SELECT * FROM STOCK_AREA_BLACKLIST "
                    +"WHERE PLATFORM || AREA  || DEPARTMENT || TEAM || MATERIAL_CODE  IN ("
                    +"<foreach collection='mergeFildList' separator=',' item='mergeFild' >"
                    +"#{mergeFild}"
                    +"</foreach>"
            +")</script>")
    List<StockAreaBlacklist> checkAreaBlacklist(@Param("mergeFildList") List<String> mergeFildList);
}
