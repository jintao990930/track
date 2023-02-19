package cn.edu.zhku.service.impl;

import cn.edu.zhku.enmu.StatsTimeUnit;
import cn.edu.zhku.service.AbstractLineStatsService;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DayLineStatsService extends AbstractLineStatsService {

    private final StatsTimeUnit STATS_TIME_UNIT = StatsTimeUnit.D;
    private final DateField DATE_FIELD = DateField.DAY_OF_YEAR;
    @Value("${querydsl.line-stats.service.day.sql-func:DATE_FORMAT({0}, '%Y-%m-%d')}")
    private String sqlFunc;
    @Value("${querydsl.line-stats.service.day.output-format:yyyy-MM-dd}")
    private String outputFormat;
    @Value("${querydsl.line-stats.service.day.input-format:yyyy-MM-dd,yyyy/MM/dd}")
    private String[] inputFormat;
    @Value("${querydsl.line-stats.service.day.length-limit:92}")
    private int lengthLimit;

    @Override
    public Date[] dateTimeFilter(Date from, Date to) {
        return new Date[] {DateUtil.truncate(from, DATE_FIELD),
                DateUtil.ceiling(to, DATE_FIELD)};
    }

    @Override
    public int getDateTimeLength(Date from, Date to) {
        return (int) (DateUtil.betweenDay(from, to, false) + 1);
    }

    @Override
    public Date next(Date date) {
        return DateUtils.addDays(date, 1);
    }

    @Override
    public String getDateTimeSqlFunction() {
        return sqlFunc;
    }

    @Override
    public String getDateTimeOutputFormat() {
        return outputFormat;
    }

    @Override
    public String[] getDateTimeInputFormat() {
        return inputFormat;
    }

    @Override
    public int getDateTimeLengthLimit() {
        return lengthLimit;
    }

    @Override
    public StatsTimeUnit getStatsTimeUnit() {
        return STATS_TIME_UNIT;
    }

}
