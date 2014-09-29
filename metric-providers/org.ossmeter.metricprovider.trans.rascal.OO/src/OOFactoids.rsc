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
	real a,
	real rr,
	real sr,
	map[loc, int] dit,
	map[loc, int] noc,
	map[loc, int] noa,
	map[loc, int] nom,
	map[loc, real] mif,
	map[loc, real] aif,
	real mhf,
	real ahf,
	real pf
) {
	// TODO determine appropriate ranges

	<ok, s> = metricsWithinRange([
		<a, "Abstractness", 0.0, 1.0>,
		<rr, "Reuse Ratio", 0.0, 1.0>,
		<sr, "Specialization Ratio", 0.0, 1.0>,
		<mhf, "Method Hiding Factor", 0.0, 1.0>,
		<ahf, "Attribute Hiding Factor", 0.0, 1.0>,
		<pf, "Polymorphism Factor", 0.0, 1.0>
	]);

	okResults = ok;
	txt = s;

	<ok, s> = mapMetricsWithinRange([
		<dit, "Depth in Inheritance Tree", 0.0, 1.0>,
		<noc, "Number of Children", 0.0, 1.0>,
		<noa, "Number of Attributes", 0.0, 1.0>,
		<nom, "Number of Methods", 0.0, 1.0>,
		<mif, "Method Inheritance Factor", 0.0, 1.0>,
		<aif, "Attribute Inheritance Factor", 0.0, 1.0>
	]);
	
	okResults += ok;
	txt += s;

	maxOkResults = 12;
	stars = \one();
	
	if (okResults > maxOkResults - 6) {
		stars = two();
	}
	else if (okResults > maxOkResults - 4) {
		stars = three();
	}
	else if (okResults > maxOkResults - 2) {
		stars = four();
	}
	
	txt = "The results of <okResults> out of <maxOkResults> complexity metrics for the <language> code are out of range.
		  `The measured values are:\n" + txt;
	
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
