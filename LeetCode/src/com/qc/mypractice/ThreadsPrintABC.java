package com.qc.mypractice;

/**
 * 三线程打印ABC，
 * 题目要求：
 *   建立三个线程，A线程打印10次A，B线程打印10次B,C线程打印10次C，要求线程同时运行，交替打印10次ABC。
 * 这个问题用Object的wait()，notify()就可以很方便的解决
 * 
 * 程序运行的主要过程：
 * 初始条件：三个线程按照A,B,C的顺序来启动
 * A线程最先运行，持有C、A对象锁，后释放A、C锁，唤醒B；
 * 线程B等待A锁，再申请B锁，后打印B，再释放B、A锁，唤醒C；
 * 线程C等待B锁，再申请C锁，后打印C，再释放C、B锁，唤醒A，继续循环执行。
 * @author Qc
 * @see https://blog.csdn.net/evankaka/article/details/44153709
 */
public class ThreadsPrintABC {
	public static void main(String[] args) throws InterruptedException {
		Object a = new Object();
		Object b = new Object();
		Object c = new Object();
		
		ThreadPrinter tpA = new ThreadPrinter("A", c, a);
		ThreadPrinter tpB = new ThreadPrinter("B", a, b);
		ThreadPrinter tpC = new ThreadPrinter("C", b, c);
		
		tpA.start();
		Thread.sleep(100);//保证线程按A、B、C启动
		tpB.start();
		Thread.sleep(100);//保证线程按A、B、C启动
		tpC.start();
	}
	
}

class ThreadPrinter extends Thread{
	private String name = null;
	private Object pre = null;
	private Object self = null;
	
	public ThreadPrinter(String name, Object pre, Object self) {
		this.name = name;
		this.pre = pre;
		this.self = self;
	}

	@Override
	public void run() {
		super.run();
		for(int i=0; i<10; i++){
			synchronized (pre) {
				synchronized (self) {
					System.out.println(Thread.currentThread().getName() + " : " + name + " count: " + (i+1));
					self.notify();
				}
				
				try {
					pre.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
