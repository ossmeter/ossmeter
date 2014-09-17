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
	map[loc, int] cbo,
	map[loc, int] dac,
	map[loc, int] mpc,
	real cf,
	map[loc, int] ce,
	map[loc, int] ca,
	map[loc, real] i,
	map[loc, int] rfc
) {
	// TODO determine appropriate ranges

	<ok, s> = metricsWithinRange([
		<cf, "Coupling Factor", 0.0, 1.0>
	]);

	okResults = ok;
	txt = s;

	<ok, s> = mapMetricsWithinRange([
		<cbo, "Coupling Between Objects", 0.0, 1.0>,
		<dac, "Data Abstraction Coupling", 0.0, 1.0>,
		<mpc, "Message Passing Coupling", 0.0, 1.0>,
		<ce, "Efferent Coupling", 0.0, 1.0>,
		<ca, "Afference Coupling", 0.0, 1.0>,
		<i, "Instability", 0.0, 1.0>,
		<rfc, "Responce for Class", 0.0, 1.0>
	]);
	
	okResults += ok;
	txt += s;

	maxOkResults = 8;
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
	
	txt = "The results of <okResults> out of <maxOkResults> coupling metrics for the <language> code are out of range.
		  `The measured values are:\n" + txt;
	
	return factoid(txt, stars);
}


Factoid Cohesion(
	str language,
	map[loc, int] lcom,
	map[loc, real] tcc,
	map[loc, real] lcc
) {
	// TODO determine appropriate ranges

	<okResults, txt> = mapMetricsWithinRange([
		<lcom, "Lack of Cohesion in Methods", 0.0, 1.0>,
		<tcc, "Tight Class Cohesion", 0.0, 1.0>,
		<lcc, "Loose Class Cohesion", 0.0, 1.0>
	]);

	maxOkResults = 3;
	stars = \one();
	
	if (okResults > maxOkResults - 3) {
		stars = two();
	}
	else if (okResults > maxOkResults - 2) {
		stars = three();
	}
	else if (okResults > maxOkResults - 1) {
		stars = four();
	}
	
	txt = "The results of <okResults> out of <maxOkResults> cohesion metrics for the <language> code are out of range.
		  `The measured values are:\n" + txt;
	
	return factoid(txt, stars);
}
