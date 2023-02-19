package cn.edu.zhku.service;

import cn.edu.zhku.bo.LineStatsQuery;
import cn.edu.zhku.enmu.StatsTimeUnit;
import cn.edu.zhku.vo.BaseDataStatsVO;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * 折线图统计业务接口
 *
 * @author jintao
 * @version [产品/模块]
 * @see [相关类/方法]
 * @since [JDK 1.8]
 * <p>
 * [2022/10/26 14:42]
 */
public interface ILineStatsService {

    /**
     * 过滤日期时间，如：
     *
     * date = '2022-10-27 10:19:53 456'，timeUnit = 'D'
     * 则：
     * from -->  2022-10-27 00:00:00 000
     * to   -->  2022-10-27 23:59:59 999
     *
     * @author jintao
     * @date 2022/10/27 10:18
     */
    Date[] dateTimeFilter(Date from, Date to);

    /**
     * 获取日期时间查询条件，如
     *
     * Expressions.stringTemplate(DATE_FORMAT({0}, '%Y-%m-%d'), {path})
     *
     * @author jintao
     * @date 2022/10/28 20:36
     */
    default Expression<?> getDateTimeExpression(DateTimePath<?> path) {
        return Expressions.stringTemplate(getDateTimeSqlFunction(), path);
    }

    /**
     * 获取区间 [from, to] 的长度，如：
     *
     * [2022-10-25, 2022-10-28] 长度为 4
     *
     * @author jintao
     * @date 2022/10/28 20:33
     */
    int getDateTimeLength(Date from, Date to);

    /**
     * 获取 给定日期时间 的下一时间单位，如
     *
     * 2022-10-31 的下一天为 2022-11-01
     *
     * @author jintao
     * @date 2022/10/28 20:34
     */
    Date next(Date date);

    /**
     * 获取 日期时间sql函数，如：
     *
     * DATE_FORMAT({0}, '%Y-%m-%d %H')
     *
     * 对应 getDateFormat() 中的日期时间格式
     *
     * @author jintao
     * @date 2022/10/28 20:31
     *
     */
    String getDateTimeSqlFunction();

    /**
     * 获取 日期输出格式模板，如：
     *
     * yyyy-MM-dd HH
     *
     * 对应 getMysqlFunc() 中的日期时间格式
     *
     * @author jintao
     * @date 2022/10/28 20:40
     */
    String getDateTimeOutputFormat();

    /**
     * 获取 日期输入格式模板，用于MVC参数接收
     *
     * @author jintao
     * @date 2023/2/18 00:56
     */
    String[] getDateTimeInputFormat();

    /**
     * 获取 日期时间长度限制，作用于查询限制
     *
     * @author jintao
     * @date 2022/10/28 20:42
     */
    int getDateTimeLengthLimit();

    /**
     * 获取 日期时间单位，用于结果展示
     *
     * @author jintao
     * @date 2022/11/4 10:26
     */
    default String getDateTimeUnit() {
        return "";
    }

    /**
     * 获取 统计单位
     *
     * @author jintao
     * @date 2022/11/4 10:26
     */
    StatsTimeUnit getStatsTimeUnit();

    /**
     * 获取 统计列表
     *
     * @author jintao
     * @date 2022/10/31 11:49
     */
    <S, T> List<BaseDataStatsVO<T>> getStatsList(Date from, Date to, LineStatsQuery<S> query, T emptyVal, Function<List<S>, T> dataHandler);

}
