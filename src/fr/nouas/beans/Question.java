package fr.nouas.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import fr.nouas.enums.TypeQuestion;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String question;

	@Column(length = 150, nullable = true)
	private String image;

	@OneToOne
	private Reponse bonneReponse;

	@Column(length = 150, nullable = true)
	private int pourcentageNeed;

	@OneToMany(mappedBy = "question", cascade = { CascadeType.REMOVE, CascadeType.PERSIST }, orphanRemoval = true)

	private List<Reponse> reponses;

	@ManyToOne(cascade = CascadeType.ALL)
	private Questionnaire questionnaire;

	@Enumerated(EnumType.STRING)
	private TypeQuestion type;

	@Column(length = 20, nullable = false)
	private int version = 0;

	public Question() {
	}

	public Question(int id, String question, Reponse bonneReponse, List<Reponse> reponses, Questionnaire questionnaire,
			TypeQuestion type, int version) {
		super();
		this.id = id;
		this.question = question;
		this.bonneReponse = bonneReponse;
		this.reponses = reponses;
		this.questionnaire = questionnaire;
		this.type = type;
		this.version = version;
	}

	public Question(String question, TypeQuestion type, Reponse bonneReponse, List<Reponse> reponses,
			Questionnaire questionnaire) {
		super();
		this.question = question;
		this.type = type;
		this.bonneReponse = bonneReponse;
		this.reponses = reponses;
		this.questionnaire = questionnaire;
	}

	public Question(String question, TypeQuestion type, Reponse bonneReponse, Questionnaire questionnaire) {
		super();
		this.question = question;
		this.type = type;
		this.bonneReponse = bonneReponse;
		this.questionnaire = questionnaire;
	}

	public int getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getPourcentageNeed() {
		return pourcentageNeed;
	}

	public void setPourcentageNeed(int pourcentageNeed) {
		this.pourcentageNeed = pourcentageNeed;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse> reponse) {
		this.reponses = reponse;
	}

	public void addReponse(Reponse reponse) {
		this.reponses.add(reponse);
	}

	public void deleteReponse(Reponse reponse) {
		this.reponses.remove(reponse);
	}

	public void deleteAllReponses() {
		this.reponses.removeAll(getReponses());
	}

	public TypeQuestion getType() {
		return type;
	}

	public void setType(TypeQuestion type) {
		this.type = type;
	}

	public Reponse getBonneReponse() {
		return bonneReponse;
	}

	public void setBonneReponse(Reponse bonneReponse) {
		this.bonneReponse = bonneReponse;
	}

}
