package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SaftyDaySummary;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SaftyDayValue;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SaftyDaySummaryParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SaftyDaySummaryResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SaftyDaySummaryResultVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SaftyDayVO;

import java.util.List;

/**
 * <p>
 * 安全天数概要表 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
public interface ISaftyDaySummaryService extends IService<SaftyDaySummary> {

    ResponseData add(SaftyDayVO saftyDayVO) throws Exception;

    PageResult<SaftyDaySummaryResultVO> list(SaftyDaySummaryParam summaryParam);

    ResponseData updateStatus(List<Integer> idList, Integer status);

    ResponseData updateValue(List<SaftyDayValue> valueList);

    ResponseData revive(SaftyDayVO saftyDayVO)  throws  Exception;


    List<SaftyDaySummaryResult> exportExcel(SaftyDaySummaryParam param);

}
