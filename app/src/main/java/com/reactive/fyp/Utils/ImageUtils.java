package com.reactive.fyp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.Display;
import android.view.WindowManager;
import android.widget.SeekBar;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    public static int dpToPx(Context context, int i) {
        context.getResources();
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float pxToDp(Context context, float f) {
        return f / (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }
}
