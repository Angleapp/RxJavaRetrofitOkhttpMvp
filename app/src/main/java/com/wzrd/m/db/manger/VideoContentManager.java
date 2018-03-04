package com.wzrd.m.db.manger;

import android.content.Context;

import com.wzrd.m.been.VideoContent;
import com.wzrd.m.db.GreenDaoHelper;
import com.wzrd.m.db.gen.DaoSession;
import com.wzrd.m.db.gen.VideoContentDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17.
 */

public class VideoContentManager {
    private VideoContentDao mVideoContentDao;
    private static VideoContentManager mPoemManager;

    public VideoContentManager(Context context) {
        DaoSession daoSession = GreenDaoHelper.getInstance().getDaoSession(context);
        mVideoContentDao = daoSession.getVideoContentDao();
    }

    public static VideoContentManager getInstance(Context context) {
        if (mPoemManager == null) {
            mPoemManager = new VideoContentManager(context);
        }
        return mPoemManager;
    }

/**
     * 完成对数据库表的插入操作
     *
     * @param videoContent
     * @return
     */

    public boolean insertVideoContent(VideoContent videoContent) {
        boolean flag = false;
        flag = mVideoContentDao.insertOrReplace(videoContent) != -1 ? true : false;
        return flag;
    }

/**
     * 完成对数据库的多次插入
     * 完成对数据库的多次插入
     *
     * @param videoContents
     */

    public void insertMultVideoContent(final List<VideoContent> videoContents) {
        mVideoContentDao.insertOrReplaceInTx(videoContents);
    }

/**
     * 更新对VideoContent某一条记录的修改
     *
     * @param videoContent
     */

    public void updateVideoContent(VideoContent videoContent) {
        mVideoContentDao.update(videoContent);
    }


/**
     * 删除一条数据
     *
     * @param videoContent
     */

    public void deleteVideoContent(VideoContent videoContent) {
        mVideoContentDao.delete(videoContent);
    }

/**
     * 返回多行记录
     *
     * @return
     */
    public List<VideoContent> getAllVideoContent() {
        return mVideoContentDao.loadAll();
    }

    public void clear() {
        mVideoContentDao.deleteAll();
    }

    public VideoContent findVideoContentById(String id) {
        QueryBuilder<VideoContent> builder = mVideoContentDao.queryBuilder();
        List<VideoContent> list = builder.where(VideoContentDao.Properties.Id.eq(id)).list();
        if (list != null) {
            VideoContent videoContent = list.get(0);
            return videoContent;
        } else {
            return null;
        }
    }
    public List<VideoContent> findVideoContentByVideoId(String videoId,String time) {
        QueryBuilder<VideoContent> builder = mVideoContentDao.queryBuilder();
        WhereCondition whereCondition = builder.and(VideoContentDao.Properties.VideoId.eq(videoId), VideoContentDao.Properties.Time.eq(time));
        List<VideoContent> list = builder.where(whereCondition).list();
        return list;
    }

}
