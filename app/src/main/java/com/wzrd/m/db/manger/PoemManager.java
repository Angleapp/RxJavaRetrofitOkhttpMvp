package com.wzrd.m.db.manger;

import android.content.Context;

import com.wzrd.m.been.Poem;
import com.wzrd.m.db.GreenDaoHelper;
import com.wzrd.m.db.gen.DaoSession;
import com.wzrd.m.db.gen.PoemDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17.
 */
public class PoemManager {
    private PoemDao mPoemDao;
    private static PoemManager mPoemManager;

    public PoemManager(Context context) {
        DaoSession daoSession = GreenDaoHelper.getInstance().getDaoSession(context);
        mPoemDao = daoSession.getPoemDao();
    }

    public static PoemManager getInstance(Context context) {
        if (mPoemManager == null) {
            mPoemManager = new PoemManager(context);
        }
        return mPoemManager;
    }

    /**
     * 完成对数据库表的插入操作
     *
     * @param poem
     * @return
     */
    public boolean insertPoem(Poem poem) {
        boolean flag = false;
        flag = mPoemDao.insertOrReplace(poem) != -1 ? true : false;
        return flag;
    }

    /**
     * 完成对数据库的多次插入
     * 完成对数据库的多次插入
     *
     * @param poems
     */
    public void insertMultPoem(final List<Poem> poems) {
        mPoemDao.insertOrReplaceInTx(poems);
    }

    /**
     * 更新对Poem某一条记录的修改
     *
     * @param poem
     */
    public void updatePoem(Poem poem) {
        mPoemDao.update(poem);
    }


    /**
     * 删除一条数据
     *
     * @param poem
     */
    public void deletePoem(Poem poem) {
        mPoemDao.delete(poem);
    }

    /**
     * 返回多行记录
     *
     * @return
     */
    public List<Poem> getAllPoem() {
        return mPoemDao.loadAll();
    }

    public void clear() {
        mPoemDao.deleteAll();
    }

    public Poem findPoemById(String id) {
        QueryBuilder<Poem> builder = mPoemDao.queryBuilder();
        List<Poem> list = builder.where(PoemDao.Properties.Id.eq(id)).list();
        if (list != null) {
            Poem poem = list.get(0);
            return poem;
        } else {
            return null;
        }
    }

    public Poem getLastPoem() {
        List<Poem> poems = mPoemDao.loadAll();
        if (poems != null) {
            return poems.get(poems.size() - 1);
        } else {
            return null;
        }
    }
}
