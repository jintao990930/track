package site.lianwu.dubbointerface.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 需实现序列化标识接口
 */
@Data
public class BillVO implements Serializable {

    private String id;

    private String details;

}
