package org.ossmeter.metricprovider.rascal.metric;

import org.ossmeter.metricprovider.rascal.RascalMetrics;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.ossmeter.metricprovider.rascal.metric.trans.model.LinesOfCodeData;
import org.ossmeter.metricprovider.rascal.metric.trans.model.Loc;
import org.ossmeter.platform.Date;
import org.rascalmpl.interpreter.Evaluator;
import org.rascalmpl.interpreter.NullRascalMonitor;

import com.mongodb.DB;

public class LinesOfCode extends RascalMetrics {
	private Loc db;
	
	public LinesOfCode() {
		this.module = "LOC";
		this.function = "countLoc";
	}
	
	public void adapt(DB db) {
		this.db = new Loc(db);
	}

	public void measure(Evaluator eval, IValue file, String revision, String fileURL, Date today) {
		ITuple result = (ITuple) eval.call(new NullRascalMonitor(), module, function, file);
		LinesOfCodeData locData = db.getLinesOfCode().findOneByFile(fileURL);//new LinesOfCodeData();
		if (locData == null) {
			locData = new LinesOfCodeData();
		}
		locData.setFile(fileURL);
		locData.setRevisionNumber(revision);
		locData.setTotalLines(((IInteger)result.get(0)).longValue());
		locData.setCommentedLines(((IInteger)result.get(1)).longValue());
		locData.setEmptyLines(((IInteger)result.get(2)).longValue());
		locData.setSourceLines(((IInteger)result.get(3)).longValue());
		db.getLinesOfCode().add(locData);
	}

	@Override
	public void sync() {
		this.db.sync();
	}
}
