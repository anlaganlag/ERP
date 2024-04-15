package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 领星添加类目返回结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinXingAddProductResp {
         private Integer code;
         private String message;
         private List<Object> error_details;
         private String require_id;
         private String response_time;
         private List<Object> data;
         private Integer total;

/*    {
    "code": 0,
    "message": "success",
    "error_details": [],
    "request_id": "CCF38623-DE1A-004F-0961-90A6AF265013",
    "response_time": "2021-11-11 11:14:33",
    "data": {
        "product_id": 1010
    },
    "total": 0
}*/

    /**
     * 添加商品成功的判断条件
     * @return
     */
   public boolean isSuccess() {
       if ((this.code==0 && "success".equals(this.message))  ) {
           return true;
       }
       return false;
    }
}

