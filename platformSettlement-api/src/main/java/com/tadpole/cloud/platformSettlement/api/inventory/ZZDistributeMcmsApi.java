package com.tadpole.cloud.platformSettlement.api.inventory;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author: ty
 * @description: K3物料分配入库API
 * @date: 2023/4/14
 */
@RequestMapping("/zZDistributeMcmsApi")
public interface ZZDistributeMcmsApi {

    /**
     * 分配K3物料
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "分配K3物料")
    void add(@RequestBody ZZDistributeMcms param);

    /**
     * 批量分配K3物料
     * @return
     */
    @RequestMapping(value = "/saveMatBatch", method = RequestMethod.POST)
    @ApiOperation(value = "批量分配K3物料")
    void saveMatBatch(@RequestBody List<ZZDistributeMcms> param);
}
