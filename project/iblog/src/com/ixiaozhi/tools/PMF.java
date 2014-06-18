package com.ixiaozhi.tools;

/**
 * PersistenceManagerFactory　PMF类
 * 周家俊  personal@zhoujiajun.com
 */
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private PMF() {
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}