package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomBoxInfo;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomBoxInfoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomBoxInfoResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomBoxInfoMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomBoxInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 报关自定义外箱表 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-08-18
 */
@Service
public class TgCustomBoxInfoServiceImpl extends ServiceImpl<TgCustomBoxInfoMapper, TgCustomBoxInfo> implements ITgCustomBoxInfoService {

    @Resource
    private TgCustomBoxInfoMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgCustomBoxInfoParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(TgCustomBoxInfoParam param) {
        if(param.getMaxVolume().compareTo(param.getMinVolume()) < 0){
            return ResponseData.error("最大值必须大于等于最小值，新增失败！");
        }
        if(param.getMaxVolume().compareTo(param.getMinVolume()) == 0
                && (param.getMinEq().equals("0") || param.getMaxEq().equals("0"))){
            return ResponseData.error("最大值必须大于等于最小值，新增失败！");
        }
        param.setBoxType("自定义箱型");
        List<TgCustomBoxInfoResult> containVal = mapper.selectContainVal(param);
        if(CollectionUtil.isNotEmpty(containVal)){
            return ResponseData.error("已存在此区间值，新增失败！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        TgCustomBoxInfo insertEntity = new TgCustomBoxInfo();
        BeanUtils.copyProperties(param, insertEntity);
        insertEntity.setCreateUser(name);
        return this.save(insertEntity) ? ResponseData.success() : ResponseData.error("新增失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(TgCustomBoxInfoParam param) {
        return this.removeById(param.getId()) ? ResponseData.success() : ResponseData.error("删除失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData edit(TgCustomBoxInfoParam param) {
        TgCustomBoxInfo updateEntity = new TgCustomBoxInfo();
        String name = LoginContext.me().getLoginUser().getName();
        updateEntity.setId(param.getId());
        updateEntity.setBoxWeight(param.getBoxWeight());
        updateEntity.setUpdateUser(name);
        updateEntity.setUpdateTime(DateUtil.date());
        return this.updateById(updateEntity) ? ResponseData.success() : ResponseData.error("编辑失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public List<BaseSelectResult> boxTypeSelect() {
        return mapper.boxTypeSelect();
    }

    /**
     * 获取所有报关外箱
     */
    @Override
    @DataSource(name = "logistics")
    public List<TgCustomBoxInfo> listTgBoxInfo(){
        return mapper.selectList(Wrappers.emptyWrapper());
    }

}
