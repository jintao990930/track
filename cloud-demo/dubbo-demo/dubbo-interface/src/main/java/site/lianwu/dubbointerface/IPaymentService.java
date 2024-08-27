package site.lianwu.dubbointerface;

/**
 * 支付接口
 */
public interface IPaymentService {

    /**
     * 根据订单ID支付
     * @param id
     * @return
     */
    String pay(String id);

}
