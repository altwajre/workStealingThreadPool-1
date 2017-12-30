package bgu.spl.a2;

/**
 * Describes a monitor that supports the concept of versioning - its idea is
 * simple, the monitor has a version number which you can receive via the method
 * {@link #getVersion()} once you have a version number, you can call
 * {@link #await(int)} with this version number in order to wait until this
 * version number changes.
 *
 * you can also increment the version number by one using the {@link #inc()}
 * method.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */


public class VersionMonitor {
	private int version;

	/**
	 * getter
	 * @return the current version of the monitor.
	 */
    public int getVersion() {
        return this.version;
    }

    
    /**
     * increments the current version and notify all the waiting threads.
     */
   //wait should be under synchronized scope
    public void inc() {     
        synchronized (this) {
        	this.version++;
			this.notifyAll();
		}
    }

    /**
     * makes the current thread wait until the version of the monitor changes.
     * @param version- the current version of the version monitor
     * @throws InterruptedException
     */
    //wait should be under synchronized scope.
    public void await(int version) throws InterruptedException {
    	synchronized (this) {
    		while(this.version == version)
    			this.wait();
		}
    }
}
