package ru.spbstu.telematics.java;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Pair;


public class App 
{
    public static void main( String[] args )
    {
    	Double T = 1.2; // 襄痂钿 忸腠�
    	Double V = 15.0; // 殃铕铖螯 忸腠�
    	Double A0 = 0.02; // 漓镫栩箐� 觐脲徉龛�
    	
    	Double step_t = 0.1; // 剜� 怵屐屙�
    	Double step_x = 1.0; // 剜� 疣耨蝾�龛�
    	
    	Integer steps_t = 100;
    	Integer steps_x = 100;
    	
    	long start = System.currentTimeMillis();
    	Data D = new Data();
    	long end = System.currentTimeMillis();
    	System.out.println(end - start);
    	System.out.println(BigDecimal.valueOf(A0 * Math.cos( ((2 * Math.PI) / T) * (4.0 - (45.0 / V)))).setScale(8, RoundingMode.HALF_EVEN).doubleValue());
    	
    	/*
    	for(int t = 1; t<=2; t++){
    		Thread thr = new Thread(new Calc(D,T,V,A0,step_t,step_x,steps_t,steps_x), "Thread "+t);
    		thr.start();
    	}
    	
    	Thread thr = new Thread(new Result(D,step_t,step_x));
    	thr.start();
		*/
    }
}



