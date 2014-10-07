module OOFactoids

import Prelude;

import org::ossmeter::metricprovider::MetricProvider;


private tuple[int, str] metricsWithinRange(lrel[num result, str label, real min, real max] tests) {
	ok = 0;
	txt = "";
	
	for (<r, l, mn, mx> <- tests) {
		txt += "<label>: <r>";
		if (r >= mn && r <= mx) {
			ok += 1;
		} else {
			txt += " (!)";
		}
		txt += "\n";	
	}

	return <ok, txt>;
}

private tuple[int, str] mapMetricsWithinRange(lrel[map[loc, num] result, str label, real min, real max] tests) {
	ok = 0;
	txt = "";
	
	for (<r, l, mn, mx> <- tests) {
		txt += "<label>: <r>";
		
		a = 0;  // TODO gini? or specific per metric?
		
		if (a >= mn && a <= mx) {
			ok += 1;
		} else {
			txt += " (!)";
		}
		txt += "\n";	
	}

	return <ok, txt>;
}


Factoid Complexity(
	str language,
	map[loc, int] dit
) {
	if (dit == ()) {
		throw undefined("No DIT data", |file:///|);
	}
	
	numClassesWithBadDepth = ( 0 | it + 1 | c <- dit, dit[c] > 5 ); // TODO find source for DIT > 5
	
	badPercentage = numClassesWithBadDepth * 100.0 / size(dit);
	
	stars = four();
	
	if (badPercentage > 20) {
		stars = \one();
	}
	else if (badPercentage > 10) {
		stars = two();
	}
	else if (badPercentage > 5) {
		stars = three();
	}

	txt = "The percentage of <language> classes with a problematic inheritance depth is <badPercentage>%."; 

	return factoid(txt, stars);
}


Factoid Coupling(
	str language,
	map[loc, int] cbo
) {
	if (cbo == ()) {
		throw undefined("No CBO data", |file:///|);
	}
	
	numClassesWithBadCoupling = ( 0 | it + 1 | c <- cbo, cbo[c] > 14 );
	
	// CBO > 14 is too high according to:
	// Houari A. Sahraoui, Robert Godin, Thierry Miceli: Can Metrics Help Bridging the Gap Between the Improvement of OO Design Quality and Its Automation?
	
	badPercentage = numClassesWithBadCoupling * 100.0 / size(cbo);
	
	stars = four();
	
	if (badPercentage > 20) {
		stars = \one();
	}
	else if (badPercentage > 10) {
		stars = two();
	}
	else if (badPercentage > 5) {
		stars = three();
	}

	txt = "The percentage of <language> classes with problematic coupling is <badPercentage>%."; 

	return factoid(txt, stars);
}


Factoid Cohesion(
	str language,
	map[loc, int] lcom4
) {
	if (lcom4 == ()) {
		throw undefined("No LCOM4 data", |file:///|);
	}
	
	numClassesWithBadCohesion = ( 0 | it + 1 | c <- lcom4, lcom4[c] != 1 );
	
	badPercentage = numClassesWithBadCohesion * 100.0 / size(lcom4);
	
	stars = four();
	
	if (badPercentage > 20) {
		stars = \one();
	}
	else if (badPercentage > 10) {
		stars = two();
	}
	else if (badPercentage > 5) {
		stars = three();
	}

	txt = "The percentage of <language> classes with problematic cohesion is <badPercentage>%."; 

	return factoid(txt, stars);
}
