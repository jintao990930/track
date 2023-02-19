package cn.edu.zhku.service;

import cn.edu.zhku.bo.LineStatsQuery;
import cn.edu.zhku.vo.BaseDataStatsVO;
import cn.hutool.core.date.DateUtil;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractLineStatsService implements ILineStatsService {

    @Resource
    protected JPAQueryFactory queryFactory;
    @Resource
    private CommLineStatsService commLineStatsService;

    @PostConstruct
    private void expose() {
        commLineStatsService.registerService(getStatsTimeUnit(), this);
    }

    @Override
    public <S, T> List<BaseDataStatsVO<T>> getStatsList(Date from, Date to, LineStatsQuery<S> query, T emptyVal, Function<List<S>, T> dataHandler) {
        Expression<?> dateExp = getDateTimeExpression(query.getDateTimePath());
        query.setWhereArg(from, to);
        Map<String, List<S>> map = queryFactory.select(dateExp, query.getSelectArg())
                .from(query.getFromArg())
                .where(query.getWhereArg())
                .groupBy(dateExp)
                .groupBy(query.getGroupByArg())
                .fetch().stream()
                .filter(t -> Objects.nonNull(t.get(dateExp)) && Objects.nonNull(t.get(query.getSelectArg())))
                .collect(Collectors.groupingBy(
                        t -> "" + t.get(dateExp),
                        Collectors.mapping(t -> t.get(query.getSelectArg()), Collectors.toList())));
        return getStatsList(from, to, map, emptyVal, dataHandler);
    }

    protected <S, T> List<BaseDataStatsVO<T>> getStatsList(Date from, Date to, Map<String, List<S>> src, T emptyVal, Function<List<S>, T> dataHandler) {
        int length = getDateTimeLength(from, to);
        List<BaseDataStatsVO<T>> res = new ArrayList<>(length);
        Date p = (Date) from.clone();
        while (length-- > 0) {
            String dateTime = DateUtil.format(p, getDateTimeOutputFormat());
            res.add(BaseDataStatsVO.<T>builder()
                            .date(p)
                            .strDate(dateTime + getDateTimeUnit())
                            .data(Optional.ofNullable(src.get(dateTime))
                                    .map(dataHandler).orElse(emptyVal))
                    .build());
            p = next(p);
        }
        return res;
    }

}
