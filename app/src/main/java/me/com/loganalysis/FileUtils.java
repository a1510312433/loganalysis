package me.com.loganalysis;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by caobin on 2017/3/15.
 */

public class FileUtils {
    private static final String TAG = "FileUtils";
    public static AnalysisResult LogAnalysis(Context context,String filename){
        AnalysisResult reslut = new AnalysisResult();
        ArrayList<String> array = new ArrayList<>();
        ArrayList<String> array1s = new ArrayList<>();
        ArrayList<String> array2s = new ArrayList<>();
        ArrayList<String> array5s = new ArrayList<>();
        ArrayList<String> arrayfail = new ArrayList<>();
        File file = new File(getSDCardPath()+"/tcb/"+filename);
//        Toast.makeText(context,""+file.length(),Toast.LENGTH_LONG).show();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line ;
            while((line=br.readLine())!=null){
                if(line.contains("时间差")){
                    array.add(line);
                    Log.e(TAG, "onClick: "+line, null);
                    String[] splits = line.split("时间差:");
                    int timespan = Integer.parseInt(splits[1]);
                    if(Integer.parseInt(splits[1])>1000){
                        array1s.add(line);
                        if(timespan>2000&&timespan<5000){
                            array2s.add(line);
                        }
                        if(timespan>=5000){
                            array5s.add(line);
                        }
                    }
                }
                if(line.contains("Constant.COMPLETE_ORDER失败了")&&line.contains("nfchandle.do")){
                    arrayfail.add(line);
                }
            }
            reslut.setTimeSpan(array);
            reslut.setTimeSpan1s(array1s);
            reslut.setTimeSpan2s(array2s);
            reslut.setTimeSpan5s(array5s);
            reslut.setFails(arrayfail);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            reslut.setResults("文件未找到异常！");
            return reslut;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            reslut.setResults("文件编码异常！");
            return reslut;
        } catch (IOException e) {
            e.printStackTrace();
            reslut.setResults("IO异常！");
            return reslut;
        }
        int a = reslut.TimeSpan1s==null?0:reslut.TimeSpan1s.size();
        int a2 = reslut.TimeSpan2s==null?0:reslut.TimeSpan2s.size();
        int a5 = reslut.TimeSpan5s==null?0:reslut.TimeSpan5s.size();
        int afail = reslut.Fails==null?0:reslut.Fails.size();
        int b = reslut.TimeSpan==null?0:reslut.TimeSpan.size();
        double c = new BigDecimal(a*100.0/b).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        double c2 = new BigDecimal(a2*100.0/b).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        double c5 = new BigDecimal(a5*100.0/b).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        double cfail = new BigDecimal(afail*100.0/b).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        reslut.setResults("失败的订单个数："+reslut.Fails.size()+",比例为："+afail+"/"+b+"="+cfail+"%"+"\n"+
        "超过1秒的订单个数："+reslut.TimeSpan1s.size()+",比例为："+a+"/"+b+"="+c+"%"+"\n"+
        "超过2秒的订单个数："+reslut.TimeSpan2s.size()+",比例为："+a2+"/"+b+"="+c2+"%"+"\n"+
        "超过5秒的订单个数："+reslut.TimeSpan5s.size()+",比例为："+a5+"/"+b+"="+c5+"%"+"\n");
        return reslut;
    }
    /**
     * 获取SDCard的目录路径功能
     *
     * @return
     */
    public static String getSDCardPath() {
        String result = null;
        try {
            // 判断SDCard是否存在
            boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
            Log.e(TAG, "" + sdcardExist);
            if (sdcardExist) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                result = sdcardDir.toString();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
