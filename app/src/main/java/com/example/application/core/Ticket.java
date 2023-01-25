package com.example.application.core;

import com.example.application.HasId;
import com.example.application.model.AbstractNamedEntity;
import com.example.application.model.ObjectName;
import com.example.application.model.User;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tickets")
public class Ticket extends AbstractNamedEntity implements HasId {

	@Id
	@Access(AccessType.FIELD)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "creationDate", nullable = false)
	@NotBlank
	//Дата создания
	private String creationDate;

	@Column(name = "status", nullable = false)
	@NotNull
	//Статус заявки
	private TicketStatus status;

	@Column(name = "responsibleSupplier")
	@Size(min = 2, max = 128)
	//Ответственный поставщик
	private String responsibleSupplier;

	@Column(name = "deliveryDate")
	@Size(min = 2, max = 20)
	//Дата поставки материала
	private String deliveryDate;

	@Column(name = "statusChangeDate")
	@Size(min = 2, max = 20)
	//Дата изменения статуса
	private String statusChangeDate;

	@Column(name = "isClosed")
	//Закрыта или нет
	private boolean isClosed;

	@Column(name = "closingDate")
	@Size(min = 2, max = 20)
	//Дата закрытия
	private String closingDate;

	@Column(name = "closedBy")
	@Size(min = 2, max = 128)
	//Кем закрыта заявка
	private String closedBy;

	@Column(name = "hasFactoryMarriage")
	//Имеет заводской брак или нет
	private boolean hasFactoryMarriage;

	@Column(name = "marriageDetectionDate")
	@Size(min = 2, max = 20)
	//Дата обнаружения брака
	private Date marriageDetectionDate;

	@Column(name = "marriageDetectedBy")
	@Size(min = 2, max = 128)
	//Кем обнаружен брак
	private String marriageDetectedBy;

	@Column(name = "marriageDescription")
	@Size(min = 2, max = 120)
	//Описание брака
	private String marriageDescription;

	@Column(name = "marriagePhotoUrl")
	@Size(min = 2, max = 150)
	//Ссылка на фотографию
	private String marriagePhotoUrl;

	@Column(name = "objectName")
	//К какому объекту относится заявка
	private ObjectName objectName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ticket")
	@OrderBy("name DESC")
	@OnDelete(action = OnDeleteAction.CASCADE)
	//Материал в заявке
	private List<Material> materials;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	//Нужно для вытягивания ID пользователя
	private User user;

	public Ticket(String name, List<Material> materials) {
		this.name = name;
		this.materials = materials;
		this.creationDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
		this.status = TicketStatus.NEW;
		User createdBy = user;

		String responsibleSupplier = "";
		Date deliveryDate = null;
		Date statusChangeDate = new Date();
		boolean isClosed = false;
		Date closingDate = null;
		String closedBy = " ";
		boolean hasFactoryMarriage = false;
		Date marriageDetectionDate;
		String marriageDetectedBy;
		String marriageDescription;
	}

	public Ticket() {
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public String getResponsibleSupplier() {
		return responsibleSupplier;
	}

	public void setResponsibleSupplier(String responsibleSupplier) {
		this.responsibleSupplier = responsibleSupplier;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getStatusChangeDate() {
		return statusChangeDate;
	}

	public void setStatusChangeDate(String statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean closed) {
		isClosed = closed;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public String getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	public boolean hasFactoryMarriage() {
		return hasFactoryMarriage;
	}

	public void setHasFactoryMarriage(boolean hasFactoryMarriage) {
		this.hasFactoryMarriage = hasFactoryMarriage;
	}

	public Date getMarriageDetectionDate() {
		return marriageDetectionDate;
	}

	public void setMarriageDetectionDate(Date marriageDetectionDate) {
		this.marriageDetectionDate = marriageDetectionDate;
	}

	public String getMarriageDetectedBy() {
		return marriageDetectedBy;
	}

	public void setMarriageDetectedBy(String marriageDetectedBy) {
		this.marriageDetectedBy = marriageDetectedBy;
	}

	public String getMarriageDescription() {
		return marriageDescription;
	}

	public void setMarriageDescription(String marriageDescription) {
		this.marriageDescription = marriageDescription;
	}

	public String getMarriagePhotoUrl() {
		return marriagePhotoUrl;
	}

	public void setMarriagePhotoUrl(String marriagePhotoUrl) {
		this.marriagePhotoUrl = marriagePhotoUrl;
	}

	public ObjectName getObjectName() {
		return objectName;
	}

	public void setObjectName(ObjectName objectName) {
		this.objectName = objectName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void closeApplication(Date closingDate, String closedBy) {
		setClosed(true);
		setClosingDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(closingDate));
		setClosedBy(closedBy);
	}

	public void detectFactoryMarriage(Date marriageDetectionDate, String marriageDetectedBy,
			String marriageDescription, String marriagePhotoUrl) {
		setHasFactoryMarriage(true);
		setMarriageDetectionDate(marriageDetectionDate);
		setMarriageDetectedBy(marriageDetectedBy);
		setMarriageDescription(marriageDescription);
		setMarriagePhotoUrl(marriagePhotoUrl);
	}

	@Override
	public String toString() {
		return "main.java.Application{" +
				"id=" + id +
				", name='" + name + '\'' +
				", materials=" + materials +
				", creationDate=" + creationDate +
				", status=" + status +
				", responsibleSupplier='" + responsibleSupplier + '\'' +
				", deliveryDate=" + deliveryDate +
				", statusChangeDate=" + statusChangeDate +
				", isClosed=" + isClosed +
				", closingDate=" + closingDate +
				", closedBy='" + closedBy + '\'' +
				", hasFactoryMarriage=" + hasFactoryMarriage +
				", marriageDetectionDate=" + marriageDetectionDate +
				", marriageDetectedBy='" + marriageDetectedBy + '\'' +
				", marriageDescription='" + marriageDescription + '\'' +
				", marriagePhotoUrl='" + marriagePhotoUrl + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ticket ticket = (Ticket) o;
		return id.equals(ticket.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
