package com.example.handschoolapplication.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	/**
	 * Save image to the SD card
	 * 
	 * @param photoBitmap
	 * @param photoName
	 * @param path
	 */
	public static String savePhoto(Bitmap photoBitmap, String path,
			String photoName) {
		String localPath = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File photoFile = new File(path, photoName + ".png");
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) { // ת�����
						localPath = photoFile.getPath();
						fileOutputStream.flush();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				localPath = null;
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				localPath = null;
				e.printStackTrace();
			} finally {
				try {
					if (fileOutputStream != null) {
						fileOutputStream.close();
						fileOutputStream = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return localPath;
	}

	/**
	 * ת��ͼƬ��Բ��
	 * 
	 * @param bitmap
	 *            ����Bitmap����
	 * @param tempUri
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap, Uri tempUri) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			left = 0;
			top = 0;
			right = width;
			bottom = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);// ���û����޾��

		canvas.drawARGB(0, 0, 0, 0); // �������Canvas
		paint.setColor(color);

		// ���������ַ�����Բ,drawRounRect��drawCircle
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
		// ��Բ�Ǿ��Σ���һ������Ϊͼ����ʾ���򣬵ڶ��������͵����������ֱ���ˮƽԲ�ǰ뾶�ʹ�ֱԲ�ǰ뾶��
		canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// ��������ͼƬ�ཻʱ��ģʽ,�ο�http://trylovecatch.iteye.com/blog/1189452
		canvas.drawBitmap(bitmap, src, dst, paint); // ��Mode.SRC_INģʽ�ϲ�bitmap���Ѿ�draw�˵�Circle

		return output;
	}

	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

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
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

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
				final String[] selectionArgs = new String[]{split[1]};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {column};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
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
	private static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	private static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * 测量View的宽高
	 *
	 * @param view View
	 */
	public static void measureWidthAndHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
	}



	/**
	 * 规则1：至少包含大小写字母及数字中的一种
	 * 是否包含
	 *
	 * @param str
	 * @return
	 */
	public static boolean isLetterOrDigit(String str) {
		boolean isLetterOrDigit = false;//定义一个boolean值，用来表示是否包含字母或数字
		for (int i = 0; i < str.length(); i++) {
			if (Character.isLetterOrDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
				isLetterOrDigit = true;
			}
		}
		String regex = "^[a-zA-Z0-9]+$";
		boolean isRight = isLetterOrDigit && str.matches(regex);
		return isRight;
	}

	/**
	 * 规则2：至少包含大小写字母及数字中的两种
	 * 是否包含
	 *
	 * @param str
	 * @return
	 */
	public static boolean isLetterDigit(String str) {
		boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
		boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
				isDigit = true;
			} else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
				isLetter = true;
			}
		}
		String regex = "^[a-zA-Z0-9]+$";
		boolean isRight = isDigit && isLetter && str.matches(regex);
		return isRight;
	}

	/**
	 * 规则3：必须同时包含大小写字母及数字
	 * 是否包含
	 *
	 * @param str
	 * @return
	 */
	public static boolean isContainAll(String str) {
		boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
		boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
		boolean isUpperCase = false;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
				isDigit = true;
			} else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
				isLowerCase = true;
			} else if (Character.isUpperCase(str.charAt(i))) {
				isUpperCase = true;
			}
		}
		String regex = "^[a-zA-Z0-9]+$";
		boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
		return isRight;
	}


	public static boolean ispsd(String psd) {
		Pattern p = Pattern.compile("^[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]");
		Matcher m = p.matcher(psd);
		return m.matches();
	}

	/**
	 * 这个方法挺简单的。
	 * DecimalFormat is a concrete subclass of NumberFormat that formats decimal numbers.
	 * @param d
	 * @return
	 */
	public static String formatDouble4(double d) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(d);
	}

}
