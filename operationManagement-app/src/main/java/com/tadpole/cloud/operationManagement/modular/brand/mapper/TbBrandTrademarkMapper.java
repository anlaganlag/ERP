package com.tadpole.cloud.operationManagement.modular.brand.mapper;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandTrademark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkPageParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkReportPageParam;
import com.tadpole.cloud.operationManagement.api.brand.model.result.TbBrandTrademarkReportResult;
import com.tadpole.cloud.operationManagement.api.brand.model.result.TbBrandTrademarkResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* <p>
* 品牌商标表 Mapper接口
* </p>
*
* @author S20190161
* @since 2023-10-19
*/
public interface TbBrandTrademarkMapper extends BaseMapper<TbBrandTrademark> {

    //获取EBMS字典
    List<Map<String, Object>> getDictionarys(@Param("code")String code);
    Page<TbBrandTrademarkResult> getPage(Page page,@Param("param") TbBrandTrademarkPageParam param);
    void saveDictionary(@Param("code")String code,@Param("className")String className,@Param("tradeName") String tradeName);
    Page<TbBrandTrademarkReportResult> getTradeReportPage(Page page, @Param("param") TbBrandTrademarkReportPageParam param);
    List<TbBrandTrademarkReportResult> getTradeReport(@Param("param") TbBrandTrademarkReportPageParam param);
}
