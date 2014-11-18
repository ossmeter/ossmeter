var app = {
	loggedIn : false,
	grid : {
		sparks : [],
		notifications : []
	},
	tooltipOptions : { 
		delay: { "show": 500, "hide": 100 }
	},
	popoverOptions : { 
		delay: { "show": 100, "hide": 1000 },
		template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
	},
	compare : {
		projects : [],
		metrics : ["bugs", "averageSentiment", "cumulativeComments"]
	},
	qualityModel : {
		categories : [
			{ 
				name : "Source Code",
				factoids : [],
				metrics : []
			},{ 
				name : "Communication Channels",
				factoids : [],
				metrics : []
			},{ 
				name : "Bug Tracking Systems",
				factoids : ["cocomo"],
				metrics : ["bugOpenTime", "sentimentAtThreadEnd", "averageSentiment", "cumulativeComments", "cumulativeBugs", "invalidBugs", "patches", "bugs", "worksForMeBugs", "resolvedClosedBugs", "nonResolvedClosedBugs", "sentimentAtThreadBeggining", "cumulativePatches", "bugOpenTime-bugs", "wontFixBugs", "duplicateBugs", "comments", "commitsovertimeline", "fixedBugs"]
			}
		]
	}
}

$(function() {
	$(".tip").tooltip(app.tooltipOptions);
	$(".pop").popover(app.popoverOptions);
	$.cookie.json = true;
	$.cookie.defaults.path = "/";

	// TODO: not working correctly
	//applyMoreLessDescription();
});

// Credit: http://shakenandstirredweb.com/240/jquery-moreless-text
function applyMoreLessDescription() {
// The height of the content block when it's not expanded
var adjustheight = 100;
// The "more" link text
var moreText = "+  More";
// The "less" link text
var lessText = "- Less";

// Sets the .more-block div to the specified height and hides any content that overflows
$(".more-less .more-block").css('height', adjustheight).css('overflow', 'hidden');

// The section added to the bottom of the "more-less" div
$(".more-less").append('<p class="continued">[&hellip;]</p><a href="#" class="adjust"></a>');

$("a.adjust").text(moreText);

$(".adjust").toggle(function() {
		$(this).parents("div:first").find(".more-block").css('height', adjustheight).css('overflow', 'hidden');
		$(this).parents("div:first").find("p.continued").css('display', 'block');
		$(this).text(moreText);
		$(this).parents("div:first").find(".more-block").css('height', 'auto').css('overflow', 'visible');
		// Hide the [...] when expanded
		$(this).parents("div:first").find("p.continued").css('display', 'none');
		$(this).text(lessText);
	}, function() {
});
}

function toggleSpark(elem, projectid, projectname, metricid, metricname) {
	
	jsRoutes.controllers.Account.watchSpark(projectid, metricid, projectname, metricname
		).ajax().success(function(result) {
			var _ind = $.inArray(metricid, app.grid.sparks);
			if (_ind == -1) {
				app.grid.sparks.push(metricid);
				$(elem).addClass("active");
			} else {
				app.grid.sparks.splice(_ind, 1);
				$(elem).removeClass("active");
			}
		}).error(function(result) {
			console.log("Error, unable to toggle spark watch.");
		});
}

function addProjectToComparison(projectid, projectname) {
	"use strict";
	var projects = $.cookie("cccc");

	if (!projects) {
		projects = [];
	}

	var ids = $.map(projects, function(a) { return a.id; });

	if ($.inArray(projectid, ids) == -1) {
		projects.push({
			id:projectid,
			name:projectname
		});
		$.cookie("cccc", projects);
	}
	$("#compare-bar").fadeIn(500);
	drawCompareBar();
}

function closeCompareBar() {
	$("#compare-bar").fadeOut(500);
}

function drawCompareBar(warn) {
	"use strict";
	$("#compare-bar-projects").empty();
	var projects = $.cookie("cccc");
	for (var p in projects) {
		$("#compare-bar-projects").append(
			'<div class="col-md-3"><h3>' + projects[p].name + '</h3></div>'
			);
	}
}

function drawSparklineTable(projectname, table, metriclist) {
         $.get("http://localhost:8182/projects/p/"+projectname+"/s/"+metriclist.join("+"), function (result) {
            for (var r in result) {
                var data = result[r];

                if (r == 0) { // Set up the header
                    $(table + " > thead:last").append(
                        "<tr><th>metric</th>" +
                        "<th>" + data.firstDate + "</th>" +
                        "<th>" + data.months + " months</th>" +
                        "<th>" + data.lastDate + "</th>" +
                        "<th>low</th>" +
                        "<th>high</th></tr>");
                }

                $(table + " > tbody:last").append("<tr><td title=\"" + data.description + "\">" + data.name + 
                    "</td><td>" + Math.round(data.first * 100) / 100  +
                    "</td><td><img class=\"spark\" src=\"http://localhost:8182" + data.spark + "\" />" +  
                    "</td><td>" + Math.round(data.last * 100) / 100  + 
                    "</td><td>" + Math.round(data.low * 100) / 100 + 
                    "</td><td>" + Math.round(data.high * 100) / 100 + "</td></tr>");
            }
        });
    }

function compareProjects() {
	var metrics = app.compare.metrics;
	// Sparks
	$.get("http://localhost:8182/projects/p/@project.getShortName()/s/"+metrics.join("+"), function (result) {
		for (var r in result) {
            var data = result[r];


        }
	});
}

function getOneYearAgoDateString() {
	var d = new Date();
	return (d.getFullYear()-1) + "" + (d.getMonth()+1) + "" + d.getDate();
}

/*
	Taken from: http://stackoverflow.com/questions/10599933/convert-long-number-into-abbreviated-string-in-javascript-with-a-special-shortn 
*/
function abbreviateNumber(value) {
    var newValue = value;
    if (value >= 1000) {
        var suffixes = ["", "k", "m", "b","t"];
        var suffixNum = Math.floor( (""+value).length/3 );
        var shortValue = '';
        for (var precision = 2; precision >= 1; precision--) {
            shortValue = parseFloat( (suffixNum != 0 ? (value / Math.pow(1000,suffixNum) ) : value).toPrecision(precision));
            var dotLessShortValue = (shortValue + '').replace(/[^a-zA-Z 0-9]+/g,'');
            if (dotLessShortValue.length <= 2) { break; }
        }
        if (shortValue % 1 != 0)  shortNum = shortValue.toFixed(1);
        newValue = shortValue+suffixes[suffixNum];
    } else if (value % 1 != 0){
    	newValue = value.toFixed(2);
    }
    return newValue;
}

function drawSpiderChart(container, factoids) {

	var d = [];

	for (var f in factoids) {
		var stars = 1;

		switch (factoids[f].stars) {
			case "FOUR": stars = 4; break;
			case "THREE": stars = 3; break;
			case "TWO": stars = 2; break;
		}

		var ax = { axis: factoids[f].name, value: stars } ;
		d.push(ax);
	}

	console.log(d);

	var config = {
		w: 200,
		h: 200,
		maxValue: 4,
		levels: 4,
		ExtraWidthX:200
	};

	RadarChart.draw(container, [d], config);
}