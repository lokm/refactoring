<form  action="<c:url value='/addCategory'/>" method="POST" >
<select name="color" required>
	<option value="" selected disabled>Couleur</option>
	<option style="background-color:rgb(59, 153, 217)" value="rgb(59, 153, 217)" >bleu</option>
	<option style="background-color:rgb(56, 202, 117)" value="rgb(56, 202, 117)" >vert</option>
	<option style="background-color:rgb(213, 72, 62)" value="rgb(213, 72, 62)" >rouge</option>
	<option style="background-color:rgb(223, 123, 47)" value="rgb(223, 123, 47)" >orange</option>
	<option style="background-color:rgb(253, 203, 46)" value="rgb(253, 203, 46)" >jaune</option>
	<option style="background-color:rgb(145, 86, 168)" value="rgb(145, 86, 168)" >magenta</option>
	<option style="background-color:rgb(119, 48, 44)" value="rgb(119, 48, 44)" >marron</option>
	<option style="background-color:rgb(41, 41, 41)" value="rgb(41, 41, 41)" >noir</option>

</select>
<input type="text" name="titre_categorie" placeholder="Nom categorie" required/>
<input type="submit" value="Créer Catégorie" required/>

</form>