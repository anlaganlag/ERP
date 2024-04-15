package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SaftyDayItem;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SaftyDaySummary;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SaftyDayValue;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SaftyDaySummaryMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SaftyDaySummaryParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SaftyDayValueParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SaftyDaySummaryResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SaftyDaySummaryResultVO;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISaftyDayItemService;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISaftyDaySummaryService;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISaftyDayValueService;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SaftyDayVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
    * 安全天数概要表 服务实现类
    * </p>
*
* @author cyt
* @since 2022-07-20
*/
@Slf4j
@Service
public class SaftyDaySummaryServiceImpl extends ServiceImpl<SaftyDaySummaryMapper, SaftyDaySummary> implements ISaftyDaySummaryService {

    @Resource
    private SaftyDaySummaryMapper mapper;

    @Resource
    private ISaftyDayValueService valueService;

    @Resource
    private ISaftyDayItemService itemService;


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(SaftyDayVO saftyDayVO) throws Exception {
        //q: 为什么要用List<SaftyDayValueParam> valueParms = saftyDayVO.getSaftyDayValueList(); ？
        //a: 因为前端传过来的是一个数组，所以要用List接收

        List<SaftyDayValueParam> valueParms = saftyDayVO.getSaftyDayValueList();
        //q: 为什么要用ObjectUtil.isEmpty(valueParms) ？
        //a: 因为前端传过来的是一个数组，所以要用List接收，如果前端没有传过来，那么valueParms就是null，所以要判断是否为空
        if (ObjectUtil.isEmpty(valueParms)) {
            return ResponseData.error("备货天数具体值不能为空");
        }
        SaftyDaySummaryParam summaryParam = saftyDayVO.getSaftyDaySummaryParam();

        Integer priority = this.getPriority(summaryParam);//优先级 、根据属性的选择深度 设置

        List<SaftyDayValue> valueList = new ArrayList<>();

        String userName = this.getUserName();
        summaryParam.setId(null);
        summaryParam.setCreateBy(userName);
        SaftyDaySummary saftyDaySummary = BeanUtil.copyProperties(summaryParam, SaftyDaySummary.class);
        saftyDaySummary.setPriority(priority);
        saftyDaySummary.setIsEnable(1);
        saftyDaySummary.setIsDelete(0);
        mapper.insert(saftyDaySummary);
        BigDecimal summaryId = saftyDaySummary.getId();
        //
        for (SaftyDayValueParam valueParm : valueParms) {
            valueParm.setCreateBy(userName);
            valueParm.setSummaryId(summaryId);
            SaftyDayValue saftyDayValue = BeanUtil.copyProperties(valueParm, SaftyDayValue.class);
            saftyDayValue.setIsEnable(1);
            valueList.add(saftyDayValue);
        }
        valueService.saveBatch(valueList);

        //拆分明细项
        HashMap<String, List<String>> parMap = new HashMap<>();
        parMap.put("platformName", this.splitPar(summaryParam.getPlatformName()));
        parMap.put("area", this.splitPar(summaryParam.getArea()));
        parMap.put("department", this.splitPar(summaryParam.getDepartment()));
        parMap.put("team", this.splitPar(summaryParam.getTeam()));
        parMap.put("productType", this.splitPar(summaryParam.getProductType()));
        parMap.put("productName", this.splitPar(summaryParam.getProductName()));
        parMap.put("style", this.splitPar(summaryParam.getStyle()));
        parMap.put("model", this.splitPar(summaryParam.getModel()));

        log.info("summaryId:{}--优先级:{}--拆分明细总条数:{}",summaryId,priority);


        SaftyDayItem item = new SaftyDayItem();

        List<SaftyDayItem> itemList = new ArrayList<>();
        itemList.add(item);


        for (Map.Entry<String, List<String>> entry : parMap.entrySet()) {
            List<String> parValues = entry.getValue();
            String par = entry.getKey();
            if (ObjectUtil.isEmpty(parValues)) {
                continue;
            }

            List<SaftyDayItem> itemListTemp = new ArrayList<>();//临时item

            for (String value : parValues) {
                for (SaftyDayItem saftyDayItem : itemList) {
                    this.copyValue(itemListTemp, saftyDayItem, par, value);
                }
            }
            itemList = itemListTemp;
        }

        for (SaftyDayItem dayItem : itemList) {
            dayItem.setSummaryId(summaryId);
            dayItem.setPriority(priority);
            dayItem.setCreateBy(userName);
            dayItem.setIsEnable(1);
        }
        itemService.saveBatch(itemList);

        return ResponseData.success();
    }


    @DataSource(name = "stocking")
    @Override
    public PageResult<SaftyDaySummaryResultVO> list(SaftyDaySummaryParam param) {
        Page pageContext = param.getPageContext();
        Page<SaftyDaySummaryResultVO> page = this.mapper.summaryList2(pageContext, param);
        List<SaftyDaySummaryResultVO> records = page.getRecords();

        if (ObjectUtil.isNotEmpty(records)) {
            Map<BigDecimal, List<SaftyDaySummaryResultVO>> summaryIdMap = records.stream().collect(Collectors.groupingBy(SaftyDaySummaryResultVO::getId));
            Set<BigDecimal> summaryIdSet = summaryIdMap.keySet();
            LambdaQueryWrapper<SaftyDayValue> valueWrapper = new LambdaQueryWrapper<>();
            valueWrapper.in(SaftyDayValue::getSummaryId,summaryIdSet);

            List<SaftyDayValue> saftyDayValueList = valueService.list(valueWrapper);

            if (ObjectUtil.isNotEmpty(saftyDayValueList)) {
                Map<BigDecimal, List<SaftyDayValue>> valueMap = saftyDayValueList.stream().collect(Collectors.groupingBy(SaftyDayValue::getSummaryId));
                for (SaftyDaySummaryResultVO record : records) {
                    List<SaftyDayValue> saftyDayValues = valueMap.get(record.getId());
                    record.setValueList(saftyDayValues);
                }
                page.setRecords(records);
            }
        }

        return new PageResult<>(page);

    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateStatus(List<Integer> idList, Integer action) {
        LambdaUpdateWrapper<SaftyDaySummary> summaryUpdateWrapper = new LambdaUpdateWrapper<>();
        summaryUpdateWrapper.in(SaftyDaySummary::getId, idList);

        LambdaUpdateWrapper<SaftyDayItem> itemUpdateWrapper = new LambdaUpdateWrapper<>();
        itemUpdateWrapper.in(SaftyDayItem::getSummaryId, idList);

        if (action == 1 || action == 0) {//启用 、禁用
            summaryUpdateWrapper.set(SaftyDaySummary::getIsEnable, action);
            itemUpdateWrapper.set(SaftyDayItem::getIsEnable, action);

        } else if (action==2) {//删除
            summaryUpdateWrapper.set(SaftyDaySummary::getIsDelete, 1);
            itemUpdateWrapper.set(SaftyDayItem::getIsDelete, 1);
        }
        summaryUpdateWrapper.set(SaftyDaySummary::getUpdateTime, new Date());
        this.update(summaryUpdateWrapper);
        itemService.update(itemUpdateWrapper);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateValue(List<SaftyDayValue> valueList) {

        if (ObjectUtil.isEmpty(valueList)) {
            return ResponseData.error("参数不能为空");
        }

        Optional<SaftyDayValue> first = valueList.stream().filter(vp -> ObjectUtil.isNotNull(vp.getSummaryId()) && vp.getSummaryId().compareTo(BigDecimal.ZERO) > 0).findFirst();
        if (! first.isPresent()) {
            return ResponseData.error("summaryId不能为空,至少保留一条原始数据对应的summaryId");
        }
        BigDecimal summaryId = first.get().getSummaryId();

        List<BigDecimal> parIdList = valueList.stream().map(v -> v.getId()).collect(Collectors.toList());

        LambdaQueryWrapper<SaftyDayValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SaftyDayValue::getSummaryId, summaryId);
        List<SaftyDayValue> saftyDayValueList = valueService.getBaseMapper().selectList(queryWrapper);
        List<SaftyDayValue> deleteValueList = saftyDayValueList.stream().filter(sv -> !(parIdList.contains(sv.getId()))).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(deleteValueList)) {
            List<BigDecimal> delIdList = deleteValueList.stream().map(d -> d.getId()).collect(Collectors.toList());
            valueService.getBaseMapper().deleteBatchIds(delIdList);
        }

        Date date = new Date();
        for (SaftyDayValue value : valueList) {

            if (ObjectUtil.isNull(value.getId())) {
                value.setCreateBy(this.getUserName());
                value.setCreateTime(date);
                value.setSummaryId(summaryId);
            }
            value.setUpdateTime(date);
            value.setUpdateBy(this.getUserName());
        }
        this.valueService.saveOrUpdateBatch(valueList);

        return ResponseData.success();
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData revive(SaftyDayVO saftyDayVO) throws  Exception {
        Integer summaryId = saftyDayVO.getSaftyDaySummaryParam().getId();
        if (ObjectUtil.isNull(summaryId)) {
            return ResponseData.error("安维度重新生成，SaftyDayVO的参数saftyDaySummaryParam需要传入summaryID");
        }
        List<Integer> idList = Arrays.asList(summaryId);
        ResponseData responseData = this.updateStatus(idList, 2);
        if (! responseData.getSuccess()) {
            throw new Exception("删除原数据失败");
        }
        return this.add(saftyDayVO);

    }

    @DataSource(name = "stocking")
    @Override
    public List<SaftyDaySummaryResult> exportExcel(SaftyDaySummaryParam param) {
        List<SaftyDaySummaryResult> page = this.baseMapper.exportExcel(param);
        return page;

    }


    private void copyValue(List<SaftyDayItem> itemListTemp, SaftyDayItem item, String par, String value) {

        SaftyDayItem saftyDayItem = BeanUtil.copyProperties(item, SaftyDayItem.class);

        switch (par){
            case "platformName":
                saftyDayItem.setPlatformName(value);break;
            case "area":
                saftyDayItem.setArea(value);break;
            case "department":
                saftyDayItem.setDepartment(value);break;
            case "team":
                saftyDayItem.setTeam(value);break;
            case "productType":
                saftyDayItem.setProductType(value);break;
            case "productName":
                saftyDayItem.setProductName(value);break;
            case "style":
                saftyDayItem.setStyle(value);break;
            case "model":
                saftyDayItem.setModel(value);break;
            default:
                ;
        }

        itemListTemp.add(saftyDayItem);

    }

    private List<String> splitPar(String par) {
        if (ObjectUtil.isEmpty(par)) {
            return null;
        }
        String[] strings = par.split(",");
        return Arrays.asList(strings);
    }

    private Integer getPriority(SaftyDaySummaryParam summaryParam) {

        int priority = 1;

        if (ObjectUtil.isNotEmpty(summaryParam.getPlatformName())) {
            priority = 1;
        }
        if (ObjectUtil.isNotEmpty(summaryParam.getArea())) {
            priority = 2;
        }
        if (ObjectUtil.isNotEmpty(summaryParam.getDepartment())) {
            priority = 3;
        }
        if (ObjectUtil.isNotEmpty(summaryParam.getTeam())) {
            priority = 4;
        }
        if (ObjectUtil.isNotEmpty(summaryParam.getProductType())) {
            priority = 5;
        }
        if (ObjectUtil.isNotEmpty(summaryParam.getProductName())) {
            priority = 6;
        }
        if (ObjectUtil.isNotEmpty(summaryParam.getStyle())) {
            priority = 7;
        }
        if (ObjectUtil.isNotEmpty(summaryParam.getModel())) {
            priority = 8;
        }
        return priority;
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
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
