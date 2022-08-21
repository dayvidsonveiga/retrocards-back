package br.com.vemser.retrocards.entity;

import br.com.vemser.retrocards.enums.SprintStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "sprints")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class SprintEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sprint_seq")
    @SequenceGenerator(name = "sprint_seq", sequenceName = "seq_sprint", allocationSize = 1)
    @Column(name = "id_sprint")
    private Integer idSprint;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SprintStatus status;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_sprint",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_sprint")
    )
    private Set<UserEntity> users;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<KudoBoxEntity> kudoboxs;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RetrospectiveEntity> retrospectives;
}
