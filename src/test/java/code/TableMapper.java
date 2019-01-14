package code;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

public class TableMapper {
    /**
     * 	数据库中的table名称
     */
    private String tableName ;

    /**
     * 	生成的实体对象名
     */
    private String modelName ;

    public String getTableName() {
        return tableName;
    }

    public String getModelName() {
        return modelName;
    }

    TableMapper (String tableName) {
        this.tableName = tableName;
        this.modelName =  tableNameConvertUpperCamel(tableName);
    }

    TableMapper (String tableName,String modelName){
        this.tableName = tableName;
        if(!StringUtils.isEmpty(modelName)){
            this.modelName = modelName ;
        }else {
            this.modelName =  tableNameConvertUpperCamel(tableName);
        }
    }

    private  String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }

}