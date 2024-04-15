package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PersonAlloc;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.PersonAllocParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.result.PersonAllocResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ;(PERSON_ALLOC)表服务接口
 * @author : LSY
 * @date : 2023-12-19
 */
public interface PersonAllocService extends IService<PersonAlloc> {
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    PersonAlloc queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param personAlloc 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<PersonAllocResult> paginQuery(PersonAllocParam personAlloc, long current, long size);


    /**
     * 新增数据
     *
     * @param personAlloc 实例对象
     * @return 实例对象
     */
    PersonAlloc insert(PersonAlloc personAlloc);
    /** 
     * 更新数据
     *
     * @param personAlloc 实例对象
     * @return 实例对象
     */
    PersonAlloc update(PersonAllocParam personAlloc);
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


    @DataSource(name = "basecloud")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    List<String> getDepart();

    @DataSource(name = "basecloud")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    List<String> getTeam();

    ResponseData initCurrent(List<PersonAlloc> dataList);

    ResponseData importExcel(MultipartFile file);

     List<PersonAlloc> getCurrent();

    HashMap<String, String> getPlatform();


    ResponseData confirm(List<PersonAlloc> personAllocList);

    ResponseData updateBatch(List<PersonAlloc> personAllocList);



}