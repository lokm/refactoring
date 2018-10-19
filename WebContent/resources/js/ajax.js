$(document).ready(function() {
	
	$('#type').change(function() {
	    var type = $('#type option:selected').val();

	    var qcm = '<br> <textarea placeholder="Ajouter une question" name="questionQcm" form="formQuestion"/> <br>image : (optionnel)<input type = "file" name = "fichier" size = "500" form="formQuestion" /> <br> <input type="text" name="correctQcm" placeholder="Bonne r&#xE9;ponse" form="formQuestion" /><br><input type="text" name="notCorrectQcm1" placeholder="Mauvaise r&#xE9;ponse 1" form="formQuestion"/> <br> <input type="text" name="notCorrectQcm2" placeholder="Mauvaise r&#xE9;ponse 2" form="formQuestion"/> <br> <input type="submit" value="Ajouter Qcm" form="formQuestion"/><br>';
	    var questionSimple = '<br><textarea placeholder="Ajouter une question" name="questionSimple" form="formQuestion"/><br> image : (optionnel) <input type = "file" name = "fichier" size = "500" form="formQuestion"  /> <br> <input type="text" placeholder="Entrer mots cl&#xE9;s, Maximum 10, le reste sera ignor&#xE9;" name="reponse" class="reponse" form="formQuestion" /> <br> Pourcentage de mots cl&#xE9;s pour valider :<select name="pourcentageNeed" form="formQuestion"><option value="100" selected>100%</option><option value="75">75%</option><option value="50">50%</option> <option value="25">25%</option>   </select> <br> <input type="submit" value="Ajouter question"  form="formQuestion"/><br>';

	    $.ajax({
	        url : '/questionnaire',
	        data: type,
	        success: function() {
	           switch(type) {
	           	case "QCM":
	           		$('#afficheQuestionnaire').before(qcm);
	        	break;
	           	case "QUESTION_SIMPLE":
	           		$('#afficheQuestionnaire').before(questionSimple);
	        	break;
	           	default:
	           }
	        },
	    });
	});


});
