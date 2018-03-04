package com.wzrd.m.db.manger;

import android.content.Context;

import com.wzrd.m.been.TextStyleCode;
import com.wzrd.m.db.GreenDaoHelper;
import com.wzrd.m.db.gen.DaoSession;
import com.wzrd.m.db.gen.TextStyleCodeDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class TextStyleCodeManager {
    private TextStyleCodeDao mTextStyleCodeDao;
    private static TextStyleCodeManager mTextStyleCodeManager;

    public TextStyleCodeManager(Context context) {
        DaoSession daoSession = GreenDaoHelper.getInstance().getDaoSession(context);

        mTextStyleCodeDao = daoSession.getTextStyleCodeDao();
    }

    public static TextStyleCodeManager getInstance(Context context) {
        if (mTextStyleCodeManager == null) {
            mTextStyleCodeManager = new TextStyleCodeManager(context);
        }
        return mTextStyleCodeManager;
    }

    /**
     * 完成对数据库表的插入操作
     *
     * @param textStyleCode
     * @return
     */
    public boolean insertTextStyleCode(TextStyleCode textStyleCode) {
        boolean flag = false;
        flag = mTextStyleCodeDao.insertOrReplace(textStyleCode) != -1 ? true : false;
        return flag;
    }

    /**
     * 完成对数据库的多次插入
     * 完成对数据库的多次插入
     *
     * @param textStyleCodes
     */
    public void insertMultTextStyleCode(final List<TextStyleCode> textStyleCodes) {
        mTextStyleCodeDao.insertOrReplaceInTx(textStyleCodes);
    }

    /**
     * 更新对TextStyleCode某一条记录的修改
     *
     * @param textStyleCode
     */
    public void updateTextStyleCode(TextStyleCode textStyleCode) {
        mTextStyleCodeDao.update(textStyleCode);
    }


    /**
     * 删除一条数据
     *
     * @param textStyleCode
     */
    public void deleteTextStyleCode(TextStyleCode textStyleCode) {
        mTextStyleCodeDao.delete(textStyleCode);
    }

    /**
     * 根据id删除一条数据
     *
     * @param id
     */
    public void deleteid(String id) {
        mTextStyleCodeDao.deleteByKey(id);
    }

    /**
     * 返回多行记录
     *
     * @return
     */
    public List<TextStyleCode> getAllTextStyleCode() {
        return mTextStyleCodeDao.loadAll();
    }

    public TextStyleCode findVideoContentById(String id) {
        QueryBuilder<TextStyleCode> builder = mTextStyleCodeDao.queryBuilder();
        List<TextStyleCode> list = builder.where(TextStyleCodeDao.Properties.Id.eq(id)).list();
        if (list != null) {
            TextStyleCode textStyleCode = list.get(0);
            return textStyleCode;
        } else {
            return null;
        }
    }

    public void clear() {
        mTextStyleCodeDao.deleteAll();
    }


}
