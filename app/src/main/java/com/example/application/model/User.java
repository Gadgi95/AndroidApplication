package com.example.application.model;


/**

 Этот класс представляет объект User в базе данных приложения и помечен аннотацией JPA @Entity,
 чтобы указать, что это сохраняемый объект. Класс расширяет класс AbstractNamedEntity
 и реализует интерфейс HasIdAndEmail, он определяет несколько полей, таких как
 электронная почта, пароль, включен, зарегистрирован и роли для пользователя.
 Эти поля сопоставляются со столбцами в таблице «users» в базе данных с помощью аннотации JPA @Column.
 Этот класс также определяет несколько именованных запросов, которые можно использовать для извлечения
 и управления объектами пользователя в базе данных, например «User.delete»,
 «User.getByEmail» и «User.getAllSorted». Эти именованные запросы определяются
 с помощью аннотаций @NamedQueries и @NamedQuery.
 Он также использует несколько других аннотаций, таких как @Cache, @ElementCollection,
 @Enumerated, @Column, @Fetch, @BatchSize, @JoinColumn и @OnDelete, которые настраивают
 способ сохранения и загрузки класса из базы данных.
 Класс также использует аннотации JSR-303 Bean Validation, такие как @NotBlank, @Size,
 @Email и @NoHtml, для проверки полей перед сохранением в базе данных.
 Он также использует аннотации Джексона, такие как @JsonProperty, для настройки того,
 как объект сериализуется при возврате как часть API JSON, а также пользовательские аннотации,
 такие как @NoHtml и @Range, для проверки и маскирования данных.
 */

import com.example.application.HasId;
import com.example.application.ValidEmailAddress;
import com.example.application.View;
import com.example.application.core.Ticket;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import com.example.application.HasIdAndEmail;
import com.example.application.util.validation.NoHtml;

import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Этот блок кода использует Hibernate для определения именованных запросов для класса «Пользователь» в приложении Java.
 Определены следующие именованные запросы:

 *DELETE: этот запрос используется для удаления пользователя из базы данных по его идентификатору.
 Синтаксис этого запроса следующий: DELETE FROM User u WHERE u.id=:id

 * BY_EMAIL: этот запрос используется для получения отдельного пользователя из базы данных по его адресу электронной почты,
 а также для получения его ролей. Синтаксис этого запроса следующий:
 SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1

 * ALL_SORTED: этот запрос используется для извлечения всех пользователей из базы данных в порядке их имени и электронной почты.
 Синтаксис этого запроса следующий: SELECT u FROM User u ORDER BY u.name, u.email

 * Кроме того, этот блок кода также применяет кэширование к классу «Пользователь», используя нестрогую стратегию чтения-записи.
 * Это означает, что несколько потоков могут читать из кеша одновременно,
 * но только один поток может одновременно записывать в кеш.
 * Это может помочь повысить производительность приложения за счет уменьшения количества запросов к базе данных,
 * которые необходимо выполнить.
 * Важно отметить, что для того, чтобы использовать эти именованные запросы, они должны вызываться в соответствующем контексте,
 * например, в DAO или сервисном уровне приложения.
 * Кроме того, класс должен быть аннотирован @Entity и иметь правильные сопоставления с таблицей базы данных.
 */

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "users")
public class User extends AbstractNamedEntity implements HasId, HasIdAndEmail, ValidEmailAddress {

  @Id
  @Access(AccessType.FIELD)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "email", nullable = false, unique = true)
  @Email
  @NotBlank
  @Size(max = 128)
  @NoHtml(groups = {View.Web.class})  // https://stackoverflow.com/questions/17480809
  private String email;

  @Column(name = "password", nullable = false)
  @NotBlank
  @Size(min = 5, max = 128)
  // https://stackoverflow.com/a/12505165/548473
  private String password;

  @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
  @NotNull
  private Date registered = new Date();

  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
          uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles")})
  @Column(name = "role")
  @ElementCollection(fetch = FetchType.EAGER)
  @Fetch(FetchMode.SUBSELECT)
  @BatchSize(size = 200)
  @JoinColumn(name = "user_id") //https://stackoverflow.com/a/62848296/548473
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Role> roles;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  @OrderBy("creationDate DESC")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Ticket> tickets;

  public User() {
  }

  public User(Integer id) {
    this.id = id;
  }

  //ToDo Поменять на метод CreateCopy
  public User(User u, Integer id) {
    this(u.id, u.name, u.email, u.password, u.roles, u.registered);
  }

  public User(Integer id, String name, String email, String password, Set<Role> roles, Date registered) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.roles = roles;
    this.registered = registered;
  }

  //ToDo Совместить 2 конструктора т.к. код дублируется с конструктором выше
  public User(Integer id, String name, String email, String password, Date registered) {
    super(name);
    this.id = id;
    this.email = email;
    this.password = password;
    this.registered = registered;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getRegistered() {
    return registered;
  }

  public void setRegistered(Date registered) {
    this.registered = registered;
  }

  //Пароль задается только через конструктор?
  public String getPassword() {
    return password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", email=" + email +
            ", name=" + name +
            ", roles=" + roles +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}