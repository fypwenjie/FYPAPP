package dev.fypwenjie.fypapp.Util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by VINTEDGE on 7/4/2018.
 */

public class Util {
    public static void shortToast(Context a, String message) {
        Toast.makeText(a, message, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context a, String message) {
        Toast.makeText(a, message, Toast.LENGTH_LONG).show();
    }
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static Integer sum(List<Integer> list) {
        Integer sum = 0;
        for (Integer i : list)
            sum = sum + i;
        return sum;
    }

    public static float spToPixels(Context context, Float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (sp * scaledDensity);
    }

    public static void expand(final View v, int duration) {
        v.setVisibility(View.VISIBLE);
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;

        final Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(duration);

        v.requestLayout();
        v.startAnimation(a);
        View parent = (View) v.getParent();
        parent.invalidate();
        // 1dp/ms
        //a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));

    }

    public static void collapse(final View v, int duration) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        //a.setDuration((int)((initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 1.5));
        a.setDuration(duration);
        v.startAnimation(a);
        View parent = (View) v.getParent();
        parent.invalidate();
    }

    // clear cache
    public static void DeleteCache(Context context) {
        try {
            File d = context.getCacheDir();
            if (d != null && d.isDirectory()) {
                DeleteDirectories(d);
            }
        } catch (Exception e) {
            // TODO: handle as you please
        }
    }

    public static boolean DeleteDirectories(File d) {
        String[] children = d.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = DeleteDirectories(new File(d, children[i]));
            if (!success) {
                return false;
            }
        }
        return d.delete(); // Delete empty directory
    }

    public static int checkAppVersion(String current, String latest) {

        if (latest == null || current == null) {
            return 0;
        }

        String[] currentArray = current.split("\\.");
        String[] latestArray = latest.split("\\.");

        int compare1, compare2;
//        Log.i("Hello 1", currentArray.length + " index");
//        Log.i("Hello 2", latestArray.length + " index");
        for (int i = 0; i < 3; i++) {
//            Log.i("Hello", i + " index");
            compare1 = (currentArray.length <= i || currentArray[i] == null) ? 0 : Integer.parseInt(currentArray[i]);
            compare2 = (latestArray.length <= i || latestArray[i] == null) ? 0 : Integer.parseInt(latestArray[i]);

            if (compare1 < compare2) {
                if (i != 2) {
                    return 1;
                } else {
                    return 2;
                }
            } else if (compare1 > compare2) {
                return 0;
            }
        }

        return 0;
    }

    public static String checkJsonExist(JSONObject json, String key) {
        try {
            return json.getString(key);
        } catch (Exception e) {
            return "";
        }
    }
}
