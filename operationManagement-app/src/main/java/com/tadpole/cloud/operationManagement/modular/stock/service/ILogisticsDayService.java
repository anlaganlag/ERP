package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.LogisticsDay;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsDayParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.LogisticsDayResult;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;


/**
 * <p>
 * 企业信息 服务类
 * </p>
 *
 * @author gal
 * @since 2019-10-10
 */
public interface ILogisticsDayService extends IService<LogisticsDay> {

    /**
     * 查询分页数据，Specification模式
     *
     * @author gal
     * @Date 2021-6-02
     */
    PageResult<LogisticsDayResult> findPageBySpec(LogisticsDayParam param);

    List<LogisticsDayResult> exportExcel(LogisticsDayParam param);
    List<LogisticsDayResult> readySet(List<String> areaList , List<SysDict> dictList, LogisticsDayParam param);



    /**
     * 获取公司详情
     *
     * @return
     * @author gal
     * @date 2019/10/12
     */
    LogisticsDay detail( String id) ;

    /**
     * 新增
     *
     * @author gal
     * @Date 2021-6-02
     */
    void add(LogisticsDayParam param) throws ParseException;
    ResponseData addBatch(List<LogisticsDayParam> param) throws ParseException;





    void insertBatch(List<LogisticsDay> LogisticsDayList) throws ParseException;



    /**
     * 更新
     *
     * @author gal
     * @Date 2021-6-02
     */
    void update(LogisticsDayParam param);



    /**
     * 删除
     *
     * @author gal
     * @Date 2021-6-02
     */
    void delete(LogisticsDayParam param);

    ResponseData deleteBatch(List<String> list);



    Boolean saveOrUpdateBatch(List<LogisticsDay> dataList) ;
    void updateBatch(HashSet<LogisticsDay> dataList) ;





}
