package com.example.application.core;

import com.example.application.HasId;
import com.example.application.model.AbstractNamedEntity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "materials")
public class Material extends AbstractNamedEntity implements HasId {

  @Id
  @Access(AccessType.FIELD)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotNull
  @Range(min = 0, max = 5000)
  //количество
  private int quantity;

  @NotBlank
  @Size(min = 2, max = 120)
  //характеристики
  private String characteristics;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ticket_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  //Это поле нужно для вытягивания ID заявки.
  private Ticket ticket;

  public Material(String name, int quantity, String characteristics) {
    this.name = name;
    this.quantity = quantity;
    this.characteristics = characteristics;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public Material() {
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getCharacteristics() {
    return characteristics;
  }

  public void setCharacteristics(String characteristics) {
    this.characteristics = characteristics;
  }

  public Ticket getTicket() {
    return ticket;
  }

  public void setTicket(Ticket ticket) {
    this.ticket = ticket;
  }

  @Override
  public String toString() {
    return "Material{" +
            "id=" + id +
            "quantity=" + quantity +
            ", characteristics='" + characteristics + '\'' +
            ", ticket=" + ticket +
            ", id=" + id +
            ", name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Material material = (Material) o;
    return id.equals(material.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
