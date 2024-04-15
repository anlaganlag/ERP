package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.product.api.productproposal.entity.RdFarSetting;
import com.tadpole.cloud.product.api.productproposal.entity.RdSdSetting;
import com.tadpole.cloud.product.api.productproposal.entity.RdSettingUpRecord;
import com.tadpole.cloud.product.api.productproposal.entity.RdTlSetting;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSettingUpRecordParam;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSettingUpRecordMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdFarSettingService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSdSettingService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSettingUpRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import com.tadpole.cloud.product.modular.productproposal.service.IRdTlSettingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 提案-设置-设置修改记录 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Service
public class RdSettingUpRecordServiceImpl extends ServiceImpl<RdSettingUpRecordMapper, RdSettingUpRecord> implements IRdSettingUpRecordService {

    @Resource
    private RdSettingUpRecordMapper mapper;

    @Resource
    private IRdTlSettingService rdTlSettingService;

    @Resource
    private IRdSdSettingService rdSdSettingService;

    @Resource
    private IRdFarSettingService rdFarSettingService;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchAdd(List<RdSettingUpRecordParam> params){

        params.forEach(l->{
            RdSettingUpRecord model = new RdSettingUpRecord();
            BeanUtils.copyProperties(l, model);
            this.baseMapper.insert(model);
        });

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<Map<String,Object>> listRdTlSettingLog() {
        List<Map<String,Object>> result = new ArrayList<>();

        List<RdTlSetting> rdTlSettings = rdTlSettingService.listPage();
        QueryWrapper<RdSettingUpRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_MODIFY_SETTING_TYPE","提案等级设置");
        List<RdSettingUpRecord> list = this.mapper.selectList(queryWrapper);
        list.sort(Comparator.comparing(RdSettingUpRecord::getSysLDate).reversed());

        Map<String, List<RdSettingUpRecord>> groupByKeys = list.stream().collect(Collectors.groupingBy(l->l.getSysPerName() + "_" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(l.getSysLDate())));
        for (Map.Entry<String, List<RdSettingUpRecord>> entry : groupByKeys.entrySet()){

            String groupByKey = entry.getKey();

            String[] keys = groupByKey.split("_");

            Map<String,Object> map = new HashMap<>();
            map.put("sysPerName",keys[0]);
            map.put("sysLDate",keys[1]);

            List<Map<String,Object>> dets = new ArrayList<>();
            for (RdSettingUpRecord rdSettingUpRecord : entry.getValue()){
                RdTlSetting rdTlSetting = rdTlSettings.stream().filter(l->l.getId().equals(rdSettingUpRecord.getSysModifyRecordNum())).collect(Collectors.toList()).get(0);
                Map<String,Object> det = new HashMap<>();

                det.put("updateRecord",rdTlSetting.getSysTaLevel());
                det.put("sysModifyRecordParam",rdSettingUpRecord.getSysModifyRecordParam());
                det.put("sysModifyRecordOrgValue",rdSettingUpRecord.getSysModifyRecordOrgValue());
                det.put("sysModifyRecordNewValue",rdSettingUpRecord.getSysModifyRecordNewValue());

                dets.add(det);
            }
            map.put("updateDet",dets);

            result.add(map);
        }

        return result;
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<Map<String,Object>> listRdSdSettingLog() {
        List<Map<String,Object>> result = new ArrayList<>();
        List<RdSdSetting> rdSdSettings = rdSdSettingService.listPage();

        QueryWrapper<RdSettingUpRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_MODIFY_SETTING_TYPE","拿样任务时长设置");
        List<RdSettingUpRecord> list = this.mapper.selectList(queryWrapper);
        list.sort(Comparator.comparing(RdSettingUpRecord::getSysLDate).reversed());

        Map<String, List<RdSettingUpRecord>> groupByKeys = list.stream().collect(Collectors.groupingBy(l->l.getSysPerName() + "_" + l.getSysLDate()));
        for (Map.Entry<String, List<RdSettingUpRecord>> entry : groupByKeys.entrySet()){

            String groupByKey = entry.getKey();

            String[] keys = groupByKey.split("_");

            Map<String,Object> map = new HashMap<>();
            map.put("sysPerName",keys[0]);
            map.put("sysLDate",keys[1]);

            List<Map<String,Object>> dets = new ArrayList<>();
            for (RdSettingUpRecord rdSettingUpRecord : entry.getValue()){
                RdSdSetting rdSdSetting = rdSdSettings.stream().filter(l->l.getId().equals(rdSettingUpRecord.getSysModifyRecordNum())).collect(Collectors.toList()).get(0);
                Map<String,Object> det = new HashMap<>();

                det.put("updateRecord",rdSdSetting.getSysSampleType());
                det.put("sysModifyRecordParam",rdSettingUpRecord.getSysModifyRecordParam());
                det.put("sysModifyRecordOrgValue",rdSettingUpRecord.getSysModifyRecordOrgValue());
                det.put("sysModifyRecordNewValue",rdSettingUpRecord.getSysModifyRecordNewValue());

                dets.add(det);
            }
            map.put("updateDet",dets);

            result.add(map);
        }

        return result;
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<Map<String,Object>> listRdFarSettingLog() {
        List<Map<String,Object>> result = new ArrayList<>();
        List<RdFarSetting> rdFarSettings = rdFarSettingService.listPage();

        QueryWrapper<RdSettingUpRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_MODIFY_SETTING_TYPE","研发费用自动过审设置");
        List<RdSettingUpRecord> list = this.mapper.selectList(queryWrapper);
        list.sort(Comparator.comparing(RdSettingUpRecord::getSysLDate).reversed());

        Map<String, List<RdSettingUpRecord>> groupByKeys = list.stream().collect(Collectors.groupingBy(l->l.getSysPerName() + "_" + l.getSysLDate()));
        for (Map.Entry<String, List<RdSettingUpRecord>> entry : groupByKeys.entrySet()){

            String groupByKey = entry.getKey();

            String[] keys = groupByKey.split("_");

            Map<String,Object> map = new HashMap<>();
            map.put("sysPerName",keys[0]);
            map.put("sysLDate",keys[1]);

            List<Map<String,Object>> dets = new ArrayList<>();
            for (RdSettingUpRecord rdSettingUpRecord : entry.getValue()){
                RdFarSetting rdFarSetting = rdFarSettings.stream().filter(l->l.getId().equals(rdSettingUpRecord.getSysModifyRecordNum())).collect(Collectors.toList()).get(0);
                Map<String,Object> det = new HashMap<>();

                det.put("updateRecord",rdFarSetting.getSysSampleMethod() + "-" + rdFarSetting.getSysAuditProcess());
                det.put("sysModifyRecordParam",rdSettingUpRecord.getSysModifyRecordParam());
                det.put("sysModifyRecordOrgValue",rdSettingUpRecord.getSysModifyRecordOrgValue());
                det.put("sysModifyRecordNewValue",rdSettingUpRecord.getSysModifyRecordNewValue());

                dets.add(det);
            }
            map.put("updateDet",dets);

            result.add(map);
        }

        return result;
    }
}
