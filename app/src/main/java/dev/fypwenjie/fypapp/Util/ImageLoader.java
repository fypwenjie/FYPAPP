package dev.fypwenjie.fypapp.Util;

/**
 * Created by VINTEDGE on 9/4/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Stack;

import dev.fypwenjie.fypapp.R;

/**
 * Created by VINTEDGE on 21/2/2017.
 */

public class ImageLoader {
    final int stub_id = R.drawable.no_image;
    PhotosQueue photosQueue = new PhotosQueue();
    PhotosLoader photoLoaderThread = new PhotosLoader();
    //the simplest in-memory cache implementation. This should be replaced with something like SoftReference or BitmapOptions.inPurgeable(since 1.6)
    private HashMap<String, Bitmap> cache = new HashMap<String, Bitmap>();
    private File cacheDir;

    public ImageLoader(Context context) {
        //Make the background thead low priority. This way it will not affect the UI performance
        photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);

        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            Log.i("Create Dir", Environment.getExternalStorageDirectory().getAbsolutePath().toString());
            cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/LazyList");
        } else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists()) {
            Log.i("Create Dir", "LazyList " + cacheDir.exists());
            cacheDir.mkdirs();
            Log.i("Create Dir", "LazyList " + cacheDir.exists());
        }
    }

    public void DisplayImage(String url, Activity activity, ImageView imageView) {
        if (cache.containsKey(url))
            //Extra Coding to re-scale the Image ratio
            //
            /*if(imageView.getContext().toString().indexOf("com.example.gsc.activities.CinemaActivity") != -1){
                if(cache.get(url).getWidth() >= cache.get(url).getHeight()){
                    imageView.setImageBitmap(Bitmap.createScaledBitmap(cache.get(url), cache.get(url).getWidth(), (cache.get(url).getHeight()/2) + cache.get(url).getHeight(),false));
                }
            }else*/
            imageView.setImageBitmap(cache.get(url));
        else {
            queuePhoto(url, activity, imageView);
            imageView.setImageResource(stub_id);
        }

    }

    private void queuePhoto(String url, Activity activity, ImageView imageView) {
        //This ImageView may be used for other images before. So there may be some old tasks in the queue. We need to discard them.
        photosQueue.Clean(imageView);
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        synchronized (photosQueue.photosToLoad) {
            photosQueue.photosToLoad.push(p);
            photosQueue.photosToLoad.notifyAll();
        }

        //start thread if it's not started yet
        if (photoLoaderThread.getState() == Thread.State.NEW)
            photoLoaderThread.start();
    }

    private Bitmap getBitmap(String url) {
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);

        //from SD cache
        Bitmap b = decodeFile(f);
        if (b != null)
            return b;

        //from web
        try {
            Bitmap bitmap = null;
            InputStream is = new URL(url).openStream();
            OutputStream os = new FileOutputStream(f);
            Util.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public void stopThread() {
        photoLoaderThread.interrupt();
    }

    public void clearCache() {
        //clear memory cache
        cache.clear();

        //clear SD cache
        File[] files = cacheDir.listFiles();
        for (File f : files)
            f.delete();
    }

    //Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    //stores list of photos to download
    class PhotosQueue {
        private Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

        //removes all instances of this ImageView
        public void Clean(ImageView image) {
            for (int j = 0; j < photosToLoad.size(); ) {
                if (photosToLoad.get(j).imageView == image)
                    photosToLoad.remove(j);
                else
                    ++j;
            }
        }
    }

    class PhotosLoader extends Thread {
        public void run() {
            try {
                while (true) {
                    //thread waits until there are any images to load in the queue
                    if (photosQueue.photosToLoad.size() == 0)
                        synchronized (photosQueue.photosToLoad) {
                            photosQueue.photosToLoad.wait();
                        }
                    if (photosQueue.photosToLoad.size() != 0) {
                        PhotoToLoad photoToLoad;
                        synchronized (photosQueue.photosToLoad) {
                            photoToLoad = photosQueue.photosToLoad.pop();
                        }
                        Bitmap bmp = getBitmap(photoToLoad.url);
                        cache.put(photoToLoad.url, bmp);
                        Object tag = photoToLoad.imageView.getTag();
                        if (tag != null && ((String) tag).equals(photoToLoad.url)) {
                            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad.imageView);
                            Activity a = (Activity) photoToLoad.imageView.getContext();
                            a.runOnUiThread(bd);
                        }
                    }
                    if (Thread.interrupted())
                        break;
                }
            } catch (InterruptedException e) {
                //allow thread to exit
            }
        }
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        ImageView imageView;

        public BitmapDisplayer(Bitmap b, ImageView i) {
            bitmap = b;
            imageView = i;
        }

        public void run() {
            //Log.i("Log Image View", imageView.getContext() + "");
            if (bitmap != null) {
                //Extra Coding to re-scale the image ratio
                //
                /*if(imageView.getContext().toString().indexOf("com.example.gsc.activities.CinemaActivity") != -1){
                    if(bitmap.getWidth() >= bitmap.getHeight()){
                        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), (bitmap.getHeight()/2) + bitmap.getHeight(),false);
                    }
                }*/
                //
                imageView.setImageBitmap(bitmap);
            } else
                imageView.setImageResource(stub_id);
        }
    }
}
