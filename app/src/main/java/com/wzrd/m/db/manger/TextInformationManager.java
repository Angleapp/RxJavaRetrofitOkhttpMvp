package com.wzrd.m.db.manger;

import android.content.Context;

import com.wzrd.m.been.TEXTIFORMATION;
import com.wzrd.m.db.GreenDaoHelper;
import com.wzrd.m.db.gen.DaoSession;
import com.wzrd.m.db.gen.TEXTIFORMATIONDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class TextInformationManager {
    private TEXTIFORMATIONDao mUserDao;
    private static TextInformationManager mUserManager;

    public TextInformationManager(Context context) {
        DaoSession daoSession = GreenDaoHelper.getInstance().getDaoSession(context);

        mUserDao = daoSession.getTEXTIFORMATIONDao();
    }

    public static TextInformationManager getInstance(Context context) {
        if (mUserManager == null) {
            mUserManager = new TextInformationManager(context);
        }
        return mUserManager;
    }

    /**
     * 完成对数据库表的插入操作
     *
     * @param User
     * @return
     */
    public boolean insertUser(TEXTIFORMATION User) {
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
    public void insertMultUser(final List<TEXTIFORMATION> Users) {
        mUserDao.insertOrReplaceInTx(Users);
    }

    /**
     * 更新对user某一条记录的修改
     *
     * @param User
     */
    public void updateUser(TEXTIFORMATION User) {
        mUserDao.update(User);
    }


    /**
     * 删除一条数据
     *
     * @param User
     */
    public void deleteUser(TEXTIFORMATION User) {
        mUserDao.delete(User);
    }
    /**
     * 根据id删除一条数据
     *
     * @param id
     */
    public void deleteid(String id ) {
        mUserDao.deleteByKey(id);
    }
    /**
     * 返回多行记录
     *
     * @return
     */
    public List<TEXTIFORMATION> getAllUser() {
        return mUserDao.loadAll();
    }

    public List<TEXTIFORMATION> getByid(String id){
        QueryBuilder<TEXTIFORMATION> builder = mUserDao.queryBuilder();
        QueryBuilder<TEXTIFORMATION> Users = builder.where(TEXTIFORMATIONDao.Properties.T_pm_id.in(id));
        return Users.list();
    }


    public List<TEXTIFORMATION> getBytype(String type){
        QueryBuilder<TEXTIFORMATION> builder = mUserDao.queryBuilder();
        QueryBuilder<TEXTIFORMATION> Users = builder.where(TEXTIFORMATIONDao.Properties.T_pm_type.eq(type)).orderDesc(TEXTIFORMATIONDao.Properties.T_pm_modify_time);
        return Users.list();
    }




    public void clear(){
        mUserDao.deleteAll();
    }


}
