package com.tadpole.cloud.operationManagement.modular.stock.verify;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.BaseSelectConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.entity.EntMaterialLogistics;
import com.tadpole.cloud.operationManagement.modular.stock.service.MaterialLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialLogisticsVerifyHandler implements IExcelVerifyHandler<EntMaterialLogistics> {

  @Autowired
  MaterialLogisticsService materialLogisticslService;

//  @Autowired
//  private RedisTemplate redisTemplate;

  @Autowired
  private BaseSelectConsumer baseSelectConsumer;


  private static final String DEPARTMENTLIST = "DEPARTMENTLIST";
  private static final String TEAMLIST = "TEAMLIST";
  private static final String AREALIST = "AREALIST";
  private static final String PLATFORMLIST = "PLATFORMLIST";


  @DataSource(name = "stocking")
  @Override
  public ExcelVerifyHandlerResult verifyHandler(EntMaterialLogistics entMaterialLogistics) {

    ExcelVerifyHandlerResult result = new ExcelVerifyHandlerResult(true);
    UpdateWrapper<EntMaterialLogistics> wp = new UpdateWrapper<>();
    result.setSuccess(true);


    if (entMaterialLogistics.getMaterialCode() == null
            || entMaterialLogistics.getDepartment() == null
            || entMaterialLogistics.getTeam() == null
            || entMaterialLogistics.getPlatform() == null
            || entMaterialLogistics.getArea() == null
            || entMaterialLogistics.getLogisticsMode() == null
            || (!entMaterialLogistics.getLogisticsMode().equals("海运-普船")
            && !entMaterialLogistics.getLogisticsMode().equals("空运")
            && !entMaterialLogistics.getLogisticsMode().equals("卡航")
            && !entMaterialLogistics.getLogisticsMode().equals("铁运")
            && !entMaterialLogistics.getLogisticsMode().equals("海运-快船")
            && !entMaterialLogistics.getLogisticsMode().equals("快递"))
    ) {
      result.setSuccess(false);
      result.setMsg("请检查物料编码,事业部,Team,平台,区域为空,物流方式为(海运-普船,空运,卡航,铁运,海运-快船,快递)！");
      return result;
    }

    wp.eq("material_code", entMaterialLogistics.getMaterialCode())
            .eq("platform", entMaterialLogistics.getPlatform())
            .eq("department", entMaterialLogistics.getDepartment())
            .eq("Team", entMaterialLogistics.getTeam())
            .eq("area", entMaterialLogistics.getArea())
            .set("logistics_mode", entMaterialLogistics.getLogisticsMode());


    if (materialLogisticslService.update(wp)) {
      result.setSuccess(false);
      result.setMsg("该记录已存在,并更新了运输方式");
      return result;
    }


    //从redis中验证平台是否合法

//    BoundValueOperations platformListTemplate = redisTemplate.boundValueOps(PLATFORMLIST);
//    if (ObjectUtil.isNotEmpty(platformListTemplate.get())) {
//      List<Map> platformList = JSONUtil.toList(JSONUtil.parseArray(platformListTemplate.get().toString()), Map.class);
      List<String> platformList = baseSelectConsumer.getPlatform();
      Boolean isPlatformValid = Boolean.FALSE;
      for (String platform : platformList) {
        //如果是Map存在合法的平台,则isPlatformValid有效的
        if (platform.equals(entMaterialLogistics.getPlatform())) {
          isPlatformValid = Boolean.TRUE;
          break;
        }
      }
      if (!isPlatformValid) {
        result.setSuccess(false);
        result.setMsg("平台数据无效！");
        return result;

      }
//    }


    //从redis中验证事业部是否合法
//    BoundValueOperations departmentListTemplate = redisTemplate.boundValueOps(DEPARTMENTLIST);
//    if (ObjectUtil.isNotEmpty(departmentListTemplate.get())) {
      List<String> departmentList = baseSelectConsumer.getDepartment();


//      List<Map> departmentList = JSONUtil.toList(JSONUtil.parseArray(Objects.requireNonNull(departmentListTemplate.get()).toString()), Map.class);
      Boolean isDepartmentValid = Boolean.FALSE;
      for (String department : departmentList) {
        //如果是Map存在合法的平台,则isPlatformValid有效的
//        String s1 = department.get("Department").toString();
//        String s2 = entMaterialLogistics.getDepartment();
        if (department.equals(entMaterialLogistics.getDepartment())) {
          isDepartmentValid = Boolean.TRUE;
          break;
        }
      }

      if (!isDepartmentValid) {
        result.setSuccess(false);
        result.setMsg("事业部数据无效！");
        return result;

      }
//    }


    //从redis中验证team是否合法
//    BoundValueOperations teamListTemplate = redisTemplate.boundValueOps(TEAMLIST);
//    if (ObjectUtil.isNotEmpty(teamListTemplate.get())) {
    List<String> teamList = baseSelectConsumer.getTeam();

//      List<Map> teamList = JSONUtil.toList(JSONUtil.parseArray(Objects.requireNonNull(teamListTemplate.get()).toString()), Map.class);
      Boolean isTeamValid = Boolean.FALSE;
      for (String team : teamList) {
        //如果是Map存在合法的平台,则isPlatformValid有效的
        if (team.equals(entMaterialLogistics.getTeam())) {
          isTeamValid = Boolean.TRUE;
          break;
        }
      }

      if (!isTeamValid) {
        result.setSuccess(false);
        result.setMsg("Team数据无效！");
        return result;

      }
//    }


    //从redis中验证地区是否合法
//    BoundValueOperations areaListTemplate = redisTemplate.boundValueOps(AREALIST);
//
//    if (ObjectUtil.isNotEmpty(areaListTemplate.get())) {

    List<String> areaList = baseSelectConsumer.getArea();

//      List<Map> areaList = JSONUtil.toList(JSONUtil.parseArray(Objects.requireNonNull(areaListTemplate.get()).toString()), Map.class);
      Boolean isAreaValid = Boolean.FALSE;
      for (String area : areaList) {
        //如果是Map存在合法的平台,则isPlatformValid有效的
        if (area.equals(entMaterialLogistics.getArea())) {
          isAreaValid = Boolean.TRUE;
          break;
        }
      }

      if (!isAreaValid) {
        result.setSuccess(false);
        result.setMsg("区域数据无效！");
        return result;

      }
//    }
      return result;
  }
}
