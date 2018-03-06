package com.wzrd.m.db.manger;

import android.content.Context;

import com.wzrd.m.been.Video;
import com.wzrd.m.db.GreenDaoHelper;
import com.wzrd.m.db.gen.DaoSession;
import com.wzrd.m.db.gen.VideoDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17.
 */
public class VideoManager {
    private VideoDao mVideoDao;
    private static VideoManager mPoemManager;

    public VideoManager(Context context) {
        DaoSession daoSession = GreenDaoHelper.getInstance().getDaoSession(context);
        mVideoDao = daoSession.getVideoDao();
    }

    public static VideoManager getInstance(Context context) {
        if (mPoemManager == null) {
            mPoemManager = new VideoManager(context);
        }
        return mPoemManager;
    }

    /**
     * 完成对数据库表的插入操作
     *
     * @param video
     * @return
     */
    public boolean insertVideo(Video video) {
        boolean flag = false;
        flag = mVideoDao.insertOrReplace(video) != -1 ? true : false;
        return flag;
    }

    /**
     * 完成对数据库的多次插入
     * 完成对数据库的多次插入
     *
     * @param videos
     */
    public void insertMultVideo(final List<Video> videos) {
        mVideoDao.insertOrReplaceInTx(videos);
    }

    /**
     * 更新对Video某一条记录的修改
     *
     * @param video
     */
    public void updateVideo(Video video) {
        mVideoDao.update(video);
    }


    /**
     * 删除一条数据
     *
     * @param video
     */
    public void deleteVideo(Video video) {
        mVideoDao.delete(video);
    }

    /**
     * 返回多行记录
     *
     * @return
     */
    public List<Video> getAllVideo() {
        return mVideoDao.loadAll();
    }

    public void clear() {
        mVideoDao.deleteAll();
    }

    public Video findVideoById(String id) {
        QueryBuilder<Video> builder = mVideoDao.queryBuilder();
        List<Video> list = builder.where(VideoDao.Properties.Id.eq(id)).list();
        if (list != null) {
            Video video = list.get(0);
            return video;
        } else {
            return null;
        }
    }

    public List<Video> findVideoByVideoType(String videoType) {
        QueryBuilder<Video> builder = mVideoDao.queryBuilder();
        List<Video> list = builder.where(VideoDao.Properties.Video_type.eq(videoType)).list();
        if (list != null) {
            return list;
        } else {
            return null;
        }
    }

    public List<Video> findVideoByIsEdit(int isEdit) {
        QueryBuilder<Video> builder = mVideoDao.queryBuilder();
        List<Video> list = builder.where(VideoDao.Properties.IsEdit.eq(isEdit)).list();
        if (list != null) {
            return list;
        } else {
            return null;
        }
    }

    public Video getLastVideo() {
        List<Video> videos = findVideoByIsEdit(1);
        if (videos != null && videos.size() > 0) {
            return videos.get(videos.size() - 1);
        } else {
            return null;
        }
    }

    public List<Video> findVideoByVideoTypeAndIsEdit(String videoType, int isEdit) {
        QueryBuilder<Video> builder = mVideoDao.queryBuilder();
        WhereCondition whereCondition = builder.and(VideoDao.Properties.Video_type.eq(videoType), VideoDao.Properties.IsEdit.eq(isEdit));
        List<Video> list = builder.where(whereCondition).list();
        if (list != null) {
            return list;
        } else {
            return null;
        }
    }
}
