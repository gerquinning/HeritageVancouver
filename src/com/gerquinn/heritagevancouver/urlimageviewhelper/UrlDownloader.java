package com.gerquinn.heritagevancouver.urlimageviewhelper;

import java.io.InputStream;

import android.content.Context;

public interface UrlDownloader {
    public static interface UrlDownloaderCallback {
        public void onDownloadComplete(UrlDownloader downloader, InputStream in, String filename);
    }
    
    public boolean allowCache();
    public boolean canDownloadUrl(String url);
    public void download(Context context, String url, String filename, UrlDownloaderCallback callback, Runnable completion);
}