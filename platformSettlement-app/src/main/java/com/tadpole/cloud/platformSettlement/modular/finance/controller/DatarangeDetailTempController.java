package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IDatarangeDetailTempService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* datarange明细数据中间表 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@RequestMapping("//datarange-detail-temp")
public class DatarangeDetailTempController {

    @Autowired
    private IDatarangeDetailTempService service;

}
