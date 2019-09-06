package whut.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import whut.dao.AccountCheckDao;
import whut.pojo.AccountCheck;
import whut.service.AccountCheckService;
import whut.utils.ResponseData;

@Service
public class AccountCheckServiceImpl implements AccountCheckService {
	
	@Autowired
	private AccountCheckDao accountCheckDao;

	@Override
	public ResponseData search(Integer type, Integer correlationId, String transactionCode, String thirdPartyNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("correlationId", correlationId);
		map.put("transactionCode", transactionCode);
		map.put("thirdPartyNo", thirdPartyNo);
		List<AccountCheck> checkList = accountCheckDao.search(map);
		if(checkList.isEmpty()) {
			return new ResponseData(400,"no data satify request",null);
		}else {
			return new ResponseData(checkList);
		}
	}

}
