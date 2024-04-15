package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProductLine2;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProductLine2Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProductLineReplaceUpdateParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 产品线 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-16
 */
public interface IProductLine2Service extends IService<ProductLine2> {

    /**
     * 查询列表页
     *
     * @param param 参数
     * @return {@link ResponseData}
     *//**/
    ResponseData queryListPage(ProductLine2Param param);

    /**
     *
     * @param response
     * @param param
     */
    List<ProductLine2> exportExcel(HttpServletResponse response, ProductLine2Param param);

    /**
     *
     * @param param
     * @return
     */
    ResponseData update(ProductLine2Param param);

    /**
     *批量更新产品线分配
     * @param param
     * @return
     */
//    ResponseData updateBatch(List<ProductLine2Param> param);



    /**
     *批量更新产品线分配
     * @param param
     */
    ResponseData replaceUpdateBatch(ProductLineReplaceUpdateParam param);





    /**
     * 批量新增产品线分配
     * @param paramList
     * @return
     */
    ResponseData insertBatch(List<ProductLine2Param> paramList);

    /**
     * 删除
     * @param id
     * @return
     */
    ResponseData delete(Integer id);

    /**
     * 批量删除
     * @param list
     * @return
     */
    ResponseData deleteBatch(List<Integer> list);

    /**
     *
     * @param param
     * @return
     */
    ResponseData lineAnalysis(ProductLine2Param param);

    /**
     *
     * @param dataList
     * @return
     */
    Boolean saveOrUpdateBatch(List<ProductLine2> dataList) ;

    ProductLine2 getUserDeptTeam(String userNo);
}
