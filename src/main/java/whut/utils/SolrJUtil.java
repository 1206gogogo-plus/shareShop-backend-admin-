package whut.utils;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;


/**
 * 搜索服务器工具类
 * @author chen cheng
 *
 */
@Service
public class SolrJUtil {
	

	private static HttpSolrClient solrClient;
	private static String coreName =  "products_core";//创建的内核名
	static {
		String serverUrl = "http://112.74.165.55:8983/solr";
		solrClient = new HttpSolrClient.Builder(serverUrl).withConnectionTimeout(10000).withSocketTimeout(60000).build();
	}
	
	public static HttpSolrClient getSolrClient() {
		return solrClient;
	}
	
	public static String getCoreName() {
		return coreName;
	}
	
	/**
	 * @param page 页数
	 * @param rows	每页内容数
	 * @param searchWord	查询关键字（可以包含查询字段*:*）
	 * @param queryItem		查询需要返回的字段列表
	 * @param sortAsc	递增排序的字段
	 * @param sortDesc	递减排序的字段
	 * @param highlightField	设置高亮的字段
	 * @return
	 */
	public static String search(int page, int rows, String searchWord, String[] queryItem, String sortAsc, 
			String sortDesc, String highlightField) {
        //创建查询对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        if(searchWord.isEmpty()) {searchWord = "*:*";}
        solrQuery.setQuery(searchWord);
        //设置分页
        solrQuery.setStart((page - 1) * rows);
        solrQuery.setRows(rows);
        //设置默认搜素域
        solrQuery.set("df", "Ptitle");
        if(sortAsc != null) {solrQuery.setSort(sortAsc, SolrQuery.ORDER.asc);}
        if(sortDesc != null) {solrQuery.addSort(sortDesc, SolrQuery.ORDER.desc);}
        if(queryItem != null) {
        	solrQuery.setFields(queryItem);
        }

        //设置高亮显示
        if(!(highlightField == null)) {
            solrQuery.setHighlight(true);
            solrQuery.addHighlightField(highlightField);
            solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
            solrQuery.setHighlightSimplePost("</em>");
        }

        

        //根据查询条件查询索引库
        QueryResponse queryResponse = null;
		try {
			queryResponse = solrClient.query(coreName,solrQuery);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        SolrDocumentListForReturn solrDocumentListForReturn = new SolrDocumentListForReturn(solrDocumentList);
        System.out.println(solrDocumentListForReturn);
        return solrDocumentListForReturn.toString();
	}
	

	

}
