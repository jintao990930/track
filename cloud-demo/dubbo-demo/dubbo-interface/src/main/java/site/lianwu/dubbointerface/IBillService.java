package site.lianwu.dubbointerface;

import site.lianwu.dubbointerface.vo.BillVO;

public interface IBillService {

    /**
     * 获取账单
     * @param id
     * @return
     */
    BillVO getBill(String id);

}
