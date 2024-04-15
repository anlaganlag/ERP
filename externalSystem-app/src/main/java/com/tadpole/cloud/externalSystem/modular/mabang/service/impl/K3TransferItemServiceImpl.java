package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferItem;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.ShopListEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3TransferItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3TransferItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3TransferItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* <p>
    * K3调拨单明细项 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Slf4j
@Service
public class K3TransferItemServiceImpl extends ServiceImpl<K3TransferItemMapper, K3TransferItem> implements IK3TransferItemService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private K3TransferItemMapper mapper;

    @DataSource(name = "k3cloud")
    @Override
    public List<K3TransferItem> list(K3TransferItemParam param) {

        List<K3TransferItem> results = mapper.list(param);
        return results;
    }

    @DataSource(name = "external")
    @Override
    public ResponseData add(List<K3TransferItem> param) {

        // K3同步到MCMS 时有可能冲突 需要加锁来排除冲突
        Object maBangSyncWork = redisTemplate.opsForValue().get(MabangConstant.MCMS_SYNC_MABANG_TRANSFER_WORK);

        if (ObjectUtil.isNotNull(maBangSyncWork)) {
            log.info("MCMS调拨单正在同步到马帮系统中，本次同步调拨单到MCMS系统取消,同步触发时间:{}",new Date());
            return ResponseData.success("MCMS调拨单正在同步到马帮系统中，本次同步调拨单到MCMS系统取消");
        }

        // 同步K3调拨单到MCMS系统,加上redis锁
        redisTemplate.boundValueOps(MabangConstant.K3_SYNC_MCMS_TRANSFER_WORK)
                .set(MabangConstant.K3_SYNC_MCMS_TRANSFER_WORK, Duration.ofSeconds(3));


        if(CollectionUtil.isEmpty(param)){
            return ResponseData.error("TransferItem.add异常>>>k3直接调拨单单据集合为空！");
        }
        try{
            for(K3TransferItem ent:param){
                ent.setSyncType(ShopListEnum.SYS_SYN.getCode());
                ent.setSyncStatus(ShopListEnum.SYN_SUCCESS.getCode());
                ent.setUpdateTime(DateUtil.date());
                ent.setSyncTime(DateUtil.date());
                this.saveOrUpdate(ent);
            }
        }catch (Exception e){
            return ResponseData.error("TransferItem.add异常>>>"+e.getMessage());
        }

        // 同步K3调拨单到MCMS系统,释放redis锁
        redisTemplate.delete(MabangConstant.K3_SYNC_MCMS_TRANSFER_WORK);
        return ResponseData.success();

    }


    @DataSource(name = "k3cloud")
    @Override
    public Map<String, String> k3MabangWarehouse() {

        //查出k3系统马帮专属仓库信息
        List<Map<String, String>> warehouseMap = mapper.k3MabangWarehouse();
        Map<String, String> map = new HashMap<>();
        for (Map<String, String> warehouse : warehouseMap) {
            map.put(warehouse.get("FNUMBER"), warehouse.get("FNAME"));
        }
        return map;
    }


    @DataSource(name = "k3cloud")
    @Override
    public ResponseData mabangWarehouse() {

        List<Map<String, String>> mapList = mapper.k3MabangWarehouse();
        return ResponseData.success(mapList);
    }
}
