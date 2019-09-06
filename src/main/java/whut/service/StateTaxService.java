package whut.service;

import whut.utils.ResponseData;

public interface StateTaxService {

	ResponseData getStateTaxList(Integer pageindex, Integer pagesize);

	ResponseData getStateTaxById(String id);

	ResponseData getStateTaxByName(String name);

	ResponseData modifyStateTax(String id, String taxRate);

}
