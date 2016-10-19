package com.hari.hearingaid;

import android.util.Log;

import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.regex.*;

class Alphabetize implements Comparator<Map.Entry<String, Int>> {
    public int compare(Map.Entry<String, Int> entry1
            , Map.Entry<String, Int> entry2) {
        return entry1.getKey().compareTo(entry2.getKey());
    }
}

class CompareFrequency implements Comparator<Map.Entry<String, Int>> {
    public int compare(Map.Entry<String, Int> word1, Map.Entry<String, Int> word2) {
        int result;
        int count1 = word1.getValue().value;
        int count2 = word2.getValue().value;
        if (count1 < count2) {
            result = -1;

        } else if (count1 > count2) {
            result = 1;

        } else {

            result = word1.getKey().compareTo(word2.getKey());
        }
        return result;
    }
}


public class WordCount {
    private static final Comparator<Map.Entry<String, Int>> SORT_BY_FREQUENCY =
            new CompareFrequency();
    private static final Comparator<Map.Entry<String, Int>> SORT_ALPHABETICALLY =
            new Alphabetize();

    public enum SortOrder {ALPHABETICALLY, BY_FREQUENCY}

    Map<String, Int> _wordFrequency;
    Map<String, Int> _wordFrequency2;
    Map<String, Int> _wordFrequency3;
    int _totalWords;
    int _total;
    int _positive;
    int _negative;


    public WordCount() {
        _wordFrequency = new HashMap<String, Int>();
        _wordFrequency2 = new HashMap<String, Int>();
        _wordFrequency3 = new HashMap<String, Int>();
        _totalWords = 0; // 전체 단어 사용갯수 //액티비티출력
        _positive = 0; //긍정단어 갯수 //액티비티출력
        _negative = 0; //부정단어 갯수 //액티비티출력
    }

    public void countWords(String sourceFile) throws IOException {
        Log.v("제발~", sourceFile + "죽어라");

        //Log.v("제발~", "죽어라");
        Scanner wordScanner = new Scanner(new File(sourceFile));
        //Log.v("제발~", "죽어라");
        Log.v("제발~", wordScanner + "죽어라1");
        /*wordScanner.useDelimiter("[^가-가,나-나,다-다,라-라,마-마,바-바,사-사,아-아,자-자,차-차,카-카,타-타,파-파,하-하,"
                + "까-까 깨-깨 꺄-꺄 꺠-꺠 꺼-꺼 께-께 껴-껴 꼐-꼐 꼬-꼬 꽈-꽈 꽤-꽤 꾀-꾀 꾜-꾜 꾸-꾸 꿔-꿔 꿰-꿰 뀌-뀌 뀨-뀨 끄-끄 끠-끠 끼-끼,"
                + "따-따 때-때 땨-땨 떄-떄 떠-떠 떼-떼 뗘-뗘 뗴-뗴 또-또 똬-똬 뙈-뙈 뙤-뙤 뚀-뚀 뚜-뚜 뚸-뚸 뛔-뛔 뛰-뛰 뜌-뜌 뜨-뜨 띄-띄 띠-띠,"
                + "빠-빠 빼-빼 뺘-뺘 뺴-뺴 뻐-뻐 뻬-뻬 뼈-뼈 뼤-뼤 뽀-뽀 뽜-뽜 뽸-뽸 뾔-뾔 뾰-뾰 뿌-뿌 뿨-뿨 쀄-쀄 쀠-쀠 쀼-쀼 쁘-쁘 쁴-쁴 삐-삐,"
                + "싸-싸 쌔-쌔 쌰-쌰 썌-썌 써-써 쎄-쎄 쎠-쎠 쎼-쎼 쏘-쏘 쏴-쏴 쐐-쐐 쐬-쐬 쑈-쑈 쑤-쑤 쒀-쒀 쒜-쒜 쒸-쒸 쓔-쓔 쓰-쓰 씌-씌 씨-씨,"
                + "짜-짜 째-째 쨔-쨔 쨰-쨰 쩌-쩌 쩨-쩨 쪄-쪄 쪠-쪠 쪼-쪼 쫘-쫘 쫴-쫴 쬐-쬐 쬬-쬬 쭈-쭈 쭤-쭤 쮀-쮀 쮜-쮜 쮸-쮸 쯔-쯔 쯰-쯰 찌-찌,"
                + "가-가 개-개 갸-갸 걔-걔 거-거 게-게 겨-겨 계-계 고-고 과-과 괘-괘 괴-괴 교-교 구-구 궈-궈 궤-궤 귀-귀 규-규 그-그 긔-긔 기-기,"
                + "나-나 내-내 냐-냐 냬-냬 너-너 네-네 녀-녀 녜-녜 노-노 놔-놔 놰-놰 뇌-뇌 뇨-뇨 누-누 눠-눠 눼-눼 뉘-뉘 뉴-뉴 느-느 늬-늬 니-니,"
                + "다-다 대-대 댜-댜 댸-댸 더-더 데-데 뎌-뎌 뎨-뎨 도-도 돠-돠 돼-돼 되-되 됴-됴 두-두 둬-둬 뒈-뒈 뒤-뒤 듀-듀 드-드 듸-듸 디-디,"
                + "라-라 래-래 랴-랴 럐-럐 러-러 레-레 려-려 례-례 로-로 롸-롸 뢔-뢔 뢰-뢰 료-료 루-루 뤄-뤄 뤠-뤠 뤼-뤼 류-류 르-르 릐-릐 리-리,"
                + "마-마 매-매 먀-먀 먜-먜 머-머 메-메 며-며 몌-몌 모-모 뫄-뫄 뫠-뫠 뫼-뫼 묘-묘 무-무 뭐-뭐 뭬-뭬 뮈-뮈 뮤-뮤 므-므 믜-믜 미-미,"
                + "바-바 배-배 뱌-뱌 뱨-뱨 버-버 베-베 벼-벼 볘-볘 보-보 봐-봐 봬-봬 뵈-뵈 뵤-뵤 부-부 붜-붜 붸-붸 뷔-뷔 뷰-뷰 브-브 븨-븨 비-비,"
                + "사-사 새-새 샤-샤 섀-섀 서-서 세-세 셔-셔 셰-셰 소-소 솨-솨 쇄-쇄 쇠-쇠 쇼-쇼 수-수 숴-숴 쉐-쉐 쉬-쉬 슈-슈 스-스 싀-싀 시-시,"
                + "아-아 애-애 야-야 얘-얘 어-어 에-에 여-여 예-예 오-오 와-와 왜-왜 외-외 요-요 우-우 워-워 웨-웨 위-위 유-유 으-으 의-의 이-이,"
                + "자-자 재-재 쟈-쟈 쟤-쟤 저-저 제-제 져-져 졔-졔 조-조 좌-좌 좨-좨 죄-죄 죠-죠 주-주 줘-줘 줴-줴 쥐-쥐 쥬-쥬 즈-즈 즤-즤 지-지,"
                + "차-차 채-채 챠-챠 챼-챼 처-처 체-체 쳐-쳐 쳬-쳬 초-초 촤-촤 쵀-쵀 최-최 쵸-쵸 추-추 춰-춰 췌-췌 취-취 츄-츄 츠-츠 츼-츼 치-치,"
                + "카-카 캐-캐 캬-캬 컈-컈 커-커 케-케 켜-켜 켸-켸 코-코 콰-콰 쾌-쾌 쾨-쾨 쿄-쿄 쿠-쿠 쿼-쿼 퀘-퀘 퀴-퀴 큐-큐 크-크 킈-킈 키-키,"
                + "타-타 태-태 탸-탸 턔-턔 터-터 테-테 텨-텨 톄-톄 토-토 톼-톼 퇘-퇘 퇴-퇴 툐-툐 투-투 퉈-퉈 퉤-퉤 튀-튀 튜-튜 트-트 틔-틔 티-티,"
                + "파-파 패-패 퍄-퍄 퍠-퍠 퍼-퍼 페-페 펴-펴 폐-폐 포-포 퐈-퐈 퐤-퐤 푀-푀 표-표 푸-푸 풔-풔 풰-풰 퓌-퓌 퓨-퓨 프-프 픠-픠 피-피,"
                + "하-하 해-해 햐-햐 햬-햬 허-허 헤-헤 혀-혀 혜-혜 호-호 화-화 홰-홰 회-회 효-효 후-후 훠-훠 훼-훼 휘-휘 휴-휴 흐-흐 희-희 히-히,]+");*/

        while (wordScanner.hasNext()) {
            String word = wordScanner.next();
            _positive++;
            Log.v("긍정",Integer.toString(_positive));
            //Log.v("어디냐","ㅁㄴㅇㅁㄴㅇ");


            /*Int count = _wordFrequency.get(word);
            Log.v("워드 프리", Integer.toString(_wordFrequency.size()));
            if (count == null) {
                _wordFrequency.put(word, new Int(1));
            } else {
                count.value++;

            }*/

        }
        wordScanner.close();

        // 부정어 처리
        Scanner wordScanner1 = new Scanner(new File(sourceFile));
        wordScanner1.useDelimiter("[^않싫못^]+");
        while (wordScanner1.hasNext()) {
            String word1 = wordScanner1.next();
            _negative++;
            Log.v("부정",Integer.toString(_negative));

            Int count = _wordFrequency2.get(word1);
            if (count == null) {
                _wordFrequency2.put(word1, new Int(1));
            } else {
                count.value++;
            }

        }
        wordScanner1.close();
        Scanner wordScanner2 = new Scanner(sourceFile);
        wordScanner2.useDelimiter("[^^]");
        while (wordScanner2.hasNext()) {
            String word2 = wordScanner2.next();
            _totalWords++;

            //Add word if not already placed, else increment count
            Int count = _wordFrequency3.get(word2);
            if (count == null) {    // Create new entry.
                _wordFrequency3.put(word2, new Int(1));
            } else {
                count.value++;
            }
        }
        Log.v("총개수", Integer.toString(_wordFrequency3.size()));
        wordScanner2.close();
    }


    // 단어 사용 갯수
  /*public void countWords(String source) {
      Scanner wordScanner = new Scanner(source);
      Log.v("어디냐",source);
      wordScanner.useDelimiter("[^^]");
      while (wordScanner.hasNext()) {
          String word = wordScanner.next();
          _totalWords++;
          
          //Add word if not already placed, else increment count
              Int count = _wordFrequency3.get(word);
              if (count == null) {    // Create new entry.
                  _wordFrequency3.put(word, new Int(1));
              } else {               
                  count.value++;
              }
      }
      _totalWords +=1;
      Log.v("총개수",Integer.toString(_wordFrequency3.size()));
  }*/


    //Return the number of words in source
    public int getWordCount() {
        return _positive;
    }

    public int getWordCount2() {
        return _negative;
    }

    public int getWordCount3() {
        return _totalWords;
    }


    //Return Number of unique words.
    public int getEntryCount() // 긍정단어 개수
    {
        return _wordFrequency.size();
    }

    public int getEntryCount2() //부정단어 개수
    {
        return _wordFrequency2.size();
    }

    public int getEntryCount3() //전체단어 개수
    {
        return _wordFrequency3.size();
    }

    //Store the words and their frequency in array
    public void getWordFrequency(ArrayList<String> out_words,
                                 ArrayList<Integer> out_counts) {
        //Sort entries in Array by frequency
        ArrayList<Map.Entry<String, Int>> entries =
                new ArrayList<Map.Entry<String, Int>>(_wordFrequency.entrySet());
        Collections.sort(entries, new CompareFrequency());

        for (Map.Entry<String, Int> ent : entries) {
            out_words.add(ent.getKey());
            out_counts.add(ent.getValue().value);
        }
    }

    //Return array of unique words in order
    public String[] getWords(SortOrder sortBy, int a) {
        if (a == 1) {

            String[] result = new String[_wordFrequency.size()];
            ArrayList<Map.Entry<String, Int>> entries =
                    new ArrayList<Map.Entry<String, Int>>(_wordFrequency.entrySet());

            if (sortBy == SortOrder.ALPHABETICALLY) {
                Collections.sort(entries, SORT_ALPHABETICALLY);

            } else {
                Collections.sort(entries, SORT_BY_FREQUENCY);

            }
            int i = 0;
            for (Map.Entry<String, Int> ent : entries) {
                result[i++] = ent.getKey();
            }
            return result;
        } else if (a == 2) {
            String[] result2 = new String[_wordFrequency2.size()];
            ArrayList<Map.Entry<String, Int>> entries2 =
                    new ArrayList<Map.Entry<String, Int>>(_wordFrequency2.entrySet());
            if (sortBy == SortOrder.ALPHABETICALLY) {
                Collections.sort(entries2, SORT_ALPHABETICALLY);

            } else {
                Collections.sort(entries2, SORT_BY_FREQUENCY);

            }
            int i = 0;
            for (Map.Entry<String, Int> ent : entries2) {
                result2[i++] = ent.getKey();
            }
            return result2;
        } else {
            String[] result3 = new String[_wordFrequency3.size()];
            ArrayList<Map.Entry<String, Int>> entries3 =
                    new ArrayList<Map.Entry<String, Int>>(_wordFrequency3.entrySet());
            if (sortBy == SortOrder.ALPHABETICALLY) {
                Collections.sort(entries3, SORT_ALPHABETICALLY);

            } else {
                Collections.sort(entries3, SORT_BY_FREQUENCY);

            }
            int i = 0;
            for (Map.Entry<String, Int> ent : entries3) {
                result3[i++] = ent.getKey();

            }
            return result3;
        }
    }


    //Return frequencies in order
    public int[] getFrequencies(SortOrder sortBy, int b) {
        if (b == 1) {
            int[] result = new int[_wordFrequency.size()];
            ArrayList<Map.Entry<String, Int>> entries =
                    new ArrayList<Map.Entry<String, Int>>(_wordFrequency.entrySet());
            if (sortBy == SortOrder.ALPHABETICALLY) {
                Collections.sort(entries, SORT_ALPHABETICALLY);
            } else {
                Collections.sort(entries, SORT_BY_FREQUENCY);
            }

            int i = 0;
            for (Map.Entry<String, Int> ent : entries) {
                result[i++] = ent.getValue().value;
            }
            return result;
        } else if (b == 2) {
            int[] result2 = new int[_wordFrequency2.size()];
            ArrayList<Map.Entry<String, Int>> entries2 =
                    new ArrayList<Map.Entry<String, Int>>(_wordFrequency2.entrySet());
            if (sortBy == SortOrder.ALPHABETICALLY) {
                Collections.sort(entries2, SORT_ALPHABETICALLY);
            } else {
                Collections.sort(entries2, SORT_BY_FREQUENCY);
            }

            int i = 0;
            for (Map.Entry<String, Int> ent : entries2) {
                result2[i++] = ent.getValue().value;
            }
            return result2;
        } else {
            int[] result3 = new int[_wordFrequency3.size()];
            ArrayList<Map.Entry<String, Int>> entries3 =
                    new ArrayList<Map.Entry<String, Int>>(_wordFrequency3.entrySet());
            if (sortBy == SortOrder.ALPHABETICALLY) {
                Collections.sort(entries3, SORT_ALPHABETICALLY);
            } else {
                Collections.sort(entries3, SORT_BY_FREQUENCY);
            }

            int i = 0;
            for (Map.Entry<String, Int> ent : entries3) {
                result3[i++] = ent.getValue().value;
            }
            return result3;
        }
    }
}
