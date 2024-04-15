package com.tadpole.cloud.externalSystem.modular.ebms.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbComMateriel;
import com.tadpole.cloud.externalSystem.modular.ebms.model.params.TbcommaterielParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gal
 * @since 2022-10-25
 */
@Service
public interface ITbcommaterielService extends IService<TbComMateriel> {

    List<TbComMateriel> getWaitMatList(List<String> matList) ;

    List<TbComMateriel> getAllMatList() ;

    public List<TbComMateriel> getWaitMatList(Date createTimeStart, Date createTimeEnd, List<String> matList);

    public List<TbComMateriel> getWaitAddMatList(Date createTimeStart, Date createTimeEnd, List<String> matList);

    List<TbComMateriel> getWaitUpdateMatList(Date updateTimeStart, Date updateTimeEnd, List<String> matList);

    PageResult<TbComMateriel> listBySpec(TbcommaterielParam param);

    ResponseData disableProdMateriel(List<String> matCodes);
}
