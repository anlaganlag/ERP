package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageQuery;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangShopList;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.ShopListEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangShopListMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangShopListParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangShopListResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangShopListService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* <p>
    * 马帮店铺列表 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Service
public class MabangShopListServiceImpl extends ServiceImpl<MabangShopListMapper, MabangShopList> implements IMabangShopListService {

    @Resource
    private MabangShopListMapper mapper;


    /**
     * 202208023: ly: 平台名称、店铺名称 支持多选查询
     * @param param
     * @return
     */
    @DataSource(name = "external")
    @Override
    public PageResult<MabangShopListResult> list(MabangShopListParam param) {
        Page pageContext = getPageContext();
        //Other或其他,统一处理为[Other,其他]
        List<String> platformNames = param.getPlatformNames();
        if ( platformNames.contains("Other") ) {
            platformNames.add("其他");
        } else if ( platformNames.contains("其他")) {
            platformNames.add("Other");
        }
        IPage<MabangShopListResult> page = mapper.list(pageContext, param);
        return new PageResult<>(page);
    }


    @DataSource(name = "external")
    @Override
    public ResponseData add(MabangResult param) {
        try {
            ArrayList list = (ArrayList) ((Map) param.getData()).get("data");
            if (CollectionUtil.isEmpty(list)) {
                return ResponseData.error("数据为空!");
            }
            for (Object obj : list) {
                MabangShopList ent = JSON.parseObject(JSON.toJSONString(obj), MabangShopList.class);
                String oriFinanceCode = ent.getFinanceCode();
                String splitFinanceCode = oriFinanceCode.split("_")[0];
                ent.setFinanceCode(splitFinanceCode);
                ent.setOriginalFinanceCode(oriFinanceCode);
                setDefaultValue(ent);
                this.saveOrUpdate(ent);
            }
            return ResponseData.success();
        } catch (Exception e){
            return ResponseData.error("ShopList.add异常>>"+e.getMessage());

        }
    }



    private void setDefaultValue(MabangShopList ent){
        ent.setIsDelete(ShopListEnum.NO_DEL.getCode());
        ent.setSyncType(ShopListEnum.SYS_SYN.getCode());
        ent.setSyncTime(DateUtil.date());
        ent.setUpdateTime(DateUtil.date());
        ent.setSyncStatus(ShopListEnum.SYN_SUCCESS.getCode());
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }



    @DataSource(name = "external")
    @Override
    public ResponseData queryNames(){
        return ResponseData.success(mapper.queryNames());
    }
    @DataSource(name = "external")
    @Override
    public ResponseData queryPlatformNames(){
        List<String> platFormNames = mapper.queryPlatformNames();
        if (ObjectUtil.isNotEmpty(platFormNames)) {
            platFormNames.remove("Other");
            platFormNames.add("其他");
        }
        return  ResponseData.success(platFormNames);
    }

    @DataSource(name = "external")
    @Override
    public ResponseData queryFinanceCodeList(){
        return  ResponseData.success(mapper.queryFinanceCodeList());
    }

    @Override
    @DataSource(name = "external")
    public List<MabangShopListResult> exportExcel(MabangShopListParam param) {
        //设置导出数据为100万；
        PageQuery page = new PageQuery();
        page.setPageSize(1_000_000);
        Page pageContext = PageFactory.createPage(page);
        Page<MabangShopListResult> pageResult = mapper.list(pageContext, param);
        //返回查询结果
        return pageResult.getRecords();
    }

    @Override
    @DataSource(name = "external")
    public List<String> getShopSelect() {
        System.out.println("py");
        return new LambdaQueryChainWrapper<>(mapper)
                .select(MabangShopList::getName)
                .groupBy(MabangShopList::getName)
                .list().stream()
                .sorted(Comparator.comparing(MabangShopList::getName, String.CASE_INSENSITIVE_ORDER))
                .map(MabangShopList::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    @DataSource(name = "external")
    public List<String> getSiteSelect() {
        return new LambdaQueryChainWrapper<>(mapper)
                .select(MabangShopList::getAmazonsite)
                .isNotNull(MabangShopList::getAmazonsite)
                .groupBy(MabangShopList::getAmazonsite)
                .list().stream()
                .sorted(Comparator.comparing(MabangShopList::getAmazonsite, String.CASE_INSENSITIVE_ORDER))
                .map(d -> d.getAmazonsite().toUpperCase())
                .distinct()
                .collect(Collectors.toList());
    }
}
