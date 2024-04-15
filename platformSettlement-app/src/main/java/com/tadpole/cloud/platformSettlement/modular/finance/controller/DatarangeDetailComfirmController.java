package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IDatarangeDetailComfirmService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* datarange明细刷完财务分类后的 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@RequestMapping("//datarange-detail-comfirm")
public class DatarangeDetailComfirmController {

    @Autowired
    private IDatarangeDetailComfirmService service;

}
