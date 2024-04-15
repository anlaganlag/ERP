package com.tadpole.cloud.externalSystem.api.k3.annotation;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.lang.reflect.Type;

@Getter
@Setter
public class TransferSerializer2 implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;

        if (object instanceof Object) {

            out.write("{\"FUserId\":\""+object+"\"}");
//            ObjectId objectId = (ObjectId) object;
//            out.writeString(objectId.toString());
            return;
        }
    }


}
