package cn.edu.zhku.dto;

import cn.edu.zhku.enmu.StatsTimeUnit;
import lombok.Data;

import java.util.Date;

/**
 * 数据统计基本请求
 *
 * @author jintao
 * @version [产品/模块]
 * @see [相关类/方法]
 * @since [JDK 1.8]
 * <p>
 * [2022/10/26 14:06]
 */
@Data
public class BaseDataStatsDTO {

    private StatsTimeUnit timeUnit;

    private Date from;

    private Date to;

}
