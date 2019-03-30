package com.zjf.fastdfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

@Configuration
@ConfigurationProperties(prefix = "fdfs")
@ConditionalOnClass(value = { StorageClient.class, StorageServer.class })
@ConditionalOnProperty(name = "fdfs.enabled", havingValue = "true")
public class FDFSAutoConfiguration {
	private static final Log log = LogFactory.getLog(EnableFDFS.class);
	static {
		try {
			File file = ResourceUtils.getFile("classpath:config/client.conf");
			ClientGlobal.init(file.getAbsolutePath());
			log.info("FastDFS init success.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
		} catch (MyException e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	@Bean
	public FDFSUtil FDFSUtil() {
		return new FDFSUtil();
	}

	private boolean enabled;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
