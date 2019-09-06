package whut.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import whut.service.StateTaxService;
import whut.utils.ResponseData;

@Controller
@RequestMapping(value = "/state/tax")
public class StateTaxController {
	@Autowired
	private StateTaxService stateTaxService;
	
	//查看所有州的税率【getStateTaxList】
	@RequestMapping(value="/getStateTaxList", method = RequestMethod.GET)
	public @ResponseBody ResponseData getStateTaxList(Integer pageindex, Integer pagesize) {
		return stateTaxService.getStateTaxList(pageindex,pagesize);
	}
	
	//根据州税的id查看对应州的税率【getStateTaxById】
	@RequestMapping(value="/getStateTaxById", method = RequestMethod.GET)
	public @ResponseBody ResponseData getStateTaxById(String id) {
		return stateTaxService.getStateTaxById(id);
	}
	
	//根据州的名称查看对应州的税率【getStateTaxByName】
	@RequestMapping(value="/getStateTaxByName", method = RequestMethod.GET)
	public @ResponseBody ResponseData getStateTaxByName(String name) {
		return stateTaxService.getStateTaxByName(name);
	}
	
	//根据州税的id修改州税【modifyStateTax】
	@RequestMapping(value="/modifyStateTax", method = RequestMethod.GET)
	public @ResponseBody ResponseData modifyStateTax(String id,String taxRate) {
		return stateTaxService.modifyStateTax(id,taxRate);
	}
}
