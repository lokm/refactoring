package fr.nouas.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Questionnaire {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 150, nullable = false)
	private String name;

	@Column(length = 150, nullable = true)
	private int version;

	@Column(length = 150, nullable = true)
	private String description;

	@JoinTable(name = "questionnaires_users", 
			joinColumns = @JoinColumn(name = "questionnaire_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_questionnaire")), 
			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user")))
	@ManyToMany
	private List<User> users = new ArrayList<User>();

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE,
			CascadeType.PERSIST }, mappedBy = "questionnaire", orphanRemoval = true)
	private List<Question> questions;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, mappedBy = "questionnaire", orphanRemoval = true)
	private List<Reponse> reponses;

	@ManyToOne
	private Category category;

	public Questionnaire() {
	};

	public Questionnaire(int id, String name, List<Question> questions, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.questions = questions;
		this.category = category;
	}

	public Questionnaire(String name, String description, Category category) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void addQuestion(Question question) {
		this.questions.add(question);
	}

	public void deleteQuestion(Question question) {
		this.questions.remove(question);
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
