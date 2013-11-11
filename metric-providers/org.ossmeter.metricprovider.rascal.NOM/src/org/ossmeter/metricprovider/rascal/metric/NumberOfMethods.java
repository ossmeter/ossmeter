package org.ossmeter.metricprovider.rascal.metric;

import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.ossmeter.metricprovider.rascal.RascalMetrics;
import org.ossmeter.metricprovider.rascal.metric.trans.model.Nom;
import org.ossmeter.metricprovider.rascal.metric.trans.model.NumberOfMethodsData;
import org.ossmeter.platform.Date;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;

import com.mongodb.DB;

public class NumberOfMethods extends RascalMetrics {

	private Nom db;
	
	public NumberOfMethods() {
		this.module = "NOM";
		this.function = "getNOM";
	}

	@Override
	public void adapt(DB db) {
		this.db = new Nom(db);
	}

	@Override
	public void sync() {
		this.db.sync();
	}

	@Override
	public void measure(Evaluator eval, IValue file, String revision,
			String fileURL, Date today) {
		IMap result = (IMap) eval.call(new NullRascalMonitor(), module, function, file);
		for (Iterator<Entry<IValue, IValue>> it = result.entryIterator(); it.hasNext(); ) {
			Entry<IValue, IValue> currentEntry = (Entry<IValue, IValue>)it.next();
			String key = ((IString)currentEntry.getKey()).getValue();
			if (!key.isEmpty()) {
				NumberOfMethodsData nomData = db.getTotalNumberOfMethods().findOneByClassName(key);
				if (nomData == null) {
					nomData = new NumberOfMethodsData();
				}
				nomData.setDate(today.toString());
				nomData.setRevisionNumber(revision);
				nomData.setClassName(key);
				nomData.setNOM(((IInteger)currentEntry.getValue()).longValue());
				db.getTotalNumberOfMethods().add(nomData);
			}
		}
	}

}
