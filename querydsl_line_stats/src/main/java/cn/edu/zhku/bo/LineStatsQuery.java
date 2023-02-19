package cn.edu.zhku.bo;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * 折线图统计查询 Builder
 *
 * @author jintao
 * @version [产品/模块]
 * @see [相关类/方法]
 * @since [JDK 1.8]
 * <p>
 * [2022/10/28 20:30]
 */
public class LineStatsQuery<T> {

    /**
     * 查询结果，支持 单字段(qXxx.yy)，bean封装(Projections.bean())
     */
    private Expression<T> res;

    /**
     * 表
     */
    private EntityPath<?>[] table;

    /**
     * 查询条件
     */
    private Predicate exp;

    /**
     * 支持 DateTimePath<Date> 与 DateTimePath<Instant>
     * 参与 dateTimePath.between(from, to) 查询条件的构建
     */
    private DateTimePath<?> dateTimePath;

    private Expression<?>[] groupBy;

    public Expression<T> getSelectArg() {
        return res;
    }

    public EntityPath<?>[] getFromArg() {
        return table;
    }

    public Predicate getWhereArg() {
        return exp;
    }

    public void setWhereArg(Date from, Date to) {
        if (Objects.equals(dateTimePath.getType(), Date.class)) {
            exp = ((DateTimePath<Date>) dateTimePath).between(from, to).and(exp);
        } else {  // Instant.class
            exp = ((DateTimePath<Instant>) dateTimePath).between(from.toInstant(), to.toInstant()).and(exp);
        }
    }

    public DateTimePath<?> getDateTimePath() {
        return dateTimePath;
    }

    public Expression<?>[] getGroupByArg() {
        return groupBy;
    }

    public static <T> StatsQueryBuilder<T> builder() {
        return new StatsQueryBuilder<>();
    }

    public static class StatsQueryBuilder<T> {

        private LineStatsQuery<T> query;

        private StatsQueryBuilder() {
            query = new LineStatsQuery<>();
            query.groupBy = new Expression[0];
        }

        public StatsQueryBuilder<T> select(Expression<T> res) {
            query.res = res;
            return this;
        }

        public StatsQueryBuilder<T> from(EntityPath<?>... table) {
            query.table = table;
            return this;
        }

        public StatsQueryBuilder<T> where(Predicate exp) {
            query.exp = exp;
            return this;
        }

        public StatsQueryBuilder<T> datePath(DateTimePath<Date> path) {
            query.dateTimePath = path;
            return this;
        }

        public StatsQueryBuilder<T> instantPath(DateTimePath<Instant> path) {
            query.dateTimePath = path;
            return this;
        }

        public StatsQueryBuilder<T> groupBy(Expression<?>... arg) {
            query.groupBy = arg;
            return this;
        }

        public LineStatsQuery<T> build() {
            return query;
        }

    }

}
