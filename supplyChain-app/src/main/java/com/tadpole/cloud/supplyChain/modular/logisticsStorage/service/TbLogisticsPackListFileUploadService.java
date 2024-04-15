package com.tadpole.cloud.supplyChain.modular.logisticsStorage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackListFileUpload;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.TbLogisticsPackListFileUploadResult;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsPackListFileUploadParam;
import java.util.List;
import java.math.BigDecimal;

 /**
 * 亚马逊货件后台生成的excel文件上传信息;(tb_logistics_pack_list_file_upload)表服务接口
 * @author : LSY
 * @date : 2023-12-29
 */
public interface TbLogisticsPackListFileUploadService extends IService<TbLogisticsPackListFileUpload> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkShipId 主键
     * @return 实例对象
     */
    TbLogisticsPackListFileUpload queryById(BigDecimal pkShipId);
    
    /**
     * 分页查询
     *
     * @param tbLogisticsPackListFileUpload 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TbLogisticsPackListFileUploadResult> paginQuery(TbLogisticsPackListFileUploadParam tbLogisticsPackListFileUpload, long current, long size);
    /** 
     * 新增数据
     *
     * @param tbLogisticsPackListFileUpload 实例对象
     * @return 实例对象
     */
    TbLogisticsPackListFileUpload insert(TbLogisticsPackListFileUpload tbLogisticsPackListFileUpload);
    /** 
     * 更新数据
     *
     * @param tbLogisticsPackListFileUpload 实例对象
     * @return 实例对象
     */
    TbLogisticsPackListFileUpload update(TbLogisticsPackListFileUploadParam tbLogisticsPackListFileUpload);
    /** 
     * 通过主键删除数据
     *
     * @param pkShipId 主键
     * @return 是否成功
     */
    boolean deleteById(BigDecimal pkShipId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param pkShipIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<BigDecimal> pkShipIdList);
}