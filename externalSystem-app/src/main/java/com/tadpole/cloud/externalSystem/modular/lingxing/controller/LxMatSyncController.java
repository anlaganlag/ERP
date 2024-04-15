package com.tadpole.cloud.externalSystem.modular.lingxing.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbComMateriel;
import com.tadpole.cloud.externalSystem.modular.ebms.service.ITbcommaterielService;
import com.tadpole.cloud.externalSystem.modular.lingxing.entity.LxMatSync;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.LxMatSyncParm;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LxMatSyncService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 领星物料同步;(LX_MAT_SYNC)表控制层
 *
 * @author : LSY
 * @date : 2022-12-5
 */
@Api(tags = "领星物料同步接口")
@RestController
@ApiResource(name = "领星物料同步接口", path = "/lxMatSync")
public class LxMatSyncController {

    public static final String controllerName = "领星物料同步接口";

    /**
     * erp物料同步redis key
     */
    public static String LX_ERP_MAT_SYNC_KEY = "LX_ERP_MAT_SYNC_KEY";

    @Autowired
    private LxMatSyncService lxMatSyncService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private ITbcommaterielService tbcommaterielService;

    /**
     * 分页查询
     *
     * @param lxMatSyncParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation("物料同步记录分页查询")
    @PostResource(name = "物料同步记录分页查询", path = "/list", menuFlag = true, requiredPermission = false, requiredLogin = false)
    public ResponseData syncQuery(@RequestBody LxMatSyncParm lxMatSyncParm) {
        //1.分页参数
        Page page = getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        PageResult<LxMatSync> pageResult = lxMatSyncService.paginQuery(lxMatSyncParm, current, size);
        //3. 分页结果组装
        return ResponseData.success(pageResult);
    }


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetResource(name = "通过ID查询单条数据", path = "/id", requiredPermission = false, requiredLogin = false)
    public ResponseEntity<LxMatSync> queryById(String id) {
        return ResponseEntity.ok(lxMatSyncService.queryById(id));
    }


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @GetResource(name = "通过主键删除数据", path = "/deleteById", requiredPermission = false, requiredLogin = false)
    public ResponseEntity<Boolean> deleteById(String id) {
        return ResponseEntity.ok(lxMatSyncService.deleteById(id));
    }


    /**
     * 同步数据到erp
     * @param createTimeStart
     * @param createTimeEnd
     * @param matList
     * @return
     */
    @GetResource(name = controllerName + "--同步新增物料到领星ERP", path = "/addToLxErp", menuFlag = false, requiredLogin = false)
    @ApiOperation(value = controllerName + "--同步新增物料到领星ERP")
    public ResponseData addToLxErp(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date createTimeStart,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date createTimeEnd,
                                   @RequestParam(required = false) List<String> matList) {

        // 刷数据时有可能冲突 需要加锁来排除冲突
        Object syncCrossTransferK3 = redisTemplate.opsForValue().get(LX_ERP_MAT_SYNC_KEY);
        if (ObjectUtil.isNotNull(syncCrossTransferK3)) {
            return ResponseData.success("同步新增物料到领星ERP有类似的业务正在作业,本次请求结束");
        }
        //调用k3接口耗时比较大，大约1分钟处理7条数据，订单数据较大时，redis锁自动过期时间应该适当增加
        redisTemplate.boundValueOps(LX_ERP_MAT_SYNC_KEY)
                .set(LX_ERP_MAT_SYNC_KEY, Duration.ofMinutes(15));

        List<TbComMateriel> waitMatList = tbcommaterielService.getWaitAddMatList(createTimeStart, createTimeEnd, matList);

        if (ObjectUtil.isEmpty(waitMatList)) {
            return ResponseData.success("EBMS中没有找到物料创建时间大于2022-09-30的新增物料");
        }
        List<String> matCodeList = waitMatList.stream().map(m -> m.getMatCode()).collect(Collectors.toSet()).stream().collect(Collectors.toList());
        //查找同步记录
        List<LxMatSync> syncList = lxMatSyncService.getSyncList(matCodeList);

        List<TbComMateriel> filterResultData = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(syncList)) {
            Set<String> syncCode = syncList.stream().map(s -> s.getMatCode()).collect(Collectors.toSet());
            boolean b = matCodeList.removeAll(syncCode);
            if (ObjectUtil.isEmpty(matCodeList)) {
                return ResponseData.success("过滤后,没有找到需要新增的物料同步到领星ERP");
            }
            filterResultData = waitMatList.stream().filter(m -> matCodeList.contains(m.getMatCode())).collect(Collectors.toList());
        } else {
            filterResultData = waitMatList;
        }

        ResponseData responseData = lxMatSyncService.initLxErpData(filterResultData);
        redisTemplate.delete(LX_ERP_MAT_SYNC_KEY);

        return responseData;
    }


    /**
     *获取分页查询参数
     */
    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
