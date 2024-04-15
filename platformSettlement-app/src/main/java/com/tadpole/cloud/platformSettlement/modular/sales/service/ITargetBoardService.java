package com.tadpole.cloud.platformSettlement.modular.sales.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.TargetBoard;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.TargetBoardParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.TargetBoardResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-08
 */
public interface ITargetBoardService extends IService<TargetBoard> {
  /**
   * 目标看板列表接口
   */
  List<TargetBoardResult> listBySpec(TargetBoardParam param);

  //取ebms事业部平台
  List<String> departmentTeam();

  //取ebms平台
  List<String> platformList();

  /**
   * 目标看板列表合计接口
   */
  TargetBoardResult listSum(TargetBoardParam param);

}
