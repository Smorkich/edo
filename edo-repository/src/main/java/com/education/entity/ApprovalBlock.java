package com.education.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

/**
 * Сущность представляющая собой блок c участниками или подписантами согласования
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "approval_block")
public class ApprovalBlock extends BaseEntity {

    /**
     * Содержит ссылку на лист согласования, за которым закреплён блок
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_id", referencedColumnName = "id")
    private Approval approval;

    /**
     * Участник перенаправивший лист согласования
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_who_redirected_approval", referencedColumnName = "id")
    private Member memberWhoRedirectedApproval;

    /**
     * Номер по порядку согласования и порядку отображения на UI
     */
    @Column(name = "ordinal_number")
    private int ordinalNumber;

    /**
     * Участники (пустые, если это блок с подписантами)
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "approvalblock_member",
            joinColumns = @JoinColumn(name = "approvalblock_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    private Collection<Member> participants;

    /**
     * Подписанты (пустой, если это блок с участниками)
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "approvalblock_member",
            joinColumns = @JoinColumn(name = "approvalblock_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    private Collection<Member> signatories;

}
