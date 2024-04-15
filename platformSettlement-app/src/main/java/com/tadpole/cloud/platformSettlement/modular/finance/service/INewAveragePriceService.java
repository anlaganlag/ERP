package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.NewAveragePrice;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.NewAveragePriceParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.NewAveragePriceResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 新核算库存平均单价表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface INewAveragePriceService extends IService<NewAveragePrice> {

    PageResult<NewAveragePriceResult> findPageBySpec(NewAveragePriceParam param);

    List<NewAveragePriceResult> queryList(NewAveragePriceParam param);

    ResponseData importExcel(MultipartFile file);

    void edit(NewAveragePriceParam param);

    void confirm(NewAveragePriceParam param);

    void delete(NewAveragePriceParam param);

    void confirmBatch(NewAveragePriceParam param);

    void deleteBatch(NewAveragePriceParam param);
}
