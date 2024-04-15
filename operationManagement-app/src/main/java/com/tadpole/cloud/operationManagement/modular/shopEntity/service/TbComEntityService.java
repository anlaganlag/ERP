package com.tadpole.cloud.operationManagement.modular.shopEntity.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntity;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityResult;
import java.util.List;

 /**
 * 资源-公司实体;(Tb_Com_Entity)--服务接口
 * @author : LSY
 * @date : 2023-7-28
 */
public interface TbComEntityService extends IService<TbComEntity> {

    /**
     * 通过ID查询单条数据
     *
     * @param comNameCn 主键
     * @return 实例对象
     */
    TbComEntityResult queryById(String comNameCn);

    /**
     * 分页查询
     *
     * @param tbComEntity 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbComEntityResult> paginQuery(TbComEntityParam tbComEntity, long current, long size);
    /**
     * 新增数据
     *
     * @param tbComEntityParam 实例对象
     * @return 实例对象
     */
    ResponseData insert(TbComEntityParam tbComEntityParam);
    /**
     * 更新数据
     *
     * @param tbComEntityParam 实例对象
     * @return 实例对象
     */
    ResponseData update(TbComEntityParam tbComEntityParam);
    /**
     * 通过主键删除数据
     *
     * @param comNameCn 主键
     * @return 是否成功
     */
    boolean deleteById(String comNameCn);

    /**
     * 通过主键删除数据--批量删除
     * @param comNameCnList
     * @return
     */
     boolean deleteBatchIds(List<String> comNameCnList);

     Page<TbComEntityResult> paginQueryJoinTable(TbComEntityParam tbComEntity, long current, long size);

     /**
      * 公司名称中文List
      * @return
      */
     List<String> comNameCnList(Boolean isNormal);
     /**
      * 公司名称-地址-法人
      * @return
      */
     List<Object> comNameCnList();
 }
