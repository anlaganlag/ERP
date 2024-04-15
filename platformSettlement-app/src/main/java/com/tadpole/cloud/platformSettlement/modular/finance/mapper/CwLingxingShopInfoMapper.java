package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.CwLingxingShopInfo;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LxShopSynRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ty
 * @since 2022-04-29
 */
public interface CwLingxingShopInfoMapper extends BaseMapper<CwLingxingShopInfo> {

    /**
     * 根据同步信息类型获取位同步的店铺信息
     * @param param
     * @return
     */
    List<CwLingxingShopInfo> getLxShopInfoBySynType(@Param("param") LxShopSynRecord param);
}
