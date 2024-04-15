package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.util.StrUtil;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComEntityTaxControlPanelParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityTaxControlPanelResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComEntityTaxControlPanel;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComEntityTaxControlPanelMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComEntityTaxControlPanelService;
 /**
 * 资源-公司实体银行设备税控盘;(Tb_Com_Entity_Tax_Control_Panel)--服务实现类
 * @author : LSY
 * @create : 2023-7-28
 */
@Slf4j
@Service
@Transactional
public class TbComEntityTaxControlPanelServiceImpl extends ServiceImpl<TbComEntityTaxControlPanelMapper, TbComEntityTaxControlPanel>  implements TbComEntityTaxControlPanelService{
    @Resource
    private TbComEntityTaxControlPanelMapper tbComEntityTaxControlPanelMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param pkCode 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityTaxControlPanel queryById(BigDecimal pkCode){
        return tbComEntityTaxControlPanelMapper.selectById(pkCode);
    }
    
    /**
     * 分页查询
     *
     * @param tbComEntityTaxControlPanel 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComEntityTaxControlPanelResult> paginQuery(TbComEntityTaxControlPanelParam tbComEntityTaxControlPanel, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComEntityTaxControlPanel> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tbComEntityTaxControlPanel.getBusTaxControlPanelNo())){
            queryWrapper.eq(TbComEntityTaxControlPanel::getBusTaxControlPanelNo, tbComEntityTaxControlPanel.getBusTaxControlPanelNo());
        }
        //2. 执行分页查询
        Page<TbComEntityTaxControlPanelResult> pagin = new Page<>(current , size , true);
        IPage<TbComEntityTaxControlPanelResult> selectResult = tbComEntityTaxControlPanelMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tbComEntityTaxControlPanel 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityTaxControlPanel insert(TbComEntityTaxControlPanel tbComEntityTaxControlPanel){
        tbComEntityTaxControlPanelMapper.insert(tbComEntityTaxControlPanel);
        return tbComEntityTaxControlPanel;
    }
    
    /** 
     * 更新数据
     *
     * @param tbComEntityTaxControlPanel 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComEntityTaxControlPanel update(TbComEntityTaxControlPanel tbComEntityTaxControlPanel){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComEntityTaxControlPanel> chainWrapper = new LambdaUpdateChainWrapper<>(tbComEntityTaxControlPanelMapper);
        if(StrUtil.isNotBlank(tbComEntityTaxControlPanel.getBusTaxControlPanelNo())){
            chainWrapper.set(TbComEntityTaxControlPanel::getBusTaxControlPanelNo, tbComEntityTaxControlPanel.getBusTaxControlPanelNo());
        }
        //2. 设置主键，并更新
        chainWrapper.eq(TbComEntityTaxControlPanel::getPkCode, tbComEntityTaxControlPanel.getPkCode());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tbComEntityTaxControlPanel.getPkCode());
        }else{
            return tbComEntityTaxControlPanel;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param pkCode 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal pkCode){
        int total = tbComEntityTaxControlPanelMapper.deleteById(pkCode);
        return total > 0;
    }
    
    /**
     * 通过主键批量删除数据
     *
     * @param pkCodeList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> pkCodeList){
         int delCount = tbComEntityTaxControlPanelMapper.deleteBatchIds(pkCodeList);
         if (pkCodeList.size() == delCount) {
             return Boolean.TRUE;
         }
         return Boolean.FALSE;
     }
}