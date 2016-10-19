package com.hari.hearingaid;


import android.nfc.Tag;
import android.util.Log;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String unused) {
        Log.v("무슨값?", unused + "뒤진다");
        //Log.v("어디냐","ㅁㄴㅇㅁㄴㅇ");
        try {

            String fileName = unused;
            Log.v("뭘까요~", fileName + "뒤진다");
            File inputFile = new File(fileName);
            Log.v("뭘까요~", inputFile + "뒤진다");



            WordCount counter = new WordCount();// 객체생성
            counter.countWords(fileName); //MyFile.txt
            Log.v("뭘까요~", inputFile + "뒤진다1");


            String[] words = counter.getWords(WordCount.SortOrder.BY_FREQUENCY, 1);
            String[] words2 = counter.getWords(WordCount.SortOrder.BY_FREQUENCY, 2);
            String[] words3 = counter.getWords(WordCount.SortOrder.BY_FREQUENCY, 3);
            //Log.v("어디냐","ㅁㄴㅇㅁㄴㅇ");
            int[] frequency = counter.getFrequencies(WordCount.SortOrder.BY_FREQUENCY, 1);
            int[] frequency2 = counter.getFrequencies(WordCount.SortOrder.BY_FREQUENCY, 2);
            int[] frequency3 = counter.getFrequencies(WordCount.SortOrder.BY_FREQUENCY, 3);
            //Log.v("어디냐","ㅁㄴㅇㅁㄴㅇ");


            int n = counter.getEntryCount();
            {
                Log.v("어디냐", "ㅁㄴㅇㅁㄴㅇ111");
                FileWriter outputFileWriter = new FileWriter("result1.txt");
                for (int i = 0; i < n; i++) {
                    outputFileWriter.write(frequency[i] + " " + words[i] + "\r\n");
                }
                outputFileWriter.write("\r\n긍정 수: " + counter.getWordCount());
                outputFileWriter.write("\r\n총   수: " + n);
                outputFileWriter.close();

            }
            int n1 = counter.getEntryCount2();
            {
                Log.v("n1의 값", Integer.toString(n1));
                FileWriter outputFileWriter = new FileWriter("result2.txt");
                for (int i = 0; i < n; i++) {
                    outputFileWriter.write(frequency2[i] + " " + words2[i] + "\r\n");
                }

                outputFileWriter.write("\r\n부정 수: " + counter.getWordCount());
                outputFileWriter.write("\r\n총   수: " + n1);
                outputFileWriter.close();
            }
            int n2 = counter.getEntryCount3();
            {
                Log.v("n의 값", Integer.toString(n2));
                FileWriter outputFileWriter = new FileWriter("result2.txt");
                for (int i = 0; i < n; i++) {
                    outputFileWriter.write(frequency3[i] + " " + words3[i] + "\r\n");
                }

                outputFileWriter.write("\r\n전체 사용 단어 개수 : " + counter.getWordCount());
                outputFileWriter.write("\r\n총   수: " + n2);
                outputFileWriter.close();
            }

        } catch (IOException error) {
            System.out.println(error);
        }
    }
}