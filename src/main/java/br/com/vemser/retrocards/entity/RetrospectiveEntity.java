package br.com.vemser.retrocards.entity;

import br.com.vemser.retrocards.enums.RetrospectiveStatus;
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
@Entity(name = "retrospectivas")
public class RetrospectiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "retrospectiva_seq")
    @SequenceGenerator(name = "retrospectiva_seq", sequenceName = "seq_retrospectiva", allocationSize = 1)
    @Column(name = "id_retrospective")
    private Integer idRetrospective;

    @Column(name = "title")
    private String title;

    @Column(name = "occurred_date")
    private LocalDate occurredDate;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private RetrospectiveStatus status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "retrospective", cascade = CascadeType.MERGE)
    private Set<ItemRetrospectiveEntity> items;
}
