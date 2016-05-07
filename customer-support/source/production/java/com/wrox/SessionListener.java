package com.wrox;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebListener
/**
 * 使用监听器检测会话的变化
 *
 */
public class SessionListener implements HttpSessionListener, HttpSessionIdListener
{
    private SimpleDateFormat formatter =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

    @Override
    // 会话创建，也就是登录成功，则向session registry中注册该session，以便统计各种信息
    public void sessionCreated(HttpSessionEvent e)
    {
        System.out.println(this.date() + ": Session " + e.getSession().getId() +
                " created.");
        SessionRegistry.addSession(e.getSession());
    }

    @Override
    // 会话销毁，一般是登录注销，即调用invalidate方法注销session，或者会话超时，默认会话时间是30min，可在web.xml中配置
    public void sessionDestroyed(HttpSessionEvent e)
    {
        System.out.println(this.date() + ": Session " + e.getSession().getId() +
                " destroyed.");
        SessionRegistry.removeSession(e.getSession());
    }

    @Override
    // sessionID改变出发此方法，同步更新到会话注册类
    public void sessionIdChanged(HttpSessionEvent e, String oldSessionId)
    {
        System.out.println(this.date() + ": Session ID " + oldSessionId +
                " changed to " + e.getSession().getId());
        SessionRegistry.updateSessionId(e.getSession(), oldSessionId);
    }

    private String date()
    {
        return this.formatter.format(new Date());
    }
}
