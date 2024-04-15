package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwSysDict;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SysDictDetail;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SysDictDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SysDictMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISysDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
* <p>
* 用户字典表 服务实现类
* </p>
*
* @author gal
* @since 2021-10-29
*/
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, CwSysDict> implements ISysDictService {

    @Resource
    SysDictDetailMapper sysDictDetailMapper;

    @DataSource(name = "finance")
    @Override
    public List<CwSysDict> findPageBySpec(CwSysDict param) {
        QueryWrapper<CwSysDict> qw = new QueryWrapper();

        qw.select().like("DICT_NAME",param.getDictName());
        return this.baseMapper.selectList(qw);
    }

    @DataSource(name = "finance")
    @Override
    public List<SysDictDetail> queryDetail(SysDictDetail param) {
        return this.baseMapper.queryDetail(param);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData addSysDictDetail(SysDictDetail param) throws IOException{
        if(StringUtils.isBlank(param.getDictCode()) || StringUtils.isBlank(param.getDictValue())){
            return ResponseData.error("字典编码和字典值不能为空！");
        }
        QueryWrapper<SysDictDetail> queryWrapperDel=new QueryWrapper<>();
        queryWrapperDel.eq("DICT_CODE",param.getDictCode())
                .eq("DICT_ID", param.getDictId());
        List<SysDictDetail> dictList=sysDictDetailMapper.selectList(queryWrapperDel);
        if(dictList.size() > 0){
         return ResponseData.error("字典编码不能重复！");
        }
        this.baseMapper.add(param);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public void updateSysDictDetail(SysDictDetail param) {
        LambdaUpdateWrapper<SysDictDetail> updateQw = new LambdaUpdateWrapper<>();
        updateQw.eq(SysDictDetail :: getId, param.getId())
                .set(StringUtils.isNotBlank(param.getDictCode()), SysDictDetail :: getDictCode, param.getDictCode())
                .set(StringUtils.isNotBlank(param.getDictValue()), SysDictDetail :: getDictValue, param.getDictValue())
                .set(SysDictDetail :: getSymbol, param.getSymbol())
                .set(SysDictDetail :: getRemark, param.getRemark());
        sysDictDetailMapper.update(null, updateQw);
    }

    @DataSource(name = "finance")
    @Override
    public void deleteSysDictDetail(SysDictDetail param) {
        this.baseMapper.delete(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<SysDictDetail> getByDictCode(CwSysDict param) {
        return this.baseMapper.getByDictCode(param);
    }
}
