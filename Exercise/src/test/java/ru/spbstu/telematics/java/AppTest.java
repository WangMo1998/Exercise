package ru.spbstu.telematics.java;

import org.apache.commons.math3.util.Pair;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
		Double T = 1.2; // Период волны
    	Double V = 15.0; // Скорость волны
    	Double A0 = 0.02; // Амплитуда колебаний
    	
    	Double step_t = 0.1; // Шаг времени
    	Double step_x = 1.0; // Шаг расстояния
    	
    	Integer steps_t = 100;
    	Integer steps_x = 100;
    	
    	Data D = new Data();
    	
    	Integer numthreads = 2;
    	
    	Thread threads[] = new Thread[numthreads+1];
    	for(int t = 1; t<=numthreads; t++){
    		threads[t] = new Thread(new Calc(D,T,V,A0,step_t,step_x,steps_t,steps_x), "Thread "+t);
    		threads[t].start();
    	}
    	assertTrue(true);
    	while(true){
    		boolean alive = false;
    		for(int t=1; t<=numthreads; t++) if(threads[t].isAlive()) {
    			alive = true;
    			break;
    		}
    		if(alive) {
    			try{
    			Thread.sleep(100);
    			} catch(InterruptedException e){
    				e.printStackTrace();
    			}
    		} else break;
    	}
    	assertEquals(0.01, D.A.get(new Pair(4.0, 45.0)));
    }
}
