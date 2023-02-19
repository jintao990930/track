package cn.edu.zhku.service.impl;

import cn.edu.zhku.enmu.StatsTimeUnit;
import cn.edu.zhku.service.AbstractLineStatsService;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Hour24LineStatsService extends AbstractLineStatsService {

    private final StatsTimeUnit STATS_TIME_UNIT = StatsTimeUnit.H_24;
    private final DateField DATE_FIELD = DateField.HOUR_OF_DAY;
    @Value("${querydsl.line-stats.service.hour24.sql-func:DATE_FORMAT({0}, '%H')}")
    private String sqlFunc;
    @Value("${querydsl.line-stats.service.hour24.output-format:HH}")
    private String outputFormat;
    @Value("${querydsl.line-stats.service.hour24.input-format:yyyy-MM-dd HH,yyyy/MM/dd HH}")
    private String[] inputFormat;
    @Value("${querydsl.line-stats.service.hour24.length-limit:24}")
    private int lengthLimit;

    @Override
    public Date[] dateTimeFilter(Date from, Date to) {
        return new Date[] {DateUtil.truncate(from, DATE_FIELD),
                DateUtil.ceiling(to, DATE_FIELD)};
    }

    @Override
    public int getDateTimeLength(Date from, Date to) {
        return (int) (DateUtil.between(from, to, DateUnit.HOUR) + 1);
    }

    @Override
    public Date next(Date date) {
        return DateUtils.addHours(date, 1);
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
    public String getDateTimeUnit() {
        return "时";
    }

    @Override
    public StatsTimeUnit getStatsTimeUnit() {
        return STATS_TIME_UNIT;
    }

}
