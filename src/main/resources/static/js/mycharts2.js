$(function() {

	
	    Morris.Donut({
	        element: 'morris-donut-chart',
	        data: [{
	            label: "FUITE",
	            value:  document.getElementById("colA").value
	        }, {
	            label: "AGRESSIVITE",
	            value: document.getElementById("colB").value
	        }, {
	            label: "MANIPULATION",
	            value: document.getElementById("colC").value
	        }, {
	            label: "ASSERTIVITÉ",
	            value: document.getElementById("colD").value
	        }],
	        resize: true
	    });

});