package com.tadpole.cloud.operationManagement.modular.stock.cache;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.tadpole.cloud.operationManagement.modular.stock.constants.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 获取redis缓存信息
 * @date: 2021/12/11
 */
@Component
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;

    public List<Map> getDepartmentList() {
        BoundValueOperations departmentListTemplate = redisTemplate.boundValueOps(RedisConstants.DEPARTMENT_LIST);
        if (ObjectUtil.isNotEmpty(departmentListTemplate.get())) {
            return JSONUtil.toList(JSONUtil.parseArray(departmentListTemplate.get().toString()), Map.class);
        }
        return null;

    }

    public List<Map> getTeamList() {
        BoundValueOperations teamListTemplate = redisTemplate.boundValueOps(RedisConstants.TEAM_LIST);
        if (ObjectUtil.isNotEmpty(teamListTemplate.get())) {
            return JSONUtil.toList(JSONUtil.parseArray(teamListTemplate.get().toString()), Map.class);
        }
        return null;
    }

    public List<Map> getAreaList() {
        BoundValueOperations areaListTemplate = redisTemplate.boundValueOps(RedisConstants.AREA_LIST);
        if (ObjectUtil.isNotEmpty(areaListTemplate.get())) {
            return JSONUtil.toList(JSONUtil.parseArray(areaListTemplate.get().toString()), Map.class);
        }
        return null;
    }

    public List<Map> getPlatformList() {
        BoundValueOperations platformListTemplate = redisTemplate.boundValueOps(RedisConstants.PLATFORM_LIST);
        if (ObjectUtil.isNotEmpty(platformListTemplate.get())) {
            return JSONUtil.toList(JSONUtil.parseArray(platformListTemplate.get().toString()), Map.class);
        }
        return null;

    }
}
