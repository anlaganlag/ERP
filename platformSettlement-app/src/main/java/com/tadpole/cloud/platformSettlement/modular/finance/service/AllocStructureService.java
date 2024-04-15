package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.AllocStructure;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.AllocStructureResult;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

 /**
 * ;(ALLOC_STRUCTURE)表服务接口
 * @author : LSY
 * @date : 2023-12-19
 */
public interface AllocStructureService extends IService<AllocStructure> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    AllocStructure queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param allocStructure 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<AllocStructureResult> paginQuery(AllocStructureParam allocStructure, long current, long size);

     @DataSource(name = "finance")
     @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
     ResponseData confirm(AllocStructureParam param);

     Boolean isNoComfirm(AllocStructureParam param);

     /**
     * 新增数据
     *
     * @param allocStructure 实例对象
     * @return 实例对象
     */
    AllocStructure insert(AllocStructure allocStructure);
    /** 
     * 更新数据
     *
     * @param allocStructure 实例对象
     * @return 实例对象
     */
    AllocStructure update(AllocStructureParam allocStructure);
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    boolean deleteById(String undefinedId);
        /** 
     * 通过主键删除数据--批量删除
     *
     * @param undefinedIdList 主键List
     * @return 是否成功
     */
    boolean deleteBatchIds(List<String> undefinedIdList);

     ResponseData importExcel(MultipartFile file);

     ResponseData doAllocStructure(AllocStructure allocStructure);

     ResponseData updateBatch(List<AllocStructure> allocStructureList);

     ResponseData fillLastMonAllocRation(AllocStructure param);
 }