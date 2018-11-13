$(function() {

	
	    Morris.Donut({
	        element: 'morris-donut-chart',
	        data: [{
	            label: "الخضوع",
	            value:  document.getElementById("colA").value
	        }, {
	            label: "العدوانية",
	            value: document.getElementById("colB").value
	        }, {
	            label: "التسخير",
	            value: document.getElementById("colC").value
	        }, {
	            label: "الحزم",
	            value: document.getElementById("colD").value
	        }],
	        resize: true
	    });

});