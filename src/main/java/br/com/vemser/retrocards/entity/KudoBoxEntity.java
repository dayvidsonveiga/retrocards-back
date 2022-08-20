package br.com.vemser.retrocards.entity;

import br.com.vemser.retrocards.enums.KudoStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "kudo_boxs")
public class KudoBoxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kudobox_seq")
    @SequenceGenerator(name = "kudobox_seq", sequenceName = "seq_kudobox", allocationSize = 1)
    @Column(name = "id_kudo_box")
    private Integer idKudoBox;

    @Column(name = "title")
    private String title;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private KudoStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sprint", referencedColumnName = "id_sprint")
    private SprintEntity sprint;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kudobox", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<KudoCardEntity> kudocards;
}
