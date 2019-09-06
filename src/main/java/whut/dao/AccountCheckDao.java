package whut.dao;


import java.util.List;
import java.util.Map;

import whut.pojo.AccountCheck;

public interface AccountCheckDao {

	//新增交易核对信息
	void add(AccountCheck accountCheck);

	//查找记录map:Integer type, Integer correlationId, String transactionCode, String thirdPartyNo,其中type必选和其它条件and关系，另外3个条件or关系
	List<AccountCheck> search(Map<String, Object> map);

}
