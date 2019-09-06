package whut.service;

import whut.utils.ResponseData;

public interface AccountCheckService {

	ResponseData search(Integer type, Integer correlationId, String transactionCode, String thirdPartyNo);

}
