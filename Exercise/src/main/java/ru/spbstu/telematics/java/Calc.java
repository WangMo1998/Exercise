package ru.spbstu.telematics.java;


import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.math3.util.Pair;

public class Calc implements Runnable{
	
	private Data D;
	private Double T;
	private Double V;
	private Double A0;
	private Double step_t;
	private Double step_x;
	private Integer steps_t;
	private Integer steps_x;
	
	Calc(Data D, Double T, Double V, Double A0, Double step_t, Double step_x, Integer steps_t, Integer steps_x){
		this.D = D;
		this.T = T;
		this.V = V;
		this.A0 = A0;
		this.step_t = step_t;
		this.step_x = step_x;
		this.steps_t = steps_t;
		this.steps_x = steps_x;
	}
	
	public void run(){
		String name = Thread.currentThread().getName();
		
		Double cur_t;
		Double cur_x;
		
		synchronized(D) {
			//System.out.println(name + " calculates " + D.C);
			cur_t = this.getT();
			cur_x = this.getX();
			//System.out.println(cur_t + " " + cur_x);
			D.C++;
		}
		
		while(true){
			Double calc_A = this.getA(cur_t, cur_x);
			
			synchronized(D){
				D.A.put(new Pair(cur_t,cur_x), calc_A);
				if(D.C == steps_t * steps_x) {
					D.notify();
					return;
				}
				//System.out.println(name + " calculates " + D.C);
				cur_t = this.getT();
				cur_x = this.getX();
				//System.out.println(cur_t + " " + cur_x);
				D.C++;
			}
		}
		
	}
	
	private Double getA(Double t, Double x){
		return BigDecimal.valueOf(A0 * Math.cos( ((2 * Math.PI) / T) * (t - (x / V)))).setScale(8, RoundingMode.HALF_EVEN).doubleValue();
		//return A0 * Math.cos( ((2 * Math.PI) / T) * (t - (x / V)));
		/*BigDecimal bd_A0 = BigDecimal.valueOf(A0);
		BigDecimal bd_T = BigDecimal.valueOf(T);
		BigDecimal bd_t = BigDecimal.valueOf(t);
		BigDecimal bd_x = BigDecimal.valueOf(x);
		BigDecimal bd_V = BigDecimal.valueOf(V);
		BigDecimal bd_2 = BigDecimal.valueOf(2);
		BigDecimal bd_PI = BigDecimal.valueOf(Math.PI);
		return bd_A0.multiply(BigDecimal.valueOf(Math.cos(bd_2.multiply(bd_PI).divide(bd_T).multiply(bd_t.subtract(bd_x.divide(bd_V))).doubleValue()))).doubleValue();
		*/
	}
	
	private Double getT(){
		return BigDecimal.valueOf(this.step_t).multiply(BigDecimal.valueOf((this.D.C / this.steps_t) + 1)).doubleValue();
	}
	
	private Double getX(){
		return BigDecimal.valueOf(this.step_x).multiply(BigDecimal.valueOf((this.D.C % this.steps_x))).doubleValue();
	}

}
