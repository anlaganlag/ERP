package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PersonAlloc;
import com.tadpole.cloud.platformSettlement.modular.finance.model.result.PersonAllocResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ;(PERSON_ALLOC)表数据库访问层
 *
 * @author : LSY
 * @date : 2023-12-19
 */
@Mapper
public interface PersonAllocMapper extends BaseMapper<PersonAlloc> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<PersonAllocResult> selectByPage(IPage<PersonAllocResult> page, @Param(Constants.WRAPPER) Wrapper<PersonAlloc> wrapper);

    List<Map<String, Object>> getPlatform();

    List<PersonAlloc> getCurrent();


    void updatePlatDept3(@Param("dur") Date dur);


    List<String> getDepart();
    List<String> getTeam();
}