package com.example.jpa.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "locker")
public class SportsMember {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    // @OneToOne(optional = false)// , fetch = FetchType.LAZY 
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;
}
