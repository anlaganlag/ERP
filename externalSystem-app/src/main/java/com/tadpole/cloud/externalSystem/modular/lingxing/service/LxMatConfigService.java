package com.tadpole.cloud.externalSystem.modular.lingxing.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.lingxing.entity.LxMatConfig;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.LxMatConfigParm;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.MatSyncConfigPlan;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.SwitchPlanParm;

import java.util.List;

/**
 * 领星物料配置表;(LX_MAT_CONFIG)表服务接口
 * @author : LSY
 * @date : 2022-12-5
 */
public interface LxMatConfigService{

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LxMatConfig queryById(String id);

    /**
     * 分页查询
     *
     * @param lxMatConfig 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    PageResult<LxMatConfig> paginQuery(LxMatConfigParm lxMatConfig, long current, long size);
    /**
     * 新增数据
     *
     * @param lxMatConfig 实例对象
     * @return 实例对象
     */
    LxMatConfig insert(LxMatConfig lxMatConfig);
    /**
     * 更新数据
     *
     * @param lxMatConfig 实例对象
     * @return 实例对象
     */
    LxMatConfig update(LxMatConfigParm lxMatConfig);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

     /**
      * 获取方案
      * @param planName
      * @return
      */
     List<MatSyncConfigPlan> allPlan(String planName);


     /**
      * 获取物料所有属性
      * @param
      * @return
      */
     ResponseData matAllProperty();

     /**
      * 添加配置
      * @param lxMatConfig
      * @return
      */
     ResponseData add(LxMatConfig lxMatConfig);

    /**
     * 修改配置
     * @param lxMatConfig
     * @return
     */
    ResponseData edit(LxMatConfig lxMatConfig);

    /**
     * 批量方案切换
     * @param switchPlanParm
     * @return
     */
    ResponseData switchPlan(SwitchPlanParm switchPlanParm);
}
