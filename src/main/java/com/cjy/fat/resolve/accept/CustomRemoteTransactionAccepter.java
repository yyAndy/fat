package com.cjy.fat.resolve.accept;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cjy.fat.data.TransactionContent;

/**
 * 自定义服务api的远程信息接收器
 * @author cjy
 *
 */
@Component
@ConditionalOnBean(CustomRemoteDataAdapter.class)
public class CustomRemoteTransactionAccepter implements RemoteTransactionAccepter{
	
	@Autowired
	ApplicationContext context;
	
	@Value("${spring.application.name}")
	String serviceName;
	
	public void acceptRemoteTransactionData(){
		//获取多个自定义服务api的adapter
		String[] adapters = context.getBeanNamesForType(CustomRemoteDataAdapter.class);
		if(adapters != null&& adapters.length > 0) {
			for(int i = 0 ; i < adapters.length ;i ++ ) {
				CustomRemoteDataAdapter adapter = (CustomRemoteDataAdapter)context.getBean(adapters[i]);
				
				// 远程数据容器
				Map<String , String> remoteDataMap = adapter.convertRemoteDataToMap();
				// 获取rootTxKey
				String rootTxKey = remoteDataMap.get(TransactionContent.STR_ROOT_TX_KEY);
				if(StringUtils.isNotBlank(rootTxKey)){
					TransactionContent.setRootTxKey(rootTxKey);
				}
				
				TransactionContent.setServiceId(serviceName);
			}
		}
	}
	
}
