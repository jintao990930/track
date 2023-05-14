package cn.doocom.mybatis.plus.ext.query.model;

import cn.doocom.mybatis.plus.ext.query.annotation.QueryColumn;
import cn.doocom.mybatis.plus.ext.query.consts.QueryConst;
import cn.doocom.mybatis.plus.ext.query.enums.Logic;
import cn.doocom.mybatis.plus.ext.query.function.BaseOperation;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.reflect.Field;

public class QueryBlock {

    private final Logic innerLogic;
    private final String column;
    private final BaseOperation operation;

    public QueryBlock(Logic innerLogic, String column, BaseOperation operation) {
        this.innerLogic = innerLogic;
        this.column = column;
        this.operation = operation;
    }

    public Logic getInnerLogic() {
        return innerLogic;
    }

    public String getColumn() {
        return column;
    }

    public BaseOperation getOperation() {
        return operation;
    }

    static QueryBlock convert(Field field, QueryColumn qc) {
        String column = qc.column();
        if (QueryConst.HUMP_2_UNDER_LINE_FLAG.equals(column)) {
            column = StringUtils.camelToUnderline(field.getName());
        }
        return new QueryBlock(qc.logic(), column, qc.value().getOperation());
    }

}
