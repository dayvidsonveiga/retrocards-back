package br.com.vemser.retrocards.entity;

import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "retrospectivas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class RetrospectiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "retrospectiva_seq")
    @SequenceGenerator(name = "retrospectiva_seq", sequenceName = "seq_retrospectiva", allocationSize = 1)
    @Column(name = "id_retrospective")
    private Integer idRetrospective;

    @Column(name = "title")
    private String title;

    @Column(name = "occurred_date")
    private LocalDateTime occurredDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RetrospectiveStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sprint", referencedColumnName = "id_sprint")
    private SprintEntity sprint;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "retrospective", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemRetrospectiveEntity> items;
}
