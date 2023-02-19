package cn.edu.zhku.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 数据统计基本响应
 *
 * @author jintao
 * @version [产品/模块]
 * @see [相关类/方法]
 * @since [JDK 1.8]
 * <p>
 * [2022/10/26 12:07]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseDataStatsVO<T> {

    private Date date;

    private String strDate;

    private T data;

}
