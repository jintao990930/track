package site.lianwu.mybatis.plus.query.processor;

import site.lianwu.mybatis.plus.query.reflection.FieldGetters;
import site.lianwu.mybatis.plus.query.model.QueryClass;
import site.lianwu.mybatis.plus.query.model.QueryField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 抽象查询类处理器，关联多个查询字段处理器，提供对{@link QueryClass}的通用解析实现{@link #doExtractClass(Class, boolean)}
 *
 * @author LianWu
 */
public abstract class BaseQueryClassProcessor implements QueryClassProcessor {

    /**
     * 查询字段处理器集合，提取和解析查询字段
     */
    protected final Collection<QueryFieldProcessor> queryFieldProcessors;

    /**
     * 默认构造方法，初始化查询字段处理器集合，默认包含{@link SimpleQueryFieldProcessor}和{@link NestedQueryFieldProcessor}
     */
    public BaseQueryClassProcessor() {
        queryFieldProcessors = new ArrayList<>(2);
        queryFieldProcessors.add(new SimpleQueryFieldProcessor());
        queryFieldProcessors.add(new NestedQueryFieldProcessor(this));
    }

    /**
     * 构造方法，允许传入自定义的查询字段处理器集合
     *
     * @param queryFieldProcessors 查询字段处理器的集合
     */
    public BaseQueryClassProcessor(Collection<QueryFieldProcessor> queryFieldProcessors) {
        this.queryFieldProcessors = queryFieldProcessors;
    }

    /**
     * 执行提取查询类的信息
     * <br>
     * 该方法遍历类的所有字段，委托查询字段处理器来提取查询字段，并将结果封装在查询类中
     *
     * @param clz 待提取查询信息的类
     * @param includeInheritedFields 是否包括继承字段
     * @return 提取后的查询类信息
     */
    protected QueryClass doExtractClass(Class<?> clz, boolean includeInheritedFields) {
        List<Field> declaredFields = FieldGetters.getDeclaredFields(clz, includeInheritedFields);
        List<QueryField> queryFields = new ArrayList<>();
        declaredFields.forEach(field ->
            queryFieldProcessors.forEach(processor -> processor.extract(field).ifPresent(queryFields::add))
        );
        return new QueryClass(clz, includeInheritedFields, queryFields);
    }

}
