package com.cjy.fat.resolve.accepter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cjy.fat.data.TransactionContent;

@Component
@ConditionalOnClass({EnableDiscoveryClient.class})
public class SpringCloudRemoteTransactionAccepter implements RemoteTransactionAccepter{
	
	@Value("${spring.application.name}")
	String serviceName;

	@Override
	public void acceptRemoteTransactionData() {

	    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	    if(sra == null) {
	    	return;
	    }
	    HttpServletRequest request = sra.getRequest();
	    
		String rootTxKey = request.getHeader(TransactionContent.STR_ROOT_TX_KEY);
		if(StringUtils.isNotBlank(rootTxKey)){
			TransactionContent.setRootTxKey(rootTxKey);
		}
		
		TransactionContent.setServiceId(serviceName);
	}
	
	
	
	
}
