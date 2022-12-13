package com.education.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.Comment;

import java.util.Date;
/**
* @author Usolkin Dmitry
* Сущность Department расширяет сущность BaseEntity
 */
@Entity
@Table(name = "department")
public class Department extends BaseEntity {
    /* Поле короткое имя*/
    @Column(name = "short_name")
    private String shortName;
    /* Поле полное имя*/
    @Column(name = "full_name")
    private String fullName;
    /* Поле адрес*/
    @Column(name = "address")
    private String address;

    /* Поле с внешним идентификатором, у себя не создаем сущность,
    скачиваем из чужого хранилища)
     */
    @Column(name = "externalId")
    private long externalId;

    /* Поле с номером телефона*/
    @Column(name = "phone")
    private int phone;

    /* Поле с вышестоящим департаментом */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Department department;

    /* Поле с датой создания */
    @Column(name="creation_date")
    private Date creationDate;

    /* Поле с датой архивирования */
    @Column(name="archived_date")
    private Date archivedDate;

    public Department() {

    }
    public Department(String shortName,
                      String fullName,
                      String address,
                      int phone,
                      Date creationDate
                      ) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.creationDate = creationDate;
    }
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getExternalId() {
        return externalId;
    }

    public void setExternalId(long externalId) {
        this.externalId = externalId;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(Date archivedDate) {
        this.archivedDate = archivedDate;
    }
    @Override
    public String toString() {
        return "Department{" +
                "shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", externalId=" + externalId +
                ", phone=" + phone +
                ", department='" + department + '\'' +
                ", creationDate=" + creationDate +
                ", archivedDate=" + archivedDate +
                '}';
    }

}
