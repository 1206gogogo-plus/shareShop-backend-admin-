package whut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import whut.service.AccountCheckService;
import whut.service.MemberInfoService;
import whut.utils.ResponseData;

@Controller
@RequestMapping(value = "/check")
public class AccountCheckController {
	
	@Autowired
	private AccountCheckService accountCheckService;
	
	//通过交易号（商城或第三方）、交易类型和关联id查询信息
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody ResponseData search(Integer type, Integer correlationId, String transactionCode, String thirdPartyNo) {
		return  accountCheckService.search(type, correlationId, transactionCode, thirdPartyNo);
	}

}
