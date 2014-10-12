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
});

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

	var ids = $.map(app.compare, function(a) { return a.id; });

	if ($.inArray(projectid, ids) == -1) {
		app.compare.projects.push({
			id:projectid,
			name:projectname
		});
		$.cookie("compare", app.compare.projects, { expires: 7, path: '/' });
	}
	$("#compare-bar").fadeIn(500);
	drawCompareBar();
}

function closeCompareBar() {
	$("#compare-bar").fadeOut(500);
}

function drawCompareBar(warn) {
	$("#compare-bar-projects").empty();
	for (p in app.compare.projects) {
		$("#compare-bar-projects").append(
			'<div class="col-md-3"><h3>' + app.compare.projects[p].name + '</h3></div>'
			);
	}
}

function drawSparklineTable(table, metriclist) {
         $.get("http://localhost:8182/projects/p/@project.getShortName()/s/"+metriclist.join("+"), function (result) {
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