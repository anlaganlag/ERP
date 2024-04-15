package com.tadpole.cloud.operationManagement.modular.stock.controller;

import cn.hutool.core.io.IoUtil;
import cn.stylefeng.guns.cloud.libs.enums.FileExceptionEnum;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.exp.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@RestController
@ApiResource(name = "下载错误模板", path = "/downloadError")
@Api(tags = "下载错误模板")
public class DownloadErrorController {
  @GetResource(name = "下载模板", path = "/download", requiredPermission = false )
  @ApiOperation("下载模板")
  @BusinessLog(title = "下载错误模板特殊备货审核保存",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void downloadError(
      @RequestParam("fileName") String fileName, HttpServletResponse response) {
    String filePath = System.getProperty("user.dir") + "/upload/" + fileName;

    try {
      response.setHeader(
          "content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      /** 读取服务器端模板文件 */
      InputStream inputStream = new FileInputStream(filePath);

      /** 将流中内容写出去 . */
      OutputStream outputStream = response.getOutputStream();
      byte[] buffer = new byte[1024];
      int len;
      while ((len = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, len);
      }

      try {

        outputStream.flush();

      } catch (IOException e) {
        throw new ServiceException(FileExceptionEnum.FILE_IO_ERROR);
      } finally {
        IoUtil.close(outputStream);
      }

      inputStream.close();
      outputStream.close();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
