package ${package.Service};


import ${cfg.result}.*;
import ${cfg.params}.*;
import ${cfg.entity}.${entity};
import ${superServiceClassPackage};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.entityName}Service extends ${superServiceClass}<${entity}> {
  void save(${entity}Param param);
   ${entity}Result queryById(Long id);
}

</#if>
