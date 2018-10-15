package fr.nouas.pojo.utils;

import java.util.HashMap;
import java.util.Map;

import fr.nouas.main.action.AddCategory;
import fr.nouas.main.action.AddQuestion;
import fr.nouas.main.action.AddQuestionnaire;
import fr.nouas.main.action.CheckProfil;
import fr.nouas.main.action.CheckReponse;
import fr.nouas.main.action.DeleteCategory;
import fr.nouas.main.action.DeleteQuestion;
import fr.nouas.main.action.DeleteQuestionnaire;
import fr.nouas.main.action.EditCategory;
import fr.nouas.main.action.EditQuestion;
import fr.nouas.main.action.EditQuestionnaire;
import fr.nouas.main.action.HomeAction;
import fr.nouas.main.action.LogIn;
import fr.nouas.main.action.LogOut;
import fr.nouas.main.action.ShowCategorie;
import fr.nouas.main.action.ShowQuestionnaire;
import fr.nouas.main.action.SignIn;
import fr.nouas.main.action.ValidQuestionnaire;

public final class ActionManager  { //final pour que personne puisse la modifier et personne la prenne en heritage
		
	
		public static final String ACTION_HOME ="home";
	    public static final String ACTION_ADD_QUESTION = "addQuestion";
	    public static final String ACTION_EDIT_QUESTION = "editQuestion";
	    public static final String ACTION_DELETE_QUESTION = "deleteQuestion";
	    public static final String ACTION_ADD_CATEGORY = "addCategory";
	    public static final String ACTION_EDIT_CATEGORY = "editCategory";
	    public static final String ACTION_DELETE_CATEGORY = "deleteCategory";
	    public static final String ACTION_ADD_QUESTIONNAIRE  = "addQuestionnaire";
	    public static final String ACTION_SHOW_QUESTIONNAIRE = "questionnaire";
	    public static final String ACTION_EDIT_QUESTIONNAIRE = "editQuestionnaire";
	    public static final String ACTION_DELETE_QUESTIONNAIRE = "deleteQuestionnaire";
	    public static final String ACTION_SHOW_CATEGORIE = "categorie";
	    public static final String ACTION_SIGN_IN = "signIn";
	    public static final String ACTION_LOG_IN = "logIn";
	    public static final String ACTION_LOG_OUT = "logOut";
	    public static final String ACTION_VALID_QUESTIONNAIRE = "validQuestionnaire";
	    public static final String ACTION_CHECK_REPONSE = "checkReponse";
	    public static final String ACTION_CHECK_PROFIL = "checkProfil";




	private ActionManager() {
	} // pour la protger et quon puisse pas linstancier

	private static Map<String, Action> actions;// recoit le nom de laction (actionname)string et cree un action

	static {
		actions = new HashMap<String, Action>();
		actions.put(ACTION_HOME, new HomeAction());
		actions.put(ACTION_ADD_QUESTION, new AddQuestion());
		actions.put(ACTION_EDIT_QUESTION, new EditQuestion());
		actions.put(ACTION_DELETE_QUESTION, new DeleteQuestion());
		actions.put(ACTION_ADD_CATEGORY, new AddCategory());
		actions.put(ACTION_DELETE_CATEGORY, new DeleteCategory());
		actions.put(ACTION_EDIT_CATEGORY, new EditCategory());
		actions.put(ACTION_ADD_QUESTIONNAIRE, new AddQuestionnaire());
		actions.put(ACTION_SHOW_QUESTIONNAIRE, new ShowQuestionnaire());
		actions.put(ACTION_EDIT_QUESTIONNAIRE, new EditQuestionnaire());
		actions.put(ACTION_DELETE_QUESTIONNAIRE, new DeleteQuestionnaire());
		actions.put(ACTION_SHOW_CATEGORIE, new ShowCategorie());
		actions.put(ACTION_SIGN_IN, new SignIn());
		actions.put(ACTION_LOG_IN, new LogIn());
		actions.put(ACTION_LOG_OUT, new LogOut());
		actions.put(ACTION_DELETE_QUESTION, new DeleteQuestion());
		actions.put(ACTION_VALID_QUESTIONNAIRE, new ValidQuestionnaire());
		actions.put(ACTION_CHECK_REPONSE, new CheckReponse ());
		actions.put(ACTION_CHECK_PROFIL, new CheckProfil ());
	}

	public static Action getAction(String actionName) { // c la que en fonction de laction (string) envoyer par la
														// frontservlet on fait une action
		return actions.get(actionName); // get permet de recuperer l'acion de la map grace a la cle
	}
}
