package com.wzrd.m.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 拍照的公共类
 * 
 * @author fenglipk
 */
public class PhotoUtil {

    private Context cxt;
    private static final int ww = 480;
    private static final int hh = 800;

    public static String path = "";
    static {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory() + "/MobileDJB";
        } else {
            path = Environment.getDataDirectory().getAbsolutePath() + "/MobileDJB";
        }
    }

    /**
     * 获取本地图片
     * 
     * @author fengli
     * @param filePath
     * @return
     */
    public static Bitmap getdecodeBitmap(String filePath) {
        if (filePath == null) {
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        int width = options.outWidth;
        int height = options.outHeight;
        float scale = 1f;
        if (width > ww && width > height) {
            scale = width / ww;
        } else if (height > hh && height > width) {
            scale = height / hh;
        } else {
            scale = 1;
        }

        options.inSampleSize = (int) scale;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    /**
     * 把drawable下面的图片变成bitmap
     * 
     * @author fengli
     * @param ctx
     * @param drawableId
     * @return
     */
    public static Bitmap drawableToBitmap(Context ctx, Integer drawableId) {
        Resources res = ctx.getResources();
        return BitmapFactory.decodeResource(res, drawableId);
    }

    /**
     * 保存涂鸦图片
     * 
     * @param view 图片所在view
     * @param rootDir 存储路径
     */
    public static boolean saveScreen(View view, File rootDir) {
        // 判断sdcard是否可用
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return false;
        }
     /*   view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();*/
        Bitmap bitmap = getScreenShot(view);
        try {
        	FileOutputStream fos=new FileOutputStream(rootDir);
            bitmap.compress(CompressFormat.JPEG, 100, fos);
            try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            view.setDrawingCacheEnabled(false);
            bitmap = null;
        }
    }
    
    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
        Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
    
 // 获取指定Activity的截屏，保存到png文件
 	public static Bitmap getScreenShot(View waterPhoto) {
 		// View是你需要截图的View
 		View view = waterPhoto;
 		view.setDrawingCacheEnabled(true);
 		view.buildDrawingCache();
 		Bitmap b1 = view.getDrawingCache();
 		/*// 获取状态栏高度
 		Rect frame = new Rect();
 		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
 		int statusBarHeight = frame.top;
 		System.out.println(statusBarHeight);*/

 		/*// 获取屏幕长和高
 		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
 		int height = activity.getWindowManager().getDefaultDisplay()
 				.getHeight();*/
 		
 		//获取长和高
 		int width=view.getWidth();
 		int height=view.getHeight();

 		// 去掉标题栏
 		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
 		Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
 		view.destroyDrawingCache();
 		return b;
 	}

    public static boolean deleteAttachmentFile(String savePath) {
        boolean flag = true;
        try {
            File file = new File(savePath);
            if (file != null) {

                if (file.exists()) {
                    flag = file.delete();
                }
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    
    public static int getbitmapdigree(String str) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(str);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                            ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int angle) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
    
    public static Bitmap pathToBitmap(String filePath) {
        if (filePath == null) {
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        int width = options.outWidth;
        int height = options.outHeight;
        float scale = 1f;
        if (width > ww && width > height) {
            scale = width / ww;
        } else if (height > hh && height > width) {
            scale = height / hh;
        } else {
            scale = 1;
        }

        options.inSampleSize = (int) scale;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }
    
//    /**
//     * 初始化ImageLoader缓存加载图片
//     * @param applicationContext
//     */
//    public static void initImageLoader(Context applicationContext) {
//        File cacheDir = StorageUtils.getOwnCacheDirectory(applicationContext, "nmxj/Cache");// 获取到缓存的目录地址
//        Log.d("cacheDir", cacheDir.getPath());
//        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(applicationContext).threadPoolSize(3)
//              // 线程池内加载的数量
//            .threadPriority(Thread.NORM_PRIORITY - 2)
//            .denyCacheImageMultipleSizesInMemory()
//            .discCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5
//            .tasksProcessingOrder(QueueProcessingType.LIFO)
//            .discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
//            .writeDebugLogs() // Remove// for
//            .build();// release// app
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config);// 全局初始化此配置
//    }
    
	// 加水印 也可以加文字
	public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark, String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		// 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		Paint paint = new Paint();
		// 加入图片
		if (watermark != null) {
			int ww = watermark.getWidth();
			int wh = watermark.getHeight();
			paint.setAlpha(50);
			cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);// 在src的右下角画入水印
		}
		// 加入文字
		if (title != null) {
			String familyName = "宋体";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(25);
			// 这里是自动换行的
			StaticLayout layout = new StaticLayout(title, textPaint, w, Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
			layout.draw(cv);
			// 文字就加左上角算了
			// cv.drawText(title,0,40,paint);
		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储
		return newb;
	}



}
