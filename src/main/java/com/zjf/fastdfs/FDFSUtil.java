package com.zjf.fastdfs;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.fastdfs.StorageClient;

public class FDFSUtil {
	private static Log log = LogFactory.getLog(FDFSUtil.class);
	private static ThreadLocal<StorageClient> threadLocal = new ThreadLocal<>();
	private static final String DEFAULT_FILE_EXT_NAME = "jpg";

	public String[] upload(byte[] image) {
		try {
			long start = System.currentTimeMillis();
			String values[] = getClient().upload_file(image, DEFAULT_FILE_EXT_NAME, null);
			long end = System.currentTimeMillis();
			log.debug("UPLOAD FILE SUCCESS USE TIME:" + (end - start) + ",RESULT:" + Arrays.asList(values));
			return values;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	public String[] upload(byte[] image, String fileExtName) {
		try {
			long start = System.currentTimeMillis();
			String values[] = getClient().upload_file(image, fileExtName, null);
			long end = System.currentTimeMillis();
			log.debug("UPLOAD FILE SUCCESS USE TIME:" + (end - start) + ",RESULT:" + Arrays.asList(values));
			return values;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	public byte[] download(String group, String path) {
		try {
			long start = System.currentTimeMillis();
			byte[] image = getClient().download_file(group, path);
			long end = System.currentTimeMillis();
			log.debug("DOWNLOAD FILE SUCCESS USE TIME:" + (end - start));
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return null;
	}

	public StorageClient getClient() {
		if (threadLocal.get() == null) {
			synchronized (threadLocal) {
				if (threadLocal.get() == null) {
					threadLocal.set(new StorageClient(null, null));
				}
			}
		}
		return threadLocal.get();
	}
}
