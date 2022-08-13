package br.com.vemser.retrocards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sprints")
public class SprintEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sprint_seq")
    @SequenceGenerator(name = "sprint_seq", sequenceName = "seq_sprint", allocationSize = 1)
    @Column(name = "id_sprint")
    private Integer idSprint;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_sprint",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_sprint")
    )
    private Set<UserEntity> users;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sprint", cascade = CascadeType.MERGE)
    private Set<KudoBoxEntity> kudoboxs;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sprint", cascade = CascadeType.MERGE)
    private Set<RetrospectiveEntity> retrospectives;
}
