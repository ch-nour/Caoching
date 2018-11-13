$(function() {


    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Fais vite",
            value:  document.getElementById("col1").value
        }, {
            label: "Fais un effort",
            value: document.getElementById("col2").value
        }, {
            label: "Sois fort",
            value: document.getElementById("col3").value
        }, {
            label: "Sois parfait",
            value: document.getElementById("col4").value
        }, {
            label: "Fais plaisir",
            value: document.getElementById("col5").value
        }],
        resize: true
    });
    
});