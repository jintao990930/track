package cn.edu.zhku.service;

import cn.edu.zhku.bo.LineStatsQuery;
import cn.edu.zhku.dto.BaseDataStatsDTO;
import cn.edu.zhku.enmu.StatsTimeUnit;
import cn.edu.zhku.vo.BaseDataStatsVO;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class CommLineStatsService {

    private final Map<StatsTimeUnit, ILineStatsService> serviceMap = new HashMap<>(8);

    public int getDateTimeLength(StatsTimeUnit timeUnit, Date from, Date to) {
        return getService(timeUnit).getDateTimeLength(from, to);
    }

    public Date next(StatsTimeUnit timeUnit, Date date) {
        return getService(timeUnit).next(date);
    }

    public Date[] dateTimeFilter(StatsTimeUnit timeUnit, Date from, Date to) {
        return getService(timeUnit).dateTimeFilter(from, to);
    }

    public String getDateTimeFormat(StatsTimeUnit timeUnit) {
        return getService(timeUnit).getDateTimeOutputFormat();
    }

    public String getDateTimeUnit(StatsTimeUnit timeUnit) {
        return getService(timeUnit).getDateTimeUnit();
    }

    public String[] getDateInputFormat(StatsTimeUnit timeUnit) {
        return getService(timeUnit).getDateTimeInputFormat();
    }

    public boolean isQueryLimited(StatsTimeUnit timeUnit, Date from, Date to) {
        return isQueryLimited(timeUnit, from, to, getService(timeUnit).getDateTimeLengthLimit());
    }

    public boolean isQueryLimited(StatsTimeUnit timeUnit, Date from, Date to, int lengthLimit) {
        if (from.compareTo(to) > 0) {
            return true;
        }
        return getService(timeUnit).getDateTimeLength(from, to) > lengthLimit;
    }

    /**
     * 获取折线图统计结果，使用默认的查询长度限制参数
     *
     * @author jintao
     * @date 2022/10/28 20:48
     */
    public <T> List<BaseDataStatsVO<T>> getStatsList(BaseDataStatsDTO dto, LineStatsQuery<T> query, T emptyVal) {
        return getStatsList(dto, query, emptyVal, getService(dto.getTimeUnit()).getDateTimeLengthLimit());
    }

    /**
     * 获取折线图统计结果，自定义查询长度限制
     *
     * @author jintao
     * @date 2022/10/28 20:48
     */
    public <T> List<BaseDataStatsVO<T>> getStatsList(BaseDataStatsDTO dto, LineStatsQuery<T> query, T emptyVal, int lengthLimit) {
        return getStatsList(dto, query, emptyVal, list -> list.get(0), lengthLimit);
    }

    /**
     * 获取折线图统计结果，使用默认的查询长度限制参数
     *
     * @author jintao
     * @date 2022/10/28 20:48
     */
    public <S, T> List<BaseDataStatsVO<T>> getStatsList(BaseDataStatsDTO dto, LineStatsQuery<S> query, T emptyVal, Function<List<S>, T> dataHandler) {
        return getStatsList(dto, query, emptyVal, dataHandler, getService(dto.getTimeUnit()).getDateTimeLengthLimit());
    }

    /**
     * 获取折线图统计结果，自定义查询长度限制
     *
     * @author jintao
     * @date 2022/10/28 20:48
     */
    public <S, T> List<BaseDataStatsVO<T>> getStatsList(BaseDataStatsDTO dto, LineStatsQuery<S> query, T emptyVal, Function<List<S>, T> dataHandler, int lengthLimit) {
        Date[] fromTo = dateTimeFilter(dto.getTimeUnit(), dto.getFrom(), dto.getTo());
        Date from = fromTo[0], to = fromTo[1];
        if (isQueryLimited(dto.getTimeUnit(), from, to, lengthLimit)) {
            return Collections.emptyList();
        }
        return getService(dto.getTimeUnit()).getStatsList(from, to, query, emptyVal, dataHandler);
    }

    /**
     * 获取空折线图统计结果，默认长度限制
     *
     * @author jintao
     * @date 2022/11/4 16:14
     */
    public <T> List<BaseDataStatsVO<T>> getEmptyStatsList(BaseDataStatsDTO dto, T emptyVal) {
        return getEmptyStatsList(dto, emptyVal, getService(dto.getTimeUnit()).getDateTimeLengthLimit());
    }

    /**
     * 获取空折线图统计结果，自定义长度限制
     *
     * @author jintao
     * @date 2022/11/4 16:14
     */
    public <T> List<BaseDataStatsVO<T>> getEmptyStatsList(BaseDataStatsDTO dto, T emptyVal, int lengthLimit) {
        Date[] fromTo = dateTimeFilter(dto.getTimeUnit(), dto.getFrom(), dto.getTo());
        Date from = fromTo[0], to = fromTo[1];
        if (isQueryLimited(dto.getTimeUnit(), from, to, lengthLimit)) {
            return Collections.emptyList();
        }
        int length = getDateTimeLength(dto.getTimeUnit(), from, to);
        List<BaseDataStatsVO<T>> res = new ArrayList<>(length);
        Date p = (Date) from.clone();
        String dateTimeFormat = getDateTimeFormat(dto.getTimeUnit());
        String dateTimeUnit = getDateTimeUnit(dto.getTimeUnit());
        while (length-- > 0) {
            String dateTime = DateUtil.format(p, dateTimeFormat);
            res.add(BaseDataStatsVO.<T>builder()
                    .strDate(dateTime + dateTimeUnit)
                    .data(emptyVal)
                    .build());
            p = next(dto.getTimeUnit(), p);
        }
        return res;
    }

    void registerService(StatsTimeUnit timeUnit, ILineStatsService service) {
        serviceMap.put(timeUnit, service);
    }

    private ILineStatsService getService(StatsTimeUnit timeUnit) {
        return serviceMap.get(timeUnit);
    }

}
