package ru.spbstu.telematics.java;

import java.math.BigDecimal;

import org.apache.commons.math3.util.Pair;

public class Result implements Runnable{

	private Data D;
	private Double step_t;
	private Double step_x;
	
	Result(Data D, Double step_t, Double step_x){
		this.D = D;
		this.step_t = step_t;
		this.step_x = step_x;
	}
	
	public void run(){
		
		synchronized(D){
			try{
				long starttime = System.nanoTime();
				D.wait();
				long totaltime = System.nanoTime() - starttime;
				System.out.println("Results:");
				Double cur_t = step_t;
				Double cur_x = step_x;
				while(D.A.containsKey(new Pair(cur_t, cur_x))){
					while(D.A.containsKey(new Pair(cur_t, cur_x))){
						System.out.println(cur_t + " " + cur_x + " " + D.A.get(new Pair(cur_t, cur_x)));
						cur_x = BigDecimal.valueOf(cur_x).add(BigDecimal.valueOf(step_x)).doubleValue();
					}
					cur_t = BigDecimal.valueOf(cur_t).add(BigDecimal.valueOf(step_t)).doubleValue();
					cur_x = 0.1;
				}
				System.out.println("Calculation time: " + (totaltime / 1000000) + "ms");
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}
}
