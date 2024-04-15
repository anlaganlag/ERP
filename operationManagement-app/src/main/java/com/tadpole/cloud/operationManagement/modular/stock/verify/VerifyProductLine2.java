package com.tadpole.cloud.operationManagement.modular.stock.verify;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.RpMaterialParam;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tadpole.cloud.operationManagement.modular.stock.cache.RedisCache;
import com.tadpole.cloud.operationManagement.modular.stock.constants.SpecialSymbolsConstants;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.BaseSelectConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.EntUserServiceConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.RpMaterialConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProductLine2;
import com.tadpole.cloud.operationManagement.modular.stock.service.IProductLine2Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 针对产品线导入功能呢数据验证类
 * <p>使用时，需要先 调用 init() 方法 进行验证数据初始化</p>
 * <p>使用验证 调用validate（）方法，会进行逐行数据 逐行验证数据</p>
 * By Ly on 2022/6/23.
 *
 * @author Ly
 */
@Component
public class VerifyProductLine2 {

    @Autowired
    private EntUserServiceConsumer entUserServiceConsumer;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;


    @Autowired
    private IProductLine2Service productLine2Service;


    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    private static Map<String, String> userMap = new HashMap<>();
    private static Set<String> userAccountSet = new HashSet<>();
    private static List<String> userNameList = new ArrayList<>();
    private static List<String> productTypeList = new ArrayList<>();
    private static List<String> platformList = new ArrayList<>();
    private static List<String> areaList = new ArrayList<>();
    private static List<String> departmentList = new ArrayList<>();
    private static List<String> teamList = new ArrayList<>();

    private void clearData() {
        userMap.clear();
        userAccountSet.clear();
        userNameList.clear();
        productTypeList.clear();
        platformList.clear();
        areaList.clear();
        departmentList.clear();
        teamList.clear();
    }

    private void fillData() {
        //获取运营部门人员信息
        List<Map<String, Object>> userInfoList = entUserServiceConsumer.getNameSelect();
        userInfoList.stream().forEach(i -> {
            String userCode = String.valueOf(i.get("userCode"));
            String name = String.valueOf(i.get("name"));
            userMap.put(userCode, name);
            userAccountSet.add(userCode);
            userNameList.add(name);
        });


        List<Map<String, Object>> productTypeInfoList = rpMaterialConsumer.getProductTypeSelect(new RpMaterialParam());
        if (CollectionUtil.isNotEmpty(productTypeInfoList)) {
            productTypeInfoList.stream().forEach(i -> {
                productTypeList.add(String.valueOf(i.get("productType")));
            });
        }


        List<Map<String, Object>> departmentInfoList = baseSelectConsumer.getDepartmentSelect();
        if (CollectionUtil.isNotEmpty(departmentInfoList)) {
            departmentInfoList.stream().forEach(i -> {
                departmentList.add(String.valueOf(i.get("department")));
            });
        }

        List<String> platformInfoList = baseSelectConsumer.getPlatform();

        if (CollectionUtil.isNotEmpty(platformInfoList)) {
            platformInfoList.stream().forEach(i -> {
                platformList.add(i);
            });
        }

        List<String> areaInfoList = baseSelectConsumer.getArea();
        if (CollectionUtil.isNotEmpty(areaInfoList)) {
            areaInfoList.stream().forEach(i -> {
                areaList.add(i);
            });
        }

        List<String> teamInfoList = baseSelectConsumer.getTeam();
        if (CollectionUtil.isNotEmpty(teamInfoList)) {
            teamInfoList.stream().forEach(i -> {
                teamList.add(i);
            });
        }
    }

    public void init() {
        clearData();
        fillData();
    }

    /**
     * 产品线导入数据-验证
     *
     * @param lists
     * @return
     */
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData validate(List<ProductLine2> lists) {

        String userAccount = this.getUserAccount();

        //表格导入时 就有重复的数据 检查
        List<String> checkList = lists.stream().map(d -> d.getPlatform() + "|" + d.getArea() + "|" + d.getDepartment() + "|" + d.getTeam() + "|" + d.getProductType()).collect(Collectors.toList());
        TreeSet checkSet = new TreeSet();
        checkSet.addAll(checkList);

        if (checkList.size() != checkSet.size()) {
            checkSet.forEach(cs->checkList.remove(cs));
            TreeSet checkSetResult = new TreeSet();
            checkSetResult.addAll(checkList);
          return   ResponseData.error("表格导入按照[平台,区域,事业部,Team,运营大类]维度有重复的设置,以下维度有重复"+checkSetResult.toString());
        }

        List<ProductLine2> waitDeleteList = new ArrayList<>();


        for (ProductLine2 model : lists) {
            if (validateIsEmpty(model)) {
                return ResponseData.error("请检查列数据是否为空！ 运营大类 平台 区域 事业部 Team 运营人员工号 Team主管工号 事业部经理工号 均不能为空！");
            }

            if (!productTypeList.contains(model.getProductType())) {
                return ResponseData.error("【" + model.getProductType() + "】运营大类数据无效!");
            }

            if (!platformList.contains(model.getPlatform())) {
                return ResponseData.error("【" + model.getPlatform() + "】平台数据无效!");
            }

            if (!departmentList.contains(model.getDepartment())) {
                return ResponseData.error("【" + model.getDepartment() + "】部门数据无效!");
            }

            if (!teamList.contains(model.getTeam())) {
                return ResponseData.error("【" + model.getTeam() + "】Team数据无效!");
            }

            if (!areaList.contains(model.getArea())) {
                return ResponseData.error("【" + model.getArea() + "】区域数据无效！");
            }

            List<String> operatorList = Arrays.asList(model.getOperator().split(SpecialSymbolsConstants.COMMA_EN_STR));
            List<String> operatorNameList = Arrays.asList(model.getOperatorName().split(SpecialSymbolsConstants.COMMA_EN_STR));
            if (operatorList.size() != operatorNameList.size()) {
                return ResponseData.error("【" + operatorList.size() +" "+ operatorNameList.size() + "】运营人员工号和运营人员姓名数量不匹配!");
            }

            //针对一条产品线多 运营人员
            if (operatorList.size() > 1) {
                if (!userAccountSet.containsAll(operatorList)) {
                    return ResponseData.error("【" + operatorList + "】找不到运营人员工号对应的工号信息");
                }

                if (!userNameList.containsAll(operatorNameList)) {
                    return ResponseData.error("【" + operatorNameList + "】找不到运营人员姓名对应的姓名信息!");
                }

                //校验运营人员工号对应的运营人员名字对应关系及顺序
                List<String> finalOperatorList = new ArrayList<>();
                List<String> finalOperatorNameList = new ArrayList<>();
                for (String operator : operatorList) {
                    String operatorName = userMap.get(operator);
                    if (!operatorNameList.contains(operatorName)) {
                        return ResponseData.error("【" + operatorName +operator+ "】运营人员工号对应运营人员姓名不一致!");
                    }

                    finalOperatorList.add(operator);
                    finalOperatorNameList.add(operatorName);
                }

                //设置矫正后运营人员信息
                model.setOperator(StringUtils.join(finalOperatorList, SpecialSymbolsConstants.COMMA_EN_STR));
                model.setOperatorName(StringUtils.join(finalOperatorNameList, SpecialSymbolsConstants.COMMA_EN_STR));
            } else {
                if (!userAccountSet.contains(model.getOperator())) {
                    return ResponseData.error("【" + model.getOperator() + "】找不到运营人员工号对应的信息!");
                }

                if (!model.getOperatorName().equals(userMap.get(model.getOperator()))) {
                    return ResponseData.error("【" + model.getOperator() + "】找不到运营人员姓名对应的信息!");
            }
            }

            List<String> teamSuperviseList = Arrays.asList(model.getTeamSupervise().split(SpecialSymbolsConstants.COMMA_EN_STR));
            List<String> teamSuperviseNameList = Arrays.asList(model.getTeamSuperviseName().split(SpecialSymbolsConstants.COMMA_EN_STR));
            if (teamSuperviseList.size() != teamSuperviseNameList.size()) {
                return ResponseData.error("【" + model.getProductType() + "】Team主管工号和Team主管姓名数量不匹配!");
            }

            //针对一条产品线多 Team主管
            if (teamSuperviseList.size() > 1) {
                if (!userAccountSet.containsAll(teamSuperviseList)) {
                    return ResponseData.error("【" + teamSuperviseList + "】找不到Team主管人员工号对应的工号信息!");
                }

                if (!userNameList.containsAll(teamSuperviseNameList)) {
                    return ResponseData.error("【" + teamSuperviseNameList + "】找不到运营人员姓名对应的姓名信息!");
                }

                //校验 Team主管 工号 的 名字 对应关系及顺序
                List<String> finalOperatorList = new ArrayList<>();
                List<String> finalOperatorNameList = new ArrayList<>();
                for (String operator : teamSuperviseList) {
                    String teamSuperviseName = userMap.get(operator);
                    if (!teamSuperviseNameList.contains(teamSuperviseName)) {
                        return ResponseData.error("【" + teamSuperviseName + "】Team主管 工号 对应 姓名 不一致!");
                    }

                    finalOperatorList.add(operator);
                    finalOperatorNameList.add(teamSuperviseName);
                }

                //设置矫正后运营人员信息
                model.setTeamSupervise(StringUtils.join(finalOperatorList, SpecialSymbolsConstants.COMMA_EN_STR));
                model.setTeamSuperviseName(StringUtils.join(finalOperatorNameList, SpecialSymbolsConstants.COMMA_EN_STR));
            } else {
                if (!userAccountSet.contains(model.getTeamSupervise())) {
                    return ResponseData.error("【" + model.getTeamSupervise() + "】找不到Team主管 工号 对应的信息!");
                }

                if (!model.getTeamSuperviseName().equals(userMap.get(model.getTeamSupervise()))) {
                    return ResponseData.error("【" + model.getTeamSupervise() + "】找不到Team主管 姓名对应的信息!");
            }
            }

            List<String> deptMgrList = Arrays.asList(model.getDeptMgr().split(SpecialSymbolsConstants.COMMA_EN_STR));
            List<String> deptMgrNameList = Arrays.asList(model.getDeptMgrName().split(SpecialSymbolsConstants.COMMA_EN_STR));
            if (deptMgrList.size() != deptMgrNameList.size()) {
                return ResponseData.error("部门经理 工号 和 姓名数量不匹配!");
            }

            if (deptMgrList.size() > 1) {//针对一条产品线多 部门经理
                if (!userAccountSet.containsAll(deptMgrList)) {
                    return ResponseData.error("【" + deptMgrList + "】找不到部门经理工号对应的信息!");
                }

                if (!userNameList.containsAll(deptMgrNameList)) {
                    return ResponseData.error("【" + deptMgrList + "】找不到部门经理姓名对应的信息!");
                }

                //校验部门经理工号对应的名字关系及顺序
                List<String> finalOperatorList = new ArrayList<>();
                List<String> finalOperatorNameList = new ArrayList<>();
                for (String operator : deptMgrList) {
                    String operatorName = userMap.get(operator);
                    if (!deptMgrNameList.contains(operatorName)) {
                        return ResponseData.error("【" +operatorName + "】部门经理 工号 对应 姓名 不一致!");
                    }

                    finalOperatorList.add(operator);
                    finalOperatorNameList.add(operatorName);
                }

                //设置部门经理信息
                model.setDeptMgr(StringUtils.join(finalOperatorList, SpecialSymbolsConstants.COMMA_EN_STR));
                model.setDeptMgrName(StringUtils.join(finalOperatorNameList, SpecialSymbolsConstants.COMMA_EN_STR));
            } else {
                if (!userAccountSet.contains(model.getDeptMgr())) {
                    return ResponseData.error("【" + model.getDeptMgr() + "】找不到部门经理工号对应的信息!");
                }

                if (!model.getDeptMgrName().equals(userMap.get(model.getDeptMgr()))) {
                    return ResponseData.error("【" + model.getDeptMgr() + "】找不到部门经理姓名对应的信息!");
            }
            }


            //导入数据和数据库 有相同维度下的重复数据 需要合并去重处理
            LambdaQueryWrapper<ProductLine2> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProductLine2::getProductType, model.getProductType())
                    .eq(ProductLine2::getPlatform, model.getPlatform())
                    .eq(ProductLine2::getArea, model.getArea())
                    .eq(ProductLine2::getDepartment, model.getDepartment())
                    .eq(ProductLine2::getTeam, model.getTeam());
//                    .eq(ProductLine2::getOperator, model.getOperator())
//                    .eq(ProductLine2::getOperatorName, model.getOperatorName())
//                    .eq(ProductLine2::getTeamSupervise, model.getTeamSupervise())
//                    .eq(ProductLine2::getTeamSuperviseName, model.getTeamSuperviseName())
//                    .eq(ProductLine2::getDeptMgr, model.getDeptMgr())
//                    .eq(ProductLine2::getDeptMgrName, model.getDeptMgr());
//            ProductLine2 result = productLine2Service.getOne(wrapper);
//            if (result != null)
//                model.setId(result.getId());

            List<ProductLine2> productLine2List = productLine2Service.list(wrapper);
            if (ObjectUtil.isNotEmpty(productLine2List)) {

                TreeSet<String> operator = new TreeSet<>();
                TreeSet<String> operatorName = new TreeSet<>();
                TreeSet<String> teamSupervise = new TreeSet<>();
                TreeSet<String> teamSuperviseName = new TreeSet<>();
                TreeSet<String> deptMgr = new TreeSet<>();
                TreeSet<String> deptMgrName = new TreeSet<>();

                for (ProductLine2 pl : productLine2List) {
                    this.splitName(operator, operatorName, teamSupervise, teamSuperviseName, deptMgr, deptMgrName, pl);
                }

                this.splitName(operator, operatorName, teamSupervise, teamSuperviseName, deptMgr, deptMgrName, model);

                if ((operator.size() != operatorName.size()) || operator.size() == 0) {
                    return ResponseData.error("运营工号【" + operator.toString() + "】运营名称：【" + operatorName.toString() + "】名称和工号个数对应不上");
                }
                if ((teamSupervise.size() != teamSuperviseName.size()) || teamSupervise.size() == 0) {
                    return ResponseData.error("Team主管【" + teamSupervise.toString() + "】Team主管名称：【" + teamSuperviseName.toString() + "】名称和工号个数对应不上");
                }
                if ((deptMgr.size() != deptMgrName.size()) || deptMgr.size() == 0) {
                    return ResponseData.error("部门经理【" + deptMgr.toString() + "】部门经理名称：【" + deptMgrName.toString() + "】名称和工号个数对应不上");
                }

                model.setOperator(operator.stream().reduce((o1, o2) -> o1 + "," + o2).get());
                model.setOperatorName(operatorName.stream().reduce((o1, o2) -> o1 + "," + o2).get());
                model.setTeamSupervise(teamSupervise.stream().reduce((o1, o2) -> o1 + "," + o2).get());
                model.setTeamSuperviseName(teamSuperviseName.stream().reduce((o1, o2) -> o1 + "," + o2).get());
                model.setDeptMgr(deptMgr.stream().reduce((o1, o2) -> o1 + "," + o2).get());
                model.setDeptMgrName(deptMgrName.stream().reduce((o1, o2) -> o1 + "," + o2).get());

                ProductLine2 oldProductLine = productLine2List.get(0);
                model.setCreateAt(oldProductLine.getCreateAt());
                model.setCreateBy(oldProductLine.getCreateBy());
                model.setUpdateBy(userAccount);
                model.setUpdateAt(new Date());

                waitDeleteList.addAll(productLine2List);

            } else {
                model.setCreateAt(new Date());
                model.setCreateBy(userAccount);
            }
        }

        if (this.productLine2Service.saveOrUpdateBatch(lists)) {

            if (ObjectUtil.isNotEmpty(waitDeleteList)) {
                List<Integer> idList = waitDeleteList.stream().map(p -> p.getId()).collect(Collectors.toList());
                return   this.productLine2Service.deleteBatch(idList);
            }
            return ResponseData.success("导入成功");
        }

        return ResponseData.error("---导入失败---");
}

    /**
     * 拆分产品线人员
     * @param operator
     * @param operatorName
     * @param teamSupervise
     * @param teamSuperviseName
     * @param deptMgr
     * @param deptMgrName
     * @param pl
     */
    private void splitName(TreeSet<String> operator, TreeSet<String> operatorName, TreeSet<String> teamSupervise, TreeSet<String> teamSuperviseName, TreeSet<String> deptMgr, TreeSet<String> deptMgrName, ProductLine2 pl) {
        operator.addAll(Arrays.asList(pl.getOperator().split(",")).stream().filter(o->null !=o).collect(Collectors.toSet()));
        operatorName.addAll(Arrays.asList(pl.getOperatorName().split(",")).stream().filter(o->null !=o).collect(Collectors.toSet()));
        teamSupervise.addAll(Arrays.asList(pl.getTeamSupervise().split(",")).stream().filter(o->null !=o).collect(Collectors.toSet()));
        teamSuperviseName.addAll(Arrays.asList(pl.getTeamSuperviseName().split(",")).stream().filter(o->null !=o).collect(Collectors.toSet()));
        deptMgr.addAll(Arrays.asList(pl.getDeptMgr().split(",")).stream().filter(o->null !=o).collect(Collectors.toSet()));
        deptMgrName.addAll(Arrays.asList(pl.getDeptMgrName().split(",")).stream().filter(o->null !=o).collect(Collectors.toSet()));
    }


    /**
     * 空值校验
     */
    private Boolean validateIsEmpty(ProductLine2 model) {
        return (StringUtils.isEmpty(model.getProductType())
                || StringUtils.isEmpty(model.getPlatform())
                || StringUtils.isEmpty(model.getArea())
                || StringUtils.isEmpty(model.getDepartment())
                || StringUtils.isEmpty(model.getTeam())
                || StringUtils.isEmpty(model.getOperator())
                || StringUtils.isEmpty(model.getTeamSupervise())
                || StringUtils.isEmpty(model.getDeptMgr()));
    }

    private String getUserName() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getName();
    }

    private String getUserAccount() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getAccount();
    }
}