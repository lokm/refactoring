$(document).ready(function() {
	$(window).on("load", function() {
		$(".export").click(function() {
	        // Convertie la section avec l'id questionnaire en image
	        kendo.drawing.drawDOM($("#questionnaire"))
	        .then(function(group) {
	            // renvoie le résultat en PDF
	            return kendo.drawing.exportPDF(group, {
	                paperSize: "auto",
	                margin: { left: "1cm", top: "1cm", right: "1cm", bottom: "1cm" }
	            });
	        })
	        .done(function(data) {
	            // Sauvegarde le PDF
	            kendo.saveAs({
	                dataURI: data,
	                fileName: "questionnaire.pdf",
	                proxyURL: "/resources/pdf"
	            });
	        });
	    });
	});
});
