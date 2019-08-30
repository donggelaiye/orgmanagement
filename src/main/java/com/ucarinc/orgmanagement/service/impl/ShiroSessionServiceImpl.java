package com.ucarinc.orgmanagement.service.impl;

import com.ucarinc.orgmanagement.service.IShiroSessionService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * description:
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @version 1.0
 * @date 2019-08-16 15:39
 */
@Service
public class ShiroSessionServiceImpl implements IShiroSessionService {
    @Autowired
    @Qualifier("ehcacheSessionDao")
    SessionDAO sessionDao;
    private Logger logger = LoggerFactory.getLogger(ShiroSessionServiceImpl.class);

    @Override
    public void killSessionByLoginAccount(String loginAccount) {
        logger.info("即将执行Session清除操作");
        Collection<Session> sessions = sessionDao.getActiveSessions();
        for (Session session : sessions) {
            if (session.getAttribute("loginAccount") != null) {
                if (session.getAttribute("loginAccount").equals(loginAccount)) {
                    logger.info("已定位到账号相关Session,即将杀死该Session");
                    session.stop();
                    logger.info("已杀死指定Session");
                    break;
                }
            }
        }
    }
}
