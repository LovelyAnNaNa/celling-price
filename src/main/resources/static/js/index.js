$(document).ready(function() {
	$("#menu").click(function() {
		$("#chid").css({"background":"red","width":"118px","color":"#FFFFFF"});
		$("#chid").toggle()
	});
	$("#chart-container").insertFusionCharts({
		  type: "column2d",
		  width: "100%",
		  height: "100%",
		  dataFormat: "json",
	  dataSource: {
		chart: {
		  caption: "Countries With Most Oil Reserves [2019-18]",
		  subcaption: "In MMbbl = One Million barrels",
		  xaxisname: "Country",
		  yaxisname: "Reserves (MMbbl)",
		  numbersuffix: "K",
		  theme: "fusion"
		},
		data: [
		  {
			label: "Venezuela",
			value: "290"
		  },
		  {
			label: "Saudi",
			value: "260"
		  },
		  {
			label: "Canada",
			value: "180"
		  },
		  {
			label: "Iran",
			value: "140"
		  },
		  {
			label: "Russia",
			value: "115"
		  },
		  {
			label: "UAE",
			value: "100"
		  },
		  {
			label: "US",
			value: "30"
		  },
		  {
			label: "China",
			value: "30"
		  }
		]
	  }
	});
});
