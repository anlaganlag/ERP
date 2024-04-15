package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBoxInfo;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBoxInfoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgBoxInfoMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgBoxInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  报关外箱服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-07-07
 */
@Service
public class TgBoxInfoServiceImpl extends ServiceImpl<TgBoxInfoMapper, TgBoxInfo> implements ITgBoxInfoService {

    @Resource
    private TgBoxInfoMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgBoxInfoParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData add(TgBoxInfoParam param) {
        if("自定义箱型".equals(param.getBoxType())){
            return ResponseData.error("不支持自定义箱型，新增失败！");
        }
        LambdaQueryWrapper<TgBoxInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TgBoxInfo :: getBoxType, param.getBoxType());
        if(this.count(queryWrapper) > 0){
            return ResponseData.error("已存在此箱型信息，新增失败！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        TgBoxInfo insertEntity = new TgBoxInfo();
        BeanUtils.copyProperties(param, insertEntity);
        insertEntity.setCreateUser(name);
        return this.save(insertEntity) ? ResponseData.success() : ResponseData.error("新增失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(TgBoxInfoParam param) {
        return this.removeById(param.getId()) ? ResponseData.success() : ResponseData.error("删除失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData edit(TgBoxInfoParam param) {
        TgBoxInfo updateEntity = new TgBoxInfo();
        BeanUtils.copyProperties(param, updateEntity);
        String name = LoginContext.me().getLoginUser().getName();
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
    public List<TgBoxInfo> listTgBoxInfo(){
        return mapper.selectList(Wrappers.emptyWrapper());
    }

}
