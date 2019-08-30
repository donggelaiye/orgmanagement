package com.ucarinc.orgmanagement.dao;

import com.ucarinc.orgmanagement.entity.LoginHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * description:登录历史记录表Dao接口
 *
 * @author dihan.chen(dihan.chen @ ucarinc.com)
 * @date 2019/8/13 9:19
 * @return
 */
@Mapper
@Repository
public interface LoginHistoryMapper {

    /**
     * description:插入
     *
     * @param record
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/13 9:46
     */
    int insert(LoginHistory record);

    int insertFirstTimeLoginInfo(@Param("loginAccount") String loginAccount,@Param("createEmpId")String createEmpId);

    /**
     * description:依照登录账号查询
     *
     * @param loginAccount 登录账号
     * @return com.ucarinc.orgmanagement.entity.LoginHistory
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/13 9:46
     */
    LoginHistory selectByLoginAccount(@Param("loginAccount") String loginAccount);

    /**
     * description:依照登录账号更新
     *
     * @param record 实体
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/13 9:46
     */
    int updateByLoginAccount(LoginHistory record);

    /**
     * description:更新账号登陆时间,修改人,修改时间
     *
     * @param loginAccount 登录账号
     * @param modifyEmpId 操作人Id
     * @return int
     * @author dihan.chen(dihan.chen @ ucarinc.com)
     * @date 2019/8/13 9:55
     */
    int updateLastLoginTimeByLoginAccount(@Param("loginAccount") String loginAccount, @Param("modifyEmpId") Long modifyEmpId);
}
