package com.tadpole.cloud.product.modular.product.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.product.modular.product.entity.RdPssManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.modular.product.model.params.RdPlManageParam;
import com.tadpole.cloud.product.modular.product.model.params.RdPssManageParam;
import com.tadpole.cloud.product.modular.product.model.result.RdPssManageResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 产品同款管理 Mapper接口
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
public interface RdPssManageMapper extends BaseMapper<RdPssManage> {

    List<RdPssManageResult> listPage(@Param("paramCondition")  RdPlManageParam param);

    @Select("SELECT DISTINCT SYS_PRO_NAME FROM RD_PSS_MANAGE")
    List<RdPssManageResult> listProName();

    @Select("SELECT DISTINCT SYS_STYLE FROM RD_PSS_MANAGE")
    List<RdPssManageResult> listStyle();

    @Select("SELECT DISTINCT SYS_MAIN_MATERIAL FROM RD_PSS_MANAGE")
    List<RdPssManageResult> listMainMaterial();

    @Select("SELECT DISTINCT SYS_BRAND FROM RD_PSS_MANAGE")
    List<RdPssManageResult> listBrand();

    @Select("SELECT DISTINCT SYS_MODEL FROM RD_PSS_MANAGE")
    List<RdPssManageResult> listModel();

    @Delete("DELETE FROM RD_PSS_MANAGE WHERE SYS_PL_CODE=#{paramCondition.sysPlCode} AND SYS_PRO_NAME=#{paramCondition.sysProName} and SYS_STYLE=#{paramCondition.sysStyle} and SYS_MAIN_MATERIAL=#{paramCondition.sysMainMaterial} AND SYS_BRAND=#{paramCondition.sysBrand} AND SYS_MODEL=#{paramCondition.sysModel} AND SYS_CUR_APP_VERSION IS NULL")
    int deletePassManage(@Param("paramCondition")  RdPssManageParam param);
}
