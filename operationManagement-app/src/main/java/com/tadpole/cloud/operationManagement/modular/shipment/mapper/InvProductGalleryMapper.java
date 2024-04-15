package com.tadpole.cloud.operationManagement.modular.shipment.mapper;

import com.tadpole.cloud.operationManagement.modular.shipment.entity.InvProductGallery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.InvProductGalleryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    *  AmazSKU Mapper 接口
    * </p>
*
* @author lsy
* @since 2023-02-03
*/
public interface InvProductGalleryMapper extends BaseMapper<InvProductGallery> {

    List<InvProductGallery> querySkuDatalimit(@Param("reqVO") InvProductGalleryParam param);
}
