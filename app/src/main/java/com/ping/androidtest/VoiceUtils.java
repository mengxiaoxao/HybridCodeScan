package com.ping.androidtest;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by mayn on 2018/6/19.
 */

public class VoiceUtils {
    private static volatile VoiceUtils singleton = null;
    public boolean IsPlaying;

    MediaPlayer mediaPlayer = null;
    private Context mContext;

    public VoiceUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * 单例
     *
     * @param context
     * @return
     */
    public static VoiceUtils getInstance(Context context) {
        if (singleton == null) {
            synchronized (VoiceUtils.class) {
                if (singleton == null) {
                    singleton = new VoiceUtils(context);
                }
            }
        }
        return singleton;
    }


    public boolean GetIsPlay() {
        return IsPlaying;
    }

    public void Play(String stramount, String payWay, boolean strsuccess) {


        String str = null;
        //如果是TRUE  就播放“收款成功”这句话
        if (strsuccess) {
            str = payWay + capitalValueOf(Double.valueOf(String.format("%.2f", Double.parseDouble(stramount))));
        } else {
            str = capitalValueOf(Double.valueOf(String.format("%.2f", Double.parseDouble(stramount))));

        }
        System.out.println("金额的长度 " + str);
        String temp = "";


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        PlaySoundList(1, str, str.length());

    }

    private void SetIsPlay(boolean IsPlaying) {

        this.IsPlaying = IsPlaying;
    }

    private void PlaySoundList(final int soundindex, final String soundString, final int soundcount) {
        singleton.SetIsPlay(true);
        boolean createState = false;
        if (mediaPlayer == null) {
            mediaPlayer = null;
        }
        System.out.println("加载音频[" + soundindex + "]");
        mediaPlayer = createSound(soundindex, soundString);
        createState = true;

        if (createState == true)
            System.out.println("加载音频成功[" + soundindex + "]");
        else
            System.out.println("加载音频失败[" + soundindex + "]");

        //播放完成触发此事件
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();//释放音频资源
                int newsoundindex = soundindex;
                System.out.println("释放资源[" + soundindex + "]");
                if (soundindex < soundcount) {
                    newsoundindex = newsoundindex + 1;
                    PlaySoundList(newsoundindex, soundString, soundcount);
                } else {
                    singleton.SetIsPlay(false);
                }

            }
        });
        try {
            //在播放音频资源之前，必须调用Prepare方法完成些准备工作
            if (createState)
                mediaPlayer.prepare();
            else
                mediaPlayer.prepare();
            //开始播放音频
            mediaPlayer.start();

            System.out.println("播放音频[" + soundindex + "]");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MediaPlayer createSound(int soundIndex, String soundString) {
        MediaPlayer mp = null;

        String soundChar = soundString.substring(soundIndex - 1, soundIndex);

        switch (soundChar) {
            case "零":
                mp = MediaPlayer.create(mContext, R.raw.sound0);
                break;
            case "壹":
                mp = MediaPlayer.create(mContext, R.raw.sound1);
                break;
            case "贰":
                mp = MediaPlayer.create(mContext, R.raw.sound2);
                break;
            case "叁":
                mp = MediaPlayer.create(mContext, R.raw.sound3);
                break;
            case "肆":
                mp = MediaPlayer.create(mContext, R.raw.sound4);
                break;
            case "伍":
                mp = MediaPlayer.create(mContext, R.raw.sound5);
                break;
            case "陆":
                mp = MediaPlayer.create(mContext, R.raw.sound6);
                break;
            case "柒":
                mp = MediaPlayer.create(mContext, R.raw.sound7);
                break;
            case "捌":
                mp = MediaPlayer.create(mContext, R.raw.sound8);
                break;
            case "玖":
                mp = MediaPlayer.create(mContext, R.raw.sound9);
                break;
            case "拾":
                mp = MediaPlayer.create(mContext, R.raw.soundshi);
                break;
            case "佰":
                mp = MediaPlayer.create(mContext, R.raw.soundbai);
                break;
            case "仟":
                mp = MediaPlayer.create(mContext, R.raw.soundqian);
                break;
            case "角":
                mp = MediaPlayer.create(mContext, R.raw.soundjiao);
                break;
            case "分":
                mp = MediaPlayer.create(mContext, R.raw.soundfen);
                break;
            case "元":
                mp = MediaPlayer.create(mContext, R.raw.soundyuan);
                break;
            case "整":
                mp = MediaPlayer.create(mContext, R.raw.soundzheng);
                break;
            case "万":
                mp = MediaPlayer.create(mContext, R.raw.soundwan);
                break;
            case "1":
                mp = MediaPlayer.create(mContext, R.raw.alipay);
                break;

            case "2":
                mp = MediaPlayer.create(mContext, R.raw.weixinpay);
                break;

            case "4":
                mp = MediaPlayer.create(mContext, R.raw.yipay);
                break;

            case "$":
                mp = MediaPlayer.create(mContext, R.raw.soundsuccess);

                break;

        }
        //下面这三句是控制语速，但是只适用于Android6.0 以上，以下的就会报错，所以这个功能下次更新时解决
//        PlaybackParams pbp = new PlaybackParams();
//        pbp.setSpeed(1.5F);
//        mp.setPlaybackParams(pbp);
        mp.stop();
        return mp;
    }

    /**
     * 截取字符串
     *
     * @param str  需要截取的字符串
     * @param idx1 开始位置
     * @param idx2 截止位置
     * @return 截取后的字符串
     */
    private String subString(String str, int idx1, int idx2) {
        try {
            return str.substring(idx1, idx2);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 传递一个字符串参数，如果是null返回“”字符串，否则去除前后的空格。
     *
     * @param str 传入参数
     * @return 没有前后没有空格的字符串
     */
    private final String trim(String str) {
        if (str == null) return "";
        else return str.trim();
    }

    /**
     * 把double类型数据转换成有格式的字符串
     *
     * @param d      需要转换的double类型数据
     * @param format 格式化方式
     * @return 有格式的字符串
     */
    private String formatDoubleToString(double d, String format) {
        String doubleStr = String.valueOf(d);
        java.text.DecimalFormat decf = new java.text.DecimalFormat(format);
        String formatStr = decf.format(d);
        /**
         * 通过java保留小数了
         * 如果转换前的长度>转换后的长度，Java的转换就有可能出错
         */
        if (doubleStr.length() > formatStr.length()) {
            /**
             * 如果前面的都一致，但最后一位大于4就需要进位
             * 否则不进位
             */
            if (formatStr.equals(doubleStr.substring(0, formatStr.length()))) {
                /**
                 * 取转换前的后一位，
                 * 有可能是“.”
                 */
                String followStr = doubleStr.substring(formatStr.length(), formatStr.length() + 1);
                if (".".equals(followStr)) {
                    followStr = doubleStr.substring(formatStr.length() + 1, formatStr.length() + 2);
                }

                if (Integer.valueOf(followStr).intValue() > 4) {
                    /**
                     * 这个时候Java没有进位
                     */
                    formatStr = decf.format(Double.valueOf(formatStr).doubleValue() + Double.valueOf(format.substring(0, format.length() - 1) + "1").
                            doubleValue());
                }
            }
        }

        return formatStr;
    }

    /**
     * 把一个都money转换成大写的money
     *
     * @param d 需要转换的money
     * @return 换成大写的money
     */
    private String capitalValueOf(double d) {
        String lowStr;
        int strLen;
        String currentStr;
        String upperPart;
        String upperStr = "";
        int index = 0;
        int findCount;
        String chns = "零壹贰叁肆伍陆柒捌玖";
        String units = "分角  拾佰仟万拾佰仟亿拾佰仟万";

        if (d >= 100000000 || d < 0) {
            return "";
        }
        if (d == 0) {
            return "零元整";
        }
        lowStr = trim(formatDoubleToString(d, "0.00"));
        strLen = lowStr.length();
        if (strLen == 0) {
            return "";
        }
        while (index < strLen) {
            currentStr = subString(lowStr, strLen - index - 1, strLen - index);
            if (".".equals(currentStr)) {
                upperPart = "元";
            } else {
                upperPart = subString(chns, Integer.valueOf(currentStr).intValue(), Integer.valueOf(currentStr).intValue() + 1);
            }
            upperPart += trim(subString(units, index, index + 1));
            upperStr = upperPart + upperStr;
            index += 1;
        }
        for (; ; ) {
            findCount = 0;
            if (upperStr.indexOf("拾零万零仟") < 0) {
                if (upperStr.indexOf("拾零万") >= 0) {
                    if ("仟".equals(subString(upperStr, upperStr.indexOf("拾零万") + 4, upperStr.indexOf("拾零万") + 5))) {
                        findCount++;
                        upperStr = upperStr.replaceFirst("拾零万", "拾万零");
                    }
                }
            }
            if (upperStr.indexOf("零元") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零元", "元");
            }
            if (upperStr.indexOf("零拾") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零拾", "零");
            }
            if (upperStr.indexOf("零佰") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零佰", "零");
            }
            if (upperStr.indexOf("零仟") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零仟", "零");
            }
            if (upperStr.indexOf("零万") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零万", "万");
            }
            if (upperStr.indexOf("零亿") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零亿", "亿");
            }
            if (upperStr.indexOf("零零") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零零", "零");
            }
            if (upperStr.indexOf("零角零分") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零角零分", "整");
            }
            if (upperStr.indexOf("零分") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零分", "整");
            }
            if (upperStr.indexOf("零角") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零角", "零");
            }
            if (upperStr.indexOf("零亿零万零元") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零亿零万零元", "亿元");
            }
            if (upperStr.indexOf("亿零万零元") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("亿零万零元", "亿元");
            }
            if (upperStr.indexOf("零亿零万") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零亿零万", "亿");
            }
            if (upperStr.indexOf("零万零元") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("零万零元", "万元");
            }
            if (upperStr.indexOf("万零元") >= 0) {
                findCount++;
                upperStr = upperStr.replaceAll("万零元", "万元");
            }
            if (findCount == 0) {
                break;
            }
        }
        while ("元".equals(subString(upperStr, 0, 1)) || "零".equals(subString(upperStr, 0, 1)) || "角".equals(subString(upperStr, 0, 1)) || "分".equals(subString(upperStr, 0, 1)) || "整".equals(subString(upperStr, 0, 1))) {
            strLen = upperStr.length();
            upperStr = subString(upperStr, 1, strLen);
        }
        return upperStr;
    }

}
