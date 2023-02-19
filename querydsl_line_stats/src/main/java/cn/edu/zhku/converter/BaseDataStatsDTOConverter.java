package cn.edu.zhku.converter;

import cn.edu.zhku.dto.BaseDataStatsDTO;
import cn.edu.zhku.enmu.StatsTimeUnit;
import cn.edu.zhku.service.CommLineStatsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Objects;

@Component
public class BaseDataStatsDTOConverter extends AbstractHttpMessageConverter<BaseDataStatsDTO> {

    @Resource
    private CommLineStatsService commLineStatsService;

    public BaseDataStatsDTOConverter() {
        super(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return BaseDataStatsDTO.class.isAssignableFrom(clazz);
    }

    @Override
    protected BaseDataStatsDTO readInternal(Class<? extends BaseDataStatsDTO> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String json = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(json);
        } catch (JSONException e) {
            throw new InputMismatchException("[BaseDataStatsDTO]JSON转换异常 ...");
        }
        StatsTimeUnit timeUnit;
        String from, to;
        if (Objects.isNull(timeUnit = jsonObject.getObject("timeUnit", StatsTimeUnit.class))) {
            throw new IllegalArgumentException("[BaseDataStatsDTO]请提供timeUnit参数 ...");
        }
        if (StringUtils.isBlank(from = jsonObject.getString("from"))) {
            throw new IllegalArgumentException("[BaseDataStatsDTO]请提供from参数 ...");
        }
        if (StringUtils.isBlank(to = jsonObject.getString("to"))) {
            throw new IllegalArgumentException("[BaseDataStatsDTO]请提供to参数 ...");
        }
        BaseDataStatsDTO dto = new BaseDataStatsDTO();
        dto.setTimeUnit(jsonObject.getObject("timeUnit", StatsTimeUnit.class));
        String[] dateInputFormat = commLineStatsService.getDateInputFormat(timeUnit);
        try {
            dto.setFrom(DateUtils.parseDate(from, dateInputFormat));
        } catch (ParseException e) {
            throw new IllegalArgumentException("[BaseDataStatsDTO]from参数格式错误 ...");
        }
        try {
            dto.setTo(DateUtils.parseDate(to, dateInputFormat));
        } catch (ParseException e) {
            throw new IllegalArgumentException("[BaseDataStatsDTO]to参数格式错误 ...");
        }
        return dto;
    }

    @Override
    protected void writeInternal(BaseDataStatsDTO baseDataStatsDTO, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    }

}
