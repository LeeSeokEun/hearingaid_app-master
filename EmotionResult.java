package com.hari.hearingaid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LeeSeokEun on 2016-10-18.
 */

public class EmotionResult extends Activity {
    public Main mainreader;
    public WordCount _wordCount;
    public static final String TAG = "TestFileActivity";
    public static final String STRSAVEPATH = Environment.
            getExternalStorageDirectory() + "/testfolder/";
    public static final String STRSAVEPATH2 = Environment.
            getExternalStorageDirectory() + "/testfolder2/";
    public static final String SAVEFILEPATH = "myfile.txt";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotionre);
        Intent intent = getIntent();
        String str = intent.getExtras().getString("txtText");//스트링형으로 저장

        //Log.v("담긴 값",str);

        //폴더 생성
        File dir = makeDirectory(STRSAVEPATH);
        //파일 생성
        File file = makeFile(dir, (STRSAVEPATH + SAVEFILEPATH));
        //절대 경로
        Log.i(TAG, "절대경로1" + getAbsolutePath(dir));
        Log.i(TAG, "절대경로2" + getAbsolutePath(file));

        //파일 쓰기 txt파일이 생김
        String content = new String(str);
        Log.v("text1", content);
        writeFile(file, content.getBytes());
        Log.v("넘어가는 절대경로값",getAbsolutePath(file));
        mainreader = new Main();
        Main.main(getAbsolutePath(file));

        //파일 읽기

        readFile(file);


        //디렉토리 내용 얻어 오기
        String[] list = getList(dir);
        for (String s : list) {
            Log.d(TAG, s);
        }
    }


    /**
     * 디렉토리 생성
     *
     * @return dir
     */
    private File makeDirectory(String dir_path) {
        File dir = new File(dir_path);
        if (!dir.exists()) {
            dir.mkdirs();
            Log.i(TAG, "!dir.exists");
        } else {
            Log.i(TAG, "dir.exists");
        }

        return dir;
    }

    /**
     * 파일 생성
     *
     * @param dir
     * @return file
     */
    private File makeFile(File dir, String file_path) {
        File file = null;
        boolean isSuccess = false;
        if (dir.isDirectory()) {
            file = new File(file_path);
            if (file != null && !file.exists()) {
                Log.i(TAG, "!file.exists");
                try {
                    isSuccess = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Log.i(TAG, "파일생성 여부 = " + isSuccess);
                }
            } else {
                Log.i(TAG, "file.exists");
            }
        }
        return file;
    }

    /**
     * (dir/file) 절대 경로 얻어오기
     *
     * @param file
     * @return String
     */
    private String getAbsolutePath(File file) {
        return "" + file.getAbsolutePath();
    }


    /**
     * 디렉토리에 안에 내용을 보여 준다.
     */
    private String[] getList(File dir) {
        if (dir != null && dir.exists())
            return dir.list();

        return null;
    }

    /**
     * 파일에 내용 쓰기
     *
     * @param file
     * @param file_content
     * @return
     */
    private boolean writeFile(File file, byte[] file_content) {
        boolean result;
        FileOutputStream fos;
        if (file != null && file.exists() && file_content != null) {
            try {
                fos = new FileOutputStream(file);
                try {
                    fos.write(file_content);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * 파일 읽어 오기
     *
     * @param file
     */
    private void readFile(File file) {
        int readcount = 0;
        if (file != null && file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                readcount = (int) file.length();
                byte[] buffer = new byte[readcount];
                fis.read(buffer);
                for (int i = 0; i < file.length(); i++) {
                    Log.d(TAG, "asd" + buffer[i]);
                }
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



    //Log.i("Text",  str);
        //String[] STT = new String[1];
        //STT[0] = str; //여기 값이 받아오고

        /*mainreader = new Main();
        Main.main(STT);
        _wordCount = new WordCount();

        int pos = _wordCount.getWordCount();
        String sop = Integer.toString(pos);
        Log.v("pos", sop);
        int neg = _wordCount.getWordCount2();
        String gen = Integer.toString(neg);
        Log.v("gen", gen);
        int total = _wordCount.getWordCount3();
        String latot = Integer.toString(total);
        Log.v("latot", latot);*/

        //TextView TxtResult1 = (TextView) findViewById(R.id.Result);
        //TxtResult1.setText(str);



