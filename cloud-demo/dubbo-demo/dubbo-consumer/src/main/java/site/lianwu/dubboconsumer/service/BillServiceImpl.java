package site.lianwu.dubboconsumer.service;

import org.apache.dubbo.config.annotation.DubboService;
import site.lianwu.dubbointerface.IBillService;
import site.lianwu.dubbointerface.UserContexts;
import site.lianwu.dubbointerface.vo.BillVO;

@DubboService
public class BillServiceImpl implements IBillService {

    @Override
    public BillVO getBill(String id) {
        UserContexts.User user = UserContexts.getUser();

        BillVO bill = new BillVO();
        bill.setId(id);
        if (user != null) {
            bill.setDetails(user.toString());
        }
        return bill;
    }

}
