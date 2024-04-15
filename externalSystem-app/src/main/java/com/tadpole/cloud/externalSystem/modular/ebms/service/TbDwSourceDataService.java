package com.tadpole.cloud.externalSystem.modular.ebms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.api.ebms.entity.TbDwSourceData;
import com.tadpole.cloud.externalSystem.modular.ebms.model.param.TbDwSourceDataParam;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwSourceDataResult;

import java.util.List;

 /**
 * TbDWSourceData;(Tb_DW_Source_Data)--服务接口
 * @author : LSY
 * @date : 2023-8-14
 */
public interface TbDwSourceDataService extends IService<TbDwSourceData> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param dwDataObjNum 主键
     * @return 实例对象
     */
    TbDwSourceData queryById(String dwDataObjNum);
    
    /**
     * 分页查询
     *
     * @param tbDwSourceData 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbDwSourceDataResult> paginQuery(TbDwSourceDataParam tbDwSourceData, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbDwSourceData 实例对象
     * @return 实例对象
     */
    TbDwSourceData insert(TbDwSourceData tbDwSourceData);
    /** 
     * 更新数据
     *
     * @param tbDwSourceData 实例对象
     * @return 实例对象
     */
    TbDwSourceData update(TbDwSourceData tbDwSourceData);
    /** 
     * 通过主键删除数据
     *
     * @param dwDataObjNum 主键
     * @return 是否成功
     */
    boolean deleteById(String dwDataObjNum);
    
    /**
     * 通过主键删除数据--批量删除
     * @param dwDataObjNumList
     * @return
     */
     boolean deleteBatchIds(List<String> dwDataObjNumList);


 }