package com.wzrd.m.db.manger;

import android.content.Context;

import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.db.GreenDaoHelper;
import com.wzrd.m.db.gen.DaoSession;
import com.wzrd.m.db.gen.TSYSCONTANTSDao;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17.
 */
public class ContactsManager {
    private TSYSCONTANTSDao mUserDao;
    private static ContactsManager mUserManager;

    public ContactsManager(Context context) {
        DaoSession daoSession = GreenDaoHelper.getInstance().getDaoSession(context);
        mUserDao = daoSession.getTSYSCONTANTSDao();
    }

    public static ContactsManager getInstance(Context context) {
        if (mUserManager == null) {
            mUserManager = new ContactsManager(context);
        }
        return mUserManager;
    }

    /**
     * 完成对数据库表的插入操作
     *
     * @param User
     * @return
     */
    public boolean insertUser(TSYSCONTANTS User) {
        boolean flag = false;
        flag = mUserDao.insertOrReplace(User) != -1 ? true : false;
        return flag;
    }

    /**
     * 完成对数据库的多次插入
     * 完成对数据库的多次插入
     *
     * @param Users
     */
    public void insertMultUser(final List<TSYSCONTANTS> Users) {
        mUserDao.insertOrReplaceInTx(Users);
    }

    /**
     * 更新对user某一条记录的修改
     *
     * @param User
     */
    public void updateUser(TSYSCONTANTS User) {
        mUserDao.update(User);
    }


    /**
     * 删除一条数据
     *
     * @param User
     */
    public void deleteUser(TSYSCONTANTS User) {
        mUserDao.delete(User);
    }

    /**
     * 返回多行记录
     *
     * @return
     */
    public List<TSYSCONTANTS> getAllUser() {
        return mUserDao.loadAll();
    }

//    public List<User> getByF_FORM_IDs(ArrayList<String> F_FORM_IDs){
//        QueryBuilder<User> builder = mUserDao.queryBuilder();
//        QueryBuilder<User> Users = builder.where(UserDao.Properties.F_FORM_ID.in(F_FORM_IDs));
//        return Users.list();
//    }



    public void clear(){
        mUserDao.deleteAll();
    }


}
