package site.lianwu.dubboprovider.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.lianwu.dubbointerface.IBillService;
import site.lianwu.dubbointerface.vo.BillVO;

@RestController
@RequestMapping("/bills")
public class BillController {

    @DubboReference(check = false)
    private IBillService billService;

    @GetMapping("/{id}")
    public BillVO getBill(@PathVariable(name = "id") String id) {
        return billService.getBill(id);
    }

}
