package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageQuery;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangWarehouse;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.ShopListEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangWarehouseMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.MabangHeadParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.ShopParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.WarehouseParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangWarehouseResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangWarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* <p>
    * 马帮仓库列表 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Service
public class MabangWarehouseServiceImpl extends ServiceImpl<MabangWarehouseMapper, MabangWarehouse> implements IMabangWarehouseService {

    @Resource
    private IMabangRequstService service;

    @Resource
    private MabangWarehouseMapper mapper;

    @DataSource(name = "external")
    @Override
    public PageResult<MabangWarehouseResult> findPageBySpec(MabangWarehouseParam param) {
        Page pageContext = getPageContext();
        IPage<MabangWarehouseResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }


    @DataSource(name = "external")
    @Override
    public ResponseData getWarehouseList(@RequestBody WarehouseParm warehouseParm) {
        try {
            if (ObjectUtil.isNull(warehouseParm)) {
                warehouseParm = new WarehouseParm();
            }

            if(warehouseParm.getType()==null){
                //接口参数：1自建仓2第三方仓库3FBA仓9全部仓库
                warehouseParm.setType(9);
            }
            for(int k=1;k<=2;k++){

                //状态：1启用2停用
                warehouseParm.setStatus(k);
                MabangHeadParm mabangHeadParm = new MabangHeadParm("sys-get-warehouse-list", warehouseParm);
                MabangResult baseRespData = service.warehouseList(mabangHeadParm);
                Object obj = baseRespData.getData();
                String jsonStr = JSON.toJSONString(obj);
                //将获取的单个json字符串翻译成JSONObject
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);

                //返回json数组
                JSONArray warehousejsonArray = jsonObject.getJSONArray("data");

                //通过循环遍历数据
                for (int i = 0; i < warehousejsonArray.size(); i++) {
                    //将获取的单个json字符串翻译成JSONObject
                    String jsonObj = warehousejsonArray.get(i).toString();

                    //将json对象翻译成MabangWarehouse对象
                    MabangWarehouse warehouse = JSONObject.parseObject(jsonObj, MabangWarehouse.class);
                    warehouse.setSyncStatus(ShopListEnum.SYN_SUCCESS.getCode());
                    warehouse.setSyncTime(DateUtil.date());
                    warehouse.setSyncType(ShopListEnum.MAN_SYN.getCode());
                    warehouse.setUpdateTime(DateUtil.date());
                    this.saveOrUpdate(warehouse);
                }
            }
            return ResponseData.success();
        }catch (Exception e){
            return ResponseData.error("Warehouse.add异常>>"+e.getMessage());
        }
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MabangWarehouse findByName(String warehouseName) {

        LambdaQueryWrapper<MabangWarehouse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MabangWarehouse::getName, warehouseName);
        List<MabangWarehouse> warehouseList = this.mapper.selectList(queryWrapper);

        if (ObjectUtil.isNotEmpty(warehouseList)) {
            return warehouseList.get(0);
        }
        return null;
    }


    /**
     * 查出k3系统马帮专属仓库信息
     * @return
     */
    @DataSource(name = "k3cloud")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, String> getK3MabangWarehouse() {

        //查出k3系统马帮专属仓库信息
        List<Map<String, String>> warehouseMap = mapper.k3MabangWarehouse();
        Map<String, String> map = new HashMap<>();
        for (Map<String, String> warehouse : warehouseMap) {
            map.put(warehouse.get("FNUMBER"), warehouse.get("FNAME"));
        }
        return map;
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    @Override
    @DataSource(name = "external")
    public ResponseData queryNames(){
        return ResponseData.success(mapper.queryNames());
    }

    @Override
    @DataSource(name = "external")
    public List<MabangWarehouseResult> exportExcel(MabangWarehouseParam param) {
        //设置导出数据为100万；
        PageQuery page = new PageQuery();
        page.setPageSize(1_000_000);
        Page pageContext = PageFactory.createPage(page);
        Page<MabangWarehouseResult> pageResult = this.baseMapper.findPageBySpec(pageContext, param);
        //返回查询结果
        return pageResult.getRecords();

    }
}
