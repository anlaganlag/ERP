package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import cn.stylefeng.guns.cloud.system.api.base.model.params.WarehouseSixCodeParam;
import cn.stylefeng.guns.cloud.system.api.base.model.result.WarehouseSixCodeResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SysMaterialParam;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RpMaterialMapper extends BaseMapper<Material> {

    List<String> selectCodeByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectProductTypeByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectProductNameByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectStyleByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectMaterialByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectPatternByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectCompanyBrandByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectBrandByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectModelByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectColorByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectSizeByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectPackingByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectVersionByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    List<String> selectStyleSecondLabelByPerson(@Param("paramCondition") SysMaterialParam paramCondition);

    /**
     * 海外仓六位码查询
     * @param page
     * @param param
     * @return
     */
    Page<WarehouseSixCodeResult> sixCodeListPage(@Param("page") Page page, @Param("paramCondition") WarehouseSixCodeParam param);

    List<String> selectCode();
}
