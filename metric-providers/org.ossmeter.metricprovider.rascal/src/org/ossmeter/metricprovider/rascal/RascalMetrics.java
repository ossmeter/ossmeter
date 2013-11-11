package org.ossmeter.metricprovider.rascal;

import org.eclipse.imp.pdb.facts.IValue;
import org.ossmeter.platform.Date;
import org.rascalmpl.interpreter.Evaluator;

import com.mongodb.DB;

public abstract class RascalMetrics {
	
	protected String module;
	protected String function;
	
	public abstract void adapt(DB db);
	public abstract void measure(Evaluator eval, IValue file, String revision, String fileURL, Date today);
	public abstract void sync();
	
	public String getModule() {
		return this.module;
	}
}
