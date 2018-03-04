package com.wzrd.m.db.manger;

import android.content.Context;

import com.wzrd.m.been.EmoticonCode;
import com.wzrd.m.db.GreenDaoHelper;
import com.wzrd.m.db.gen.DaoSession;
import com.wzrd.m.db.gen.EmoticonCodeDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class EmoticonCodeManager {
    private EmoticonCodeDao mEmoticonCodeDao;
    private static EmoticonCodeManager mEmoticonCodeManager;

    public EmoticonCodeManager(Context context) {
        DaoSession daoSession = GreenDaoHelper.getInstance().getDaoSession(context);

        mEmoticonCodeDao = daoSession.getEmoticonCodeDao();
    }

    public static EmoticonCodeManager getInstance(Context context) {
        if (mEmoticonCodeManager == null) {
            mEmoticonCodeManager = new EmoticonCodeManager(context);
        }
        return mEmoticonCodeManager;
    }

    /**
     * 完成对数据库表的插入操作
     *
     * @param emoticonCode
     * @return
     */
    public boolean insertEmoticonCode(EmoticonCode emoticonCode) {
        boolean flag = false;
        flag = mEmoticonCodeDao.insertOrReplace(emoticonCode) != -1 ? true : false;
        return flag;
    }

    /**
     * 完成对数据库的多次插入
     * 完成对数据库的多次插入
     *
     * @param emoticonCodes
     */
    public void insertMultEmoticonCode(final List<EmoticonCode> emoticonCodes) {
        mEmoticonCodeDao.insertOrReplaceInTx(emoticonCodes);
    }

    /**
     * 更新对EmoticonCode某一条记录的修改
     *
     * @param emoticonCode
     */
    public void updateEmoticonCode(EmoticonCode emoticonCode) {
        mEmoticonCodeDao.update(emoticonCode);
    }


    /**
     * 删除一条数据
     *
     * @param emoticonCode
     */
    public void deleteEmoticonCode(EmoticonCode emoticonCode) {
        mEmoticonCodeDao.delete(emoticonCode);
    }

    /**
     * 根据id删除一条数据
     *
     * @param id
     */
    public void deleteid(String id) {
        mEmoticonCodeDao.deleteByKey(id);
    }

    /**
     * 返回多行记录
     *
     * @return
     */
    public List<EmoticonCode> getAllEmoticonCode() {
        return mEmoticonCodeDao.loadAll();
    }

    public EmoticonCode findVideoContentById(String id) {
        QueryBuilder<EmoticonCode> builder = mEmoticonCodeDao.queryBuilder();
        List<EmoticonCode> list = builder.where(EmoticonCodeDao.Properties.Id.eq(id)).list();
        if (list != null) {
            EmoticonCode emoticonCode = list.get(0);
            return emoticonCode;
        } else {
            return null;
        }
    }

    public void clear() {
        mEmoticonCodeDao.deleteAll();
    }


}
