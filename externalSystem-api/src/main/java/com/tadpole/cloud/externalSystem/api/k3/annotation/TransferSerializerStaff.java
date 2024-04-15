package com.tadpole.cloud.externalSystem.api.k3.annotation;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import lombok.Data;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author: ty
 * @description:
 * @date: 2023/12/11
 */
@Data
public class TransferSerializerStaff implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;

        if (object instanceof Object) {

            out.write("{\"FSTAFFNUMBER\":\""+object+"\"}");
            return;
        }
    }
}
