package com.autoai.android.toolkit;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class LogManager {

    public static final String TAG = "AutoAi";

    private final static boolean isLoggable = true;
    private final static boolean isFileLoggable = isLoggable;

    private static final String GLOBAL = "GLOBAL";

    public static boolean isLoggable() {
        return isLoggable;
    }

    public static String logParentPath = "/mnt/sdcard/autoai/debug";
    private static final String FILE_START_SUFFIX = "msg_";

    private static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
    private static ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, workQueue);

    /**
     * 时间格式
     */
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat mSdf = new SimpleDateFormat("yyyyMMddHH");

    private static final class FilePrintRunnable implements Runnable {

        private static PrintWriter printWriter = null;

        private Level level;
        private String tag;
        private String msg;
        private Throwable t;

        private FilePrintRunnable(Level level, String tag, String msg, Throwable t) {
            this.level = level;
            this.tag = tag;
            this.msg = msg;
            this.t = t;
        }

        @Override
        public void run() {

            boolean exception = false;

            try {
                if (null == printWriter) {
                    File file = new File(logParentPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    String logFileName = file.getAbsolutePath() + File.separator + FILE_START_SUFFIX + mSdf.format(System.currentTimeMillis()) + ".log";
                    File logFile = new File(logFileName);
                    if (!logFile.exists()) {
                        logFile.createNewFile();
                    }
                    printWriter = new PrintWriter(new FileOutputStream(logFile, true));
                }

                // 级别
                printWriter.print("|");
                printWriter.print(level);

                // 时间
                printWriter.print("|");
                printWriter.print(TIME_FORMAT.format(new Date()));

                // TAG
                printWriter.print("|");
                printWriter.print(tag);

                // 信息
                printWriter.print("|");
                printWriter.print(msg);

                // 异常
                if (null != t) {
                    t.printStackTrace(printWriter);
                }

                printWriter.println();
                printWriter.flush();

            } catch (Exception e) {
                exception = true;
            }

            if (workQueue.size() < 1 || exception) {
                if (null != printWriter) {
                    printWriter.close();
                    printWriter = null;
                }
            }
        }
    }

    public enum CodeLocationStyle {

        /**
         * 第一行
         */
        FIRST(true, true),
        /**
         * 随后的行
         */
        SUBSEQUENT(true, true);

        /**
         * 是否添加at字眼在行首
         */
        private boolean isAt;

        /**
         * 是否使用简单类名
         */
        private boolean isSimpleClassName;

        CodeLocationStyle(boolean isAt, boolean isSimpleClassName) {
            this.isAt = isAt;
            this.isSimpleClassName = isSimpleClassName;
        }

        /**
         * @return the {@link #isAt}
         */
        public boolean isAt() {
            return isAt;
        }

        /**
         * @return the {@link #isSimpleClassName}
         */
        public boolean isSimpleClassName() {
            return isSimpleClassName;
        }

    }

    private static void smartPrint(Level level, String tag, String msg, Throwable t, boolean printStackTrace) {
        if (!isLoggable) {
            return;
        }

        Thread currentThread = Thread.currentThread();

        StackTraceElement[] stackTrace = currentThread.getStackTrace();

        int i = 4;

        String msgResult = String.valueOf(currentThread.getId()) + //
                "|" + //
                getCodeLocation(CodeLocationStyle.FIRST, null, stackTrace[i]) + //
                "|" + //
                msg;
        print(level, tag, msgResult, t);
        filePrint(level, tag, msgResult, t);

        i++;

        for (; printStackTrace && i < stackTrace.length; i++) {
            String s = getCodeLocation(CodeLocationStyle.SUBSEQUENT, currentThread, stackTrace[i]).toString();
            print(level, tag, s, null);
            filePrint(level, tag, s, null);
        }

    }

    private static void print(Level level, String tag, String msg, Throwable t) {
        switch (level) {
            case VERBOSE:
                if (t != null) {
                    android.util.Log.v(tag, msg, t);
                } else {
                    android.util.Log.v(tag, msg);
                }
                break;
            case DEBUG:
                if (t != null) {
                    android.util.Log.d(tag, msg, t);
                } else {
                    android.util.Log.d(tag, msg);
                }
                break;
            case INFO:
                if (t != null) {
                    android.util.Log.i(tag, msg, t);
                } else {
                    android.util.Log.i(tag, msg);
                }
                break;
            case WARN:
                if (t != null) {
                    android.util.Log.w(tag, msg, t);
                } else {
                    android.util.Log.w(tag, msg);
                }
                break;
            case ERROR:
                if (t != null) {
                    android.util.Log.e(tag, msg, t);
                } else {
                    android.util.Log.e(tag, msg);
                }
                break;
        }
    }

    private static StringBuilder getCodeLocation(CodeLocationStyle style, Thread currentThread, StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        int lineNumber = stackTraceElement.getLineNumber();
        String methodName = stackTraceElement.getMethodName();
        String fileName = stackTraceElement.getFileName();
        StringBuilder sb = new StringBuilder();
        if (style.isAt()) {
            sb.append("	at ");
        }
        if (style.isSimpleClassName()) {
            sb.append(getSimpleName(className));
        } else {
            sb.append(className);
        }
        sb.append(".").append(methodName).append("(").append(fileName).append(":").append(lineNumber).append(")");
        return sb;
    }

    private static String getSimpleName(String className) {
        String[] split = className.split("\\.");
        return split[split.length - 1];
    }

    public static void v(String tag, String msg) {
        smartPrint(Level.VERBOSE, tag, msg, null, false);
    }

    public static void v(String tag, String msg, Throwable t) {
        smartPrint(Level.VERBOSE, tag, msg, t, false);
    }

    public static void d(String tag, String msg) {
        smartPrint(Level.DEBUG, tag, msg, null, false);
    }


    public static void d(String tag, String msg, Throwable t) {
        smartPrint(Level.DEBUG, tag, msg, t, false);
    }

    public static void i(String tag, String msg) {
        smartPrint(Level.INFO, tag, msg, null, false);
    }

    public static void i(String tag, String msg, Throwable t) {
        smartPrint(Level.INFO, tag, msg, t, false);
    }

    public static void w(String tag, String msg) {
        smartPrint(Level.WARN, tag, msg, null, false);
    }

    public static void w(String tag, String msg, Throwable t) {
        smartPrint(Level.WARN, tag, msg, t, false);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        smartPrint(Level.ERROR, tag, msg, null, false);
    }

    public static void e(String tag, String msg, Throwable t) {
        smartPrint(Level.ERROR, tag, msg, t, false);
    }

    private enum Level {
        VERBOSE, DEBUG, INFO, WARN, ERROR
    }

    public static void registerUncaughtExceptionHandler() {
        final UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                e(GLOBAL, "uncaughtException", ex);
                defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
            }
        });
    }

    public static void filePrint(Level level, String tag, String msg, Throwable t) {
        if (!isFileLoggable) {
            return;
        }
        executorService.submit(new FilePrintRunnable(level, tag, msg, t));
    }

}
