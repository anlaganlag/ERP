package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyInfoReqVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface IExportOrImportService {
    ResponseData operImport(MultipartFile file);


    void operExportFast(HttpServletResponse response) throws Exception;


    void operExportFast2(HttpServletResponse response) throws Exception;

    ResponseData specialImport(MultipartFile file);

    void operExport(HttpServletResponse response) throws Exception;

    void specialExport(HttpServletResponse response, SpecialApplyInfoReqVO param) throws Exception;


    ResponseData operImportComit(MultipartFile file);



    ResponseData operImportComit2(MultipartFile file);


    ResponseData operImportComit3(MultipartFile file);

    ResponseData specialImportComit(MultipartFile file);

     void teamVerifyExport(HttpServletResponse response) throws Exception;

    @DataSource(name = "stocking")
    ResponseData teamVerifyImport(MultipartFile file);

    @DataSource(name = "stocking")
    void purchaseOrdersExport(HttpServletResponse response, @RequestBody OperApplyInfoReqVO param) throws Exception;

    @DataSource(name = "stocking")
    void purchaseOrdersExportV3(HttpServletResponse response, @RequestBody OperApplyInfoReqV3 param) throws Exception;

    ResponseData purchaseOrdersImport(MultipartFile file);

    ResponseData operImportV3(MultipartFile file);
}
