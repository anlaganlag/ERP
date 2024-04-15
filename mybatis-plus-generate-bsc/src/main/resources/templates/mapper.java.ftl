package ${package.Mapper};

import ${cfg.entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* <p>
* ${table.comment!} Mapper接口
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
