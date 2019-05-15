package com.jake.experience.commonlib.file_tool;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;



public class FileUtils {


    private static final int BUFFER = 8192;
    private static String captureImgPath = "";

    public static void setCaptureImgPath(String captureImgPath) {
        FileUtils.captureImgPath = captureImgPath;
    }

    /**
     * @param filePath f.mkdir(); //只能创建一级目录
     *                 f.mkdirs() //能创建多级目录
     * @return
     * @Description: 检查目录是否存在, 不存在则创建
     */
    public static boolean checkDir(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            return f.mkdirs();

        }
        return true;
    }

    /**
     * @return
     * @Description: 判断SD卡是否存在, 并且是否具有读写权限
     */
    public static boolean isExistSD() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }


    /**
     * Java文件操作 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 获取缓存路径
     */
    public static String getCacheDir(Context context) {
        String cacheDir;
        if (FileUtils.isExistSD()) {
            //SD存在
            cacheDir = FileUtils.getSDCacheDir(context);
        } else {
            //不存在则使用系统目录
            cacheDir = context.getCacheDir().getPath();
        }
        cacheDir += "/";
//		L.e("----缓存目录---->>>:" + cacheDir);

//		checkDir(cacheDir);
        return cacheDir;
    }

    /**
     * 获取SD卡路径
     */
    public static String getSDCardPath() {
        if (isExistSD()) {
            return Environment.getExternalStorageDirectory().toString() + "/";
        }
        return null;
    }

    /**
     * @param context
     * @return
     * @Description: 获取当前应用SD卡缓存目录
     */
    public static String getSDCacheDir(Context context) {
        //api大于8的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            //目录为/mnt/sdcard/Android/data/com.mt.mtpp/cache
            return context.getExternalCacheDir().getPath();
        }
        String cacheDir = "/Android/data/" + context.getPackageName() + "/cache";
        return Environment.getExternalStorageDirectory().getPath() + cacheDir;

//		return getCacheDirectory(context,true);
    }

    public String saveFile(Bitmap bitmap, String fileName, String path) {
        File myCaptureFile = null;
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        try {
            myCaptureFile = new File(path + fileName);
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myCaptureFile == null ? null : myCaptureFile.getAbsolutePath();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean mkdirs(File directory) {
        try {
            forceMkdir(directory);
            return true;
        } catch (IOException var2) {
            return false;
        }
    }

    public static void forceMkdir(File directory) throws IOException {
        String message;
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                message = "File " + directory + " exists and is " + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else if (!directory.mkdirs() && !directory.isDirectory()) {
            message = "Unable to create directory " + directory;
            throw new IOException(message);
        }

    }

    /**
     * 暴露图片到媒体库
     */
    public static void exposeInMedia() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(FileUtils.captureImgPath));
        intent.setData(uri);
//        CoreApp.getContext().sendBroadcast(intent);
    }


    //lp add
    //字符串、json 写入文件
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void writeStringToFile(String json, String filePath) {
        File txt = new File(filePath);
        if (txt.exists()) {
            txt.delete();
            try {
                txt.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(txt);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getJsonFile(Context context, String pkgName) {

        File file = new File(context.getFilesDir()
                + "/" + pkgName + "jsondata.txt");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));
            StringBuffer stringBuffer = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    //lp end
}
