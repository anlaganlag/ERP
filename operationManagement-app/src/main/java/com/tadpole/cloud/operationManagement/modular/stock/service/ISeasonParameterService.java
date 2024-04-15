package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SeasonParameter;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SeasonParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SeasonParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SeasonParameterVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 季节系数参数表 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
public interface ISeasonParameterService extends IService<SeasonParameter> {

    ResponseData add(SeasonParameterParam seasonParameterParam);

    PageResult<SeasonParameterResult> queryList(SeasonParameterVO seasonParameterVO);

    ResponseData batchDeleteById(List<BigDecimal> idList);

    ResponseData update(SeasonParameter seasonParameter);


    List<SeasonParameterResult> exportExcel(SeasonParameterVO param);




    void verifyData (List<SeasonParameter> list,List<String> platList,List<String> teamList,List<String> ptList,List<String> deptList,List<String> brandList,List<String> areaList);






}
