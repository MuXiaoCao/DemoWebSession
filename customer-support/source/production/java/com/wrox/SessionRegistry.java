package com.wrox;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 会话注册类，用户保存所有会话的一些公共信息
 * 提供增删改查的方法
 */
public final class SessionRegistry {
	// session会话列表
	private static final Map<String, HttpSession> SESSIONS = new Hashtable<>();

	public static void addSession(HttpSession session) {
		SESSIONS.put(session.getId(), session);
	}

	public static void updateSessionId(HttpSession session, String oldSessionId) {
		synchronized (SESSIONS) {
			SESSIONS.remove(oldSessionId);
			addSession(session);
		}
	}

	public static void removeSession(HttpSession session) {
		SESSIONS.remove(session.getId());
	}

	public static List<HttpSession> getAllSessions() {
		return new ArrayList<>(SESSIONS.values());
	}

	public static int getNumberOfSessions() {
		return SESSIONS.size();
	}

	private SessionRegistry() {

	}
}
