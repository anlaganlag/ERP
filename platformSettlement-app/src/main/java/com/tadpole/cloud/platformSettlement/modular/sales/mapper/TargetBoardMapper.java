package com.tadpole.cloud.platformSettlement.modular.sales.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.TargetBoard;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.TargetBoardParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.TargetBoardResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2022-03-08
 */
public interface TargetBoardMapper extends BaseMapper<TargetBoard> {

  /**
   * 目标看板列表
   */
  List<TargetBoardResult> list(@Param("paramCondition") TargetBoardParam paramCondition);

  /**
   * 目标看板合计
   */
  TargetBoardResult listSum(@Param("paramCondition") TargetBoardParam paramCondition);

  List<TargetBoardResult> listInv(@Param("paramCondition") TargetBoardParam paramCondition);

  //取ebms事业部team
  List<String> departmentTeam();

  //取ebms平台
  List<String> platformList();
}
